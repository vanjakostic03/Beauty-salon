package model;

import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import entiteti.VrstaTretmana;
import manager.CenovnikManager;
import manager.VrstaTretmanaManager;

public class CenovnikModel extends AbstractTableModel {
    private static final long serialVersionUID = 173122351138550735L;
	private CenovnikManager cenovnikMng;
	private VrstaTretmanaManager vrstaTretmanaMng;
	private String[] columnNames = { "vrsta tretmana", "cena"  };

	public CenovnikModel(CenovnikManager kl,VrstaTretmanaManager v) {
		this.cenovnikMng = kl;
		this.vrstaTretmanaMng = v;
	}

	@Override
	public int getRowCount() {
		return cenovnikMng.getCene().getCeneCenovnik().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			HashMap<Integer,Double> cm = cenovnikMng.getCene().getCeneCenovnik();
			Object kljuc = cm.keySet().toArray()[rowIndex];
			Object vrednost = cm.get(kljuc);
			switch (columnIndex) {
			case 0:
				VrstaTretmana vt = vrstaTretmanaMng.vratiVrstaTretmanaid((int) kljuc);
				return vt.getNaziv();
			case 1:
				return vrednost;
			default:
				return null;
			}
		}catch(Exception e){return null;}
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
