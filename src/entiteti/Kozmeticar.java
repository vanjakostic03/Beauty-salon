package entiteti;

import java.util.ArrayList;

public class Kozmeticar extends Zaposleni{
    private ArrayList<Integer> tretmani;

    public Kozmeticar(){
        super();
        tretmani = new ArrayList<Integer>();
    }

    public Kozmeticar(int id, String ime, String prezime, String broj, String adresa,Pol pol, String korisnicko_ime, String lozinka,int nivo_strucne_spreme,  int staz, double bonus, double plata, ArrayList<Integer> tretmani){
        super(id,ime, prezime,broj,adresa,pol, korisnicko_ime, lozinka,nivo_strucne_spreme,staz,bonus,plata);
        this.tretmani = tretmani;
    }

    //geteri
    public ArrayList<Integer> getTretmani(){ return tretmani; }

    //seteri
    public void setTretmani(ArrayList<Integer> tretmani){this.tretmani = tretmani; }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        Kozmeticar other = (Kozmeticar) o;
        if(tretmani != other.tretmani)
            return false;

        return true;
    }

}

