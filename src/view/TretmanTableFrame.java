package view;


import manager.KlijentManager;
import manager.KozmeticarManager;
import manager.SalonInfoManager;
import manager.TretmanManager;
import manager.VrstaTretmanaManager;
import model.TretmanModel;
import view.add.TretmanAddEditDialog;
import entiteti.Tretman;
import entiteti.Klijent;
import entiteti.Kozmeticar;
import entiteti.Tretman.Stanje;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

public class TretmanTableFrame extends JFrame{
    private static final long serialVersionUID = -8026201049950423764L;
	private TretmanManager tretmanMng;
	private KlijentManager klijentMng;
	private KozmeticarManager kozmeticarMng;
	private VrstaTretmanaManager vrstaTretmanaMng;
	private SalonInfoManager salonMng;
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
	protected JButton btnIzvrsi = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;
	protected String klijent; //korisnicko ime klijenta
	protected String kozmeticar; //korisnicko ime klijenta
	protected String recepcioner; //korisnicko ime klijenta
	protected String menadzer; //korisnicko ime klijenta

    public TretmanTableFrame(JFrame parent, TretmanManager mng, String klijent,String kozmeticar,String recepcioner,String menadzer, KlijentManager kmng, KozmeticarManager komng, VrstaTretmanaManager vt,SalonInfoManager s){
        this.parentFrame = parent;
        this.tretmanMng = mng;
        this.klijentMng = kmng;
		this.kozmeticarMng  = komng;
		this.vrstaTretmanaMng = vt;
		this.klijent = klijent;
		this.kozmeticar = kozmeticar;
		this.recepcioner = recepcioner;
		this.menadzer = menadzer;
		this.salonMng = s;

        setTitle("Tretmani");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(255, 204, 255));
        
