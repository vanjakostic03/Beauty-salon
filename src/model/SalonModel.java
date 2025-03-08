package model;
import javax.swing.table.AbstractTableModel;

import entiteti.Salon;
import manager.SalonInfoManager;
public class SalonModel extends AbstractTableModel{
    private static final long serialVersionUID = 173122351138550735L;
	private SalonInfoManager SalonMng;
	private String[] columnNames = { "Naziv", "Pocetak radnog vremena", "Kraj radnog vremena","Kasa"};

	public SalonModel(SalonInfoManager kl) {
		this.SalonMng = kl;
	}

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Salon k = SalonMng.getSalonInfo();
			switch (columnIndex) {
			case 0:
				return k.getNaziv();
			case 1:
				return k.getPocetak();
			case 2:
				return k.getKraj();
			case 3: 
				return k.getStanjeUKasi();
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
