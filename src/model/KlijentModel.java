package model;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entiteti.Klijent;
import manager.KlijentManager;
import manager.SalonInfoManager;
public class KlijentModel extends AbstractTableModel {

    private static final long serialVersionUID = 173122351138550735L;
	private KlijentManager klijentMng;
	private SalonInfoManager salonMng;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Adresa" , "Broje telefona" , "pol","Korisnicko ime" , "Lozinka"  };
	private boolean pravo; 
	public KlijentModel(KlijentManager kl,boolean pravo,SalonInfoManager s) {
		this.klijentMng = kl;
		this.salonMng = s;
		this.pravo = pravo;
	}

	public ArrayList<Klijent> getKlijentiZaPrikaz(){
		ArrayList<Klijent> klijenti = new ArrayList<>();
		if(pravo){
			for(Klijent k: this.klijentMng.getKlijenti()){
				if(k.getPotrosenNovac()>= salonMng.getSalonInfo().getPrag()  && !k.getPravoNaKarticu()){
					klijenti.add(k);
				}
			}
		}else{
			klijenti = klijentMng.getKlijenti();
		}
		return klijenti;
	}

	@Override
	public int getRowCount() {
		return getKlijentiZaPrikaz().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Klijent k = getKlijentiZaPrikaz().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return k.getId();
			case 1:
				return k.getIme();
			case 2:
				return k.getPrezime();
			case 3:
				return k.getAdresa();
			case 4:
				return k.getBroj();
			case 5:
				return k.getPol();
			case 6:
				return k.getKorisnickoIme();
			case 7:
				return k.getLozinka();
			default:
				return null;
			}
		}catch(IndexOutOfBoundsException e){
			return null;
		}
		

	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		try{
			return this.getValueAt(0, columnIndex).getClass();
		}catch(Exception e){
			return null;
		}
		
	}
    
}
