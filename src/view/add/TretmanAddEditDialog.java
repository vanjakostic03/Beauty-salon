package view.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentListener;

import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import entiteti.Klijent;
import entiteti.Kozmeticar;
import entiteti.Tretman;
import entiteti.VrstaTretmana;
import net.miginfocom.swing.MigLayout;
import view.TretmanTableFrame;
import view.mainFrame;
import manager.TretmanManager;
import manager.KlijentManager;
import manager.KozmeticarManager;
import manager.SalonInfoManager;

// import org.jdesktop.swingx.autocomplete.AutoCompleteComboBoxEditor;
// import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdatepicker.impl.JDatePickerImpl;

import java.util.Date;
import java.util.Properties;


import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;


public class TretmanAddEditDialog extends JDialog {
    private static final long serialVersionUID = -5247231764310200252L;
	private TretmanManager tretmanMng;
	private KozmeticarManager kozmeticarMng;
	private KlijentManager klijentMng;
	private SalonInfoManager salonMng;
	//private VrstaTretmanaManager vrstaTretmanaMng;
    private String klijent;
    private Tretman zaedit;
	private JFrame parent;
    public String[] kozmeticari;
    public String[] termini;

    public TretmanAddEditDialog(JFrame parent, TretmanManager tretmanMng,Tretman zaedit, KozmeticarManager kozmeticarMng, String klijent,KlijentManager klijentMng, SalonInfoManager salonInfoManager) {
		super(parent, true);
		this.parent = parent;

        if(zaedit == null){
            setTitle("Dodavanje");
        }else{
            setTitle("Izmena");
        }
		this.tretmanMng = tretmanMng;
		this.klijentMng = klijentMng;
        this.salonMng = salonInfoManager;
        this.kozmeticarMng = kozmeticarMng;
        //this.vrstaTretmanaMng = vrstaTretmanaManager;
        this.zaedit = zaedit;
        this.klijent = klijent;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}
    private void namestiKozmeticare(String[] kozm,JComboBox<String> cbkozmeticari){
        if(kozm != null){
            cbkozmeticari.removeAllItems();
            for(int i = 0; i< kozm.length ; i++){
                cbkozmeticari.addItem(kozm[i]);
            }
        }
    }

