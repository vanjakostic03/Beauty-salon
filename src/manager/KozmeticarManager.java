package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Kozmeticar;


import static entiteti.User.Pol;

import com.opencsv.CSVWriter;

public class KozmeticarManager {
    private  String fileKozmeticari;
    private  ArrayList<Kozmeticar> kozmeticari;
    private  int idKozmeticari;

    public KozmeticarManager(String fileKozmeticari){
        this.fileKozmeticari = fileKozmeticari;
        this.kozmeticari = new ArrayList<Kozmeticar>();
        this.idKozmeticari = 0;
    }

    public ArrayList<Kozmeticar> getKozmeticari(){ return kozmeticari; }
    public int getIdKozmeticari(){return idKozmeticari; }

    public  void setIdKozmeticari(){
        if(kozmeticari.size()!=0){
            idKozmeticari = kozmeticari.get(kozmeticari.size()-1).getId() + 1;
        }
    }

    public  void registracija(String ime, String prezime, String broj, String adresa, String pol, String korisnickoime, String lozinka, String nivo_strucne_spreme, String staz, String[] tretmani){
        setIdKozmeticari();
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
        ArrayList<Integer> t = new ArrayList<>();
        for(String s : tretmani){
            String [] polja = s.split(";");
            t.add(Integer.parseInt(polja[0]));
        }

        double plata = 7000 * (Integer.parseInt(nivo_strucne_spreme)+ Integer.parseInt(staz));
        Kozmeticar k = new Kozmeticar(idKozmeticari, ime,prezime,broj,adresa,p, korisnickoime, lozinka, Integer.parseInt(nivo_strucne_spreme), Integer.parseInt(staz), 0.00,plata, t);
        
        
        kozmeticari.add(k);
        try{
            FileWriter fw = new FileWriter(fileKozmeticari);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKozmeticari, writer);
            writer.close();
        }catch(IOException e){}        
    }

    public void izmeniKozmeticara(int id,String ime, String prezime, String broj, String adresa,String pol, String korisnickoime, String lozinka, String nivo, String staz,String[] tretmani ){
        Kozmeticar k = this.vratiKozmeticaraid(id);
        k.setIme(ime);
        k.setPrezime(prezime);
        k.setAdresa(adresa);
        k.setBroj(broj);
        k.setPol(Pol.valueOf(pol.toUpperCase()));
        k.setKorisnickoIme(korisnickoime);
        k.setLozinka(lozinka);
        k.setNivoStrucneSpreme(Integer.parseInt(nivo));
        k.setStaz(Integer.parseInt(staz));
        k.setPlata(7000 * (Integer.parseInt(nivo)+ Integer.parseInt(staz)));
        ArrayList<Integer> t = new ArrayList<>();
        for(int i = 0 ; i<tretmani.length; i++){
            tretmani[i] =  tretmani[i].replaceAll("\"", "");
            String polja[] = tretmani[i].split(";");
            t.add(Integer.parseInt(polja[0]));
        }
        k.setTretmani(t);
        try{
            FileWriter fw = new FileWriter(fileKozmeticari);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKozmeticari, writer);
            writer.close();
        }catch(IOException e){}
    }
    
    
    public void obrisi(String kime){
        for(Kozmeticar k : kozmeticari){
            if(k.getKorisnickoIme().equals(kime)){
                kozmeticari.remove(k);
                break;
            }
        }

        try{
            FileWriter fw = new FileWriter(fileKozmeticari);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKozmeticari, writer);
            writer.close();
           }catch(IOException e){}
    }
    
    public  void WriteDataFile(String path, CSVWriter writer){
            for(Kozmeticar k: kozmeticari){
                String idString = String.valueOf(k.getId());
                String plataString = String.valueOf(k.getPlata());
                String nivoString = String.valueOf(k.getNivoStrucneSpreme());
                String stazString = String.valueOf(k.getStaz());
                String bonusString = String.valueOf(k.getBonus());
                String tretmaniString = "";
                for(int i: k.getTretmani()){
                    tretmaniString+= String.valueOf(i);
                    tretmaniString+= ';';
                }
                tretmaniString = tretmaniString.substring(0, tretmaniString.length() - 1);
                String k1[]={idString,k.getIme(),k.getPrezime(),k.getBroj(),k.getAdresa(),k.getPol().toString(),k.getKorisnickoIme(),k.getLozinka(),nivoString, stazString, bonusString ,plataString, tretmaniString};
                //dodati jos za listu tretmana
                writer.writeNext(k1);

            }
    }

    public boolean loadData(){
        
        try{
            //"1,2,3,4,5"
            BufferedReader br = new BufferedReader(new FileReader(this.fileKozmeticari));
            String line = null;
            while ((line = br.readLine()) != null){
                String polja[] = line.split(",");
                for(int i = 0; i<polja.length ; i++){
                    polja[i]=polja[i].replaceAll("\"", "");

                    //polja[12] = 1,2,3,4,5
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
                ArrayList<Integer> tretmani = new ArrayList<>();
                String ts[] = polja[12].split(";");
                
                for(String t : ts){
                    tretmani.add(Integer.parseInt(t));
                }
                Kozmeticar k = new Kozmeticar(Integer.parseInt(polja[0]),polja[1],polja[2],polja[3],polja[4], p, polja[6],polja[7],Integer.parseInt(polja[8]),Integer.parseInt(polja[9]), Double.parseDouble(polja[10]), Double.parseDouble(polja[11]),tretmani);
                this.kozmeticari.add(k);
            }
            br.close();

        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void ispisi(){
        for(Kozmeticar k: kozmeticari){
            System.out.println(k);
        }
    }

    public Kozmeticar vratiKozmeticaraid(int id){
        Kozmeticar k1 = null;
        for(Kozmeticar k: kozmeticari){
            if(k.getId()==id){
                k1 = k;
                break;
            }
        }
        return k1;
    }
    public Kozmeticar vratiKozmeticaraKorisnicko(String kime){
        Kozmeticar k1 = null;
        for(Kozmeticar k: kozmeticari){
            if(k.getKorisnickoIme().equals(kime)){
                k1 = k;
                break;
            }
        }
        return k1;
    }

    public void dodajBonus(Kozmeticar k,double value){
        double iznos = k.getBonus();
        iznos+=value;
        k.setBonus(iznos);
        try{
            FileWriter fw = new FileWriter(fileKozmeticari);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileKozmeticari, writer);
            writer.close();
           }catch(IOException e){}
    }

    public boolean inKozmeticari(String korisnicko_ime, String lozinka){
        boolean in = false;
        for(Kozmeticar k: kozmeticari){
            if(k.getKorisnickoIme().equals(korisnicko_ime) && k.getLozinka().equals(lozinka)){
                in = true;
                break;
            }
        }
        return in;
    }

    public String[] stringKozmeticari(ArrayList<Kozmeticar> lista){
        String niz[] = new String[lista.size()];
        int i = 0;
        for(Kozmeticar k: lista){
            niz[i] = String.valueOf(k.getId()) + ';' + k.getIme() + ';' + k.getPrezime();
            i++;
        }
        return niz;
    }

    public String[] obuceniKozmeticari(int idtip){
        ArrayList<Kozmeticar> obuceni = new ArrayList<>();
        for(Kozmeticar k : kozmeticari){
            if(k.getTretmani().contains(idtip)){
                obuceni.add(k);
            }
        }
        return stringKozmeticari(obuceni);
    }

    public double plate(){
        double iznos = 0.00;
        for(Kozmeticar k: kozmeticari){
            iznos+= k.getPlata();
            iznos+=k.getBonus();
        }
        return iznos;
    }

    // public int[] getAngazovanje(){
    //     int[] angazovanje = new int[kozmeticari.size()];
    //     int i = 0;
    //     for(Kozmeticar k : kozmeticari){}

    //     return angazovanje;
    // }


}
