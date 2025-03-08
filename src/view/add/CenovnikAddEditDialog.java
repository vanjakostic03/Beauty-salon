package view.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import view.CenovnikTableFrame;
import view.mainFrame;
import manager.CenovnikManager;

public class CenovnikAddEditDialog extends JDialog{
    private static final long serialVersionUID = -5247231764310200252L;
	private CenovnikManager cenovnikMng;
    //private Cenovnik zaedit;
	private JFrame parent;

    public CenovnikAddEditDialog(JFrame parent, CenovnikManager cenovnikMng){
		super(parent, true);
		this.parent = parent;

            setTitle("Izmena");
        
		this.cenovnikMng = cenovnikMng;
        //this.zaedit = zaedit;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

    private void initGUI(){
        MigLayout layout = new MigLayout("wrap 2", "[][]","[]15[]15[]15[][]");
        setLayout(layout);
        
        String[] vrste = mainFrame.appMng.getVrstaTretmanaMng().stringsVrsteTretmana();
        JList<String> lvrste = new JList<String>(vrste);
        JTextField tfcena = new JTextField(20);

        JButton bpotvrdi = new JButton("Potvrdi");
        JButton bcancel = new JButton("Cancel");

         getRootPane().setDefaultButton(bpotvrdi);
         add(new JLabel("Unesite podatke za izmenu cenovnika: "),"span 2");
         add(new JLabel("Vrsta tretmana"));
         add(lvrste);
         add(new JLabel("Nova cena"));
         add(tfcena);
        
         add(new JLabel());
         add(bpotvrdi, "split 2");
         add(bcancel);

         

        bcancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                 setVisible(false);
                 dispose();
            }
        });

        bpotvrdi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                String cena = tfcena.getText().trim();
                String vrsta = (String) lvrste.getSelectedValue();
                String polja[] = vrsta.split(";");

                
                
                cenovnikMng.izmeniCenovnik(Integer.parseInt(polja[0]),Double.parseDouble(cena));

            
                ((CenovnikTableFrame) parent).refreshData();
                CenovnikAddEditDialog.this.dispose();
                
            }
        });
    }
}
