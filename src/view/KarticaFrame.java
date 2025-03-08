package view;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import entiteti.Klijent;
import net.miginfocom.swing.MigLayout;

import manager.KlijentManager;

public class KarticaFrame extends JFrame{
    private String kime;
    private KlijentManager klijentMng;
    public Klijent klijent = new Klijent();

    public KarticaFrame(String kime, KlijentManager klijentMng){
        this.kime = kime;
        this.klijentMng = klijentMng;

        setTitle("Kartica lojalnosti");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initKartica();
        getContentPane().setBackground(new Color(255, 204, 255));
        
    }

    public void initKartica(){
        ArrayList<Klijent> klijenti = this.klijentMng.getKlijenti();
        for(Klijent k : klijenti){
            if(k.getKorisnickoIme().equals(kime)){
                klijent = k;
                break;
            }
        }

        MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
        this.setLayout(layout);
        JButton napravi = new JButton("Napravi karticu lojalnosti");

        if(!klijent.getPravoNaKarticu()){
            this.add(new JLabel("Pravo na karticu: "));
            this.add(new JLabel("NE"),"wrap");
            this.add(new JLabel(),"span 2");
        }else if(klijent.getPravoNaKarticu() && !klijent.getImaKarticu()){
            this.add(new JLabel("Pravo na karticu: "));
            this.add(new JLabel("DA"),"wrap");
            this.add(new JLabel(),"span 2");
            this.add(napravi,"wrap");
        }else if(klijent.getImaKarticu()){
            this.add(new JLabel("Imate karticu: "));
            this.add(new JLabel("DA"),"wrap");
            this.add(new JLabel(),"span 2");
        }

        this.add(new JLabel("Dosadasnja potrosnja: "));
        this.add(new JLabel( String.valueOf(klijent.getPotrosenNovac())));
    
        napravi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                klijentMng.napraviKarticu(klijent);
                dispose();
            }
        });
    
    }

}
