package model;

import javax.swing.table.AbstractTableModel;

import entiteti.Menadzer;
import manager.MenadzerManager;

public class MenadzerModel extends AbstractTableModel{
    private static final long serialVersionUID = 173122351138550735L;
	private MenadzerManager menadzerdMng;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Adresa" , "Broje telefona" , "pol","Korisnicko ime" , "Lozinka" ,"nivo strucne spreme", "staz", "bonus", "plata" };

	public MenadzerModel(MenadzerManager mng) {
		this.menadzerdMng = mng;
	}

	@Override
	public int getRowCount() {
		return menadzerdMng.getMenadzeri().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Menadzer m = menadzerdMng.getMenadzeri().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return m.getId();
			case 1:
				return m.getIme();
			case 2:
				return m.getPrezime();
			case 3:
				return m.getAdresa();
			case 4:
				return m.getBroj();
			case 5:
				return m.getPol();
			case 6:
				return m.getKorisnickoIme();
			case 7:
				return m.getLozinka();
			case 8:
				return m.getNivoStrucneSpreme();
			case 9:
				return m.getStaz();
			case 10:
				return m.getBonus();
			case 11:
				return m.getPlata();
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
