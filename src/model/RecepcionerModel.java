package model;

import javax.swing.table.AbstractTableModel;

import entiteti.Recepcioner;
import manager.RecepcionerManager;

public class RecepcionerModel extends AbstractTableModel {

    private static final long serialVersionUID = 173122351138550735L;
	private RecepcionerManager recepcionerMng;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Adresa" , "Broje telefona" , "pol","Korisnicko ime" , "Lozinka" ,"nivo strucne spreme", "staz", "bonus", "plata" };

	public RecepcionerModel(RecepcionerManager rec) {
		this.recepcionerMng = rec;
	}

	@Override
	public int getRowCount() {
		return recepcionerMng.getRecepcioneri().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Recepcioner r = recepcionerMng.getRecepcioneri().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return r.getId();
			case 1:
				return r.getIme();
			case 2:
				return r.getPrezime();
			case 3:
				return r.getAdresa();
			case 4:
				return r.getBroj();
			case 5:
				return r.getPol();
			case 6:
				return r.getKorisnickoIme();
			case 7:
				return r.getLozinka();
			case 8:
				return r.getNivoStrucneSpreme();
			case 9:
				return r.getStaz();
			case 10:
				return r.getBonus();
			case 11:
				return r.getPlata();
			default:
				return null;
			}
		
		}catch(IndexOutOfBoundsException e){ return null;}

	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		try{
			return this.getValueAt(0, columnIndex).getClass();
		}catch(NullPointerException e){
			return null;
		}
	}
    
}