		if(kozmeticar == null){
			ImageIcon addIcon = new ImageIcon("img/add.png");
			btnAdd.setIcon(addIcon);
			mainToolbar.add(btnAdd);

		}if (klijent == null && kozmeticar == null){
			ImageIcon editIcon = new ImageIcon("img/edit.png");
			btnEdit.setIcon(editIcon);
			mainToolbar.add(btnEdit);
		
		}if (kozmeticar != null){
			ImageIcon izvrsiIcon = new ImageIcon("img/izvrsi.png");
			btnIzvrsi.setIcon(izvrsiIcon);
			mainToolbar.add(btnIzvrsi);
		}
		ImageIcon deleteIcon = new ImageIcon("img/remove.png");
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);

		table = new JTable(new TretmanModel(tretmanMng,klijent,kozmeticar,recepcioner, menadzer,klijentMng, kozmeticarMng, vrstaTretmanaMng));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu
		try{
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			add(sc, BorderLayout.CENTER);
		}catch(NullPointerException e){}
		
		if(recepcioner != null){
			JTextField tfSearch = new JTextField(20);
			JTextField tfcena1 = new JTextField(10);
			JTextField tfcena2 = new JTextField(10);
			JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));		
			pSearch.setBackground(Color.PINK);
			pSearch.add(new JLabel("Search:"));
			pSearch.add(tfSearch);
			pSearch.add(new JLabel("Minimalna cena:"));
			pSearch.add(tfcena1);
			pSearch.add(new JLabel("Maksimalna cena:"));
			pSearch.add(tfcena2);
			
			add(pSearch,BorderLayout.SOUTH);
			
			tfSearch.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					//System.out.println("~ "+tfSearch.getText());
					// if (tfSearch.getText().trim().length() == 0) {
					// 	tableSorter.setRowFilter(null);
					// } else {
					// 	tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
					// }
					applyFilters(tfSearch, tfcena1, tfcena2);
				}
			});

			tfcena1.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// if (tfcena1.getText().trim().length() == 0) {
					// 	tableSorter.setRowFilter(null);
					// } else {
					// String regex = "\\d+(\\.\\d+)?";
					// String cenaInput = tfcena1.getText().trim();
					// tableSorter.setRowFilter(RowFilter.regexFilter(regex, 4)); 
					// tableSorter.setRowFilter(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(cenaInput), 4));
					// }
					applyFilters(tfSearch, tfcena1, tfcena2);
				}
			});

			tfcena2.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					applyFilters(tfSearch, tfcena1, tfcena2);
					// if (tfcena2.getText().trim().length() == 0) {
					// 	tableSorter.setRowFilter(null);
					// } else {
					// String regex = "\\d+(\\.\\d+)?";
					// String cenaInput = tfcena2.getText().trim();
					// tableSorter.setRowFilter(RowFilter.regexFilter(regex, 4)); 
					// tableSorter.setRowFilter(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(cenaInput), 4));
					// }
				}
			});

			
		}
		initActions();
    }
	private void applyFilters(JTextField tfSearch,JTextField tfcena1,JTextField tfcena2){
		String regex = "\\d+(\\.\\d+)?";
		String searchInput = tfSearch.getText().trim();
		String cena1Input = tfcena1.getText().trim();
		String cena2Input = tfcena2.getText().trim();
		
		List<RowFilter<Object, Object>> filters = new ArrayList<>();
		filters.add(RowFilter.regexFilter("(?i)" + searchInput));
		
		if (!cena1Input.isEmpty()) {
			filters.add(RowFilter.regexFilter(regex, 4));
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(cena1Input), 4));
		}
		
		if (!cena2Input.isEmpty()) {
			filters.add(RowFilter.regexFilter(regex, 4));
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(cena2Input), 4));
		}
		
		RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
		tableSorter.setRowFilter(combinedFilter);
	}

	private void initActions(){
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				TretmanAddEditDialog add = new TretmanAddEditDialog(TretmanTableFrame.this, tretmanMng,null,kozmeticarMng,klijent,klijentMng,salonMng);
				add.setVisible(true);
				
				refreshData();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Odabrati red u tabeli!", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(row, 0).toString());
					Tretman tt = tretmanMng.vratiTretmanId(id);
					if(tt != null) {
						Stanje s = Stanje.ZAKAZAN;
						//Stanje s = tretmanMng.obrisiTretman(tt.getId(),klijent,kozmeticar,recepcioner,menadzer);
						int ko;
						if(recepcioner!= null){
							Stanje opcije[] = {Stanje.OTKAZAO_KLIJENT, Stanje.OTKAZAO_SALON};
							ko = JOptionPane.showOptionDialog(null, "Ko otkazuje tretman?", " - Potvrda otkazivanja", 0, 3, null, opcije, opcije[1]);
							
							if(ko == 0)
								s = Stanje.OTKAZAO_KLIJENT;
							else
								s = Stanje.OTKAZAO_SALON;
						}else if(klijent!= null)
							s = Stanje.OTKAZAO_KLIJENT;
						else if(kozmeticar!=null)
							s = Stanje.NIJE_SE_POJAVIO;
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da otkazete tretman?", " - Potvrda otkazivanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							
							refreshData();
							if(s == Stanje.OTKAZAO_KLIJENT){
								tretmanMng.otkaziTretman(tt.getId(), s);
								int idklij = tt.getIdKlijent();
								Klijent k = klijentMng.vratiKlijentaid(idklij);
								klijentMng.vratiKlijentuNovac(tt.getCena()*0.1,k);
								salonMng.dodajUKasu(tt.getCena()*0.1 * (-1));
							}else if(s == Stanje.OTKAZAO_SALON){
								tretmanMng.otkaziTretman(tt.getId(), s);
								int idklij = tt.getIdKlijent();
								Klijent k = klijentMng.vratiKlijentaid(idklij);
								klijentMng.vratiKlijentuNovac(tt.getCena(),k);
								salonMng.dodajUKasu(tt.getCena() * (-1));
							}
							else if(s == Stanje.NIJE_SE_POJAVIO){
								tretmanMng.otkaziTretman(tt.getId(), s);
							}else{
								tretmanMng.obrisiTretman(tt.getId());
							}
						}
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani tretman!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnIzvrsi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Odabrati red u tabeli!", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(row, 0).toString());
					Tretman tt = tretmanMng.vratiTretmanId(id);
					if(tt != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete tretman?", " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							tretmanMng.izvrsiTretman(tt.getId());
							Kozmeticar k = kozmeticarMng.vratiKozmeticaraKorisnicko(kozmeticar);
							int br= 0 ;
							for(Tretman t: tretmanMng.getTretmani()){
								if(t.getStanje() == Stanje.IZVRSEN && k.getId() == t.getIdKozmeticar() && br!=0){
									br++;
								}
							}
							if((br % salonMng.getSalonInfo().getBrTretmana()) == 0)
								kozmeticarMng.dodajBonus(k,salonMng.getSalonInfo().getBonus());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani tretman!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Odabrati red u tabeli!", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(row, 0).toString());
					Tretman t = tretmanMng.vratiTretmanId(id);
					if(t != null) {
						TretmanAddEditDialog edit = new TretmanAddEditDialog(TretmanTableFrame.this,tretmanMng,t, kozmeticarMng, klijent,klijentMng,salonMng);
						edit.setVisible(true);
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani tretman!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
	}


	public void refreshData() {
		try{
			TretmanModel k = (TretmanModel)this.table.getModel();
			k.fireTableDataChanged();
		}catch(NullPointerException e){}
	}
    
}
