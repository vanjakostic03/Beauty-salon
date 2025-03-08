package model;

import javax.swing.table.AbstractTableModel;

import entiteti.TipTretmana;
import entiteti.VrstaTretmana;
import manager.TipTretmanaManager;
import manager.VrstaTretmanaManager;

public class VrstaTretmanaModel extends AbstractTableModel{
    private static final long serialVersionUID = 173122351138550735L;
	private VrstaTretmanaManager vrstaTretmanaMng;
	private TipTretmanaManager tipTretmanaMng;
	private String[] columnNames = { "id", "naziv", "trajanje", "cena", "tip tretmana"  };

	public VrstaTretmanaModel(VrstaTretmanaManager kl,TipTretmanaManager t) {
		this.vrstaTretmanaMng = kl;
		this.tipTretmanaMng = t;
	}

	@Override
	public int getRowCount() {
		return vrstaTretmanaMng.getVrsteTretmana().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			VrstaTretmana k = vrstaTretmanaMng.getVrsteTretmana().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return k.getId();
			case 1:
				return k.getNaziv();
			case 2:
				return k.getTrajanje();
			case 3:
				return k.getCena();
			case 4:
				TipTretmana tt= tipTretmanaMng.vratiTipTretmanaId(k.getTip());
				return tt.getNaziv();
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
