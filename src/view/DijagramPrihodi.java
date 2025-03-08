package view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

public class DijagramPrihodi extends JFrame {
    public DijagramPrihodi(){
        setTitle("Mesecni prihodi u poslednjih godinu dana po tipovima tretmana");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 255));

        XYChart chart = new XYChartBuilder().width(600).height(400).title(getClass().getSimpleName()).xAxisTitle("Meseci").yAxisTitle("Prihodi").build();
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisDecimalPattern("din #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        List<Date> datumi = new ArrayList<>();
        

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); 

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i < 12; i++) {
            Date month = calendar.getTime();
            datumi.add(month);
            String formattedMonth = formatter.format(month);
            calendar.add(Calendar.MONTH, -1); // Pomeranje za jedan mesec unazad
        }
        
        HashMap<String, List<Double>> prihodi = mainFrame.appMng.getTretmanMng().getPrihodi();
        for (String key : prihodi.keySet()) {
            List<Double> value = prihodi.get(key);
            chart.addSeries(key, datumi, value);
        }

        XChartPanel<XYChart> chartPanel = new XChartPanel<>(chart);
        add(chartPanel);
    }
}