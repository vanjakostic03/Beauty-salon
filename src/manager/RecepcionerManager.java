package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Recepcioner;
import static entiteti.User.Pol;


import com.opencsv.CSVWriter;

public class RecepcionerManager {
    private  String fileRecepcioneri;
    private  ArrayList<Recepcioner> recepcioneri;
    private  int idrecepcioneri;

    public RecepcionerManager(String fileRecepcioneri){
        this.fileRecepcioneri = fileRecepcioneri;
        this.recepcioneri = new ArrayList<Recepcioner>();
        this.idrecepcioneri = 0;
    }

    public ArrayList<Recepcioner> getRecepcioneri(){ return recepcioneri; }
    public int getIdRecepcioneri(){return idrecepcioneri; }

    public  void setIdRecepcioneri(){
        if(recepcioneri.size()!=0){
            idrecepcioneri = recepcioneri.get(recepcioneri.size()-1).getId() + 1;
        }
    }

    public  void registracija(String ime, String prezime, String broj, String adresa, String pol, String korisnickoime, String lozinka, String nivo_strucne_spreme, String staz){
        setIdRecepcioneri();
        Pol p = Pol.NEPOZNATO;
        switch(pol){
            case "ZENSKI":
                p=Pol.ZENSKI;
                break;
            case "MUSKI":
                p=Pol.MUSKI;
                break;
            case "NEPOZNATO":
                p=Pol.NEPOZNATO;
        }
        double plata = 5000*(Integer.parseInt(nivo_strucne_spreme) +Integer.parseInt(staz));
        Recepcioner r = new Recepcioner(idrecepcioneri, ime,prezime,broj,adresa,p, korisnickoime, lozinka, Integer.parseInt(nivo_strucne_spreme),Integer.parseInt(staz),0.00,plata);
        
        
        recepcioneri.add(r);
        try{
            FileWriter fw = new FileWriter(fileRecepcioneri);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileRecepcioneri, writer);
            writer.close();
        }catch(IOException e){}        
    }
    public void izmeniRecepcionera(int id,String ime, String prezime, String broj, String adresa,String pol, String korisnickoime, String lozinka, String nivo, String staz ){
        Recepcioner r = this.vratiRecepcioneraid(id);
        r.setIme(ime);
        r.setPrezime(prezime);
        r.setAdresa(adresa);
        r.setBroj(broj);
        r.setPol(Pol.valueOf(pol.toUpperCase()));
        r.setKorisnickoIme(korisnickoime);
        r.setLozinka(lozinka);
        r.setNivoStrucneSpreme(Integer.parseInt(nivo));
        r.setStaz(Integer.parseInt(staz));
        r.setPlata(5000*(Integer.parseInt(nivo) +Integer.parseInt(staz)));
        try{
            FileWriter fw = new FileWriter(fileRecepcioneri);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileRecepcioneri, writer);
            writer.close();
        }catch(IOException e){}
    }

    public  void obrisi(String kime){
        for(Recepcioner r: recepcioneri){
            if(r.getKorisnickoIme().equals(kime)){
                recepcioneri.remove(r);
                break;
            }
        }
       
       try{
        FileWriter fw = new FileWriter(fileRecepcioneri);
        CSVWriter writer = new CSVWriter(fw);
        WriteDataFile(fileRecepcioneri, writer);
        writer.close();
       }catch(IOException e){}
    }

    public  void WriteDataFile(String path, CSVWriter writer){
            for(Recepcioner r: recepcioneri){
                String idString = String.valueOf(r.getId());
                String plataString = String.valueOf(r.getPlata());
                String nivoString = String.valueOf(r.getNivoStrucneSpreme());
                String stazString = String.valueOf(r.getStaz());
                String bonusString = String.valueOf(r.getBonus());
                String k1[]={idString,r.getIme(),r.getPrezime(),r.getBroj(),r.getAdresa(),r.getPol().toString(),r.getKorisnickoIme(),r.getLozinka(),nivoString, stazString, bonusString,plataString};
                writer.writeNext(k1);

            }
    }

    public boolean loadData(){
        
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(this.fileRecepcioneri));
            String line = null;
            while ((line = br.readLine()) != null){
                String polja[] = line.split(",");
                for(int i = 0; i<polja.length ; i++){
                    polja[i]=polja[i].replaceAll("\"", "");
                }                
                Pol p=Pol.NEPOZNATO;
                switch(polja[5]){
                    case "NEPOZNATO":
                        p=Pol.NEPOZNATO;
                        break;
                    case "ZENSKI":
                        p=Pol.ZENSKI;
                        break;
                    case "MUSKI":
                        p=Pol.MUSKI;
                }
                Recepcioner r = new Recepcioner(Integer.parseInt(polja[0]),polja[1],polja[2],polja[3],polja[4], p, polja[6],polja[7],Integer.parseInt(polja[8]),Integer.parseInt(polja[9]), Double.parseDouble(polja[10]), Double.parseDouble(polja[11]));
                this.recepcioneri.add(r);
            }
            br.close();

        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void ispisi(){
        for(Recepcioner r: recepcioneri){
            System.out.println(r);
        }
    }

    public Recepcioner vratiRecepcioneraid(int id){
        Recepcioner r1 = null;
        for(Recepcioner r: recepcioneri){
            if(r.getId()==id){
                r1 = r;
                break;
            }
        }
        return r1;
    }

    public boolean inRecepcioneri(String korisnicko_ime, String lozinka){
        boolean in = false;
        for(Recepcioner r: recepcioneri){
            if(r.getKorisnickoIme().equals(korisnicko_ime) && r.getLozinka().equals(lozinka)){
                in = true;
                break;
            }
        }
        return in;
    }

    public double plate(){
        double iznos = 0.00;
        for(Recepcioner r: recepcioneri){
            iznos+= r.getPlata();
            iznos+=r.getBonus();
        }
        return iznos;
    }
}