    private void initGUI(){
        MigLayout layout = new MigLayout("wrap 2", "[][]","[]15[]15[]15[]15[]15[]15[][]");
        setLayout(layout);
        
        String vrstetretmana[] = mainFrame.appMng.getVrstaTretmanaMng().stringsVrsteTretmana();
        //JComboBox<String> cbtipovi = new JComboBox<>(vrstetretmana);
        JList<String> ltipovi = new JList<>(vrstetretmana);
        JTextField searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
            private void filterList() {
                String searchText = searchField.getText().toLowerCase();
                ArrayList<String> filteredItems = new ArrayList<>();
                for (String item : vrstetretmana) {
                    if (item.toLowerCase().contains(searchText)) {
                        filteredItems.add(item);
                    }
                }
                ltipovi.setListData(filteredItems.toArray(new String[0]));
            }
        });
        JComboBox<String> cbkozmeticari = new JComboBox<>();
        cbkozmeticari.addItem("Odaberite prvo tip tretmana");
        
        ltipovi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ltipovi.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String tip = ltipovi.getSelectedValue();
                // String[] options = generateOptions(selectedCategory);
                int idVrsta =Integer.parseInt(tip.substring(0, 1));
                VrstaTretmana vt = mainFrame.appMng.getVrstaTretmanaMng().vratiVrstaTretmanaid(idVrsta);
                kozmeticari = kozmeticarMng.obuceniKozmeticari(vt.getTip());
                namestiKozmeticare(kozmeticari,cbkozmeticari);
            }
        });
        
        JComboBox<String> cbtermini = new JComboBox<>();
        cbtermini.addItem("Odaberite prvo datum");

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date datum = (Date) datePicker.getModel().getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String datumString = dateFormat.format(datum);
                String kozmeticar = (String) cbkozmeticari.getSelectedItem();
                int idkozm = Integer.parseInt(kozmeticar.substring(0, 1));
                ArrayList<LocalTime> lista = salonMng.getTermini();
                termini = tretmanMng.termini(idkozm, datumString, lista);
                try{
                    cbtermini.removeAllItems();
                    for(int i = 0; i<termini.length ; i++){
                        cbtermini.addItem(termini[i]);
                    }
                }catch(NullPointerException ex){
                    cbtermini.addItem("Nema slobodnih termina za odabrani datum");
                }
            }
        });
         

        JButton bpotvrdi = new JButton("Potvrdi");
        JButton bcancel = new JButton("Cancel");

        
        getRootPane().setDefaultButton(bpotvrdi);
        add(new JLabel("Unesite podatke za zakazivanje tretmana: "),"span 2");
        add(new JLabel("Tip"));
        add(searchField,"wrap");
        //add(cbtipovi);
        add(new JLabel());
        add(ltipovi);
        add(new JLabel("Kozmeticar"));
        add(cbkozmeticari);

        //String klijenti[] = KlijentManager.stringKlijenti();
        String klijenti[] = mainFrame.appMng.getKlijentMng().stringKlijenti();
        JComboBox<String> cbklijenti = new JComboBox<>(klijenti);
        JTextField tftermin = new JTextField(20);

        
        if (klijent == null){
        add(new JLabel("Klijent"));
        add(cbklijenti);
        }

        add(new JLabel("Datum"), "alignx right");
        add(datePicker, "wrap");
        add(new JLabel("Termin"));
        add(cbtermini);
    
        add(new JLabel());
        add(bpotvrdi, "split 2");
        add(bcancel);

        if(zaedit != null){
            
            tftermin.setText(zaedit.getTermin()+"");
            String stringkozmeticar = "";
            Kozmeticar k = kozmeticarMng.vratiKozmeticaraid(zaedit.getIdKozmeticar());
            ArrayList<Kozmeticar> lista = new ArrayList<>();
            lista.add(k);
            stringkozmeticar = kozmeticarMng.stringKozmeticari(lista)[0];
            // for(int i = 0; i<kozmeticari.length ; i++){
            //     String polja[] = kozmeticari[i].split(";");
            //     int id = Integer.parseInt(polja[0]);
            //     if(zaedit.getIdKozmeticar() == id){
            //         stringkozmeticar = kozmeticari[i];
            //     }
            // }
            cbkozmeticari.setSelectedItem(stringkozmeticar);

            
            String stringklijent="";
            for(int i = 0; i<klijenti.length ; i++){
                String polja[] = klijenti[i].split(";");
                int id = Integer.parseInt(polja[0]);
                if(zaedit.getIdKlijent() == id){
                    stringklijent = klijenti[i];
                }
            }
            cbklijenti.setSelectedItem(stringklijent);

            String stringTretmani="";
            for(int i = 0; i<vrstetretmana.length ; i++){
                String polja[] = vrstetretmana[i].split(";");
                int id = Integer.parseInt(polja[0]);
                if(zaedit.getIdVrstaTretman() == id){
                    stringklijent = vrstetretmana[i];
                }
            }
            cbklijenti.setSelectedItem(stringTretmani);


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
                //String tip = (String) cbtipovi.getSelectedItem();
                String tip = (String) ltipovi.getSelectedValue();
                String kozmeticar = (String) cbkozmeticari.getSelectedItem();
                String kl = (String) cbklijenti.getSelectedItem();
                String vreme = (String) cbtermini.getSelectedItem();

                Date datum = (Date) datePicker.getModel().getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String datumString = dateFormat.format(datum);
                
                if(zaedit == null){
                    
                    if (vreme.length() == 0 || tip.length() == 0 || kozmeticar.length() == 0 || kl.length() ==0 || datumString.length() ==0){
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
                    }else if(datum.before(new Date())){
                    JWindow jw = new JWindow();
                        MigLayout layoutwindow = new MigLayout("wrap","[]","[]15[]");
                        jw.setLayout(layoutwindow);
                        jw.setVisible(true);
                        jw.setLocationRelativeTo(null);
                        jw.setSize(300,100);

                        JButton btn = new JButton("Ok");
                        jw.add(new JLabel("Nevalidan datum! Pokusajte ponovo."));
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

                    String polja1[] = kozmeticar.split(";"); 
                    String polja2[] = kl.split(";"); 
                    String polja3[] = tip.split(";");
                    int id = Integer.parseInt(polja2[0]);
                    Klijent k = new Klijent();
                    if(klijent != null){
                        k=klijentMng.vratiKlijentaKorisnicko(klijent);
                        polja2[0] = String.valueOf(k.getId());
                    }else
                        k = klijentMng.vratiKlijentaid(id);
                    boolean popust = false;
                    if(k.getImaKarticu()){
                        popust = true;
                    }
                    int trajanje = Integer.parseInt(polja3[2]);
                    String termin = datumString + " " + vreme;
                    Tretman tt = tretmanMng.zakaziTretman(polja1[0], polja2[0], termin, polja3[0],popust,trajanje);
                    klijentMng.vratiKlijentuNovac(tt.getCena()*(-1), k);
                    salonMng.dodajUKasu(tt.getCena());
                    
                }
                else{
                    String polja1[] = kozmeticar.split(";"); 
                    String polja2[] = klijent.split(";"); 
                    String polja3[] = tip.split(";"); 
                    String termin = datumString + " " + vreme;
                    tretmanMng.izmeniTretman(zaedit.getId(), polja1[0], polja2[0],termin, polja3[0]);
                

                }
                    ((TretmanTableFrame) parent).refreshData();
                    TretmanAddEditDialog.this.dispose();
                
            }
        });

    }
}
