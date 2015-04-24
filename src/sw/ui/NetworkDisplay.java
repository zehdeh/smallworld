package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;

public class NetworkDisplay extends JComponent {
	Image image;
	Graphics2D graphics2D;
	public static final long serialVersionUID = 42L;

	public NetworkDisplay() {
		super();

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				initImage();
			}
		});
	}

	protected void initImage() {
		image = createImage(getSize().width, getSize().height);
		graphics2D = (Graphics2D)image.getGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void paintComponent(Graphics g) {
		clear();

		g.drawImage(image,0,0,null);
	}

	public void clear() {
		graphics2D.setPaint(Color.blue);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}
}
