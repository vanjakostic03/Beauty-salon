package view;

import java.util.Date;
import java.util.Properties;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import manager.TretmanManager;
import net.miginfocom.swing.MigLayout;

public class IzvestajiOtkazivanjeFrame extends JFrame{
    
    private JFrame parent;
    private TretmanManager tretmanMng;
    public IzvestajiOtkazivanjeFrame(JFrame parent){
        this.parent = parent;
        //this.vrstaTretmanaMng = v;
        this.tretmanMng = mainFrame.appMng.getTretmanMng();

        setTitle("Otkazivanje tretmana");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 255));

        MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
        this.setLayout(layout);

    
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl pocetak = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        
        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        JDatePickerImpl kraj = new JDatePickerImpl(datePanel2, new DateComponentFormatter());
        
        JButton btnpotvrdi = new JButton("ok");
        JButton btncancel = new JButton("cancel");


        this.add(new JLabel("Pocetni datum: "));
        this.add(pocetak,"wrap");
        this.add(new JLabel(),"split 2");

        this.add(new JLabel("Krajnji datum: "));
        this.add(kraj,"wrap");
        this.add(new JLabel(),"split 2");

        this.add(btnpotvrdi);
        this.add(btncancel);
    
        
        btnpotvrdi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(pocetak.getModel().getValue() == null || kraj.getModel().getValue()==null){
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
                }else if(((Date) pocetak.getModel().getValue()).after((Date) kraj.getModel().getValue())){
                    JWindow jw = new JWindow();
                        MigLayout layoutwindow = new MigLayout("wrap","[]","[]15[]");
                        jw.setLayout(layoutwindow);
                        jw.setVisible(true);
                        jw.setLocationRelativeTo(null);
                        jw.setSize(300,100);

                        JButton btn = new JButton("Ok");
                        jw.add(new JLabel("Pocetni datum je nakon krajnjeg! Pokusajte ponovo."));
                        jw.add(btn);

                        btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae){
                                jw.setVisible(false);
                                jw.dispose();
                                
                            }
                        });
                }
                else{
                    Date pocetakDateUtil = (Date) pocetak.getModel().getValue();
                    LocalDate pocetakDate = pocetakDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Date krajDateUtil = (Date) kraj.getModel().getValue();
                    LocalDate krajDate = krajDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String lista[] = tretmanMng.otkazaniTretmani(pocetakDate, krajDate);
                    

                    JFrame info = new JFrame("Informacije");
                    info.setSize(500, 300);
                    info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    info.setLocationRelativeTo(null);
                    info.setVisible(true);
                    info.getContentPane().setBackground(new Color(255, 204, 255));

                    MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
                    info.setLayout(layout);

                    info.add(new JLabel("Izvrsenih: "));
                    info.add(new JLabel(lista[0]),"wrap");
                    info.add(new JLabel(),"split 2");
                    
                    info.add(new JLabel("Otkazanih od strane klijenta: "));
                    info.add(new JLabel(lista[1]),"wrap");
                    info.add(new JLabel(),"split 2");

                    info.add(new JLabel("Otkazanih od strane salona: "));
                    info.add(new JLabel(lista[2]),"wrap");
                    info.add(new JLabel(),"split 2");

                    info.add(new JLabel("Klijent se nije pojavio: "));
                    info.add(new JLabel(lista[3]),"wrap");
                    info.add(new JLabel(),"split 2");
                }
                }
                
        });

        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajiOtkazivanjeFrame.this.dispose();
            }
        });
    }
}
