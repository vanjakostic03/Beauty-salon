package entiteti;


public class VrstaTretmana {
    private int idVrstaTretmana;
    private String naziv;
    private int trajanje;
    private double cena;
    //private TipTretmana tip;
    private int tipId;

    public VrstaTretmana(){
        this.idVrstaTretmana = 0;
        this.naziv = "";
        this.tipId = 0;
        this.cena = 0.00;
        this.trajanje = 0;

    }

    public VrstaTretmana(int id,String naziv, int trajanje, double cena, int tip){
        this.idVrstaTretmana = id;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.cena = cena;
        this.tipId = tip;
    }

    public int getId(){ return idVrstaTretmana; }
    public String getNaziv(){ return naziv; }
    public int getTrajanje(){ return trajanje; }
    public double getCena(){ return cena;}
    public int getTip(){ return tipId;}

    public void setId(int id){this.idVrstaTretmana = id; }
    public void setNaziv(String naziv){this.naziv = naziv;}
    public void setTrajanje(int trajanje){ this.trajanje = trajanje; }
    public void setCena(double cena){ this.cena = cena;}
    public void setTip(int tip){this.tipId = tip;}


}
