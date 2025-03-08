package model;
import javax.swing.table.AbstractTableModel;

import entiteti.Kozmeticar;
import manager.KozmeticarManager;
public class KozmeticarModel extends AbstractTableModel{

    private static final long serialVersionUID = 173122351138550735L;
	private KozmeticarManager kozmeticarMng;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Adresa" , "Broje telefona" , "pol","Korisnicko ime" , "Lozinka" ,"nivo strucne spreme", "staz", "bonus", "plata" ,"tretmani"};

	public KozmeticarModel(KozmeticarManager mng) {
		this.kozmeticarMng = mng;
	}

	@Override
	public int getRowCount() {
		return kozmeticarMng.getKozmeticari().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Kozmeticar k = kozmeticarMng.getKozmeticari().get(rowIndex);
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
			case 8:
				return k.getNivoStrucneSpreme();
			case 9:
				return k.getStaz();
			case 10:
				return k.getBonus();
			case 11:
				return k.getPlata();
			case 12:
				return k.getTretmani();
			default:
				return null;
			}
		}catch(IndexOutOfBoundsException e){return null;}

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
