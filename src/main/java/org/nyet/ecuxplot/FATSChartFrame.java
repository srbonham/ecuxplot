package org.nyet.ecuxplot;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class FATSChartFrame extends ChartFrame implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final FATSDataset dataset;
    private final ECUxPlot plotFrame;
    private final JTextField start;
    private final JTextField end;

    public static FATSChartFrame createFATSChartFrame(
	    TreeMap<String, ECUxDataset> fileDatasets, ECUxPlot plotFrame) {
	final FATSDataset dataset = new FATSDataset(fileDatasets);
	final JFreeChart chart =
	    ECUxChartFactory.createFATSChart(dataset);
	return new FATSChartFrame(chart, dataset, plotFrame);
    }

    public FATSChartFrame (JFreeChart chart, FATSDataset dataset,
	    ECUxPlot plotFrame) {
	super("FATS Time", chart);

	this.dataset=dataset;
	this.plotFrame=plotFrame;

	final CategoryPlot plot = chart.getCategoryPlot();
	plot.getRangeAxis().setLabel("seconds");
	final BarRenderer renderer = (BarRenderer)plot.getRenderer();

	renderer.setBaseItemLabelGenerator(
	    new org.jfree.chart.labels.StandardCategoryItemLabelGenerator(
		"{2}", new java.text.DecimalFormat("##.##")
	    )
	);
	renderer.setBaseItemLabelsVisible(true);

	final JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	final ECUxChartPanel chartPanel = new ECUxChartPanel(chart);
	// chartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	chartPanel.setBorder(BorderFactory.createLoweredBevelBorder());
	panel.add(chartPanel, BorderLayout.CENTER);


	final JPanel rpmPanel = new JPanel();
	rpmPanel.setLayout(new BoxLayout(rpmPanel, BoxLayout.LINE_AXIS));
	rpmPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	this.start=new JTextField(""+dataset.getStart(), 5);
	this.end=new JTextField(""+dataset.getEnd(), 5);
	rpmPanel.add(this.start);
	rpmPanel.add(new JLabel(" to "));
	rpmPanel.add(this.end);

	rpmPanel.add(Box.createRigidArea(new Dimension(5,0)));

	final JButton apply = new JButton("Apply");
	apply.addActionListener(this);
	this.getRootPane().setDefaultButton(apply);
	rpmPanel.add(apply);

	rpmPanel.add(Box.createRigidArea(new Dimension(5,0)));

	final JButton defaults = new JButton("Defaults");
	defaults.addActionListener(this);
	rpmPanel.add(defaults);

	panel.add(rpmPanel, BorderLayout.PAGE_END);

	this.setContentPane(panel);
	this.setPreferredSize(this.windowSize());
	restoreLocation();
    }

    private void restoreLocation() {
	final Toolkit tk = Toolkit.getDefaultToolkit();
	final Dimension d = tk.getScreenSize();
	final Dimension s = this.windowSize();
	final Point pl = this.plotFrame.getLocation();

	final Point l = this.windowLocation();
	l.translate(pl.x, pl.y);
	if(l.x<0) l.x=0;
	if(l.y<0) l.y=0;
	if(l.x+s.width > d.width-s.width) l.x=d.width-s.width;
	if(l.y+s.height > d.height-s.width) l.y=d.height-s.height;
	super.setLocation(l);
    }

    public void setDatasets(TreeMap<String, ECUxDataset> fileDatasets) {
	this.dataset.clear();
	for(final ECUxDataset data : fileDatasets.values())
	    setDataset(data);
    }

    public void setDataset(ECUxDataset data) {
	this.dataset.setValue(data);
    }
    public void clearDataset() {
	this.dataset.clear();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	if(event.getActionCommand().equals("Apply")) {
	    this.dataset.setStart(Integer.valueOf(this.start.getText()));
	    this.dataset.setEnd(Integer.valueOf(this.end.getText()));
	} else if(event.getActionCommand().equals("Defaults")) {
	    this.dataset.setStart(4200);
	    this.dataset.setEnd(6500);
	}
	this.getChartPanel().getChart().setTitle(this.dataset.getTitle());
    }

    private java.awt.Dimension windowSize() {
	return new java.awt.Dimension(
	    ECUxPlot.getPreferences().getInt("FATSWindowWidth", 300),
	    ECUxPlot.getPreferences().getInt("FATSWindowHeight", 400));
    }

    private void putWindowSize() {
	ECUxPlot.getPreferences().putInt("FATSWindowWidth",
		this.getWidth());
	ECUxPlot.getPreferences().putInt("FATSWindowHeight",
		this.getHeight());
    }

    // relative to plot frame
    private java.awt.Point windowLocation() {
	return new java.awt.Point(
	    ECUxPlot.getPreferences().getInt("FATSWindowX",
		this.plotFrame.getWidth()),
	    ECUxPlot.getPreferences().getInt("FATSWindowY", 0));
    }

    private void putWindowLocation() {
	final Point l = this.getLocation();
	final Point pl = this.plotFrame.getLocation();
	l.translate(-pl.x, -pl.y);
	ECUxPlot.getPreferences().putInt("FATSWindowX", l.x);
	ECUxPlot.getPreferences().putInt("FATSWindowY", l.y);
    }

    // cleanup
    @Override
    public void dispose() {
	putWindowSize();
	putWindowLocation();
	super.dispose();
    }

    public void saveChartAsPNG(File f) throws IOException {
           ChartUtilities.saveChartAsPNG(f, this.getChartPanel().getChart(), this.getWidth(),
                   this.getHeight());
    }

    public void saveChartAsPNG(String filename) throws IOException {
           this.saveChartAsPNG(new File(filename));
    }
}
