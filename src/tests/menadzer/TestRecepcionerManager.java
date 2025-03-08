package tests.menadzer;

import org.junit.Test;

import com.opencsv.CSVWriter;

import entiteti.Recepcioner;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;

import manager.RecepcionerManager;

public class TestRecepcionerManager {
    private RecepcionerManager recepcionerMng;


    @Before
    public void setup(){
        recepcionerMng = new RecepcionerManager("./data/recepcioneritest.csv");
        recepcionerMng.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka", "0","0" );
        recepcionerMng.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2", "2","5" );
    }

    @Test
    public void testDodatirecepcioneri(){
        assertEquals(recepcionerMng.getRecepcioneri().size(),2);
    }

    @Test
    public void testRegistracijaMenadzer(){
        recepcionerMng.obrisi("korisnicko_ime");
        assertEquals(1,recepcionerMng.getRecepcioneri().size());
        recepcionerMng.obrisi("korisnicko_ime2");
        assertEquals(0,recepcionerMng.getRecepcioneri().size());
    }

    // @Test 
    // public void testVracanje(){
    //     assertEquals(recepcionerMng.vratiRecepcioneraid("korisnicko_ime"), menadzerMng.vratiRecepcioneraid(0));
    // }

    //test zbog logina
    @Test
    public void testContains(){
        assertEquals(true, recepcionerMng.inRecepcioneri("korisnicko_ime","lozinka"));
    }

    @Test 
    public void testPlate(){
        double expected = 35000.0;
        double tolerance = 0.0001;
        assertEquals(expected, recepcionerMng.plate(),tolerance);
    }

    @Test
    public void testUpisFajl() throws IOException{

        RecepcionerManager k = new RecepcionerManager("data/recepcioneritest.csv");
        k.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka" ,"2","5");
        k.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2","2","5");
        k.registracija("ime3","prezime3","065222222","adresa 2","ZENSKI","korisnicko_ime3", "lozinka3","2","5");
        k.registracija("ime4","prezime4","065222222","adresa 2","ZENSKI","korisnicko_ime4", "lozinka4","2","5");
        
        FileWriter fw = new FileWriter("data/recepcioneritest.csv");
        CSVWriter writer = new CSVWriter(fw);
        k.WriteDataFile("data/recepcioneritest.csv", writer);
        writer.close();

        RecepcionerManager k1 = new RecepcionerManager("data/recepcioneritest.csv");
        k1.loadData();
        ArrayList<Recepcioner> recepcioneri = k.getRecepcioneri();
        ArrayList<Recepcioner> recepcioneri1 = k1.getRecepcioneri();
        assertEquals(recepcioneri.size(), recepcioneri1.size());
    
        for (int i = 0; i < recepcioneri.size(); i++) {
            Recepcioner recepcioner = recepcioneri.get(i);
            Recepcioner recepcioner1 = recepcioneri1.get(i);
            
            assertEquals(recepcioner.getIme(), recepcioner1.getIme());
            assertEquals(recepcioner.getPrezime(), recepcioner1.getPrezime());
            assertEquals(recepcioner.getBroj(), recepcioner1.getBroj());
            assertEquals(recepcioner.getAdresa(), recepcioner1.getAdresa());
            assertEquals(recepcioner.getPol(), recepcioner1.getPol());
            assertEquals(recepcioner.getKorisnickoIme(), recepcioner1.getKorisnickoIme());
            assertEquals(recepcioner.getLozinka(), recepcioner1.getLozinka());
            assertEquals(recepcioner.getBonus(), recepcioner1.getBonus(),0.0001);
            assertEquals(recepcioner.getPlata(), recepcioner1.getPlata(),0.0001);
            assertEquals(recepcioner.getStaz(), recepcioner1.getStaz());
            assertEquals(recepcioner.getNivoStrucneSpreme(), recepcioner1.getNivoStrucneSpreme());

        }

    }
    

    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/recepcioneritest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
