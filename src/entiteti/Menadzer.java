package entiteti;

public class Menadzer extends Zaposleni{
    
    public Menadzer(){ super(); }

    public Menadzer(int id,String ime, String prezime, String broj, String adresa,Pol pol, String korisnicko_ime, String lozinka,int nivo_strucne_spreme,  int staz, double bonus, double plata){
        super(id,ime, prezime, broj, adresa, pol, korisnicko_ime, lozinka, nivo_strucne_spreme, staz, bonus, plata);
    }
    
}
