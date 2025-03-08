package manager;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;
import entiteti.User.Pol;
import entiteti.Menadzer;

public class MenadzerManager {
    private String fileMenadzeri;
    private  ArrayList<Menadzer> menadzeri;
    private int idmenadzeri;

    public MenadzerManager(String fileMenadzeri){
        this.fileMenadzeri = fileMenadzeri;
        this.menadzeri = new ArrayList<Menadzer>();
        this.idmenadzeri = 0;
    }

    public ArrayList<Menadzer> getMenadzeri() {return menadzeri; }
    public int getIdMenadzeri(){return idmenadzeri;}

    public void setIdMenadzeri(){
        if(menadzeri.size()!=0){
            idmenadzeri = menadzeri.get(menadzeri.size()-1).getId() + 1;
        }
    }

    public void registracijaMenadzer(String ime, String prezime, String broj, String adresa,String pol, String korisnickoime, String lozinka, String nivo, String staz){
        setIdMenadzeri();
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

        double plata = 10000 * (Integer.parseInt(nivo) + Integer.parseInt(staz));

        Menadzer m = new Menadzer(idmenadzeri, ime, prezime, broj, adresa, p, korisnickoime, lozinka,Integer.parseInt(nivo),Integer.parseInt(staz) , 0.00, plata);
        menadzeri.add(m);
        try{
            FileWriter fw = new FileWriter(fileMenadzeri);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileMenadzeri, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void izmeniMenadzera(int id,String ime, String prezime, String broj, String adresa,String pol, String korisnickoime, String lozinka, String nivo, String staz ){
        Menadzer m = this.vratiMenadzeraid(id);
        m.setIme(ime);
        m.setPrezime(prezime);
        m.setAdresa(adresa);
        m.setBroj(broj);
        m.setPol(Pol.valueOf(pol.toUpperCase()));
        m.setKorisnickoIme(korisnickoime);
        m.setLozinka(lozinka);
        m.setNivoStrucneSpreme(Integer.parseInt(nivo));
        m.setStaz(Integer.parseInt(staz));
        m.setPlata(10000 * (Integer.parseInt(nivo) + Integer.parseInt(staz)));
        try{
            FileWriter fw = new FileWriter(fileMenadzeri);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(fileMenadzeri, writer);
            writer.close();
        }catch(IOException e){}
    }

    public void obrisi(String kime){
        System.out.println(menadzeri.size());
        for(Menadzer m: menadzeri){
            if(m.getKorisnickoIme().equals(kime)){
                menadzeri.remove(m);
                
                break;
            }
        }
        System.out.println(menadzeri.size());
       
       try{
        FileWriter fw = new FileWriter(fileMenadzeri);
        CSVWriter writer = new CSVWriter(fw);
        WriteDataFile(fileMenadzeri, writer);
        writer.close();
       }catch(IOException e){}
    }

    public void WriteDataFile(String path, CSVWriter writer){
        for(Menadzer m: menadzeri){
            String m1[]={String.valueOf(m.getId()),m.getIme(),m.getPrezime(),m.getBroj(),m.getAdresa(),m.getPol().toString(),m.getKorisnickoIme(),m.getLozinka(),String.valueOf(m.getNivoStrucneSpreme()), String.valueOf(m.getStaz()), String.valueOf(m.getBonus()), String.valueOf(m.getPlata())};
            writer.writeNext(m1);
        }
    }

    public boolean loadData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.fileMenadzeri));
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
                Menadzer m = new Menadzer(Integer.parseInt(polja[0]),polja[1],polja[2],polja[3],polja[4], p, polja[6],polja[7],Integer.parseInt(polja[8]),Integer.parseInt(polja[9]), Double.parseDouble(polja[10]), Double.parseDouble(polja[11]));
                this.menadzeri.add(m);
            }
            br.close();
        }catch (IOException e){
            return false;
        }
        return true;
    }

        public void ispisi(){
            for(Menadzer m: menadzeri){
                System.out.println(m);
            }
        }

        public Menadzer vratiMenadzera(String kime){
            Menadzer m1 = null;
            for(Menadzer m: menadzeri){
                if(m.getKorisnickoIme().equals(kime)){
                    m1 = m;
                    break;
                }
            }
            return m1;
        }

        public Menadzer vratiMenadzeraid(int id){
            Menadzer m1 = null;
            for(Menadzer m: menadzeri){
                if(m.getId()==id){
                    m1 = m;
                    break;
                }
            }
            return m1;
        }

        public boolean inMenadzeri(String korisnicko_ime, String lozinka){
            boolean in = false;
            for(Menadzer m: menadzeri){
                if(m.getKorisnickoIme().equals(korisnicko_ime) && m.getLozinka().equals(lozinka)){
                    in = true;
                    break;
                }
            }
            return in;
        }

        public double plate(){
            double iznos = 0.00;
            for(Menadzer m: menadzeri){
                iznos+= m.getPlata();
                iznos+=m.getBonus();
            }
            return iznos;
        }

}
