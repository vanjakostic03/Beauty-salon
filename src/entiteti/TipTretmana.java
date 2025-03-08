package entiteti;

public class TipTretmana {
    private int idTipTretmana;
    private String naziv;

    public TipTretmana(){
        this.idTipTretmana = 0;
        this.naziv="";
    }

    public TipTretmana(int id, String naziv){
        this.idTipTretmana = id;
        this.naziv = naziv;
    }

    //geteri
    public int getIdTipTretmana(){ return this.idTipTretmana;}
    public String getNaziv() {return this.naziv; }

    //seteri
    public void setIdTipTretmana(int id){this.idTipTretmana = id;}
    public void setNaziv(String naziv){this.naziv = naziv;}
}
