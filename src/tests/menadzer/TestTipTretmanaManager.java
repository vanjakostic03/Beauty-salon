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

import entiteti.TipTretmana;
import manager.TipTretmanaManager;

public class TestTipTretmanaManager {
    //private static final String ArrayList = null;
    private TipTretmanaManager t;
    
    @Before
    public void setup(){
        t = new TipTretmanaManager("./data/tiptretmanatest.csv");
        t.kreirajTipTretmana("naziv");
        t.kreirajTipTretmana("naziv2");
        t.kreirajTipTretmana("naziv3");
    }

    @Test
    public void testKreiraj(){
        assertEquals( t.getTipoviTretmana().size(),3);
    }

    @Test
    public void testObrisi(){
        t.obrisiTipTretmana("naziv");
        t.obrisiTipTretmana("naziv2");
        assertEquals( t.getTipoviTretmana().size(),1);
    }

    // @Test
    // public void testVratiTipId(){

    // }

    @Test
    public void testStrings(){
        String niz[]= {"0;naziv","1;naziv2","2;naziv3"};
        assertArrayEquals(niz, t.stringsTipoviTretmana());
    }

    @Test
    public void testFajl() throws IOException{
        TipTretmanaManager tt = new TipTretmanaManager("./data/tiptretmanatest.csv");
        tt.kreirajTipTretmana("naziv");
        tt.kreirajTipTretmana("naziv2");

        FileWriter fw = new FileWriter("./data/tiptretmanatest.csv");
        CSVWriter writer = new CSVWriter(fw);
        tt.WriteDataFile("./data/tiptretmanatest.csv", writer);
        writer.close();

        TipTretmanaManager tt1 = new TipTretmanaManager("./data/tiptretmanatest.csv");
        tt1.loadData();

        ArrayList<TipTretmana> tipovi = tt.getTipoviTretmana();
        ArrayList<TipTretmana> tipovi1 = tt1.getTipoviTretmana();
        assertEquals(tipovi.size(),tipovi1.size());

        for(int i = 0; i < tipovi.size(); i++){
            TipTretmana tip = tipovi.get(i);
            TipTretmana tip1 = tipovi1.get(i);

            assertEquals(tip.getNaziv(),tip1.getNaziv());
        }
    
    }


    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/tiptretmanatest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
