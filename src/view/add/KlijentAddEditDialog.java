package view.add;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JWindow;

import entiteti.Klijent;
import entiteti.User.Pol;
import net.miginfocom.swing.MigLayout;
import view.KlijentTableFrame;
import manager.KlijentManager;

public class KlijentAddEditDialog extends JDialog{
    private static final long serialVersionUID = -5247231764310200252L;
	private KlijentManager klijentrMng;
    private Klijent zaedit;
	private JFrame parent;

    public KlijentAddEditDialog(JFrame parent, KlijentManager klijentMng,Klijent zaedit) {
		super(parent, true);
		this.parent = parent;

        if(zaedit == null){
            setTitle("Dodavanje");
        }else{
            setTitle("Izmena");
        }
		this.klijentrMng = klijentMng;
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

        String[] polstring = {"Nepoznato", "Muski", "Zenski"};

        JTextField tfime = new JTextField(20);
        JTextField tfprezime = new JTextField(20);
        JTextField tfadresa = new JTextField(20);
        JTextField tfbroj = new JTextField(20);
        JComboBox<String> cbpol = new JComboBox<>(polstring);
        JTextField tfkime = new JTextField(20);
        JPasswordField pfpass = new JPasswordField(20);
        JPasswordField pfpass1 = new JPasswordField(20);

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
         };
        
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

                if(zaedit == null){
                     pol=pol.toUpperCase();
                    if (ime.length() == 0 || prezime.length() == 0 || adresa.length() == 0 || broj.length() == 0 || korisnickoime.length() == 0 || lozinka.length() == 0 || lozinka1.length() == 0 ){
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
                    klijentrMng.registracija(ime, prezime, broj, adresa, pol, korisnickoime, lozinka);
                    
                }else
                     klijentrMng.izmeniKlijenta(zaedit.getId(), ime, prezime, broj, adresa, pol, korisnickoime, lozinka);
                ((KlijentTableFrame) parent).refreshData();
                KlijentAddEditDialog.this.dispose();
                
            }
        });
    }
}
