package view;
import manager.CenovnikManager;
import model.CenovnikModel;
import view.add.CenovnikAddEditDialog;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

public class CenovnikTableFrame extends JFrame{
    private static final long serialVersionUID = -8026201049950423764L;
	private CenovnikManager cenovnikMng;
	
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;

    public CenovnikTableFrame(JFrame parent, CenovnikManager mng){
        this.parentFrame = parent;
        this.cenovnikMng = mng;

        setTitle("Cenovnik");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(255, 204, 255));
        
        /*ImageIcon addIcon = new ImageIcon("img/add.png");		
		ImageIcon scaled = new ImageIcon(addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		addIcon = scaled;
		btnAdd.setIcon(addIcon);
		mainToolbar.add(btnAdd);*/
		ImageIcon editIcon = new ImageIcon("img/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		/*ImageIcon deleteIcon = new ImageIcon("img/remove.png");
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);*/
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);

		table = new JTable(new CenovnikModel(cenovnikMng,mainFrame.appMng.getVrstaTretmanaMng()));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);

		initActions();
    }

	private void initActions(){
		/*btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				CenovnikTableFrame add = new CenovnikTableFrame(CenovnikTableFrame.this, cenovnikMng,null);
				add.setVisible(true);
				refreshData();
			}
		});*/

		/*btnDelete.addActionListener(new ActionListener() {
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
							tretmanMng.obrisiTretman(tt.getId());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani tretman!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});*/

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Odabrati red u tabeli!", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					
                    //int key = Integer.parseInt(table.getValueAt(row, 0).toString());
					
                    CenovnikAddEditDialog edit = new CenovnikAddEditDialog(CenovnikTableFrame.this,cenovnikMng);
                    edit.setVisible(true);
                    refreshData();
					
				}
			}

		});
	}


	public void refreshData() {
		CenovnikModel c = (CenovnikModel)this.table.getModel();
		c.fireTableDataChanged();
	}
}
