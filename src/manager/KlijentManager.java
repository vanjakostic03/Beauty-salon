package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Klijent;
import static entiteti.User.Pol;

import com.opencsv.CSVWriter;


public class KlijentManager {
    private  String fileKlijenti;
    private  ArrayList<Klijent> klijenti;
    private  int idklijenti;

    public KlijentManager(String fileKlijenti){
        this.fileKlijenti = fileKlijenti;
        this.klijenti = new ArrayList<Klijent>();
        this.idklijenti = 0;
    }

    public ArrayList<Klijent> getKlijenti(){ return klijenti; }
    public int getIdKlijenti(){return idklijenti; }
    public String getPath(){return fileKlijenti; }

    public  void setIdKlijenti(){
        if(klijenti.size()!=0){
            idklijenti = klijenti.get(klijenti.size()-1).getId() + 1;
        }
    }

    public  void registracija(String ime, String prezime, String broj, String adresa, String pol, String korisnickoime, String lozinka){
        setIdKlijenti();
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
        Klijent k = new Klijent(idklijenti, ime,prezime,broj,adresa,p, korisnickoime, lozinka, false, false,0.00);
        
        
        klijenti.add(k);
        try{
            FileWriter fw = new FileWriter(fileKlijenti);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKlijenti, writer);
            writer.close();
        }catch(IOException e){}        
    }

    
    public void izmeniKlijenta(int id,String ime, String prezime, String broj, String adresa,String pol, String korisnickoime, String lozinka ){
        Klijent k = this.vratiKlijentaid(id);
        k.setIme(ime);
        k.setPrezime(prezime);
        k.setAdresa(adresa);
        k.setBroj(broj);
        k.setPol(Pol.valueOf(pol.toUpperCase()));
        k.setKorisnickoIme(korisnickoime);
        k.setLozinka(lozinka);
        try{
            FileWriter fw = new FileWriter(fileKlijenti);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKlijenti, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void napraviKarticu(Klijent k){
        k.setImaKarticu(true);
        try{
            FileWriter fw = new FileWriter(fileKlijenti);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKlijenti, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void odobriPravo(Klijent k){
        k.setPravoNaKarticu(true);
        try{
            FileWriter fw = new FileWriter(fileKlijenti);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKlijenti, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void obrisi( String kime){
        
        for(Klijent k: klijenti){
            if(k.getKorisnickoIme().equals(kime)){
                klijenti.remove(k);
                
                break;
            }
        }
       
       try{
        FileWriter fw = new FileWriter(fileKlijenti);
        CSVWriter writer = new CSVWriter(fw);
        WriteDataFile(fileKlijenti, writer);
        writer.close();
       }catch(IOException e){}

    }
 
    public  void WriteDataFile(String path, CSVWriter writer){
            for(Klijent k: klijenti){
                String str1 = String.valueOf(k.getPravoNaKarticu());
                String str2 = String.valueOf(k.getImaKarticu());
                String str3 = String.valueOf(k.getPotrosenNovac());
                String idString = String.valueOf(k.getId());
                String k1[]={idString,k.getIme(),k.getPrezime(),k.getBroj(),k.getAdresa(),k.getPol().toString(),k.getKorisnickoIme(),k.getLozinka(),str1 , str2,str3};
                writer.writeNext(k1);

            }
    }

    public boolean loadData(){
        
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(this.fileKlijenti));
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
                Klijent k = new Klijent(Integer.parseInt(polja[0]),polja[1],polja[2],polja[3],polja[4], p, polja[6],polja[7],Boolean.parseBoolean(polja[8]),Boolean.parseBoolean(polja[9]),Double.parseDouble(polja[10]));
                this.klijenti.add(k);
            }
            br.close();

        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void ispisi(){
        for(Klijent k: klijenti){
            System.out.println(k);
        }
    }

    public Klijent vratiKlijentaid(int id){
        Klijent k1 = null;
        for(Klijent k: klijenti){
            if(k.getId()==id){
                k1 = k;
                break;
            }
        }
        return k1;
    }
    public Klijent vratiKlijentaKorisnicko(String kime){
        Klijent k1 = null;
        for(Klijent k: klijenti){
            if(k.getKorisnickoIme().equals(kime)){
                k1 = k;
                break;
            }
        }
        return k1;
    }

    public void vratiKlijentuNovac(double iznos, Klijent k){
        double stanje = k.getPotrosenNovac();
        stanje-=iznos;
        k.setPotrosenNovac(stanje);
        try{
            FileWriter fw = new FileWriter(fileKlijenti);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKlijenti, writer);
            writer.close();
           }catch(IOException e){}
    }

    public boolean inKlijenti(String korisnicko_ime, String lozinka){
        boolean in = false;
        for(Klijent k: klijenti){
            if(k.getKorisnickoIme().equals(korisnicko_ime) && k.getLozinka().equals(lozinka)){
                in = true;
                break;
            }
        }
        return in;
    }
    public boolean inKlijenti(String korisnicko_ime){
        boolean in = false;
        for(Klijent k: klijenti){
            if(k.getKorisnickoIme().equals(korisnicko_ime)){
                in = true;
                break;
            }
        }
        return in;
    }
    
    public String [] stringKlijenti(){
        String niz[] = new String[klijenti.size()];
        int i = 0;
        for(Klijent k: klijenti){
            niz[i] = String.valueOf(k.getId()) + ';' + k.getIme() + ';' + k.getPrezime();
            i++;
        }
        return niz;
    }
}
