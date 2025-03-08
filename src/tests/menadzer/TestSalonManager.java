package tests.menadzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVWriter;

import entiteti.Salon;
import manager.SalonInfoManager;

public class TestSalonManager {
    private SalonInfoManager s;
    @Before
    public void setup(){
        s = new SalonInfoManager("./data/salontest.csv");
        s.kreirajizmeniSalon("ime", "10:00", "20:00", 0.0, 0.0, 0.0,0);
    }

    // @Test
    // public void testKreiraj(){
    //     assertEquals(1, s.getSalonInfo().get);
    // }

    @Test
    public void testKasa(){
        s.dodajUKasu(500);
        assertEquals(500.0,s.getSalonInfo().getStanjeUKasi(),0.0001);
    }

    @Test
    public void testGetTermini(){
        ArrayList<LocalTime> termini= new ArrayList<>();
        termini.add(LocalTime.parse("10:00"));
        termini.add(LocalTime.parse("11:00"));
        termini.add(LocalTime.parse("12:00"));
        termini.add(LocalTime.parse("13:00"));
        termini.add(LocalTime.parse("14:00"));
        termini.add(LocalTime.parse("15:00"));
        termini.add(LocalTime.parse("16:00"));
        termini.add(LocalTime.parse("17:00"));
        termini.add(LocalTime.parse("18:00"));
        termini.add(LocalTime.parse("19:00"));

        assertEquals(termini, s.getTermini());
    }

    @Test
    public void testFajl() throws IOException{
        SalonInfoManager si = new SalonInfoManager("./data/salontest.csv");
        si.kreirajizmeniSalon("ime", "10:00", "20:00", 0.0, 0.0, 0.0,0);

        FileWriter fw = new FileWriter("./data/salontest.csv");
        CSVWriter writer = new CSVWriter(fw);
        si.WriteDataFile("./data/salontest.csv", writer);
        writer.close();

        SalonInfoManager si1 = new SalonInfoManager("./data/salontest.csv");
        si1.loadData();
        Salon salon = si.getSalonInfo();
        Salon salon1 = si1.getSalonInfo();
        assertEquals(salon.getBonus(),salon1.getBonus(),0.0001);
        assertEquals(salon.getKraj(),salon1.getKraj());
        assertEquals(salon.getNaziv(),salon1.getNaziv());
        assertEquals(salon.getPocetak(),salon1.getPocetak());
        assertEquals(salon.getPrag(),salon1.getPrag(),0.001);
        assertEquals(salon.getStanjeUKasi(),salon1.getStanjeUKasi(),0.001);
    }
    

    @AfterClass
    public static void finished() {
        File[] fajloviZaBrisanje = {
                new File("./data/salontest.csv")
        };

        for (File fajl : fajloviZaBrisanje) {
            boolean _nesto = fajl.delete();
        }
    }
}
