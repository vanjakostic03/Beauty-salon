package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import manager.TretmanManager;
import net.miginfocom.swing.MigLayout;

public class KlijentFrame extends JFrame{
    private static final long serialVersionUID = -8026201049950423764L;
    private TretmanManager tretmanMng;
    private String klijent;            //korisnicko ime trenutno ulogovanog korisnika

    public KlijentFrame(String korisnickoIme){
        this.klijent = korisnickoIme;
        klijentMeni();
    }

    public void klijentMeni(){

        setTitle("Kozmeticki salon");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600,600);
        initKlijentFrame();
        getContentPane().setBackground(new Color(255, 204, 255));
    }

    private void initKlijentFrame(){

        MigLayout layout = new MigLayout("wrap 3", "[][][]", "[]15[]15[]15[]15[]");
        this.setLayout(layout);

        JButton btnPrikazi = new JButton("Prikazi aktuelne tretmane");
        JButton btnIstorija = new JButton("Prikazi istoriju tretmana");
        JButton btnKartica = new JButton("Prikazi podatke o kartici lojalnosti");
        JButton btnLogout = new JButton("LogOut");

        
        this.add(new JLabel());
        this.add(btnPrikazi);
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(btnIstorija);
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(btnKartica);
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(btnLogout);
        this.add(new JLabel());

        btnPrikazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){

                TretmanTableFrame prikaz = new TretmanTableFrame(KlijentFrame.this, mainFrame.appMng.getTretmanMng(), klijent,null,null,null, mainFrame.appMng.getKlijentMng(),mainFrame.appMng.getKozmeticarMng(),mainFrame.appMng.getVrstaTretmanaMng(),mainFrame.appMng.getSalonInfoMng());
                prikaz.setVisible(true);
            }
        });

        btnIstorija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){

                IstorijaFrame istorija = new IstorijaFrame(KlijentFrame.this, mainFrame.appMng.getTretmanMng(), klijent);
                istorija.setVisible(true);
            }
        });

        btnKartica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                KarticaFrame kartica = new KarticaFrame(klijent,mainFrame.appMng.getKlijentMng());
                kartica.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da se odjavite?"," ODJAVITE SE", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							KlijentFrame.this.dispose();  
                            mainFrame main = new mainFrame(mainFrame.appMng);
						}
            }
        });
    }
}
