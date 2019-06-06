//Package: http://www.jfree.org/jfreechart/

package team1.userinterface;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

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
import team1.util.TraceV4;

/**
 * The PlotPanel provide the plot for the presentation of the calculations
 *
 */
public class PlotPanel extends JPanel {
	private TraceV4 trace = new TraceV4(this);
	private static final long serialVersionUID = 1L;

	private JFreeChart chart = ChartFactory.createXYLineChart("Titel", "Frequency[Hz]", "Insertion loss[dB]", null,
			PlotOrientation.VERTICAL, false, false, false);
	
	private Color[] colors = new Color[] {Color.black, Color.green, Color.blue, Color.red, Color.cyan, Color.gray, Color.magenta, Color.orange, Color.gray, Color.pink};

	/**
	 * Constructor of the plotpanel
	 * 
	 * @param title Title of the plot
	 */
	public PlotPanel(String title) {
		super(new BorderLayout());
		trace.constructorCall();

		setBorder(MyBorderFactory.createMyBorder(""));
		setPreferredSize(new Dimension(100, 100));
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

		xyplot.setRangeAxis(yAxis);
		xyplot.setDomainAxis(xAxis);

		for (int i = 0; i < colors.length; i++) {
			XYItemRenderer renderer = new StandardXYItemRenderer();
			renderer.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			renderer.setSeriesPaint(0, colors[i]);
			xyplot.setRenderer(i, renderer);
		}		

		add(new ChartPanel(chart));
	}

	/**
	 * This method makes the plots for the given datasets
	 * 
	 * @param Data Data of the plot
	 */
	public void setData(double[][][] Data) {
		trace.methodeCall();
		// cmData&dmData [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
		int filteriterator = 0;
		while (Data[filteriterator][0][0] != 0) {
			XYSeries series = new XYSeries("");
			for (int i = 0; i < Data[filteriterator][0].length; i++) {
				series.add(Data[filteriterator][0][i], Data[filteriterator][1][i]);
			}
			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			if (Data[filteriterator][0][0] == -1)
				chart.getXYPlot().setDataset(filteriterator, null);
			else
				chart.getXYPlot().setDataset(filteriterator, dataset);
			filteriterator++;
			chart.getXYPlot().getRenderer().setSeriesPaint(3, Color.ORANGE);
			chart.getXYPlot().setDataset(filteriterator, null); // in case a dataset got removed it delets the last dataseries														
		}
		
		repaint();
	}

	/**
	 * Update the plotpanel
	 * 
	 * @param obs Observable
	 * @param obj Object
	 */
	public void update(Observable obs, Object obj) {
		trace.methodeCall();
		Model model = (Model) obs;
		if (this.chart.getTitle().getText() == "CM") {
			setData(model.getCM());
		} else {
			setData(model.getDM());
		}
	}
}
