package entiteti;
import java.util.HashMap;
import java.util.Map.Entry;


public class Cenovnik {
    private HashMap<Integer,Double> cene; 

    public Cenovnik(){
        cene = new HashMap<>();
    }

    public HashMap<Integer,Double> getCeneCenovnik(){ return cene;}
    public void setCeneCenovnik(int id, double cena){
        if(cene.size() == 0)
            cene = new HashMap<>(); 
        cene.put(id, cena); }

    public Entry<String, Integer>[] entrySet() {
        return null;
    }
      
}
