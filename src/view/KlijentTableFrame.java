package view;

import manager.KlijentManager;
import manager.SalonInfoManager;
import model.KlijentModel;
import view.add.KlijentAddEditDialog;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entiteti.Klijent;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;


public class KlijentTableFrame extends JFrame{
    private static final long serialVersionUID = -8026201049950423764L;
	private KlijentManager klijentMng;
	private SalonInfoManager salonMng;
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
	protected JButton btnIzvrsi = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;

    public KlijentTableFrame(JFrame parent, KlijentManager mng,boolean pravo,SalonInfoManager smng){
		
        this.parentFrame = parent;
        this.klijentMng = mng;
        this.salonMng = smng;

        setTitle("Klijenti");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(255, 204, 255));

		
        if(!pravo){
			ImageIcon addIcon = new ImageIcon("img/add.png");		
			// ImageIcon scaled = new ImageIcon(addIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			// addIcon = scaled;
			btnAdd.setIcon(addIcon);
			mainToolbar.add(btnAdd);
			ImageIcon editIcon = new ImageIcon("img/edit.png");
			btnEdit.setIcon(editIcon);
			mainToolbar.add(btnEdit);
			ImageIcon deleteIcon = new ImageIcon("img/remove.png");
			btnDelete.setIcon(deleteIcon);
			mainToolbar.add(btnDelete);
			mainToolbar.setFloatable(false);		
		}else{
			ImageIcon izvrsIcon = new ImageIcon("img/izvrsi.png");
			btnIzvrsi.setIcon(izvrsIcon);
			mainToolbar.add(btnIzvrsi);
		}
        add(mainToolbar, BorderLayout.NORTH);
		table = new JTable(new KlijentModel(klijentMng,pravo,salonMng));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu
		try{
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			add(sc, BorderLayout.CENTER);
		}catch(NullPointerException e){}
		

		initActions();
    }

	private void initActions(){
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				KlijentAddEditDialog add = new KlijentAddEditDialog(KlijentTableFrame.this, klijentMng,null);
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
					Klijent k = klijentMng.vratiKlijentaid(id);
					if(k != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete klijenta?", 
								k.getIme() + " "+k.getPrezime() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							klijentMng.obrisi(k.getKorisnickoIme());
							
						}
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog klijenta!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					Klijent k = klijentMng.vratiKlijentaid(id);
					if(k != null) {
						KlijentAddEditDialog edit = new KlijentAddEditDialog(KlijentTableFrame.this,klijentMng,k);
						edit.setVisible(true);
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog klijenta!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					Klijent k = klijentMng.vratiKlijentaid(id);
					if(k != null) {
						klijentMng.odobriPravo(k);
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog klijenta!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
	}


	public void refreshData() {
		try{
			KlijentModel k = (KlijentModel)this.table.getModel();
			k.fireTableDataChanged();
		}catch(NullPointerException e){}
		
	}
    
}
