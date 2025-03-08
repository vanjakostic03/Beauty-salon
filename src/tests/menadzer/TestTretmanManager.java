package tests.menadzer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVWriter;

import entiteti.Kozmeticar;
import entiteti.Tretman;
import entiteti.VrstaTretmana;
import entiteti.Tretman.Stanje;
import manager.KozmeticarManager;
import manager.TretmanManager;
import manager.VrstaTretmanaManager;

public class TestTretmanManager {
    private TretmanManager tretmanMng;
    private VrstaTretmanaManager vrstaTretmanaMng;
    private KozmeticarManager kozmeticarMng;

    @Before
    public void setup(){
        vrstaTretmanaMng = new VrstaTretmanaManager("./data/vrstetretmanatest.csv");
        vrstaTretmanaMng.dodajVrstu("Tretman", "0", "5000.0", "55");
        kozmeticarMng = new KozmeticarManager("./data/kozmeticaritest.csv");
        String tretmani1[] = {"0","1"};
        String tretmani2[] = {"0"};
        kozmeticarMng.registracija("ime","prezime","0655474506","adresa bb","MUSKI","korisnicko_ime", "lozinka", "0","0",tretmani1 );
        kozmeticarMng.registracija("ime2","prezime2","065222222","adresa 2","ZENSKI","korisnicko_ime2", "lozinka2", "2","5",tretmani2 );
    
        tretmanMng = new TretmanManager("./data/tretmanitest.csv",vrstaTretmanaMng,kozmeticarMng);

        tretmanMng.zakaziTretman("0", "0", "2023-04-04 12:00", "0", false, 55);
        tretmanMng.zakaziTretman("1", "1", "2023-04-04 13:00", "0", false, 55);
        tretmanMng.zakaziTretman("1", "2", "2023-04-04 14:00", "0", false, 55);
    }

    @Test
    public void testZakazivanje(){
        assertEquals(tretmanMng.getTretmani().size(),3);
    }

    @Test
    public void testObrisi(){
        tretmanMng.obrisiTretman(0);
        assertEquals(2,tretmanMng.getTretmani().size());
    }

    @Test
    public void testIzvrsi(){
        tretmanMng.izvrsiTretman(0);
        assertEquals(Stanje.IZVRSEN,tretmanMng.vratiTretmanId(0).getStanje());
    }

    @Test
    public void testOtkazi(){
        tretmanMng.otkaziTretman(0, Stanje.NIJE_SE_POJAVIO);
        tretmanMng.otkaziTretman(1, Stanje.OTKAZAO_KLIJENT);
        tretmanMng.otkaziTretman(2, Stanje.OTKAZAO_SALON);

        assertEquals(Stanje.NIJE_SE_POJAVIO,tretmanMng.vratiTretmanId(0).getStanje());
        assertEquals(Stanje.OTKAZAO_KLIJENT,tretmanMng.vratiTretmanId(1).getStanje());
        assertEquals(Stanje.OTKAZAO_SALON,tretmanMng.vratiTretmanId(2).getStanje());
    }

   @Test
   public void testFajl() throws IOException{
        VrstaTretmanaManager v = new VrstaTretmanaManager("./data/vrstetretmanatest.csv");
        KozmeticarManager k = new KozmeticarManager("kozmeticaritest.csv");
        v.dodajVrstu("Tretman", "0", "5000.0", "55");

        TretmanManager t = new TretmanManager("./data/tretmanitest.csv",v,k);
        t.zakaziTretman("0", "0", "2023-04-04 12:00", "0", false, 55);
        t.zakaziTretman("1", "1", "2023-04-04 13:00", "0", false, 55);
        t.zakaziTretman("2", "2", "2023-04-04 14:00", "0", false, 55);
   

        FileWriter fw = new FileWriter("./data/tretmanitest.csv");
        CSVWriter writer = new CSVWriter(fw);
        t.WriteDataFile("./data/tretmanitest.csv", writer);
        writer.close();

        TretmanManager t1 = new TretmanManager("./data/tretmanitest.csv",v,k);
        t1.loadData();

        ArrayList<Tretman> tretmani = t.getTretmani();
        ArrayList<Tretman> tretmani1 = t1.getTretmani();
        assertEquals(tretmani.size(),tretmani1.size());

        for(int i = 0; i < tretmani.size(); i++){
            Tretman tretman = tretmani.get(i);
            Tretman tretman1 = tretmani1.get(i);

            assertEquals(tretman.getCena(),tretman1.getCena(),0.001);
            assertEquals(tretman.getIdKlijent(),tretman1.getIdKlijent());
            assertEquals(tretman.getIdKozmeticar(),tretman1.getIdKozmeticar());
            assertEquals(tretman.getIdVrstaTretman(),tretman1.getIdVrstaTretman());
            assertEquals(tretman.getStanje(),tretman1.getStanje());
            assertEquals(tretman.getTermin(),tretman1.getTermin());
            assertEquals(tretman.getTrajanje(),tretman1.getTrajanje());
        }
   }

