package tests.menadzer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import entiteti.VrstaTretmana;
import manager.VrstaTretmanaManager;

public class TestVrstaTretmanaManager {
    private VrstaTretmanaManager v;

    @Before
    public void setup(){
         v = new VrstaTretmanaManager("./data/vrstetretmanatest.csv");
        v.dodajVrstu("naziv", "0", "1500", "55");
        v.dodajVrstu("naziv2", "1", "2500", "65");

    }

    @Test
    public void testDodaj(){
        assertEquals( 2, v.getVrsteTretmana().size());
    }

    @Test
    public void testObrisi(){
        v.obrisiVrstuTretmana("naziv");
        assertEquals( 1, v.getVrsteTretmana().size());
    }

    @Test
    public void testObrisiVise(){
        java.util.ArrayList<VrstaTretmana> vrste = v.getVrsteTretmana();
        v.obrisiVrsteTretmana(vrste);
        assertEquals( 0, v.getVrsteTretmana().size());
    }

    @Test
    public void testContains(){
        assertEquals(true, v.inVrsteTretmana("naziv"));
    }

    @Test
    public void testStrings(){
        String[] niz = {"0;naziv;55;1500.0","1;naziv2;65;2500.0"};
        assertArrayEquals(niz,v.stringsVrsteTretmana());
    }

    @Test
    public void testVratiCenu(){
        assertEquals(1500.0, v.getCenaVrsta(0),0.000001);
    }

    @Test
    public void testFajl() throws IOException{
        VrstaTretmanaManager vt = new VrstaTretmanaManager("./data/vrstetretmanatest.csv");
        vt.dodajVrstu("naziv", "0", "1500", "55");
        vt.dodajVrstu("naziv2", "1", "2500", "65");


        FileWriter fw = new FileWriter("./data/vrstetretmanatest.csv");
        CSVWriter writer = new CSVWriter(fw);
        vt.WriteDataFile("./data/vrstetretmanatest.csv", writer);
        writer.close();

        VrstaTretmanaManager vt1 = new VrstaTretmanaManager("./data/vrstetretmanatest.csv");
        vt1.loadData();

        ArrayList<VrstaTretmana> vrste = vt.getVrsteTretmana();
        ArrayList<VrstaTretmana> vrste1 = vt1.getVrsteTretmana();
        assertEquals(vrste.size(),vrste1.size());

        for(int i = 0; i < vrste.size(); i++){
            VrstaTretmana vrsta = vrste.get(i);
            VrstaTretmana vrsta1 = vrste1.get(i);

            assertEquals(vrsta.getNaziv(),vrsta1.getNaziv());
            assertEquals(vrsta.getId(),vrsta1.getId());
            assertEquals(vrsta.getTrajanje(),vrsta1.getTrajanje());
            assertEquals(vrsta.getCena(),vrsta1.getCena(),0.0001);
        }
    }

    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/vrstetretmanatest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
