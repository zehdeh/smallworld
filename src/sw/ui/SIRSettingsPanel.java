package sw.ui;

import java.awt.Component;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

import sw.Configuration;

public class SIRSettingsPanel extends JPanel {
	public static final long serialVersionUID = 41L;
	public SIRSettingsPanel(Configuration configuration, Window window) {
		super();

		setBorder(BorderFactory.createTitledBorder("SIR settings"));
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(boxLayout);

		JLabel alphaSliderLabel = new JLabel("Alpha", JLabel.CENTER);
		alphaSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(alphaSliderLabel);

		int defaultAlpha = 50;
		JSlider alphaSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, defaultAlpha);
		window.setAlpha(defaultAlpha/100.0);
		alphaSlider.setMajorTickSpacing(50);
		alphaSlider.setPaintTicks(true);
		alphaSlider.setPaintLabels(true);
		Hashtable<Integer,JLabel> labelTable = new Hashtable<Integer,JLabel>();
		labelTable.put(new Integer(0), new JLabel("0.0"));
		labelTable.put(new Integer(50), new JLabel("0.5"));
		labelTable.put(new Integer(100), new JLabel("1.0"));
		alphaSlider.setLabelTable(labelTable);
		alphaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				window.setAlpha(((JSlider)e.getSource()).getValue()/100.0);
			}
		});
		add(alphaSlider);

		JLabel gammaSliderLabel = new JLabel("Gamma", JLabel.CENTER);
		gammaSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(gammaSliderLabel);

		int defaultGamma = 50;
		JSlider gammaSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, defaultGamma);
		window.setGamma(defaultGamma/100.0);
		gammaSlider.setMajorTickSpacing(50);
		gammaSlider.setPaintTicks(true);
		gammaSlider.setPaintLabels(true);
		gammaSlider.setLabelTable(labelTable);
		gammaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				window.setGamma(((JSlider)e.getSource()).getValue()/100.0);
			}
		});
		add(gammaSlider);
	}
}
