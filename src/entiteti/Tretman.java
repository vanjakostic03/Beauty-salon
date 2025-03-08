package entiteti;

import java.time.LocalDateTime;


public class Tretman {

    public enum Stanje{ZAKAZAN, IZVRSEN, OTKAZAO_KLIJENT, OTKAZAO_SALON, NIJE_SE_POJAVIO}
    

    private int id;
    private Stanje stanje;
    private int idkozmeticar;
    private int idklijent;
    private LocalDateTime termin;
    //private boolean realizovan;
    private double cena;
    private int idvrstatretman;
    private int trajanje;
    
    public Tretman(){
        id=0;
        stanje = Stanje.ZAKAZAN;
        idkozmeticar = 0;
        idklijent = 0;
        termin = LocalDateTime.now();
        //realizovan = false;
        idvrstatretman = 0;
        cena = 0;
        trajanje = 0;
    }

    public Tretman(int id, Stanje stanje,int idkozmeticar, int idklijent, double cena, LocalDateTime termin, int idvrstatretman,int trajanje){
        this.id = id;
        this.stanje = stanje;
        this.idkozmeticar = idkozmeticar;
        this.idklijent = idklijent;
        this.termin = termin;
        //this.realizovan = realizovan;
        this.idvrstatretman = idvrstatretman;
        this.cena = cena;
        this.trajanje = trajanje;
    }
    
    public int getId(){return this.id;}
    public Stanje getStanje(){ return this.stanje; }
    public int getIdKozmeticar(){ return this.idkozmeticar; }
    public int getIdKlijent(){ return this.idklijent; }
    public LocalDateTime getTermin(){ return this.termin; }
    //public boolean getRealizovan(){ return this.realizovan; }
    public double getCena(){ return this.cena; }
    public int getIdVrstaTretman(){return this.idvrstatretman; }
    public int getTrajanje(){return this.trajanje; }

    public void setId(int id){ this.id = id;}
    public void setStanje(Stanje stanje){  this.stanje=stanje; }
    public void setIdKozmeticar(int id){  this.idkozmeticar=id; }
    public void setIdKlijent(int id){  this.idklijent=id; }
    public void setTermin(LocalDateTime termin){  this.termin=termin; }
    //public void setRealizovan(boolean realizovan){  this.realizovan=realizovan; }
    public void setCena(double cena){  this.cena=cena; }
    public void setIdVrstaTretman(int id){ this.idvrstatretman=id; }
    public void setTrajanje(int t){ this.trajanje=t; }

    
}
