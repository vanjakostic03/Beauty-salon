package entiteti;

public class Zaposleni extends User {
    protected int nivo_strucne_spreme;
    protected int staz;
    protected double bonus;
    protected double plata;

    public Zaposleni(){
        super();
        this.nivo_strucne_spreme = 0;
        this.staz = 0;
        this.bonus = 0;
        this.plata = 0.00;
    }

    public Zaposleni(int id,String ime, String prezime, String broj, String adresa,Pol pol, String korisnicko_ime, String lozinka,int nivo_strucne_spreme,  int staz, double bonus, double plata){
        super(id,ime, prezime,broj,adresa,pol, korisnicko_ime, lozinka);
        this.nivo_strucne_spreme = nivo_strucne_spreme;
        this.staz = staz;
        this.bonus = bonus;
        this.plata = plata;
    }

    //geteri
    public int getNivoStrucneSpreme(){ return this.nivo_strucne_spreme; }
    public double getBonus(){ return this.bonus; }
    public int getStaz(){ return this.staz; }
    public double getPlata(){ return this.plata; }

    //seteri
    public void setNivoStrucneSpreme(int nivo_strucne_spreme){  this.nivo_strucne_spreme=nivo_strucne_spreme; }
    public void setBonus(double bonus){  this.bonus =bonus; }
    public void setStaz(int staz){  this.staz=staz; }
    public void setPlata(double plata){  this.plata=plata; }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        Zaposleni other = (Zaposleni) o;
        if(nivo_strucne_spreme != other.nivo_strucne_spreme)
            return false;
        if(bonus != other.bonus)
            return false;
        if(staz != other.staz)
            return false;
        if(plata != other.plata)
            return false;

        return true;
    }

}
