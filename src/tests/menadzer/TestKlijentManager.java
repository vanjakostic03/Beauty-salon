package tests.menadzer;

import org.junit.Test;

import com.opencsv.CSVWriter;

import entiteti.Klijent;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;

import manager.KlijentManager;

public class TestKlijentManager {

    private KlijentManager klijentMng;


    @Before
    public void setup(){
        klijentMng = new KlijentManager("./data/klijentitest.csv");
        klijentMng.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka" );
        klijentMng.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2");
    }

    @Test
    public void testDodatiKlijenti(){
        assertEquals(klijentMng.getKlijenti().size(),2);
    }

    @Test
    public void testRegistracijaMenadzer(){
        klijentMng.obrisi("korisnicko_ime");
        assertEquals(1,klijentMng.getKlijenti().size());
        klijentMng.obrisi("korisnicko_ime2");
        assertEquals(0,klijentMng.getKlijenti().size());
    }

    @Test 
    public void testVracanje(){
        assertEquals(klijentMng.vratiKlijentaKorisnicko("korisnicko_ime"), klijentMng.vratiKlijentaid(0));
    }

    //test zbog logina
    @Test
    public void testContains(){
        assertEquals(true, klijentMng.inKlijenti("korisnicko_ime","lozinka"));
    }

    @Test
    public void testStrings(){
        String niz[] = {"0;ime;prezime", "1;ime2;prezime2"};
        assertArrayEquals(niz, klijentMng.stringKlijenti());
    }

    //test za povracaj novca

    @Test
    public void testUpisFajl() throws IOException{

        KlijentManager k = new KlijentManager("data/klijentitest.csv");
        k.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka" );
        k.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2");
        k.registracija("ime3","prezime3","065222222","adresa 2","ZENSKI","korisnicko_ime3", "lozinka3");
        k.registracija("ime4","prezime4","065222222","adresa 2","ZENSKI","korisnicko_ime4", "lozinka4");
        
        FileWriter fw = new FileWriter(k.getPath());
        CSVWriter writer = new CSVWriter(fw);
        k.WriteDataFile(k.getPath(), writer);
        writer.close();

        KlijentManager k1 = new KlijentManager("data/klijentitest.csv");
        k1.loadData();
        ArrayList<Klijent> klijenti1 = k.getKlijenti();
        ArrayList<Klijent> klijenti2 = k1.getKlijenti();
        assertEquals(klijenti1.size(), klijenti2.size());
    
        for (int i = 0; i < klijenti1.size(); i++) {
            Klijent klijent1 = klijenti1.get(i);
            Klijent klijent2 = klijenti2.get(i);
            // klijent2.setIme("nesto");
            // assertEquals(klijent1,klijent2);

            assertEquals(klijent1.getIme(), klijent2.getIme());
            assertEquals(klijent1.getPrezime(), klijent2.getPrezime());
            assertEquals(klijent1.getBroj(), klijent2.getBroj());
            assertEquals(klijent1.getAdresa(), klijent2.getAdresa());
            assertEquals(klijent1.getPol(), klijent2.getPol());
            assertEquals(klijent1.getKorisnickoIme(), klijent2.getKorisnickoIme());
            assertEquals(klijent1.getLozinka(), klijent2.getLozinka());

        }

    }
    
    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/klijentitest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}


