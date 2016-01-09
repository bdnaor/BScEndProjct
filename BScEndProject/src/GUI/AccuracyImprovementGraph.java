package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


@SuppressWarnings("serial")
public class AccuracyImprovementGraph extends JFrame 
{
	/*	Variables	*/
    private String title = "Accuracy Improvement / Adaboost Iteration";    
    private XYSeries Series1 = new XYSeries("Accuracy percent");
    private XYSeries Series2 = new XYSeries("Sensitivity percent");
    private XYSeries Series3 = new XYSeries("Specificity percent");  
    private int min1 = 1;  
    private int max1 = 101;
    private int min2 = 0;
    private int max2 = 100;
    
    /*	Constructor	*/
    public AccuracyImprovementGraph(String s,ArrayList<Integer> Iteration,ArrayList<Double> a,ArrayList<Double> sE,ArrayList<Double> sP, int max1X) 
    {
        super(s);
        max1=max1X;
        for(int i=0 ; i<Iteration.size() ; i++)
        {        	
        		Series1.add(Iteration.get(i),a.get(i));
        		Series2.add(Iteration.get(i),sE.get(i));
        		Series3.add(Iteration.get(i),sP.get(i));
        }
        
        final ChartPanel chartPanel = createDemoPanel();
        this.add(chartPanel, BorderLayout.CENTER);
    }

    /*	Methods	*/	
    private ChartPanel createDemoPanel() 
    {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(title, "X", "Y", createSampleData(),PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(min1,max1);//1-50
        domain.setTickUnit(new NumberTickUnit(2));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(min2,max2);//0-100
        range.setTickUnit(new NumberTickUnit(2));
        return new ChartPanel(jfreechart);
    }
    private XYDataset createSampleData() 
    {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();       
        xySeriesCollection.addSeries(Series1);
        xySeriesCollection.addSeries(Series2);
        xySeriesCollection.addSeries(Series3);
        return xySeriesCollection;
    }
}