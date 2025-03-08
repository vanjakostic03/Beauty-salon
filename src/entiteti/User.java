package entiteti;

public class User {

    public enum Pol{ MUSKI, ZENSKI, NEPOZNATO}

    protected int id;
    protected String ime;
    protected String prezime;
    protected String broj;
    protected String adresa;
    protected String korisnicko_ime;
    protected String lozinka;
    protected Pol pol;

    public User(){
        this.id=0;
        this.ime = " ";
        this.prezime = " ";
        this.broj = " ";
        this.adresa = " ";
        this.korisnicko_ime = " ";
        this.lozinka = " ";
        this.pol = Pol.NEPOZNATO;
    }

    public User(int id,String ime, String prezime, String broj, String adresa,  Pol pol,String korisnicko_ime, String lozinka){
        this.id=id;
        this.ime = ime;
        this.prezime = prezime;
        this.broj = broj;
        this.adresa = adresa;
        this.pol = pol;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
        
    }

    //geteri
    public int getId(){return this.id;}
    public String getIme(){ return this.ime; }
    public String getPrezime(){ return this.prezime; }
    public String getBroj(){ return this.broj; }
    public String getAdresa(){ return this.adresa; }
    public String getKorisnickoIme(){ return this.korisnicko_ime; }
    public String getLozinka(){ return this.lozinka; }
    public Pol getPol(){ return this.pol; }

    //seteri
    public void setId(int id){this.id = id;}
    public void setIme(String ime){ this.ime = ime; }
    public void setPrezime(String prezime){ this.prezime = prezime; }
    public void setBroj(String broj){ this.broj = broj; }
    public void setAdresa(String adresa){ this.adresa = adresa; }
    public void setKorisnickoIme(String korisnicko_ime){ this.korisnicko_ime = korisnicko_ime; }
    public void setLozinka(String lozinka){ this.lozinka = lozinka; }
    public void setPol(Pol pol){ this.pol = pol; }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.broj.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        User other = (User) o;
        if(korisnicko_ime != other.korisnicko_ime)
            return false;
        if(lozinka != other.lozinka)
            return false;
        if(ime != other.ime)
            return false;
        if(prezime != other.prezime)
            return false;
        if(broj != other.broj)
            return false;

        return true;
    }
    
    @Override
    public String toString(){ return "User: "+ ime + "," + prezime + "," + broj + "," + adresa + "," + korisnicko_ime + "," + lozinka ; }

    

}
