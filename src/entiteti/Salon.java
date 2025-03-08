package entiteti;

import java.time.LocalTime;

public class Salon {
    private String naziv;
    private LocalTime pocetak;
    private LocalTime kraj;
    private double stanjeUKasi;
    private double prag;
    private double bonus;
    private int brtretmana;

    public Salon(){
        this.naziv = "";
        this.pocetak= LocalTime.now();
        this.kraj= LocalTime.now();
        this.stanjeUKasi = 0.00;
        this.prag = 0.00;
        this.bonus = 0.00;
        this.brtretmana = 0;
        
    }
    public Salon(String naziv, LocalTime pocetak, LocalTime kraj, double stanjeUKasi, double prag,double bonus,int brtretmana){
        this.naziv = naziv;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.stanjeUKasi = stanjeUKasi;
        this.prag = prag;
        this.bonus = bonus;
        this.brtretmana = brtretmana;
    }

    public String getNaziv(){ return this.naziv; }
    public LocalTime getPocetak(){ return this.pocetak; }
    public LocalTime getKraj(){ return this.kraj; }
    public double getStanjeUKasi() {return this.stanjeUKasi; }
    public double getPrag() {return this.prag; }
    public double getBonus() {return this.bonus; }
    public int getBrTretmana() {return this.brtretmana; }

    public void setNaziv(String naziv){ this.naziv = naziv; }
    public void setPocetak(LocalTime pocetak){ this.pocetak = pocetak; }
    public void setKraj(LocalTime kraj){ this.kraj = kraj; }
    public void setStanjeUKasi(double stanjeUKasi){this.stanjeUKasi = stanjeUKasi ;}
    public void setPrag(double prag){this.prag = prag ;}
    public void setBonus(double bonus){this.bonus = bonus ;}
    public void setBrTretmana(int brtretmana){this.brtretmana = brtretmana ;}
}
