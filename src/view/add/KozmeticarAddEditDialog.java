package view.add;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import static entiteti.User.Pol;

import entiteti.Kozmeticar;
import net.miginfocom.swing.MigLayout;
import view.KozmeticarTableFrame;
import view.mainFrame;
import manager.KozmeticarManager;


public class KozmeticarAddEditDialog extends JDialog{
    private static final long serialVersionUID = -5247231764310200252L;
	private KozmeticarManager kozmeticarMng;
    private Kozmeticar zaedit;
	private JFrame parent;

    public KozmeticarAddEditDialog(JFrame parent, KozmeticarManager kozmeeticarMng,Kozmeticar zaedit) {
		super(parent, true);
		this.parent = parent;

        if(zaedit == null){
            setTitle("Dodavanje");
        }else{
            setTitle("Izmena");
        }
		this.kozmeticarMng = kozmeeticarMng;
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
        
        System.out.println(zaedit==null);

        String[] polstring = {"Nepoznato", "Muski", "Zenski"};

        JTextField tfime = new JTextField(20);
        JTextField tfprezime = new JTextField(20);
        JTextField tfadresa = new JTextField(20);
        JTextField tfbroj = new JTextField(20);
        JComboBox<String> cbpol = new JComboBox<>(polstring);
        JTextField tfkime = new JTextField(20);
        JPasswordField pfpass = new JPasswordField(20);
        JPasswordField pfpass1 = new JPasswordField(20);
        JTextField tfnivo = new JTextField(20);
        JTextField tfstaz = new JTextField(20);
        JTextField tfbonus = new JTextField(20);
        JTextField tfplata = new JTextField(20);
        String[] stringovitt = mainFrame.appMng.getTipTretmanaMng().stringsTipoviTretmana();
        JList<String> ltretmani = new JList<String>(stringovitt);
        ltretmani.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        JButton bpotvrdi = new JButton("Potvrdi");
        JButton bcancel = new JButton("Cancel");

         getRootPane().setDefaultButton(bpotvrdi);
         add(new JLabel("Unesite podatke za registraciju: "),"span 2");
         add(new JLabel("Ime"));
         add(tfime);
         add(new JLabel("Prezime"));
         add(tfprezime);
         add(new JLabel("Adresa"));
         add(tfadresa);
         add(new JLabel("Broj telefona"));
         add(tfbroj);
         add(new JLabel("Pol: "));
         add(cbpol);
         add(new JLabel("Korisnicko ime"));
         add(tfkime);
         add(new JLabel("Lozinka"));
         add(pfpass);
         if(zaedit == null){
            add(new JLabel("Ponovo unesite lozinku"));
         add(pfpass1);
         }
         
         add(new JLabel("Nivo strucne spreme"));
         add(tfnivo);
         add(new JLabel("Staz"));
         add(tfstaz);

         
        add(new JLabel("Izaberite tretmane "));
        add(ltretmani);
        
         add(new JLabel());
         add(bpotvrdi, "split 2");
         add(bcancel);

        if(zaedit != null){
            
            tfime.setText(zaedit.getIme());
            tfprezime.setText(zaedit.getPrezime());
            tfadresa.setText(zaedit.getAdresa());
            tfbroj.setText(zaedit.getBroj());
            String string = "";
            if(zaedit.getPol() == Pol.NEPOZNATO)
                string = "Nepoznato";
            else if(zaedit.getPol() == Pol.MUSKI)
                string = "Muski";
            else 
                string = "Zenski";
            cbpol.setSelectedItem(string);
            tfkime.setText(zaedit.getKorisnickoIme());
            pfpass.setText(zaedit.getLozinka());
            tfnivo.setText(zaedit.getNivoStrucneSpreme()+"");
            tfstaz.setText(zaedit.getStaz()+"");
            tfbonus.setText(zaedit.getBonus()+"");
            tfplata.setText(zaedit.getPlata()+"");
            
            
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
                String ime = tfime.getText().trim();
                String prezime = tfprezime.getText().trim();
                String adresa = tfadresa.getText().trim();
                String broj = tfbroj.getText().trim();
                String korisnickoime = tfkime.getText().trim();
                String lozinka = new String(pfpass.getPassword()).trim();
                String lozinka1 = new String(pfpass1.getPassword()).trim();
                String pol = (String) cbpol.getSelectedItem();
                String nivo = tfnivo.getText().trim();
                String staz = tfstaz.getText().trim();
                String tretmani[] = new String[ltretmani.getSelectedValuesList().size()];
                int i = 0;
                for(Object o: ltretmani.getSelectedValuesList()){
                    tretmani[i] = (String) o;
                    i++;
                }

                if(zaedit == null){
                     pol=pol.toUpperCase();
                    if (ime.length() == 0 || prezime.length() == 0 || adresa.length() == 0 || broj.length() == 0 || korisnickoime.length() == 0 || lozinka.length() == 0 || lozinka1.length() == 0 || nivo.length() == 0 || staz.length() == 0 || ltretmani.getSelectedValuesList()==null){
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

                    if(!lozinka.equals(lozinka1)){
                        JWindow jw = new JWindow();
                        MigLayout layoutwindow = new MigLayout("wrap","[]","[]15[]");
                        jw.setLayout(layoutwindow);
                        jw.setVisible(true);
                        jw.setLocationRelativeTo(null);
                        jw.setSize(300,100);

                        JButton btn = new JButton("Ok");
                        jw.add(new JLabel("Lozinke se ne poklapaju! Pokusajte ponovo."));
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
                    kozmeticarMng.registracija(ime, prezime, broj, adresa, pol, korisnickoime, lozinka1, nivo, staz, tretmani);
                    
                }else
                     kozmeticarMng.izmeniKozmeticara(zaedit.getId(), ime, prezime, broj, adresa, pol, korisnickoime, lozinka, nivo, staz,tretmani);
                ((KozmeticarTableFrame) parent).refreshData();
                KozmeticarAddEditDialog.this.dispose();
            
                    
                    
                
            }
        });
    }
    
}
