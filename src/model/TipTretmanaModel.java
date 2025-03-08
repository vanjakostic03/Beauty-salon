package model;
import javax.swing.table.AbstractTableModel;

import entiteti.TipTretmana;
import manager.TipTretmanaManager;

public class TipTretmanaModel extends AbstractTableModel{

    private static final long serialVersionUID = 173122351138550735L;
	private TipTretmanaManager tipTretmanatMng;
	private String[] columnNames = { "Id", "Naziv"};

	public TipTretmanaModel(TipTretmanaManager tt) {
		this.tipTretmanatMng = tt;
	}

	@Override
	public int getRowCount() {
		return  tipTretmanatMng.getTipoviTretmana().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			TipTretmana tt =  tipTretmanatMng.getTipoviTretmana().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return tt.getIdTipTretmana();
			case 1:
				return tt.getNaziv();
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
