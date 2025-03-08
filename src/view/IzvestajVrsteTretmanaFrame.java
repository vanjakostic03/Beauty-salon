package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entiteti.VrstaTretmana;
import manager.TretmanManager;
import manager.VrstaTretmanaManager;
import net.miginfocom.swing.MigLayout;

public class IzvestajVrsteTretmanaFrame extends JFrame{
    private VrstaTretmanaManager vrstaTretmanaMng;
    private TretmanManager tretmanMng;
    private JFrame parent;

    public IzvestajVrsteTretmanaFrame(JFrame parent){
        this.parent = parent;
        this.vrstaTretmanaMng = mainFrame.appMng.getVrstaTretmanaMng();
        this.tretmanMng = mainFrame.appMng.getTretmanMng();

        setTitle("Analiza vrsta tretmana");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 255));
        
        MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
        this.setLayout(layout);

        JComboBox<String> cbtretmani = new JComboBox<>(vrstaTretmanaMng.stringsVrsteTretmana());
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

        this.add(new JLabel("Vrsta tretmana: "));
        this.add(cbtretmani,"wrap");
        this.add(new JLabel(),"split 2");

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
                if(cbtretmani == null || pocetak.getModel().getValue()==null || kraj.getModel().getValue()==null){
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
                String vrsta = (String) cbtretmani.getSelectedItem();
                int idVrsta = Integer.parseInt(vrsta.substring(0, 1));
                VrstaTretmana vt = vrstaTretmanaMng.vratiVrstaTretmanaid(idVrsta);
                
                Date pocetakDateUtil = (Date) pocetak.getModel().getValue();
                LocalDate pocetakDate = pocetakDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                Date krajDateUtil = (Date) kraj.getModel().getValue();
                LocalDate krajDate = krajDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String lista[]=tretmanMng.analizaVrstaTretmana(vt, pocetakDate, krajDate);

                JFrame info = new JFrame("Informacije");
                info.setSize(500, 300);
                info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                info.setLocationRelativeTo(null);
                info.setVisible(true);
                info.getContentPane().setBackground(new Color(255, 204, 255));


                MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
                info.setLayout(layout);

                info.add(new JLabel("Naziv i tip: "));
                info.add(new JLabel(lista[0]),"wrap");
                info.add(new JLabel(),"split 2");
                
                info.add(new JLabel("Cena: "));
                info.add(new JLabel(lista[1]),"wrap");
                info.add(new JLabel(),"split 2");

                info.add(new JLabel("Trajanje: "));
                info.add(new JLabel(lista[2]),"wrap");
                info.add(new JLabel(),"split 2");

                info.add(new JLabel("Broj zakazanih tretmana: "));
                info.add(new JLabel(lista[3]),"wrap");
                info.add(new JLabel(),"split 2");

                info.add(new JLabel("Ukupni prihodi za period: "));
                info.add(new JLabel(lista[4]),"wrap");
                info.add(new JLabel(),"split 2");
                }
            }
        });

        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                IzvestajVrsteTretmanaFrame.this.dispose();
            }
        });
    }


}
