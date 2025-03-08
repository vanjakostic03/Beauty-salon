package view;

import java.time.Month;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import net.miginfocom.swing.MigLayout;

public class IzvestajPrihodiRashodiFrame extends JFrame{
    private MenadzerFrame parent;


    public IzvestajPrihodiRashodiFrame(MenadzerFrame parent){
        this.parent = parent;

        setTitle("Prihodi i rashodi");
        setSize(200,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 255));

        MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
        this.setLayout(layout);
        JComboBox<Month> cbmeseci = new JComboBox<>(Month.values());
        this.add(cbmeseci);
        JButton btncancel = new JButton("cancel");

        cbmeseci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesec = cbmeseci.getSelectedItem().toString();

                Month m = Month.valueOf(mesec);
                double rashodi = 0.00;
                double prihodi = mainFrame.appMng.getTretmanMng().prihodi(m);
                rashodi += mainFrame.appMng.getRecepcionerMng().plate();
                rashodi += mainFrame.appMng.getMenadzerMng().plate();
                rashodi += mainFrame.appMng.getKozmeticarMng().plate();
                
                //potrebno je omoguciti da se prikazuju i ostali meseci pravilno tj. da se isprate plate radnika u to vreme i cuvaju negde 

                JFrame info = new JFrame("Informacije");
                info.setSize(300, 200);
                info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                info.setLocationRelativeTo(null);
                info.setVisible(true);
                info.getContentPane().setBackground(new Color(255, 204, 255));


                MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
                info.setLayout(layout);

                info.add(new JLabel(" Mesec: "));
                info.add(new JLabel(mesec),"wrap");
                info.add(new JLabel(),"split 2");
                
                info.add(new JLabel("Prihodi: "));
                info.add(new JLabel(String.valueOf(prihodi)),"wrap");
                info.add(new JLabel(),"split 2");

                info.add(new JLabel("Rashodi: "));
                info.add(new JLabel(String.valueOf(rashodi)),"wrap");
                info.add(new JLabel(),"split 2");
            }
        });

        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajPrihodiRashodiFrame.this.dispose();
            }
        });


    }
    
}
