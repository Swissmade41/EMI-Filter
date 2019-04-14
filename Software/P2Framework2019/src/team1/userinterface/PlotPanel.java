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

import team1.matlabfunctions.MikroMatlab;
import team1.util.MyBorderFactory;
import team1.util.Observable;
import team1.util.TraceV4;

public class PlotPanel extends JPanel {
	private TraceV4 trace = new TraceV4(this);

	private static final long serialVersionUID = -4522467773085225830L;
	private JFreeChart chart = ChartFactory.createXYLineChart("Titel", "Frequency[Hz]", "Insertion loss[dB]", null,
			PlotOrientation.VERTICAL, false, false, false);

	public PlotPanel(String title) {
		super(new BorderLayout());
		trace.constructorCall();
		
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
		xAxis.setRange(0, 30e6);
		xAxis.setAutoRange(false);
		xAxis.setTickLabelsVisible(true);
		xAxis.setLog10TickLabelsFlag(true);
		xAxis.setExpTickLabelsFlag(true);
		xAxis.setMinorTickCount(1000);
		
		NumberAxis yAxis = new NumberAxis("dB");
		yAxis.setTickLabelsVisible(true);
		yAxis.setAutoRange(true);
		yAxis.setTickLabelsVisible(true);
		
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

		// Test:
		update(null, null);
	}

	public void setData(double[] x, double[] y1, double[] y2) {
		trace.methodeCall();
		XYPlot xyplot = chart.getXYPlot();

		XYSeries series = new XYSeries("Plot1");
		for (int i = 1; i < x.length; i++) {
			series.add(x[i], y1[i]);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		chart.getXYPlot().setDataset(0, dataset);

		series = new XYSeries("Plot2");
		for (int i = 1; i < x.length; i++) {
			series.add(x[i], y2[i]);
		}
		dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		chart.getXYPlot().setDataset(1, dataset);

		repaint();
	}

	public void update(Observable obs, Object obj) {
		trace.methodeCall();
		double[] n = MikroMatlab.linspace(0, 100, 101);

		double[] cos = new double[n.length];
		double[] sin = new double[n.length];

		setData(n, cos, sin);
	}
}
