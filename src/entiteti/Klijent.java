package entiteti;


public class Klijent extends User{
    private boolean pravoNaKarticu;
    private boolean imaKarticu;
    private double potrosenNovac;

    public Klijent(){
        super();
        this.pravoNaKarticu = false;
        this.imaKarticu = false;
        this.potrosenNovac = 0.00;
    }
    public Klijent(int id,String ime, String prezime, String broj, String adresa,Pol pol, String korisnicko_ime, String lozinka, boolean pravoNaKarticu, boolean imaKarticu, double novac){
        super(id,ime, prezime,broj,adresa, pol,korisnicko_ime, lozinka);
        this.pravoNaKarticu = pravoNaKarticu;
        this.imaKarticu = imaKarticu;
        this.potrosenNovac = novac;
    }

    //seteri
    public void setPravoNaKarticu(boolean pravoNaKarticu){ this.pravoNaKarticu = pravoNaKarticu; }
    public void setImaKarticu(boolean imaKarticu){ this.imaKarticu = imaKarticu; }
    public void setPotrosenNovac(double potrosenNovac){ this.potrosenNovac = potrosenNovac; }
    //geteri
    
    public boolean getPravoNaKarticu(){ return pravoNaKarticu; }
    public boolean getImaKarticu(){ return imaKarticu; }
    public double getPotrosenNovac(){ return potrosenNovac; }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        Klijent other = (Klijent) o;
        if (this != other)
            return false;
        if(pravoNaKarticu != other.pravoNaKarticu)
            return false;
        if(imaKarticu != other.imaKarticu)
            return false;
        if(potrosenNovac != other.potrosenNovac)
            return false;

        return true;
    }
}