   //IZVESTAJI


   @Test
   public void testAnaliza(){

    String[] rezultat = {"Tretman", "5000.0", "55", "3", "15000.0"};
    VrstaTretmana vt = vrstaTretmanaMng.vratiVrstaTretmanaid(0);
    String[] lista = tretmanMng.analizaVrstaTretmana(vt, LocalDate.of(2023, 4, 3), LocalDate.of(2023, 4, 5));
    assertArrayEquals(rezultat,lista);
   }

   @Test
   public void testOtkazaniTretmani(){
        String[] rezultat = {"0","0","0","0"};
        String[] lista = tretmanMng.otkazaniTretmani(LocalDate.of(2023, 4, 3), LocalDate.of(2023, 4, 5));
        assertArrayEquals(rezultat,lista);
   }

   @Test
   public void testKozmeticari(){

        Kozmeticar k1 = kozmeticarMng.vratiKozmeticaraid(0);
        tretmanMng.getTretmani().get(0).setStanje(Stanje.IZVRSEN);
        String[] lista = tretmanMng.izvestajKozmeticari(k1, LocalDate.of(2023, 4, 3), LocalDate.of(2023, 4, 5));
        String ime = k1.getIme() + " " + k1.getPrezime();
        String[] rezultati = {ime,"1","5000.0"};
        assertArrayEquals(rezultati,lista);
    }

    @Test
    public void testPrihodi(){
        double rezultat = 5000.0;
        tretmanMng.getTretmani().get(0).setStanje(Stanje.IZVRSEN);
        assertEquals(rezultat,tretmanMng.prihodi(Month.APRIL), 0.0001);
    }

    @Test
    public void testAngazovanje(){
        tretmanMng.zakaziTretman("0", "0", "2023-06-04 14:00", "0", false, 55);
        tretmanMng.getTretmani().get(0).setStanje(Stanje.IZVRSEN);
        tretmanMng.getTretmani().get(1).setStanje(Stanje.IZVRSEN);
        tretmanMng.getTretmani().get(2).setStanje(Stanje.IZVRSEN);
        tretmanMng.getTretmani().get(3).setStanje(Stanje.IZVRSEN);
        Kozmeticar k1 = kozmeticarMng.vratiKozmeticaraid(0);
        Kozmeticar k2 = kozmeticarMng.vratiKozmeticaraid(1);
        HashMap<String, Integer> angazovanje = new HashMap<String, Integer>();
        angazovanje.put(k1.getKorisnickoIme(), 1);
        angazovanje.put(k2.getKorisnickoIme(), 0);
        assertEquals(angazovanje, tretmanMng.getAngazovanje());
    }
    @Test
    public void testZastupljenost(){
        tretmanMng.zakaziTretman("0", "0", "2023-06-04 14:00", "0", false, 55);
        tretmanMng.getTretmani().get(0).setStanje(Stanje.IZVRSEN);
        tretmanMng.getTretmani().get(1).setStanje(Stanje.IZVRSEN);
        tretmanMng.getTretmani().get(2).setStanje(Stanje.IZVRSEN);
        
        HashMap<String, Integer> zastupljenost = new HashMap<String, Integer>();
        zastupljenost.put("Zakazani", 1);
        zastupljenost.put("Izvrseni", 0);
        zastupljenost.put("Otkazao klijent", 0);
        zastupljenost.put("Otkazao salon", 0);
        zastupljenost.put("Nije se pojavio", 0);
        assertEquals(zastupljenost, tretmanMng.getZastupljenost());
    }


    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/tretmanitest.csv"),
                new File("./data/vrstetretmanatest.csv"),
                new File("./data/kozmeticaritest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
