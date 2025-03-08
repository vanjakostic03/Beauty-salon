package model;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.VrstaTretmana;
import manager.KlijentManager;
import manager.TretmanManager;
import manager.VrstaTretmanaManager;



public class IstorijaModel extends AbstractTableModel {
    private static final long serialVersionUID = 173122351138550735L;
	private TretmanManager tretmanMng;
    private KlijentManager klijentMng;
    private VrstaTretmanaManager vrstaTretmanaMng;
    private String klijent;
	//private VrstaTretmanaManager vrstaTretmanaMng;
	private String[] columnNames = { "Vrsta tretmana", "Stanje tretmana", "Kolicina potrosenog novca"};

    public IstorijaModel(TretmanManager t, KlijentManager k,VrstaTretmanaManager vt, String klijent) {
		this.tretmanMng = t;
		this.klijentMng = k;
		this.vrstaTretmanaMng = vt;
		this.klijent = klijent;
	}

    public ArrayList<Tretman> getTretmaniZaPrikaz(){
		ArrayList<Tretman> tretmani = new ArrayList<>();
        ArrayList<Klijent> klijenti = klijentMng.getKlijenti();
        int klijentId = -1;
        for (Klijent k: klijenti){
            if(k.getKorisnickoIme().equals(this.klijent)){
                klijentId = k.getId();
            }
        }
        for (Tretman t : this.tretmanMng.getTretmani()){
            if (t.getIdKlijent() == klijentId ){
                tretmani.add(t);
            }
        }
		return tretmani;
	}

    public String nadjiVrstu(int id){
		for (VrstaTretmana vt: this.vrstaTretmanaMng.getVrsteTretmana()){
			if (vt.getId() == id){
				return vt.getNaziv();
			}
		}
		return null;
	}

    @Override
	public int getRowCount() {
		return getTretmaniZaPrikaz().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Tretman t = getTretmaniZaPrikaz().get(rowIndex);
			
            switch (columnIndex) {
                case 0:
                    return nadjiVrstu(t.getIdVrstaTretman());
                case 1:
                    return t.getStanje();
                case 2:
                    switch (t.getStanje()) {
                        case ZAKAZAN:
                            return t.getCena();
                        case OTKAZAO_KLIJENT:
                            return t.getCena()*0.9;
                        case OTKAZAO_SALON:
                            return 0;
                        case IZVRSEN:
                            return t.getCena();
                        case NIJE_SE_POJAVIO:
                            return t.getCena();
                        default:
                            break;
                    }
                default:
                    return null;
                }
			
		}catch (IndexOutOfBoundsException e){
			System.out.println("Nema tretmana za prikaz");
			return null;
		
		}catch (NullPointerException e){
			System.out.println("Nema tretmana za prikaz");
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
        // if (getRowCount() > 0) {
        //     try {
        //         return this.getValueAt(0, columnIndex).getClass();
        //     } catch (NullPointerException e) {
        //         return null;
        //     }
        // }
        // return super.getColumnClass(columnIndex);
		try{
			return this.getValueAt(0, columnIndex).getClass();
		}catch(NullPointerException e){
			return null;
		}
		
	}
}
