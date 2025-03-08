package view;

import manager.SalonInfoManager;
import model.SalonModel;
import view.add.SalonEditDialog;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entiteti.Salon;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

public class SalonTableFrame extends JFrame {
    private static final long serialVersionUID = -8026201049950423764L;
	private SalonInfoManager salontMng;
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;

    public SalonTableFrame(JFrame parent, SalonInfoManager mng){
        this.parentFrame = parent;
        this.salontMng = mng;

        setTitle("Salon info");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(255, 204, 255));
        
        
		ImageIcon editIcon = new ImageIcon("img/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
			
		add(mainToolbar, BorderLayout.NORTH);

		table = new JTable(new SalonModel(salontMng));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		
		try{
			tableSorter.setModel((AbstractTableModel) table.getModel());
		}catch(NullPointerException e){}
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);

		initActions();
    }

	private void initActions(){
		


		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Odabrati red u tabeli!", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					Salon k = salontMng.getSalonInfo();
					if(k != null) {
						SalonEditDialog edit = new SalonEditDialog(SalonTableFrame.this,salontMng,k);
						edit.setVisible(true);
						refreshData();
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci !", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
	}


	public void refreshData() {
		try{
			SalonModel k = (SalonModel)this.table.getModel();
			k.fireTableDataChanged();
		}
		catch(NullPointerException e){}
	}
    
}
