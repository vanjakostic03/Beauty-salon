package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;

import manager.TretmanManager;
import model.IstorijaModel;

public class IstorijaFrame extends JFrame {
    private static final long serialVersionUID = -8026201049950423764L;
	private TretmanManager tretmanMng;
	
    protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
    protected JTable table;
    protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame;

    public IstorijaFrame(JFrame parent,TretmanManager tretmanMng, String klijent){
        this.parentFrame = parent;
        this.tretmanMng = tretmanMng;

        setTitle("Cenovnik");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 255));

        table = new JTable(new IstorijaModel(tretmanMng,mainFrame.appMng.getKlijentMng(),mainFrame.appMng.getVrstaTretmanaMng(),klijent));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu7
        try{
            tableSorter.setModel((AbstractTableModel) table.getModel());
		    table.setRowSorter(tableSorter);
            JScrollPane sc = new JScrollPane(table);
            add(sc, BorderLayout.CENTER);
        }catch(NullPointerException e){}

        add(new JLabel("Ukpuno potrosenog novca: "),BorderLayout.AFTER_LAST_LINE);
        add(new JLabel(String.valueOf(mainFrame.appMng.getKlijentMng().vratiKlijentaKorisnicko(klijent).getPotrosenNovac())),BorderLayout.AFTER_LAST_LINE);

    }
}
