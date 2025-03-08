package manager;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

import entiteti.Salon;

public class SalonInfoManager {
    private String fileSalonInfo;
    private Salon salonInfo;

    public SalonInfoManager(String file){
        this.fileSalonInfo = file;
        this.salonInfo = new Salon();
    }

    public Salon getSalonInfo(){return salonInfo;}
    public void setSalonInfo(String naziv, LocalTime pocetak,LocalTime kraj, double kasa, double prag,double bonus, int brtretmana){
        this.salonInfo.setNaziv(naziv);
        this.salonInfo.setPocetak(pocetak);
        this.salonInfo.setKraj(kraj);
        this.salonInfo.setStanjeUKasi(kasa);
        this.salonInfo.setPrag(prag);
        this.salonInfo.setBonus(bonus);
        this.salonInfo.setBrTretmana(brtretmana);

    }

    public void kreirajizmeniSalon(String ime, String pocetak, String kraj,double kasa, double prag, double bonus, int brtretmana){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime p = LocalTime.parse(pocetak, formatter);
        LocalTime k = LocalTime.parse(kraj, formatter);
        this.setSalonInfo(ime, p, k,kasa,prag,bonus,brtretmana);

        try{
            FileWriter fw = new FileWriter(fileSalonInfo);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileSalonInfo, writer);
            writer.close();
        }catch(IOException e){}
    }

    public ArrayList<LocalTime> getTermini(){
        LocalTime pocetak = salonInfo.getPocetak(); 
        LocalTime kraj = salonInfo.getKraj(); 

        ArrayList<LocalTime> sviTermini = new ArrayList<>();

        LocalTime temp = pocetak;
        while (temp.isBefore(kraj)) {
            sviTermini.add(temp);
            temp = temp.plusHours(1);
        }

        return sviTermini;
    }

    public void setPrag(double vrednost){
        this.salonInfo.setPrag(vrednost);
        try{
            FileWriter fw = new FileWriter(fileSalonInfo);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileSalonInfo, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void dodajUKasu(double iznos){
        double ukupno = salonInfo.getStanjeUKasi();
        ukupno +=iznos;
        salonInfo.setStanjeUKasi(ukupno);
        try{
            FileWriter fw = new FileWriter(fileSalonInfo);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileSalonInfo, writer);
            writer.close();
        }catch(IOException e){}
    }

    public  void WriteDataFile(String path, CSVWriter writer){

        String str1 = String.valueOf(salonInfo.getPocetak());
        String str2 = String.valueOf(salonInfo.getKraj());
        String str3 = String.valueOf(salonInfo.getStanjeUKasi());
        String str4 = String.valueOf(salonInfo.getPrag());
        String str5 = String.valueOf(salonInfo.getBonus());
        String str6 = String.valueOf(salonInfo.getBrTretmana());
        String item[] = {salonInfo.getNaziv(),str1,str2,str3,str4,str5,str6};
        writer.writeNext(item);
        
    }

    public boolean loadData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.fileSalonInfo));
            String line = null;
            while((line = br.readLine()) != null){
                
                String polja[]= line.split(",");
                for(int i = 0; i< polja.length ; i++){
                    polja[i] = polja[i].replaceAll("\"", "");
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                this.setSalonInfo(polja[0],LocalTime.parse(polja[1], formatter) , LocalTime.parse(polja[2], formatter), Double.parseDouble(polja[3]), Double.parseDouble(polja[4]), Double.parseDouble(polja[5]), Integer.parseInt(polja[6]));
            }
            br.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public void podesiBonus(double v){
        salonInfo.setBonus(v);
        try{
            FileWriter fw = new FileWriter(fileSalonInfo);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileSalonInfo, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void podesiBrTretmana(int br){
        salonInfo.setBrTretmana(br);
        try{
            FileWriter fw = new FileWriter(fileSalonInfo);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileSalonInfo, writer);
            writer.close();
        }catch(IOException e){}
    }
}
