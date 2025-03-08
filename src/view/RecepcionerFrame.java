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

public class RecepcionerFrame extends JFrame{
    private static final long serialVersionUID = -8026201049950423764L;
    private TretmanManager tretmanMng;
    private String recepcioner;            //korisnicko ime trenutno ulogovanog korisnika

    public RecepcionerFrame(String korisnickoIme){
        this.recepcioner = korisnickoIme;
        recepcionerMeni();
    }

    public void recepcionerMeni(){

        setTitle("Kozmeticki salon");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600,600);
        initRecepcionerFrame();
        getContentPane().setBackground(new Color(255, 204, 255));
    }

    private void initRecepcionerFrame(){

        MigLayout layout = new MigLayout("wrap 3", "[][][]", "[]15[]15[]15[]15[]");
        this.setLayout(layout);

        JButton btnPrikazi = new JButton("Prikazi tretmane");
        JButton btnLogout = new JButton("LogOut");

        
        this.add(new JLabel());
        this.add(btnPrikazi);
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(btnLogout);
        this.add(new JLabel());

        btnPrikazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){

                TretmanTableFrame prikaz = new TretmanTableFrame(RecepcionerFrame.this, mainFrame.appMng.getTretmanMng(), null,null,recepcioner,null, mainFrame.appMng.getKlijentMng(),mainFrame.appMng.getKozmeticarMng(),mainFrame.appMng.getVrstaTretmanaMng(),mainFrame.appMng.getSalonInfoMng());
                prikaz.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da se odjavite?"," ODJAVITE SE", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							RecepcionerFrame.this.dispose();  
                            mainFrame main = new mainFrame(mainFrame.appMng);
						}
            }
        });
    
    
    }
}
