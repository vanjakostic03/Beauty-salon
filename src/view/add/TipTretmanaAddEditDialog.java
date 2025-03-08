package view.add;

import javax.swing.JDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import entiteti.TipTretmana;
import net.miginfocom.swing.MigLayout;
import view.TipTretmanaTableFrame;
import manager.TipTretmanaManager;

public class TipTretmanaAddEditDialog extends JDialog {
    private static final long serialVersionUID = -5247231764310200252L;
	private TipTretmanaManager tipTretmanaMng;
    private TipTretmana zaedit;
	private JFrame parent;

    public TipTretmanaAddEditDialog(JFrame parent, TipTretmanaManager tipTretmanaMng,TipTretmana zaedit) {
		super(parent, true);
		this.parent = parent;

        if(zaedit == null){
            setTitle("Dodavanje");
        }else{
            setTitle("Izmena");
        }
		this.tipTretmanaMng = tipTretmanaMng;
        this.zaedit = zaedit;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

    private void initGUI(){
        MigLayout layout = new MigLayout("wrap 2", "[][]","[]15[]15[][]");
        setLayout(layout);
        
        System.out.println(zaedit==null);


        JTextField tfnaziv = new JTextField(20);

        JButton bpotvrdi = new JButton("Potvrdi");
        JButton bcancel = new JButton("Cancel");

         getRootPane().setDefaultButton(bpotvrdi);
         add(new JLabel("Unesite podatke za kreiranje tipa tretmana: "),"span 2");
         add(new JLabel("Naziv"));
         add(tfnaziv);
        
         add(new JLabel());
         add(bpotvrdi, "split 2");
         add(bcancel);

        if(zaedit != null){
            
            tfnaziv.setText(zaedit.getNaziv());
           
        }

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
                String naziv = tfnaziv.getText().trim();
                
                if(zaedit == null){
                    
                    if (naziv.length() == 0 ){
                        JWindow jw = new JWindow();
                        MigLayout layoutwindow = new MigLayout("wrap","[]","[]15[]");
                        jw.setLayout(layoutwindow);
                        jw.setVisible(true);
                        jw.setLocationRelativeTo(null);
                        jw.setSize(300,100);

                        JButton btn = new JButton("Ok");
                        jw.add(new JLabel("Greska prilikom unosa podataka! Pokusajte ponovo."));
                        jw.add(btn);

                        btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae){
                                jw.setVisible(false);
                                jw.dispose();
                                
                            }
                        });
                        return;
                    }

                    
                    tipTretmanaMng.kreirajTipTretmana(naziv);
                    
                }else
                     tipTretmanaMng.izmeniTipTretmana(zaedit.getIdTipTretmana(), naziv);
                
               
                ((TipTretmanaTableFrame) parent).refreshData();
                

                TipTretmanaAddEditDialog.this.dispose();
                
            }
        });
    }
}
