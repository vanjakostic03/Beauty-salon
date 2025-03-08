package manager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import entiteti.Cenovnik;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class CenovnikManager {
    private  String filecenovnik;
    private  Cenovnik cene;

    public CenovnikManager(String fileCenovnik){ 
        this.filecenovnik = fileCenovnik;
        this.cene = new Cenovnik();
    }
    public Cenovnik getCene(){return cene;}

    public  void dodajUCenovnik(int t, double cena){
        cene.getCeneCenovnik().put(t, cena);
        try{
            FileWriter fw = new FileWriter(filecenovnik);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filecenovnik, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void izmeniCenovnik(int t, double cena){
        cene.setCeneCenovnik(t,cena);
        try{
            FileWriter fw = new FileWriter(filecenovnik);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filecenovnik, writer);
            writer.close();
        }catch(IOException e){}
    }
    
    public  void WriteDataFile(String path, CSVWriter writer){
        HashMap<Integer,Double> cmng = cene.getCeneCenovnik();
        for (Map.Entry<Integer, Double> entry : cmng.entrySet()) {
            String str1 = String.valueOf(entry.getKey());
            String str2 = String.valueOf(entry.getValue());
            String item[] = {str1,str2};
            writer.writeNext(item);
        }
    }

    public  void obrisiIzCenovnika(int idt){
        
        HashMap<Integer,Double> cmng = cene.getCeneCenovnik();
        for (Map.Entry<Integer, Double> entry : cmng.entrySet()) {
            if(idt == entry.getKey()){
                cmng.remove(idt);
                break;
            }
        }
        try{
            FileWriter fw = new FileWriter(filecenovnik);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filecenovnik, writer);
            writer.close();
        }catch(IOException e){}

    }
    
    public void obrisiViseCena(Set<Integer> kljucevi){
        cene.getCeneCenovnik().keySet().removeAll(kljucevi);
        try{
            FileWriter fw = new FileWriter(filecenovnik);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filecenovnik, writer);
            writer.close();
        }catch(IOException e){}

    }

    public boolean loadData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.filecenovnik));
            String line = null;
            while((line = br.readLine()) != null){
                
                String polja[]= line.split(",");
                for(int i = 0; i< polja.length ; i++){
                    polja[i] = polja[i].replaceAll("\"", "");
                }
                this.cene.setCeneCenovnik(Integer.parseInt(polja[0]), Double.parseDouble(polja[1]));
            }
            br.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    

    public void ispisi(){
        HashMap<Integer,Double> cmng = cene.getCeneCenovnik();

        for (Map.Entry<Integer, Double> entry : cmng.entrySet()) {
            System.out.print(entry.getKey() + " , " + entry.getValue()+"\n");
        }
    }

    public boolean inCene(int id){
        boolean in = false;
        HashMap<Integer,Double> cmng = cene.getCeneCenovnik();

        for(Map.Entry<Integer, Double> entry : cmng.entrySet()){
            if(id==entry.getKey()){
                in = true;
                break;
            }
        }
        return in;
    }


}
