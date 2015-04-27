package sw.ui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

import sw.Configuration;
import sw.graph.Graph;

public class GraphSettingsPanel extends JPanel {
	public static final long serialVersionUID = 41L;
	public GraphSettingsPanel(Configuration conf, Graph graph, NetworkDisplay networkDisplay) {
		super();

		final int minNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "min"));
		final int maxNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "max"));
		final int defaultNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "default"));

		setBorder(BorderFactory.createTitledBorder("Graph settings"));
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(boxLayout);

		JLabel nodeSliderLabel = new JLabel("Number of nodes", JLabel.CENTER);
		nodeSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(nodeSliderLabel);

		JSlider nodeSlider = new JSlider(JSlider.HORIZONTAL, minNumNodes, maxNumNodes, defaultNumNodes);
		nodeSlider.setMinorTickSpacing(Math.abs(maxNumNodes-minNumNodes)/8);
		nodeSlider.setMajorTickSpacing(Math.abs(maxNumNodes-minNumNodes)/4);
		nodeSlider.setPaintTicks(true);
		nodeSlider.setPaintLabels(true);
		add(nodeSlider);

		JLabel probabilitySliderLabel = new JLabel("Rewiring probability", JLabel.CENTER);
		probabilitySliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(probabilitySliderLabel);

		JSlider probabilitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		probabilitySlider.setMajorTickSpacing(50);
		probabilitySlider.setPaintTicks(true);
		probabilitySlider.setPaintLabels(true);
		Hashtable<Integer,JLabel> labelTable = new Hashtable<Integer,JLabel>();
		labelTable.put(new Integer(0), new JLabel("0.0"));
		labelTable.put(new Integer(50), new JLabel("0.5"));
		labelTable.put(new Integer(100), new JLabel("1.0"));
		probabilitySlider.setLabelTable(labelTable);
		probabilitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				graph.rewire(((JSlider)e.getSource()).getValue()/100.0);
				networkDisplay.repaint();
			}
		});
		add(probabilitySlider);

		nodeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				graph.setNumberNodes(((JSlider)e.getSource()).getValue());
				networkDisplay.repaint();

				probabilitySlider.setValue(0);
			}
		});
	}
}
