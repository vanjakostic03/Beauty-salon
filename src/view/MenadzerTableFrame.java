package view;

import manager.MenadzerManager;
import model.MenadzerModel;
import view.add.MenadzerAddEditDialog;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entiteti.Menadzer;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;




public class MenadzerTableFrame extends JFrame{
    private static final long serialVersionUID = -8026201049950423764L;
	private MenadzerManager menadzerMng;
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;

    public MenadzerTableFrame(JFrame parent, MenadzerManager menadzerMng){
        this.parentFrame = parent;
        this.menadzerMng = menadzerMng;

        setTitle("Menadzeri");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(255, 204, 255));
        
        ImageIcon addIcon = new ImageIcon("img/add.png");		
		// ImageIcon scaled = new ImageIcon(addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
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
		add(mainToolbar, BorderLayout.NORTH);

		table = new JTable(new MenadzerModel(menadzerMng));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		
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
				MenadzerAddEditDialog add = new MenadzerAddEditDialog(MenadzerTableFrame.this, menadzerMng,null);
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
					Menadzer m = menadzerMng.vratiMenadzeraid(id);
					if(m != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete menadzera?", 
								m.getIme() + " "+m.getPrezime() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							menadzerMng.obrisi(m.getKorisnickoIme());
							
						}
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog menadzera!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					Menadzer m = menadzerMng.vratiMenadzeraid(id);
					if(m != null) {
						System.out.println(m==null);
						MenadzerAddEditDialog edit = new MenadzerAddEditDialog(MenadzerTableFrame.this,menadzerMng,m);
						edit.setVisible(true);
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog menadzera!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
	}


	public void refreshData() {
		try{
			MenadzerModel mm = (MenadzerModel)this.table.getModel();
			mm.fireTableDataChanged();
		}catch(NullPointerException e){}
	}

}
