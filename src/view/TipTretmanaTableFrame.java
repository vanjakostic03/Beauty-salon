package view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entiteti.TipTretmana;
import entiteti.VrstaTretmana;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.Color;

import manager.CenovnikManager;
import manager.TipTretmanaManager;
import manager.VrstaTretmanaManager;
import model.TipTretmanaModel;
import view.add.TipTretmanaAddEditDialog;


public class TipTretmanaTableFrame extends JFrame {
    
    private static final long serialVersionUID = -8026201049950423764L;
	private TipTretmanaManager tipTretmanaMng;
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;
	private VrstaTretmanaManager vrstaTretmanaMng;
	private CenovnikManager cenovnikMng;

    public TipTretmanaTableFrame(JFrame parent, TipTretmanaManager mng, VrstaTretmanaManager vtmng, CenovnikManager cmng){
        this.parentFrame = parent;
        this.tipTretmanaMng = mng;
		this.vrstaTretmanaMng = vtmng;
		this.cenovnikMng = cmng;

        setTitle("Tipovi tretmana");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(255, 204, 255));
        
        ImageIcon addIcon = new ImageIcon("img/add.png");		
		// ImageIcon scaled = new ImageIcon(addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		// addIcon = scaled;
		btnAdd.setIcon(addIcon);
		mainToolbar.add(btnAdd);
        //if (tipTretmanaMng.getTipoviTretmana().size()!=0){
            ImageIcon editIcon = new ImageIcon("img/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		ImageIcon deleteIcon = new ImageIcon("img/remove.png");
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
        //}
		
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
        //table = new JTable();
        //if (tipTretmanaMng.getTipoviTretmana().size()!=0){
            table = new JTable(new TipTretmanaModel(tipTretmanaMng));
            table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getTableHeader().setReorderingAllowed(false);

            try{
				tableSorter.setModel((AbstractTableModel) table.getModel());
				table.setRowSorter(tableSorter);
				JScrollPane sc = new JScrollPane(table);
				add(sc, BorderLayout.CENTER);
			}catch(NullPointerException e){}
            
        //}
        //table.addRow​(new Object[table.getColumnCount()]);
		

		initActions();
    }

	private void initActions(){
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				TipTretmanaAddEditDialog add = new TipTretmanaAddEditDialog(TipTretmanaTableFrame.this, tipTretmanaMng,null);
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
					TipTretmana tt = tipTretmanaMng.vratiTipTretmanaId(id);
					if(tt != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete tip tretmana?", 
								tt.getNaziv() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							tipTretmanaMng.obrisiTipTretmana(tt.getNaziv());
							String vrsta = "";
							ArrayList<VrstaTretmana> vtretmana = vrstaTretmanaMng.getVrsteTretmana();
							ArrayList<VrstaTretmana> zabrisanje = new ArrayList<>();
							Set<Integer> set = new HashSet<> ();
							for(VrstaTretmana vt: vtretmana){
								if(tt.getIdTipTretmana() == vt.getTip()){
									vrsta = vt.getNaziv();
									zabrisanje.add(vt);
									set.add(vt.getId());
									//vrstaTretmanaMng.obrisiVrstuTretmana(vrsta);
									//cenovnikMng.obrisiIzCenovnika(vt.getId());
								}
							}
							vrstaTretmanaMng.obrisiVrsteTretmana(zabrisanje);
							cenovnikMng.obrisiViseCena(set);

							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani tip tretmana!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					TipTretmana tt = tipTretmanaMng.vratiTipTretmanaId(id);
					if(tt != null) {
						TipTretmanaAddEditDialog edit = new TipTretmanaAddEditDialog(TipTretmanaTableFrame.this,tipTretmanaMng,tt);
						edit.setVisible(true);
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani tip tretmana!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
	}


	public void refreshData() {
		try{
			TipTretmanaModel k = (TipTretmanaModel)this.table.getModel();
			k.fireTableDataChanged();
		}catch(NullPointerException e){}
		
	}
}
