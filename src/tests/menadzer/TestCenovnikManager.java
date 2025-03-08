package tests.menadzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVWriter;

import entiteti.Cenovnik;
import manager.CenovnikManager;

public class TestCenovnikManager {
    private CenovnikManager c;

    @Before
    public void setup(){
        c = new CenovnikManager("./data/cenovniktest.csv");
        c.dodajUCenovnik(0,1000);
        c.dodajUCenovnik(1,1500);
        c.dodajUCenovnik(2,1500);
        c.dodajUCenovnik(3,1500);
    }


    @Test
    public void testDodavanje(){
        assertEquals(4, c.getCene().getCeneCenovnik().size());
    }

    @Test
    public void testBrisanje(){
        c.obrisiIzCenovnika(0);
        assertEquals(3, c.getCene().getCeneCenovnik().size());
    }

    @Test
    public void testBrisanjeVise(){
        Set<Integer> kljucevi = new HashSet<Integer>();
        kljucevi.add(1);
        kljucevi.add(2);
        c.obrisiViseCena(kljucevi);
        assertEquals(2, c.getCene().getCeneCenovnik().size());

    }

    @Test 
    public void testInCene(){
        assertEquals(true,c.inCene(0));
        assertEquals(true,c.inCene(1));
        assertEquals(true,c.inCene(2));
        assertEquals(true,c.inCene(3));
    }

    @Test
    public void testFajl() throws IOException{
        CenovnikManager c1 = new CenovnikManager("data/cenovniktest.csv");
        c1.dodajUCenovnik(0,2000);
        c1.dodajUCenovnik(1,2500);

        FileWriter fw = new FileWriter("data/cenovniktest.csv");
        CSVWriter writer = new CSVWriter(fw);
        c1.WriteDataFile("data/cenovniktest.csv", writer);
        writer.close();

        CenovnikManager c2 = new CenovnikManager("data/cenovniktest.csv");
        c2.loadData();
        Cenovnik cenovnik1 = c1.getCene();
        Cenovnik cenovnik2 = c2.getCene();
        assertEquals(c1.getCene().getCeneCenovnik().size(),c2.getCene().getCeneCenovnik().size());
        
        // for (Map.Entry<String, Integer> entry : cenovnik1.entrySet()) {
        //     String key = entry.getKey();
        //     Integer value = entry.getValue();
        // }
    }
    
    // @Test
    // public void ispisi(){

    // }

    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/cenovniktest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
