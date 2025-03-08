package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entiteti.Kozmeticar;
import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.VrstaTretmana;
import entiteti.Tretman.Stanje;
import view.mainFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.opencsv.CSVWriter;

public class TretmanManager {
    private  String filetretmani;
    private  ArrayList<Tretman> tretmani;
    private  int idTretmani;
    private  VrstaTretmanaManager vrstaTretmanaMng;
    private  KozmeticarManager kozmeticarMng;

    public TretmanManager(String filetretmani,VrstaTretmanaManager vrstaTretmanaMng,KozmeticarManager kozmeticarMng){
        this.filetretmani = filetretmani;
        this.tretmani = new ArrayList<Tretman>();
        this.idTretmani = 0; 
        this.vrstaTretmanaMng = vrstaTretmanaMng;
        this.kozmeticarMng = kozmeticarMng;
    }

    public ArrayList<Tretman> getTretmani(){ return tretmani;}


    public int getidTretmani(){return idTretmani; }

    public  void setidTretmani(){
        if(tretmani.size()!=0){
            idTretmani = tretmani.get(tretmani.size()-1).getId() + 1;
        }
    }

    public Tretman zakaziTretman(String idkozm, String idklij, String termin,  String vrstetretmana,boolean popust,int trajanje){
        setidTretmani();
        //double cena = mainFrame.appMng.getVrstaTretmanaMng().getCenaVrsta(Integer.parseInt(vrstetretmana));;
        double cena = vrstaTretmanaMng.getCenaVrsta(Integer.parseInt(vrstetretmana));
        if(popust){
            cena = cena - cena*0.1;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Tretman t = new Tretman(idTretmani,Stanje.ZAKAZAN, Integer.parseInt(idkozm), Integer.parseInt(idklij),cena,LocalDateTime.parse(termin.replace("T", " "),formatter), Integer.parseInt(vrstetretmana),trajanje);

        tretmani.add(t);
        try{
            FileWriter fw = new FileWriter(filetretmani);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filetretmani, writer);
            writer.close();
        }catch(IOException e){} 
        return t;

    }
    public void izvrsiTretman(int id){
        for(Tretman t : tretmani){
            if(t.getId()== id){
                t.setStanje(Stanje.IZVRSEN);
                break;
            }
        }
        try{
            FileWriter fw = new FileWriter(filetretmani);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filetretmani, writer);
            writer.close();
        }catch(IOException e){} 
    }
    
    public void otkaziTretman(int id,Stanje s){
        // Tretman tr = new Tretman();
        for(Tretman t : tretmani){
            if(t.getId()== id){
                t.setStanje(s);
                // if(klijent == null && kozmeticar == null){
                //     t.setStanje(Stanje.OTKAZAO_SALON);
                // }
                // if(kozmeticar != null){
                //     t.setStanje(Stanje.NIJE_SE_POJAVIO);
                    
                // }
                // if(klijent != null)
                //     t.setStanje(Stanje.OTKAZAO_KLIJENT);
                // tr = t;
                break;
            }
        }
    
        try{
            FileWriter fw = new FileWriter(filetretmani);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filetretmani, writer);
            writer.close();
        }catch(IOException e){} 
    }

    public void obrisiTretman(int id){
        for(Tretman t : tretmani){
            if(t.getId()== id){
                tretmani.remove(t);
                break;
            }
        }
        try{
            FileWriter fw = new FileWriter(filetretmani);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filetretmani, writer);
            writer.close();
        }catch(IOException e){} 
    }


    public void izmeniTretman(int id, String idkozm,String idklij, String termin, String idvrstetretmana){
        Tretman vt = this.vratiTretmanId(id);
        vt.setIdKozmeticar(Integer.parseInt(idkozm));
        vt.setIdKlijent(Integer.parseInt(idklij));
        vt.setTermin(LocalDateTime.parse(termin));
        vt.setIdVrstaTretman(Integer.parseInt(idvrstetretmana));
        try{
            FileWriter fw = new FileWriter(filetretmani);
            CSVWriter writer = new CSVWriter(fw);
            WriteDataFile(filetretmani, writer);
            writer.close();
        }catch(IOException e){}
    }


    public  void WriteDataFile(String path, CSVWriter writer){
        for(Tretman t: tretmani){
            String idString = String.valueOf(t.getId());
            String stanjeString = String.valueOf(t.getStanje());
            String kozmeticarString = String.valueOf(t.getIdKozmeticar());
            String klijentrString = String.valueOf(t.getIdKlijent());
            String terminString = String.valueOf(t.getTermin());
            String cenaString = String.valueOf(t.getCena());
            String vrstaString = String.valueOf(t.getIdVrstaTretman());
            String trajanjeString = String.valueOf(t.getTrajanje());
            String t1[] = {idString,stanjeString,kozmeticarString,klijentrString,cenaString,terminString,vrstaString,trajanjeString};
            writer.writeNext(t1);
        }
    }

    public boolean loadData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.filetretmani));
            String line = null;
            while((line = br.readLine()) != null){
                String polja[] = line.split(",");
                for(int i = 0; i<polja.length ; i++){
                    polja[i]=polja[i].replaceAll("\"", "");
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                Tretman t = new Tretman(Integer.parseInt(polja[0]), Stanje.valueOf(polja[1]),Integer.parseInt(polja[2]), Integer.parseInt(polja[3]),Double.parseDouble(polja[4]),LocalDateTime.parse(polja[5].replace("T", " "),formatter), Integer.parseInt(polja[6]),Integer.parseInt(polja[7]) );
                this.tretmani.add(t);
            }
            br.close();
        }catch (IOException e){ return false;}
        return true;
    }

    public Tretman vratiTretmanId(int id){
        Tretman k1 = null;
        for(Tretman k: tretmani){
            if(k.getId()==id){
                k1 = k;
                break;
            }
        }
        return k1;
    }
    public void ispisi(){
        for(Tretman t: tretmani){
            System.out.println(t);
        }
    }

    public String[] termini( int idkozm, String datum,ArrayList<LocalTime> terminiLT){
        ArrayList<String> termini =  new ArrayList<>();
        for (LocalTime localTime : terminiLT) {
            String strTime = localTime.toString();
            termini.add(strTime);
        }

        for(Tretman t : tretmani){
            if(t.getIdKozmeticar() == idkozm && t.getStanje()==Stanje.ZAKAZAN){
                //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
                String strDate = String.valueOf(t.getTermin());
                if(strDate.contains(datum) ){
                    
                    String termin = strDate.substring(11);
                    
                    int brisanja = 0;
                    if (t.getTrajanje() % 60 == 0)
                        brisanja = t.getTrajanje() / 60;
                    else
                        brisanja = t.getTrajanje() / 60 + 1;
                    int index = termini.indexOf(termin);
                    if(index!=-1){
                        for(int i = 0; i<brisanja ; i++){
                        
                            termini.remove(index);
                        }
                        
                    }
                    
                }
            }
        }

        String[] terminiStrings = new String[termini.size()];
        if(termini.size() != 0){

            for (int i = 0; i < termini.size(); i++) {
                terminiStrings[i] = termini.get(i).toString();
            }
            return terminiStrings;
        }
        
        return null;
    }
    //IZVESTAJI



    public String[] analizaVrstaTretmana(VrstaTretmana v,LocalDate pocetak, LocalDate kraj){
        //tip, broj zakazanih, prihodi u odredjenom datumu 
        
        String lista[] = new String[5];
        lista[0] =String.valueOf(v.getNaziv());
        lista[1] =String.valueOf(v.getCena());
        lista[2] =String.valueOf(v.getTrajanje());
        int brzakazanih = 0;
        for(Tretman t: tretmani){
            if(t.getIdVrstaTretman() == v.getId() && t.getStanje()==Stanje.ZAKAZAN){
                brzakazanih++;
            }
        }
        lista[3] =String.valueOf(brzakazanih);
        double prihodi = 0.0;
        for(Tretman t: tretmani){
            if(t.getIdVrstaTretman() == v.getId()){
                String datum = String.valueOf(t.getTermin()).substring(0, 10);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate temp = LocalDate.parse(datum, formatter);
                if((temp.isEqual(pocetak) || temp.isAfter(pocetak)) && (temp.isEqual(kraj) || temp.isBefore(kraj))){
                    prihodi+=t.getCena();
                }
            }
        }
        lista[4] = String.valueOf(prihodi);
        return lista;
    }

    public String[] otkazaniTretmani(LocalDate pocetak, LocalDate kraj){
        String[] lista = new String[4];
        int izvrseni = 0;
        int salon = 0;
        int klijent = 0;
        int nijesepojavio = 0;

        for(Tretman t: tretmani){
            String datum = String.valueOf(t.getTermin()).substring(0, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate temp = LocalDate.parse(datum, formatter);
            if((temp.isEqual(pocetak) || temp.isAfter(pocetak)) && (temp.isEqual(kraj) || temp.isBefore(kraj))){
                if(t.getStanje() == Stanje.IZVRSEN)
                    izvrseni++;
                if(t.getStanje() == Stanje.OTKAZAO_SALON)
                    salon++;
                if(t.getStanje() == Stanje.OTKAZAO_KLIJENT)
                    klijent++;
                if(t.getStanje() == Stanje.NIJE_SE_POJAVIO)
                    nijesepojavio++;
            }
        }
        lista[0] = String.valueOf(izvrseni);
        lista[1] = String.valueOf(salon);
        lista[2] = String.valueOf(klijent);
        lista[3] = String.valueOf(nijesepojavio);
        return lista;
    }

    //broj tretmana
    //prihodi u datumu
    public String[] izvestajKozmeticari(Kozmeticar k, LocalDate pocetak, LocalDate kraj){
        String[] lista = new String[3];
        lista[0] = k.getIme() + " " + k.getPrezime();
        int brojtermina = 0;
        double prihodi = 0.00;
        for(Tretman t: tretmani){
            String datum = String.valueOf(t.getTermin()).substring(0, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate temp = LocalDate.parse(datum, formatter);
            if(t.getIdKozmeticar() == k.getId() && (temp.isEqual(pocetak) || temp.isAfter(pocetak)) && (temp.isEqual(kraj) || temp.isBefore(kraj)) &&t.getStanje() ==Stanje.IZVRSEN){
                brojtermina++;
                prihodi+=t.getCena();
            }
        }
        lista[1] = String.valueOf(brojtermina);
        lista[2] = String.valueOf(prihodi);
        return lista;
    }

    public double prihodi(Month mesec){
        double iznos = 0.00;
        for(Tretman t: tretmani){
            String datum = String.valueOf(t.getTermin()).substring(0, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate temp = LocalDate.parse(datum, formatter);
            Month m = temp.getMonth();
            if(m == mesec ){
                if(t.getStanje() == Stanje.IZVRSEN || t.getStanje() == Stanje.NIJE_SE_POJAVIO){
                    iznos += t.getCena();
                }else if(t.getStanje() == Stanje.OTKAZAO_KLIJENT){
                    iznos += t.getCena()*0.9;
                }
            }
        }
        return iznos;
    }

    public HashMap<String,Integer> getAngazovanje(){
        
        HashMap<String, Integer> angazovanje = new HashMap<String, Integer>();
        for(Kozmeticar ko : kozmeticarMng.getKozmeticari()){
            int broj = 0;
            for(Tretman t : tretmani){
                LocalDate i = t.getTermin().toLocalDate();
                LocalDate j = LocalDate.now();
                long brdana = ChronoUnit.DAYS.between(i, j);
                // long brdana = razlika.toDays();
                if(ko.getId() == t.getIdKozmeticar() && t.getStanje() == Stanje.IZVRSEN && brdana<=30){
                    broj++;
                }
            }
            angazovanje.put(ko.getKorisnickoIme(),broj);
        }
        return angazovanje;   
    }

    public HashMap<String,Integer> getZastupljenost(){
        
        HashMap<String, Integer> zastupljenost = new HashMap<String, Integer>();
        int zakazani = 0,  izvrseni = 0, otkazaoklijent = 0, otkazaosalon = 0, nijesepojavio = 0;
        for(Tretman t: tretmani){
            LocalDate i = t.getTermin().toLocalDate();
            LocalDate j = LocalDate.now();
            long brdana = ChronoUnit.DAYS.between(i, j);
            if (brdana <=30){
                switch(t.getStanje()){
                    case ZAKAZAN:
                        zakazani++;
                        break;
                    case IZVRSEN:
                        izvrseni++;
                        break;
                    case OTKAZAO_KLIJENT:
                        otkazaoklijent++;
                        break;
                    case OTKAZAO_SALON:
                        otkazaosalon++;
                        break;
                    case NIJE_SE_POJAVIO:
                        nijesepojavio++;
                }
            }
        }

        zastupljenost.put("Zakazani", zakazani);
        zastupljenost.put("Izvrseni", izvrseni);
        zastupljenost.put("Otkazao klijent", otkazaoklijent);
        zastupljenost.put("Otkazao salon", otkazaosalon);
        zastupljenost.put("Nije se pojavio", nijesepojavio);
         
        return zastupljenost;   
    }

    public HashMap<String, List<Double>> getPrihodi(){
        HashMap<String, List<Double>> prihodi = new HashMap<String, List<Double>>();

        for(TipTretmana tt : mainFrame.appMng.getTipTretmanaMng().getTipoviTretmana()){
            
            List<Double> list = new ArrayList<Double>();
            for(int j = 0; j<12;j++){
                list.add(0.0);
            }
            int i = 0;
            YearMonth now  = YearMonth.now();
            YearMonth temp = now.minus(1, ChronoUnit.YEARS);
            
            while(!temp.equals(YearMonth.now())){
                for(Tretman t: tretmani){
                    
                    VrstaTretmana vt = mainFrame.appMng.getVrstaTretmanaMng().vratiVrstaTretmanaid(t.getIdVrstaTretman());
                    if( tt.getIdTipTretmana() == vt.getTip() && temp.equals(YearMonth.from(t.getTermin()))){
                        
                        switch(t.getStanje()){
                            case IZVRSEN:
                            list.set(i, list.get(i) + t.getCena());
                            break;
                        case OTKAZAO_KLIJENT:
                            list.set(i, list.get(i) + t.getCena() * 0.9);
                            break;
                        case NIJE_SE_POJAVIO:
                            list.set(i, list.get(i) + t.getCena());
                            break;
                        default:
                            break;
                        }
                    }
                }
                
                i++;
                temp = temp.plus(1, ChronoUnit.MONTHS);
            }
            prihodi.put(tt.getNaziv(), list);
        }

        return prihodi;
    }

    public ArrayList<LocalTime> raspored(String kozmeticar, LocalDate datum){
        ArrayList<LocalTime> termini = new ArrayList<>();
        for(Tretman t: tretmani){
            Kozmeticar k = kozmeticarMng.vratiKozmeticaraKorisnicko(kozmeticar);
            if(t.getStanje() == Stanje.ZAKAZAN && k.getId() == t.getIdKozmeticar() && datum.equals(t.getTermin().toLocalDate())){
                termini.add(t.getTermin().toLocalTime()); //namesiti da samo uzme termin
            }
        }
        return termini;
    }
}
