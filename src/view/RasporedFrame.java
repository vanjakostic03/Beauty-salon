package view;

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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class RasporedFrame extends JFrame {
    private JFrame parent;
    private String kozmeticar;
    private TretmanManager tretmanMng;
    public RasporedFrame(JFrame parent,TretmanManager t,String kozmeticar){
        this.parent = parent;
        this.tretmanMng = t;
        this.kozmeticar = kozmeticar;

        setTitle("Raspored kozmeticara");
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
        JDatePickerImpl datum = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        
        JButton btnpotvrdi = new JButton("ok");
        JButton btncancel = new JButton("cancel");


        this.add(new JLabel("Izaberite datum: "));
        this.add(datum,"wrap");
        this.add(new JLabel(),"split 2");

        this.add(btnpotvrdi);
        this.add(btncancel);
    
        btnpotvrdi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(datum.getModel().getValue() == null){
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

            
                Date datumDateUtil = (Date) datum.getModel().getValue();
                LocalDate datumDate = datumDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                JFrame info = new JFrame("Raspored kozmeticara");
                info.setSize(500, 300);
                info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                info.setLocationRelativeTo(null);
                info.setVisible(true);
                info.getContentPane().setBackground(new Color(255, 204, 255));

                MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[]15[]15[]15[]15[]15[]");
                info.setLayout(layout);

                ArrayList<LocalTime> termini = tretmanMng.raspored(kozmeticar, datumDate);
                if(termini.size() != 0 ){
                    for(LocalTime lt: termini){
                    info.add(new JLabel(lt.toString()),"wrap");
                    info.add(new JLabel(),"split 2");
                    }
                }else{
                    info.add(new JLabel("Nema zakazanih tretmana za izabrani datum."));
                }
            }
            }
        });

        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                RasporedFrame.this.dispose();
            }
        });
    }
}
