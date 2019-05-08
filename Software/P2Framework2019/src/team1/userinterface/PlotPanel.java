package team1.userinterface;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import team1.model.Model;
import team1.util.MyBorderFactory;
import team1.util.Observable;

public class PlotPanel extends JPanel {

	private static final long serialVersionUID = -4522467773085225830L;
	private JFreeChart chart = ChartFactory.createXYLineChart("Titel", "Frequency[Hz]", "Insertion loss[dB]", null,
			PlotOrientation.VERTICAL, false, false, false);

	/**
	 * 
	 * @param title
	 */
	public PlotPanel(String title) {
		super(new BorderLayout());
		
		setBorder(MyBorderFactory.createMyBorder(""));
		setPreferredSize(new Dimension(100, 100));

		// Farben und Settings
		chart.setTitle(title);
		chart.setBackgroundPaint(getBackground());
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setBackgroundPaint(Color.WHITE);
		xyplot.setRangeGridlinePaint(Color.black);
		xyplot.setDomainGridlinePaint(Color.black);

		LogarithmicAxis xAxis = new LogarithmicAxis("Frequency");
		xAxis.setRange(10e3, 30e6);
		xAxis.setAutoRange(false);
		xAxis.setTickLabelsVisible(true);
		xAxis.setLog10TickLabelsFlag(true);
		xAxis.setExpTickLabelsFlag(true);
		xAxis.setAutoTickUnitSelection(false);
		xAxis.setMinorTickCount(9);
		xAxis.setAutoRangeNextLogFlag(true);
		
		NumberAxis yAxis = new NumberAxis("dB");
		yAxis.setTickLabelsVisible(true);
		yAxis.setAutoRange(true);
		yAxis.setTickLabelsVisible(true);
		yAxis.setRange(0, 150);
		
		xyplot.setRangeAxis(yAxis);
		xyplot.setDomainAxis(xAxis);
		
		XYItemRenderer renderer = new StandardXYItemRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		renderer.setSeriesPaint(0, Color.black);
		xyplot.setRenderer(0, renderer);

		renderer = new StandardXYItemRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		renderer.setSeriesPaint(0, Color.green);
		xyplot.setRenderer(1, renderer);

		add(new ChartPanel(chart));
	}

	/**
	 * This method makes the plots for the given datasets
	 * @param cmData contains the dataset for the common mode plot
	 * @param dmData contains the dataset for the differential mode plot
	 */
	public void setData(double[][][] cmData, double[][][] dmData) {
		// cmData&dmData [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
		XYPlot xyplot = chart.getXYPlot();
		if(this.chart.getTitle().getText() == "CM") {
			XYSeries series = new XYSeries("CM-Plot");
			for(int i=0; i<cmData[0][0].length; i++)
			{
				series.add(cmData[0][0][i], cmData[0][1][i]);
			}
			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			
			chart.getXYPlot().setDataset(0, dataset);
		}
		else {
			XYSeries series= new XYSeries("DM-Plot");
			for(int i=0; i<dmData[0][0].length; i++)
			{
				series.add(dmData[0][0][i], dmData[0][1][i]);
			}
			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			chart.getXYPlot().setDataset(0, dataset);
		}
		repaint();
	}

	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;
		setData(model.getCM(), model.getDM());
	}
}
