package tests.menadzer;

import org.junit.Test;

import com.opencsv.CSVWriter;

import entiteti.Menadzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;

import manager.MenadzerManager;

public class TestMenadzerManager {
    private MenadzerManager menadzerMng;


    @Before
    public void setup(){
        menadzerMng = new MenadzerManager("./data/menadzeritest.csv");
        menadzerMng.registracijaMenadzer("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka", "0","0" );
        menadzerMng.registracijaMenadzer("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2", "2","5" );
    }

    @Test
    public void testDodatiMenadzeri(){
        assertEquals(menadzerMng.getMenadzeri().size(),2);
    }

    @Test
    public void testRegistracijaMenadzer(){
        menadzerMng.obrisi("korisnicko_ime");
        assertEquals(1,menadzerMng.getMenadzeri().size());
        menadzerMng.obrisi("korisnicko_ime2");
        assertEquals(0,menadzerMng.getMenadzeri().size());
    }

    @Test 
    public void testVracanje(){
        assertEquals(menadzerMng.vratiMenadzera("korisnicko_ime"), menadzerMng.vratiMenadzeraid(0));
    }

    //test zbog logina
    @Test
    public void testContains(){
        assertEquals(true, menadzerMng.inMenadzeri("korisnicko_ime","lozinka"));
    }

    @Test 
    public void testPlate(){
        double expected = 70000.0;
        double tolerance = 0.0001;
        assertEquals(expected, menadzerMng.plate(),tolerance);
    }

    @Test
    public void testUpisFajl() throws IOException{

        MenadzerManager k = new MenadzerManager("data/menadzeritest.csv");
        k.registracijaMenadzer("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka" ,"2","5");
        k.registracijaMenadzer("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2","2","5");
        k.registracijaMenadzer("ime3","prezime3","065222222","adresa 2","ZENSKI","korisnicko_ime3", "lozinka3","2","5");
        k.registracijaMenadzer("ime4","prezime4","065222222","adresa 2","ZENSKI","korisnicko_ime4", "lozinka4","2","5");
        
        FileWriter fw = new FileWriter("data/menadzeritest.csv");
        CSVWriter writer = new CSVWriter(fw);
        k.WriteDataFile("data/menadzeritest.csv", writer);
        writer.close();

        MenadzerManager k1 = new MenadzerManager("data/menadzeritest.csv");
        k1.loadData();
        ArrayList<Menadzer> menadzeri = k.getMenadzeri();
        ArrayList<Menadzer> menadzeri1 = k1.getMenadzeri();
        assertEquals(menadzeri.size(), menadzeri1.size());
    
        for (int i = 0; i < menadzeri.size(); i++) {
            Menadzer menadzer = menadzeri.get(i);
            Menadzer menadzer1 = menadzeri1.get(i);
            
            assertEquals(menadzer.getIme(), menadzer1.getIme());
            assertEquals(menadzer.getPrezime(), menadzer1.getPrezime());
            assertEquals(menadzer.getBroj(), menadzer1.getBroj());
            assertEquals(menadzer.getAdresa(), menadzer1.getAdresa());
            assertEquals(menadzer.getPol(), menadzer1.getPol());
            assertEquals(menadzer.getKorisnickoIme(), menadzer1.getKorisnickoIme());
            assertEquals(menadzer.getLozinka(), menadzer1.getLozinka());
            assertEquals(menadzer.getBonus(), menadzer1.getBonus(),0.0001);
            assertEquals(menadzer.getPlata(), menadzer1.getPlata(),0.0001);
            assertEquals(menadzer.getStaz(), menadzer1.getStaz());
            assertEquals(menadzer.getNivoStrucneSpreme(), menadzer1.getNivoStrucneSpreme());

        }

    }
    

    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/menadzeritest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
