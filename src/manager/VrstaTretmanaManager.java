package manager;
import java.util.ArrayList;

import entiteti.VrstaTretmana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class VrstaTretmanaManager {
    private  String filevrstetretmana;
    private   ArrayList<VrstaTretmana> vrstetretmana;
    private  int idVrsteTretmana;

    public VrstaTretmanaManager(String filevrstetretmana){
        this.filevrstetretmana = filevrstetretmana;
        this.vrstetretmana = new ArrayList<VrstaTretmana>();
        this.idVrsteTretmana = 0; 
    }

    public ArrayList<VrstaTretmana> getVrsteTretmana(){ return vrstetretmana;}


    public int getidVrsteTretmana(){return idVrsteTretmana; }

    public  void setidVrsteTretmana(){
        if(vrstetretmana.size()!=0){
            idVrsteTretmana = vrstetretmana.get(vrstetretmana.size()-1).getId() + 1;
        }
    }

    public  VrstaTretmana dodajVrstu(String naziv, String tip , String cena, String trajanje){
        setidVrsteTretmana();
        VrstaTretmana vt = new VrstaTretmana(idVrsteTretmana, naziv, Integer.parseInt(trajanje), Double.parseDouble(cena), Integer.parseInt(tip));

        vrstetretmana.add(vt);
        try{
            FileWriter fw = new FileWriter(filevrstetretmana);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filevrstetretmana, writer);
            writer.close();
        }catch(IOException e){} 
        return vt;
    }

    public  void obrisiVrstuTretmana(String naziv){
        for(VrstaTretmana vt : vrstetretmana){
            if(vt.getNaziv().equals(naziv)){
                vrstetretmana.remove(vt);
                break;
            }
        }
        try{
            FileWriter fw = new FileWriter(filevrstetretmana);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filevrstetretmana, writer);
            writer.close();
        }catch(IOException e){} 
    }
    
    public  void obrisiVrsteTretmana(ArrayList<VrstaTretmana> vrste){
        vrstetretmana.removeAll(vrste);
        try{
            FileWriter fw = new FileWriter(filevrstetretmana);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filevrstetretmana, writer);
            writer.close();
        }catch(IOException e){} 
    }

    public void izmeniVrstu(int id, String naziv, String cena, String trajanje, String tipid){
        VrstaTretmana vt = this.vratiVrstaTretmanaid(id);
        vt.setNaziv(naziv);
        vt.setCena(Double.parseDouble(cena));
        vt.setTrajanje(Integer.parseInt(trajanje));
        vt.setTip(Integer.parseInt(tipid));
        try{
            FileWriter fw = new FileWriter(filevrstetretmana);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filevrstetretmana, writer);
            writer.close();
        }catch(IOException e){}
        
    }

    public VrstaTretmana vratiVrstaTretmanaid(int id){
        VrstaTretmana k1 = null;
        for(VrstaTretmana k: vrstetretmana){
            if(k.getId()==id){
                k1 = k;
                break;
            }
        }
        return k1;
    }

    public  void WriteDataFile(String path, CSVWriter writer){
        for(VrstaTretmana vt: vrstetretmana){
            String idString = String.valueOf(vt.getId());
            String nazivString = vt.getNaziv();
            String trajanjeString = String.valueOf(vt.getTrajanje());
            String cenaString = String.valueOf(vt.getCena());
            String tipString = String.valueOf(vt.getTip());
            String vt1[] = {idString,nazivString,trajanjeString,cenaString,tipString};
            writer.writeNext(vt1);
        }
    }

    public boolean loadData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.filevrstetretmana));
            String line = null;
            while((line = br.readLine()) != null){
                String polja[] = line.split(",");
                for(int i = 0; i<polja.length ; i++){
                    polja[i]=polja[i].replaceAll("\"", "");
                }

                VrstaTretmana vt = new VrstaTretmana(Integer.parseInt(polja[0]), polja[1], Integer.parseInt(polja[2]), Double.parseDouble(polja[3]), Integer.parseInt(polja[4]));

                this.vrstetretmana.add(vt);
            }
            br.close();
        }catch (IOException e){ return false;}
        return true;
    }

    public void ispisi(){
        for(VrstaTretmana vt: vrstetretmana){
            System.out.println(vt);
        }
    }

    public  int findVrsteTretmana(String naziv){
        int id = -1;
        for(VrstaTretmana vt: vrstetretmana){
            if(vt.getNaziv().equals(naziv) ){
                id = vt.getId();
                break;
            }
        }
        return id;
    }

    public  boolean inVrsteTretmana(String naziv){
        boolean in = false;
        for(VrstaTretmana vt: vrstetretmana){
            if(vt.getNaziv().equals(naziv) ){
                in = true;
                break;
            }
        }
        return in;
    }

    public String[] stringsVrsteTretmana(){
        String niz[] = new String[vrstetretmana.size()];
        int i = 0;
        for(VrstaTretmana vt: vrstetretmana){
            niz[i] = String.valueOf(vt.getId()) + ';' + vt.getNaziv() + ';' + vt.getTrajanje() + ';' + vt.getCena();
            i++;
        }
        return niz;
    }

    public double getCenaVrsta(int id){
        double cena = 0.00;
        for(VrstaTretmana vt: vrstetretmana){
            if(vt.getId() == id){
                cena = vt.getCena();
            }
        }
        return cena;
    }
}
