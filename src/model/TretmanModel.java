package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entiteti.Klijent;
import entiteti.Kozmeticar;
import entiteti.Tretman;
import entiteti.VrstaTretmana;
import entiteti.Tretman.Stanje;
import manager.TretmanManager;
import manager.VrstaTretmanaManager;
import manager.KlijentManager;
import manager.KozmeticarManager;

public class TretmanModel extends AbstractTableModel{
    private static final long serialVersionUID = 173122351138550735L;
	private TretmanManager tretmanMng;
	private KlijentManager klijentMng;
	private KozmeticarManager kozmeticarMng;
	//private RecepcionerManager recepcionerMng;
	private VrstaTretmanaManager vrstaTretmanaMng;
	private String klijent;
	private String kozmeticar;
	private String recepcioner;
	private String menadzer;
	private String[] columnNames = { "Id", "stanje", "kozmeticar", "klijent" , "cena","datum i vreme" , "vrsta tretmana"};
	private String[] columnNamesKlijent = { "id","naziv tretmana", "stanje", "kozmeticar", "cena", "datum i vreme"};

	public TretmanModel(TretmanManager t, String klijent, String kozmeticar, String recepcioner, String menadzer, KlijentManager  kl, KozmeticarManager ko, VrstaTretmanaManager vt) {
		this.tretmanMng = t;
		this.klijentMng = kl;
		this.kozmeticarMng = ko;
		this.vrstaTretmanaMng = vt;
		//this.recepcionerMng = r;
		this.klijent = klijent;
		this.kozmeticar = kozmeticar;
		this.recepcioner = recepcioner;
		this.menadzer = menadzer;
	}

	public ArrayList<Tretman> getTretmaniZaPrikaz(){
		ArrayList<Tretman> tretmani = new ArrayList<>();
		if(this.klijent != null){
			ArrayList<Klijent> klijenti = klijentMng.getKlijenti();
			int klijentId = -1;
			for (Klijent k: klijenti){
				if(k.getKorisnickoIme().equals(this.klijent)){
					klijentId = k.getId();
				}
			}
			for (Tretman t : this.tretmanMng.getTretmani()){
				//if (t.getIdKlijent() == klijentId && (t.getStanje() == Stanje.ZAKAZAN || t.getStanje() == Stanje.IZVRSEN )){
					
				if (t.getIdKlijent() == klijentId && t.getStanje() == Stanje.ZAKAZAN ){
					tretmani.add(t);
					
				}
			}
		}
		else if (this.kozmeticar != null){
			ArrayList<Kozmeticar> kozmeticari = kozmeticarMng.getKozmeticari();
			int kozmeticarId = -1;
			for (Kozmeticar k: kozmeticari){
				if(k.getKorisnickoIme().equals(this.kozmeticar)){
					kozmeticarId = k.getId();
				}
			}

			for (Tretman t : this.tretmanMng.getTretmani()){
				if (t.getIdKozmeticar() == kozmeticarId && t.getStanje() == Stanje.ZAKAZAN ){
					tretmani.add(t);
				}
			}
		}
		else if (this.recepcioner != null){

			for (Tretman t : this.tretmanMng.getTretmani()){
				if (t.getStanje() == Stanje.ZAKAZAN){
					tretmani.add(t);
				}
			}
		}
		else{
			tretmani = this.tretmanMng.getTretmani();
			
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
	public String nadjiKozmeticara(int id){
		for (Kozmeticar k: this.kozmeticarMng.getKozmeticari()){
			if (k.getId() == id){
				return k.getIme() + " " + k.getPrezime();
			}
		}
		return null;
	}
	public String nadjiKlijenta(int id){
		for (Klijent k: this.klijentMng.getKlijenti()){
			if (k.getId() == id){
				return k.getIme() + " " + k.getPrezime();
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
		if(this.klijent == null)
			return columnNames.length;
		return columnNamesKlijent.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			Tretman t = getTretmaniZaPrikaz().get(rowIndex);
			if(klijent == null){
				switch (columnIndex) {
					case 0:
						return t.getId();
					case 1:
						return t.getStanje();
					case 2:
						return nadjiKozmeticara(t.getIdKozmeticar());
					case 3:
						return nadjiKlijenta(t.getIdKlijent());
					case 4:
						return t.getCena();
					case 5:
						return t.getTermin();
					case 6:
						return nadjiVrstu(t.getIdVrstaTretman());
					default:
						return null;
					}
			}else{
				switch (columnIndex) {
					//"naziv tretmana", "stanje", "kozmeticar", "cena", "datum i vreme"
					case 0:
						return t.getId(); 
					case 1:
						return nadjiVrstu(t.getIdVrstaTretman()); 
					case 2:
						return t.getStanje();
					case 3:
						return nadjiKozmeticara(t.getIdKozmeticar()); 
					case 4:
						return t.getCena();
					case 5:
						return t.getTermin(); //
					default:
						return null;
					}
			}
		}
		catch (IndexOutOfBoundsException e){
			System.out.println("Nema tretmana za prikaz");
			return null;
		
		}
		// catch (NullPointerException e){
		// 	System.out.println("Nema tretmana za prikaz");
		// 	return null;
		// }
	}

	@Override
	public String getColumnName(int column) {
		if(klijent == null)
			return this.columnNames[column];
		return this.columnNamesKlijent[column];
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
