package view;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JWindow;
import manager.AppManager;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;

public class MenadzerFrame extends JFrame{
    private static final long serialVersionUID = 8456560429229699543L;
    private String menadzer;
    //public mainFrame parent;
    //private AppManager a;
    //MenadzerTableFrame menadzerTbl;
    
    public MenadzerFrame(boolean setup,AppManager a,String menadzer ){
        this.menadzer = menadzer;
       // this.parent = parent;
        //this.a=a;
        if(setup == false)
            setupDialog(a);
        menadzerMeni();
    }

    public void setupDialog(AppManager a){
        setTitle("SetUp");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600,600);
        getContentPane().setBackground(new Color(255, 204, 255));
        initSetUp(a);
        
    }

    public void initSetUp(AppManager a){
        
            JDialog jd = new JDialog();
            MigLayout layoutdialog = new MigLayout("wrap","[][]","[]15[]15[]15[]15[]15[]15[]15[][]");
            jd.setLayout(layoutdialog);
            jd.setTitle("Setup");
            jd.setVisible(true);
            jd.setLocationRelativeTo(null);
            jd.setSize(500,300);
            jd.getContentPane().setBackground(new Color(255, 204, 255));

            JTextField tfnaziv = new JTextField(20);
            JTextField tfpocetak = new JTextField(20);
            JTextField tfkraj = new JTextField(20);

            JButton btn = new JButton("Ok");
            jd.getRootPane().setDefaultButton(btn);

            jd.add(new JLabel("Unesite podatke za kreiranje salona: "),"span 2");
            jd.add(new JLabel("Naziv"));
            jd.add(tfnaziv);
            jd.add(new JLabel("Pocetak radnog vremena u formatu HH:mm"));
            jd.add(tfpocetak);
            jd.add(new JLabel("Kraj radnog vremena u formatu HH:mm"));
            jd.add(tfkraj);
            jd.add(new JLabel());
            jd.add(btn,"span 2");
            jd.add(new JLabel());

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae){
                    String naziv = tfnaziv.getText().trim();
                    String pocetak = tfpocetak.getText().trim();
                    String kraj = tfkraj.getText().trim();
            
                    if (naziv.length() == 0 || pocetak.length() == 0 || kraj.length() == 0){
                        
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
                    }else{

                        a.getSalonInfoMng().kreirajizmeniSalon(naziv, pocetak, kraj,0.00,0.00,0.00,0);
                        jd.setVisible(false);
                        jd.dispose();
                    }              
                }
            });
    }

    public void menadzerMeni(){

        
        setTitle("Kozmeticki salon");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600,600);
        initMenadzerFrame();
        getContentPane().setBackground(new Color(255, 204, 255));


    }
    private void initMenadzerFrame(){

        JMenuBar menadzerMenu = new JMenuBar();

        JMenu prikaziMenu = new JMenu("Prikazi");
        JMenuItem prikazMenadzeri = new JMenuItem("menadzeri");
        JMenuItem prikazKlijenti = new JMenuItem("klijenti");
        JMenuItem prikazKozmeticari = new JMenuItem("kozmeticari");
        JMenuItem prikazRecepcioneri = new JMenuItem("recepcioneri");
        JMenuItem prikazTretmani = new JMenuItem("tretmani");
        JMenuItem prikazCenovnik = new JMenuItem("cenovnik");
        JMenuItem prikazVrsteTretmana = new JMenuItem("vrste tretmana");
        JMenuItem prikazTipovaTretmana = new JMenuItem("tipovi tretmana");
        JMenuItem prikazSalona = new JMenuItem("Salon info");

        prikaziMenu.add(prikazMenadzeri);
        prikaziMenu.add(prikazCenovnik);
        prikaziMenu.add(prikazKlijenti);
        prikaziMenu.add(prikazKozmeticari);
        prikaziMenu.add(prikazRecepcioneri);
        prikaziMenu.add(prikazTretmani);
        prikaziMenu.add(prikazVrsteTretmana);
        prikaziMenu.add(prikazTipovaTretmana);
        prikaziMenu.add(prikazSalona);

        menadzerMenu.add(prikaziMenu);

        JMenu izvestajiMenu = new JMenu("Izvestaji");
        JMenuItem izvestajKozmeticari = new JMenuItem("Tretmani i prihodi kozmeticara");
        JMenuItem izvestajOtkazivanje = new JMenuItem("Statistika otkazivanja tretmana");
        JMenuItem izvestajVrsteTretmana = new JMenuItem("Analiza vrste tretmana");
        JMenuItem izvestajLojalniKlijenti = new JMenuItem("Lojalni klijenti");
        JMenuItem izvestajPrihodiRashodi = new JMenuItem("Prihodi i rashodi");

        izvestajiMenu.add(izvestajKozmeticari);
        izvestajiMenu.add(izvestajOtkazivanje);
        izvestajiMenu.add(izvestajVrsteTretmana);
        izvestajiMenu.add(izvestajLojalniKlijenti);
        izvestajiMenu.add(izvestajPrihodiRashodi);

        menadzerMenu.add(izvestajiMenu);

        JMenu dijagramiMenu = new JMenu("Dijagrami");
        JMenuItem dijagramPrihodi = new JMenuItem("Prihodi u poslednjih god. dana");
        JMenuItem dijagramAngazovanje = new JMenuItem("Angazovanje kozmeticara u poslednjih 30 dana");
        JMenuItem dijagramZastupljenost = new JMenuItem("Zastupljenost tretmana u poslednjih 30 dana");

        dijagramiMenu.add(dijagramPrihodi);
        dijagramiMenu.add(dijagramAngazovanje);
        dijagramiMenu.add(dijagramZastupljenost);

        menadzerMenu.add(dijagramiMenu);


        this.setJMenuBar(menadzerMenu);


        prikazMenadzeri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                MenadzerTableFrame mtf = new MenadzerTableFrame(MenadzerFrame.this, mainFrame.appMng.getMenadzerMng());
                mtf.setVisible(true);
            }
        });

        prikazRecepcioneri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                RecepcionerTableFrame rtf = new RecepcionerTableFrame(MenadzerFrame.this, mainFrame.appMng.getRecepcionerMng());
                rtf.setVisible(true);
            }
        });

        prikazKozmeticari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                KozmeticarTableFrame ktf = new KozmeticarTableFrame(MenadzerFrame.this, mainFrame.appMng.getKozmeticarMng());
                ktf.setVisible(true);
            }
        });

        prikazKlijenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                KlijentTableFrame ktf = new KlijentTableFrame(MenadzerFrame.this, mainFrame.appMng.getKlijentMng(),false,mainFrame.appMng.getSalonInfoMng() );
                ktf.setVisible(true);
            }
        });

        prikazCenovnik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                CenovnikTableFrame ctf = new CenovnikTableFrame(MenadzerFrame.this, mainFrame.appMng.getCenovnikMng());
                ctf.setVisible(true);
            }
        });

        prikazTretmani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                TretmanTableFrame ttf = new TretmanTableFrame(MenadzerFrame.this, mainFrame.appMng.getTretmanMng(),null,null,null,menadzer,mainFrame.appMng.getKlijentMng(),mainFrame.appMng.getKozmeticarMng(),mainFrame.appMng.getVrstaTretmanaMng(),mainFrame.appMng.getSalonInfoMng());
                ttf.setVisible(true);
            }
        });

        prikazVrsteTretmana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                VrstaTretmanaTableFrame vttf = new VrstaTretmanaTableFrame(MenadzerFrame.this, mainFrame.appMng.getVrstaTretmanaMng(),mainFrame.appMng.getCenovnikMng(),mainFrame.appMng.getTipTretmanaMng());
                vttf.setVisible(true);
            }
        });

        prikazTipovaTretmana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                TipTretmanaTableFrame tttf = new TipTretmanaTableFrame(MenadzerFrame.this, mainFrame.appMng.getTipTretmanaMng(),mainFrame.appMng.getVrstaTretmanaMng(),mainFrame.appMng.getCenovnikMng());
                tttf.setVisible(true);
            }
        });

        prikazSalona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                SalonTableFrame stf = new SalonTableFrame(MenadzerFrame.this, mainFrame.appMng.getSalonInfoMng());
                stf.setVisible(true);
            }
        });

        izvestajKozmeticari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajKozmeticariFrame ikf = new IzvestajKozmeticariFrame(MenadzerFrame.this);
                ikf.setVisible(true);
            }
        });

        izvestajOtkazivanje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajiOtkazivanjeFrame iof = new IzvestajiOtkazivanjeFrame(MenadzerFrame.this);
                iof.setVisible(true);
            }
        });

        izvestajVrsteTretmana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajVrsteTretmanaFrame ivtf = new IzvestajVrsteTretmanaFrame(MenadzerFrame.this);
                ivtf.setVisible(true);
            }   
        });

        izvestajLojalniKlijenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                KlijentTableFrame ktf = new KlijentTableFrame(MenadzerFrame.this, mainFrame.appMng.getKlijentMng(),true,mainFrame.appMng.getSalonInfoMng());
                ktf.setVisible(true);
            }
        });

        izvestajPrihodiRashodi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajPrihodiRashodiFrame iprf = new IzvestajPrihodiRashodiFrame(MenadzerFrame.this);
                iprf.setVisible(true);
            }
        });

        dijagramAngazovanje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                angazovanjeDijagram ad = new angazovanjeDijagram();
                ad.setVisible(true);
            }
        });

        dijagramZastupljenost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                DijagramZastupljenost dz = new DijagramZastupljenost();
                dz.setVisible(true);
            }
        });
        
        dijagramPrihodi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                DijagramPrihodi dp = new DijagramPrihodi();
                dp.setVisible(true);
            }
        });
        
        // prihodi.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e){
        //         IzvestajPrihodiRashodiFrame iprf = new IzvestajPrihodiRashodiFrame(MenadzerFrame.this);
        //         iprf.setVisible(true);
        //     }
        // });
        // dijagramAngazovanje.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e){
        //         PieChart chart = new PieChartBuilder().width(800).height(600).title("Broj realizovanih tretmana po kozmeticaru u poslednjih 30 dana").build();
        //         Thread t = new Thread(() -> new SwingWrapper<>(chart).displayChart().setDefaultCloseOperation(DISPOSE_ON_CLOSE));
        //         t.start();
        //     }
        // });
        // zastupljenost.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e){
        //         IzvestajPrihodiRashodiFrame iprf = new IzvestajPrihodiRashodiFrame(MenadzerFrame.this);
        //         iprf.setVisible(true);
        //     }
        // });


        MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
        this.setLayout(layout);

        JButton podesiPrag =new JButton("Podesi prag lojalnosti");
        JButton podesiBonus =new JButton("Podesi bonus");
        JButton prikaziLojalne =new JButton("Prikazi lojalne korisnike");
        JButton logout =new JButton("Logout");
        
        this.add(new JLabel("Dobrodosli"),"span 2");
        this.add(new JLabel());
        this.add(podesiPrag, "span 2");
        this.add(new JLabel());
        this.add(podesiBonus, "span 2");
        this.add(new JLabel());
        this.add(logout, "span 2");
        this.add(new JLabel());

        podesiPrag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JDialog prag = new JDialog();
                prag.setVisible(true);
                prag.setSize(300,300);
                prag.setLocation(600, 300);
                prag.getContentPane().setBackground(new Color(255, 204, 255));
                MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");

                prag.setLayout(layout);
                JTextField unos = new JTextField(20);
                JButton ok = new JButton("OK");
                JButton cancel = new JButton("cancel");

                prag.add(new JLabel("Trenutni prag: "));
                String vrednost = String.valueOf(mainFrame.appMng.getSalonInfoMng().getSalonInfo().getPrag());
                prag.add(new JLabel(vrednost), "wrap");
                prag.add(new JLabel(),"span 2");
                prag.add(new JLabel("Unesite novi prag: "));
                prag.add(unos);
                prag.add(new JLabel(),"span 2");
                prag.add(ok);
                prag.add(cancel);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        try{
                            String u = unos.getText().trim();
                            double vrednost = Double.parseDouble(u);
                            mainFrame.appMng.getSalonInfoMng().setPrag(vrednost);
                        }catch(NullPointerException ex){}
                        prag.dispose();
                    }
                });
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        prag.dispose();
                    }
                });
            }
        });


        podesiBonus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JDialog bonus = new JDialog();
                bonus.setVisible(true);
                bonus.setSize(300,300);
                bonus.setLocation(600, 300);
                bonus.getContentPane().setBackground(new Color(255, 204, 255));
                MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");

                bonus.setLayout(layout);
                JTextField unos1 = new JTextField(20);
                JTextField unos2 = new JTextField(20);
                JButton ok = new JButton("OK");
                JButton cancel = new JButton("cancel");

                bonus.add(new JLabel("Trenutni iznos bonusa: "));
                String vrednost = String.valueOf(mainFrame.appMng.getSalonInfoMng().getSalonInfo().getBonus());
                bonus.add(new JLabel(vrednost));
                bonus.add(new JLabel(),"span 2");

                bonus.add(new JLabel("Trenutni broj termina: "));
                String br = String.valueOf(mainFrame.appMng.getSalonInfoMng().getSalonInfo().getBrTretmana());
                bonus.add(new JLabel(br));
                bonus.add(new JLabel(),"span 2");

                bonus.add(new JLabel("Unesite novi iznos bonusa: "));
                bonus.add(unos1);
                bonus.add(new JLabel(),"span 2");
                bonus.add(new JLabel("Unesite novi broj termina: "));
                bonus.add(unos2);
                bonus.add(new JLabel(),"span 2");
                bonus.add(ok);
                bonus.add(cancel);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        try{
                            String u1 = unos1.getText().trim();
                            String u2 = unos2.getText().trim();
                            double value  = Double.parseDouble(u1);
                            int broj  = Integer.parseInt(u2);
                            mainFrame.appMng.getSalonInfoMng().podesiBonus(value);
                            mainFrame.appMng.getSalonInfoMng().podesiBrTretmana(broj);
                            //set kozmeticarima bonus
                            
                        }catch(NullPointerException ex){}
                        bonus.dispose();
                    }
                });
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        bonus.dispose();
                    }
                });
            }
        });

        prikaziLojalne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                KlijentTableFrame ktf = new KlijentTableFrame(MenadzerFrame.this, mainFrame.appMng.getKlijentMng(),true,mainFrame.appMng.getSalonInfoMng());
                ktf.setVisible(true);     
            }
        });
        
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da se odjavite?"," ODJAVITE SE", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							MenadzerFrame.this.dispose();  
                            mainFrame main = new mainFrame(mainFrame.appMng);
						}
            }
        });
        

        
    }

    

    


}
