package tests.menadzer;

import org.junit.Test;

import com.opencsv.CSVWriter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;

import manager.KozmeticarManager;
import entiteti.Kozmeticar;

public class TestKozmeticarManager {
    private KozmeticarManager kozmeticarMng;


    @Before
    public void setup(){
        String tretmani1[] = {"0","1"};
        String tretmani2[] = {"0"};
        kozmeticarMng = new KozmeticarManager("./data/kozmeticaritest.csv");
        kozmeticarMng.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka", "0","0",tretmani1 );
        kozmeticarMng.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2", "2","5",tretmani2 );
    }

    @Test
    public void testDodatiMenadzeri(){
        assertEquals(kozmeticarMng.getKozmeticari().size(),2);
    }

    @Test
    public void testRegistracijaMenadzer(){
        kozmeticarMng.obrisi("korisnicko_ime");
        assertEquals(1,kozmeticarMng.getKozmeticari().size());
        kozmeticarMng.obrisi("korisnicko_ime2");
        assertEquals(0,kozmeticarMng.getKozmeticari().size());
    }

    @Test 
    public void testVracanje(){
        assertEquals(kozmeticarMng.vratiKozmeticaraKorisnicko("korisnicko_ime"), kozmeticarMng.vratiKozmeticaraid(0));
    }

    //test zbog logina
    @Test
    public void testContains(){
        assertEquals(true, kozmeticarMng.inKozmeticari("korisnicko_ime","lozinka"));
    }

    @Test 
    public void testPlate(){
        double expected = 49000.0;
        double tolerance = 0.0001;
        assertEquals(expected, kozmeticarMng.plate(),tolerance);
    }

    @Test 
    public void testFajl(){
        
    }

    @Test
    public void testStrings(){
        String niz[] = {"0;ime;prezime", "1;ime2;prezime2"};
        assertArrayEquals(niz, kozmeticarMng.stringKozmeticari(kozmeticarMng.getKozmeticari()));
    }

    @Test
    public void testObuceniKozmeticari(){
        int tipid = 0;
        ArrayList<entiteti.Kozmeticar> svi = kozmeticarMng.getKozmeticari();
        assertArrayEquals(kozmeticarMng.stringKozmeticari(svi), kozmeticarMng.obuceniKozmeticari(tipid));
    }
    
    @Test
    public void testUpisFajl() throws IOException{

        KozmeticarManager k = new KozmeticarManager("data/kozmeticaritest.csv");
        String[] tretmani = {"0","1"};
        k.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka" ,"2","5",tretmani);
        k.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2","2","5",tretmani);
        k.registracija("ime3","prezime3","065222222","adresa 2","ZENSKI","korisnicko_ime3", "lozinka3","2","5",tretmani);
        k.registracija("ime4","prezime4","065222222","adresa 2","ZENSKI","korisnicko_ime4", "lozinka4","2","5",tretmani);
        
        FileWriter fw = new FileWriter("data/kozmeticaritest.csv");
        CSVWriter writer = new CSVWriter(fw);
        k.WriteDataFile("data/kozmeticartest.csv", writer);
        writer.close();

        KozmeticarManager k1 = new KozmeticarManager("data/kozmeticaritest.csv");
        k1.loadData();
        ArrayList<Kozmeticar> kozmeticari = k.getKozmeticari();
        ArrayList<Kozmeticar> kozmeticari1 = k1.getKozmeticari();
        assertEquals(kozmeticari.size(), kozmeticari1.size());
    
        for (int i = 0; i < kozmeticari.size(); i++) {
            Kozmeticar kozmeticar = kozmeticari.get(i);
            Kozmeticar kozmeticar1 = kozmeticari1.get(i);
            
            assertEquals(kozmeticar.getIme(), kozmeticar1.getIme());
            assertEquals(kozmeticar.getPrezime(), kozmeticar1.getPrezime());
            assertEquals(kozmeticar.getBroj(), kozmeticar1.getBroj());
            assertEquals(kozmeticar.getAdresa(), kozmeticar1.getAdresa());
            assertEquals(kozmeticar.getPol(), kozmeticar1.getPol());
            assertEquals(kozmeticar.getKorisnickoIme(), kozmeticar1.getKorisnickoIme());
            assertEquals(kozmeticar.getLozinka(), kozmeticar1.getLozinka());
            assertEquals(kozmeticar.getBonus(), kozmeticar1.getBonus(),0.0001);
            assertEquals(kozmeticar.getPlata(), kozmeticar1.getPlata(),0.0001);
            assertEquals(kozmeticar.getStaz(), kozmeticar1.getStaz());
            assertEquals(kozmeticar.getNivoStrucneSpreme(), kozmeticar1.getNivoStrucneSpreme());
            assertEquals(kozmeticar.getTretmani(), kozmeticar1.getTretmani());

        }

    }

    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/kozmeticaritest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
