package view.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JWindow;

import entiteti.VrstaTretmana;
import net.miginfocom.swing.MigLayout;
import view.VrstaTretmanaTableFrame;
import view.mainFrame;
import manager.CenovnikManager;
import manager.VrstaTretmanaManager;

public class VrstaTretmanaAddEditDialog extends JDialog {
    private static final long serialVersionUID = -5247231764310200252L;
	private VrstaTretmanaManager vrstaTretmanaMng;
    private CenovnikManager cenovnikMng;
    private VrstaTretmana zaedit;
	private JFrame parent;

    public VrstaTretmanaAddEditDialog(JFrame parent, VrstaTretmanaManager vrstaTretmanaMng,VrstaTretmana zaedit, CenovnikManager cenovnikMng) {
		super(parent, true);
		this.parent = parent;
        this.cenovnikMng = cenovnikMng;

        if(zaedit == null){
            setTitle("Dodavanje");
        }else{
            setTitle("Izmena");
        }
		this.vrstaTretmanaMng = vrstaTretmanaMng;
        this.zaedit = zaedit;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

    private void initGUI(){
        MigLayout layout = new MigLayout("wrap 2", "[][]","[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[][]");
        setLayout(layout);
        
        JTextField tfnaziv = new JTextField(20);
        JTextField tfcena = new JTextField(20);
        JTextField tftrajanje = new JTextField(20);
        String[] stringovi = mainFrame.appMng.getTipTretmanaMng().stringsTipoviTretmana();
        JList<String> ltipovi = new JList<String>(stringovi);
        

        JButton bpotvrdi = new JButton("Potvrdi");
        JButton bcancel = new JButton("Cancel");

         getRootPane().setDefaultButton(bpotvrdi);
         add(new JLabel("Unesite podatke za kreiranje vrste tretmana: "),"span 2");
         add(new JLabel("Naziv"));
         add(tfnaziv);
         add(new JLabel("Cena"));
         add(tfcena);
         add(new JLabel("Trajanje"));
         add(tftrajanje);
         add(new JLabel("Tip usluge"));
         add(ltipovi);
        
         add(new JLabel());
         add(bpotvrdi, "split 2");
         add(bcancel);

        if(zaedit != null){
            
            tfnaziv.setText(zaedit.getNaziv());
            tfcena.setText(zaedit.getCena()+"");
            tftrajanje.setText(zaedit.getTrajanje()+"");
            //String stringovitipovi[] = mainFrame.appMng.getTipTretmanaMng().stringsTipoviTretmana();
            String string = "";
            for(int i = 0; i<stringovi.length; i++){
                String polja[] = stringovi[i].split(";");
                int id = Integer.parseInt(polja[0]);
                if(zaedit.getTip() == id){
                    string = stringovi[i];
                }
            }
            ltipovi.setSelectedValue(string,true);
            
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
                String cena = tfcena.getText().trim();
                String trajanje = tftrajanje.getText().trim();
                String tip = (String) ltipovi.getSelectedValue();
                
                
                
                if(zaedit == null){
                    
                    if (naziv.length() == 0 || cena.length() == 0 || trajanje.length() == 0 || ltipovi.getSelectedValue() == null ){
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

                    String polja[] = tip.split(";"); 
                    vrstaTretmanaMng.dodajVrstu(naziv,polja[0], cena, trajanje);
                    int id = -1;
                    for(VrstaTretmana vt : vrstaTretmanaMng.getVrsteTretmana()){
                        if (naziv.equals(vt.getNaziv())){
                            id = vt.getId();
                        }
                    }
                    cenovnikMng.dodajUCenovnik(id, Double.parseDouble(cena));
                    
                }else{
                    String polja[] = tip.split(";"); 
                    vrstaTretmanaMng.izmeniVrstu(zaedit.getId(), naziv, cena, trajanje, polja[0]);
                    cenovnikMng.izmeniCenovnik(zaedit.getId(), Double.parseDouble(cena));
                

                }
                    ((VrstaTretmanaTableFrame) parent).refreshData();
                VrstaTretmanaAddEditDialog.this.dispose();
                
            }
        });
    }
}
