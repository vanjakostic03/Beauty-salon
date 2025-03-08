package manager;


import java.util.ArrayList;
import entiteti.TipTretmana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class TipTretmanaManager {
    private String fileTipTretmana;
    private ArrayList<TipTretmana> tipoviTretmana;
    private int idTipoviTretmana;

    public TipTretmanaManager(String file){
        this.fileTipTretmana = file;
        this.tipoviTretmana = new ArrayList<TipTretmana>();
        this.idTipoviTretmana = 0;
    }

    public ArrayList<TipTretmana> getTipoviTretmana(){return tipoviTretmana; }
    public int getIdTipoviTretmana(){return this.idTipoviTretmana; }

    public void setIdTipoviTretmana(){
        if(tipoviTretmana.size()!=0){
            idTipoviTretmana = tipoviTretmana.get(tipoviTretmana.size()-1).getIdTipTretmana() + 1;
        }
    }
    
    public void kreirajTipTretmana(String naziv){
        setIdTipoviTretmana();
        TipTretmana tt = new TipTretmana(idTipoviTretmana,naziv);
        tipoviTretmana.add(tt);

        try{
            FileWriter fw = new FileWriter(fileTipTretmana);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileTipTretmana, writer);
            writer.close();
        }catch(IOException e){}

    }

    public void obrisiTipTretmana(String naziv){
        for(TipTretmana tt: tipoviTretmana){
            if(tt.getNaziv().equals(naziv)){
                tipoviTretmana.remove(tt);
                break;
            }
        }
       
       try{
        FileWriter fw = new FileWriter(fileTipTretmana);
        CSVWriter writer = new CSVWriter(fw);
        WriteDataFile(fileTipTretmana, writer);
        writer.close();
       }catch(IOException e){}
    }

    public void izmeniTipTretmana(int id, String naziv){
        TipTretmana tt = this.vratiTipTretmanaId(id);
        tt.setNaziv(naziv);
        try{
            FileWriter fw = new FileWriter(fileTipTretmana);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileTipTretmana, writer);
            writer.close();
        }catch(IOException e){}
    }

    public  void WriteDataFile(String path, CSVWriter writer){
        for(TipTretmana tt: tipoviTretmana){
            
            String tt1[]={String.valueOf(tt.getIdTipTretmana()), tt.getNaziv()};
            writer.writeNext(tt1);

        }
    }

    public boolean loadData(){
        
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(this.fileTipTretmana));
            String line = null;
            while ((line = br.readLine()) != null){
                String polja[] = line.split(",");
                for(int i = 0; i<polja.length ; i++){
                    polja[i]=polja[i].replaceAll("\"", "");
                }                
                
                TipTretmana tt = new TipTretmana(Integer.parseInt(polja[0]),polja[1]);
                this.tipoviTretmana.add(tt);
            }
            br.close();

        }catch (IOException e){
            return false;
        }
        return true;
    }

    public TipTretmana vratiTipTretmanaId(int id){
        TipTretmana tt1 = null;
        for(TipTretmana tt: tipoviTretmana){
            if(tt.getIdTipTretmana()==id){
                tt1 = tt;
                break;
            }
        }
        return tt1;
    }

    public String[] stringsTipoviTretmana(){
        String niz[] = new String[tipoviTretmana.size()];
        int i = 0;
        for(TipTretmana tt: tipoviTretmana){
            niz[i] = String.valueOf(tt.getIdTipTretmana()) + ';' + tt.getNaziv() ;
            i++;
        }
        return niz;
    }

    public TipTretmana get(int i) {
        return null;
    }


}
