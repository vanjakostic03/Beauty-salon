package view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

public class angazovanjeDijagram extends JFrame {

    public angazovanjeDijagram(){
        setTitle("Angazovanja kozmeticara");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 204, 255));
        setLocationRelativeTo(null);
        
        
        PieChart chart = new PieChartBuilder().width(500).height(500).title("Angazovanje kozmeticara u poslednjih 30 dana").build();
        Color[] sliceColors = new Color[] { new Color(225, 0, 225), new Color(204, 51, 255), new Color(153, 102, 255), new Color(153, 0, 204), new Color(255, 51, 153) };
        chart.getStyler().setSeriesColors(sliceColors);
        
        HashMap<String, Integer> angazovanje = mainFrame.appMng.getTretmanMng().getAngazovanje();
        for (String key : angazovanje.keySet()) {
          Integer value = angazovanje.get(key);
          chart.addSeries(key, value);

        }
        // chart.addSeries("Gold", 24);
        // chart.addSeries("Silver", 21);
        // chart.addSeries("Platinum", 39);
        // chart.addSeries("Copper", 17);
        // chart.addSeries("Zinc", 40);

        XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);
        add(chartPanel);

        // PieChart chart = new PieChartBuilder().width(800).height(600).title("Broj realizovanih tretmana po kozmeticaru u poslednjih 30 dana").build();
        // Thread t = new Thread(() -> new SwingWrapper<>(chart).displayChart().setDefaultCloseOperation(DISPOSE_ON_CLOSE));
        // t.start();
      }
}
