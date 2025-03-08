package view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

public class DijagramZastupljenost extends JFrame{
    public DijagramZastupljenost(){
        setTitle("Zastupljenost stanja tretmana");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 255));
        
        PieChart chart = new PieChartBuilder().width(500).height(500).title("Zastupljenost stanja tretmana u prethodnih 30 dana").build();
        Color[] sliceColors = new Color[] { new Color(225, 0, 225), new Color(204, 51, 255), new Color(153, 102, 255), new Color(153, 0, 204), new Color(255, 51, 153) };
        chart.getStyler().setSeriesColors(sliceColors);
        
        HashMap<String, Integer> zastupljenost = mainFrame.appMng.getTretmanMng().getZastupljenost();
        for (String key : zastupljenost.keySet()) {
          Integer value = zastupljenost.get(key);
          chart.addSeries(key, value);

        }

        XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);
        add(chartPanel);
      }
}
