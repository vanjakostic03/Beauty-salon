package view;

import java.awt.Color;
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

import manager.AppManager;
import net.miginfocom.swing.MigLayout;

public class mainFrame extends JFrame {
    private static final long serialVersionUID = 8456560429229699542L;

    public static AppManager appMng;

    public mainFrame(AppManager appMng){
        this.appMng = appMng;
        loginDialog();
    }


    public void loginDialog(){
        JDialog frame = new JDialog();
        frame.setTitle("PRIJAVA");
        frame.setSize(300,300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 204, 255));
        
        loginContent(frame);
        frame.pack();
        frame.setVisible(true);

    } 

    public void loginContent(JDialog dialog){
        MigLayout layout = new MigLayout("wrap 2","[][]","[]15[][]15[]15[]");
        dialog.setLayout(layout);

        JTextField kime = new JTextField(20);
        JPasswordField pass = new JPasswordField(20);
        JButton ok = new JButton("OK");
        JButton Cancel = new JButton("Cancel");
        JButton Register = new JButton("Registruj se");

        dialog.getRootPane().setDefaultButton(ok);
        
        dialog.add(new JLabel("Dobrodosli. Unesite podatke za prijavu."),"span 2");
        dialog.add(new JLabel("Korisnicko ime"));
        dialog.add(kime);
        dialog.add(new JLabel("Sifra "));
        dialog.add(pass);
        dialog.add(ok,"split 2");
        dialog.add(Cancel,"wrap");
        dialog.add(new JLabel("Nemate nalog? Registrujte se"));
        dialog.add(new JLabel());
        dialog.add(Register);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                String korisnickoime = kime.getText().trim();
                String lozinka = new String(pass.getPassword()).trim();
        
                if (korisnickoime.length() == 0 || lozinka.length() == 0){
                    
                    JWindow jw = new JWindow();
                    MigLayout layoutwindow = new MigLayout("wrap","[]","[]15[]");
                    jw.setLayout(layoutwindow);
                    jw.setVisible(true);
                    jw.setLocationRelativeTo(null);
                    jw.setSize(340,100);

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
                if (appMng.getKlijentMng().inKlijenti(korisnickoime, lozinka)){
                    dialog.setVisible(false);
                    dialog.dispose();
                    new KlijentFrame(korisnickoime);
                    //pozovi meni za klijenta
                }else if(appMng.getMenadzerMng().inMenadzeri(korisnickoime, lozinka)){
                    dialog.setVisible(false);
                    dialog.dispose();
                    if(appMng.getSalonInfoMng().getSalonInfo().getNaziv().equals("")){
                        new MenadzerFrame(false,appMng,korisnickoime);
                    }else{
                    new MenadzerFrame(true, appMng,korisnickoime);}
                    //pozovi meni za menadzera
                }else if(appMng.getRecepcionerMng().inRecepcioneri(korisnickoime, lozinka)){
                    dialog.setVisible(false);
                    dialog.dispose();
                     new RecepcionerFrame(korisnickoime);
                }else if(appMng.getKozmeticarMng().inKozmeticari(korisnickoime, lozinka)){
                    dialog.setVisible(false);
                    dialog.dispose();
                     new KozmeticarFrame(korisnickoime);
                }
                else{
                    JWindow jw = new JWindow();
                    MigLayout layoutwindow = new MigLayout("wrap","[]","[]15[]");
                    jw.setLayout(layoutwindow);
                    jw.setVisible(true);
                    jw.setLocationRelativeTo(null);
                    jw.setSize(340,100);

                    JButton btn = new JButton("Ok");
                    jw.add(new JLabel("Pogresno korisnicko ime ili lozinka! Pokusajte ponovo."));
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
            }

        }); 
        
        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        Register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                register();
            }
        });
    
    }

    public static void register(){
        JDialog frame = new JDialog();
        frame.setTitle("Registracija korisnika");
        frame.setLocationRelativeTo(null);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(255, 204, 255));
        
        registerInit(frame);
        frame.setVisible(true);
    }

    public static void registerInit(JDialog dialog){
        
        MigLayout layout = new MigLayout("wrap 2", "[][]","[]15[]15[]15[]15[]15[]15[]15[]15[]15[][]");
        dialog.setLayout(layout);

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

        dialog.getRootPane().setDefaultButton(bpotvrdi);
        dialog.add(new JLabel("Unesite podatke za registraciju: "),"span 2");
        dialog.add(new JLabel("Ime"));
        dialog.add(tfime);
        dialog.add(new JLabel("Prezime"));
        dialog.add(tfprezime);
        dialog.add(new JLabel("Adresa"));
        dialog.add(tfadresa);
        dialog.add(new JLabel("Broj telefona"));
        dialog.add(tfbroj);
        dialog.add(new JLabel("Pol: "));
        dialog.add(cbpol);
        dialog.add(new JLabel("Korisnicko ime"));
        dialog.add(tfkime);
        dialog.add(new JLabel("Lozinka"));
        dialog.add(pfpass);
        dialog.add(new JLabel("Ponovo unesite lozinku"));
        dialog.add(pfpass1);
        dialog.add(new JLabel());
        dialog.add(bpotvrdi, "split 2");
        dialog.add(bcancel);

        bcancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                dialog.setVisible(false);
                dialog.dispose();
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
                pol=pol.toUpperCase();
                if (ime.length() == 0 || prezime.length() == 0 || adresa.length() == 0 || broj.length() == 0 || korisnickoime.length() == 0 || lozinka.length() == 0 || lozinka1.length() == 0){
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
                //KlijentManager km = new KlijentManager("C:\\Users\\Vanja Kostic\\faks\\drugi semestar\\oop\\KozmetickiSalon\\data\\klijenti.csv");
                appMng.getKlijentMng().registracija(ime,prezime,broj,adresa,pol,korisnickoime,lozinka);
                dialog.setVisible(false);
                dialog.dispose();
            
            }
        });
    }   
}
