/*
 * A simple demonstration of three of the new Java 7 GUI features:
 * 	- A shaped window
 *  - Transparency of the window
 *  - Decorating (or drawing on) a component
 *  
 *  by Alan McLeod
 */

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.LayerUI;

public class Java7Frame extends JFrame {
	
	boolean startScreen = true;

	public Java7Frame(float alphaLevel) {

		super();
		
		
		Font fancyFont = new Font("OCR A Extended", Font.ITALIC, 30);
		Font biggerFont = new Font("Arial", Font.ITALIC, 18);
		Color myGray = new Color(0, 0, 0);
		setLayout(new BorderLayout());
		//JPanel southPanel = new JPanel(new FlowLayout());
		JPanel bottom = new JPanel(new GridLayout(2,1));
		
		Box middle = Box.createVerticalBox();
		bottom.setBackground(myGray);
		
		setUndecorated(true);	// If the window is shaped or translucent it cannot have a title bar
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Drizzy's Fireworks Extravaganza");
		setResizable(true);		// Cannot have title bar for shaped window
		setLocation(300, 200);
		String path = "C:\\Users\\9jc54\\workspace\\AssignmentFour9jc54\\src\\";
		setIconImage(new ImageIcon(path + "drake.jpg").getImage());
		Container contain = getContentPane();
		contain.setBackground(myGray);
		// Give the window an elliptical shape.
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
			}
		});
		
		// Make all of window translucent
		setOpacity(alphaLevel);

        JButton quitButton = new JButton("Close");
        quitButton.setFont(biggerFont);
        
        JButton enterButton = new JButton("Enter");
        enterButton.setFont(biggerFont);
        
        // An anonymous class for the button
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	startScreen = false;
            }
        });
        
        // Overlay a JLayer object on the button so you can draw on it
        LayerUI<JButton> layerUI = new ButtonLayerUI();
        JLayer<JButton> jLayer = new JLayer<JButton>(quitButton, layerUI);
       
        JLayer<JButton> jLayer2 = new JLayer<JButton>(enterButton, layerUI);
        
        bottom.add(jLayer2);
        bottom.add(jLayer);
        
        add(bottom, BorderLayout.SOUTH);
        //bottom.add(Box.createVerticalGlue());
        //bottom.add(Box.createRigidArea(new Dimension(400,50)));
        
        // Add a label to the window
        JLabel demoLabel = new JLabel("DRIZZY'S FIREWORKS ADVENTURE");
        JLabel demoLabel2 = new JLabel("~ this time i'm rly goin off ~");
        demoLabel.setFont(fancyFont);
        demoLabel2.setFont(biggerFont);
        demoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        demoLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        demoLabel.setForeground(Color.ORANGE);
        demoLabel2.setForeground(Color.RED);
        
        add(demoLabel, BorderLayout.CENTER);
        
	} // end Java7Frame constructor

	// Adds a drawing (in this case a gradient fill) to a component after it has been
	// drawn normally
	private class ButtonLayerUI extends LayerUI<JButton> {
		@Override
		public void paint(Graphics g, JComponent c) {
			super.paint(g, c);
			Graphics2D g2 = (Graphics2D)g.create();
			int w = c.getWidth();
			int h = c.getHeight();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
			g2.setPaint(new GradientPaint(0, 0, Color.YELLOW, 0, h, Color.PINK));
			g2.fillRect(0, 0, w, h);
			g2.dispose();
		} // end paint
	} // end ButtonLayerUI
	
	

} // end Java7Frame
