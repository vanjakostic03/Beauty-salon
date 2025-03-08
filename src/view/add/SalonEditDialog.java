package view.add;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entiteti.Salon;
import net.miginfocom.swing.MigLayout;
import view.SalonTableFrame;
import manager.SalonInfoManager;

public class SalonEditDialog extends JDialog{
    private static final long serialVersionUID = -5247231764310200252L;
	private SalonInfoManager salonMng;
    private Salon zaedit;
	private JFrame parent;

    public SalonEditDialog(JFrame parent, SalonInfoManager salonMng,Salon zaedit) {
		super(parent, true);
		this.parent = parent;

        setTitle("Izmena");
    
		this.salonMng = salonMng;
        this.zaedit = zaedit;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

    private void initGUI(){
        MigLayout layout = new MigLayout("wrap 2", "[][]","[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[]15[][]");
        setLayout(layout);

        JTextField tfnaziv = new JTextField(20);
        JTextField tfpocetak = new JTextField(20);
        JTextField tfkraj = new JTextField(20);

        JButton btn = new JButton("Ok");
        JButton bcancel = new JButton("cancel");
        getRootPane().setDefaultButton(btn);
        add(new JLabel("Unesite podatke za kreiranje salona: "),"span 2");
        add(new JLabel("Naziv"));
        add(tfnaziv);
        add(new JLabel("Pocetak radnog vremena u formatu HH:mm"));
        add(tfpocetak);
        add(new JLabel("Kraj radnog vremena u formatu HH:mm"));
        add(tfkraj);
        add(new JLabel());
        add(btn,"span 2");
        add(new JLabel());
    
        tfnaziv.setText(zaedit.getNaziv());
        tfpocetak.setText(zaedit.getPocetak()+"");
        tfkraj.setText(zaedit.getKraj()+"");
            
        

        bcancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                 setVisible(false);
                 dispose();
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                String naziv = tfnaziv.getText().trim();
                String pocetak = tfpocetak.getText().trim();
                String kraj = tfkraj.getText().trim();
            
                salonMng.kreirajizmeniSalon(naziv,pocetak,kraj,0.00,0.00,0.00,0);
                ((SalonTableFrame) parent).refreshData();
                SalonEditDialog.this.dispose();
                
            }
        });
    }
}