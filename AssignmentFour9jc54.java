/*
 *STUDENT NAME: Johannes Chan
 *STUDENT NUMBER: 06209784
 *NET ID: 9jc54
 */
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel; 
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;




public class AssignmentFour9jc54 extends JFrame {

	// By convention, size values are placed here, so that they can be easily re-coded.
	private final int WIDTH = 1000;
	private final int HEIGHT = 900;
	private final int candleLength = 50;
    private final int candleWidth = 20;
	
	
	private AffineTransform transform = null;
    private Graphics2D gDraw;
	private Timer clockTimer;
	private BufferedImage candle = null;
	
	ArrayList<Particle> fireworks;
    ParticleManager manager = null;
	
	// Textfield example
	// The other components do not need to be class attributes as they are only needed
	// in the constructor.
	// Each is created to be wide enough to hold 8 characters.
	//private JTextField RfValue = new DefaultEditableTextField(8);

	
	//sliders
	JSlider windSlider;
	JSlider angleSlider;
	private final int windMax = 20;
	private final int windMin = -20;
	private final int angleMax = 15;
	private final int angleMin = -15;
	
	double startTime;
	static int windSpeed;
	static int launchAngle;
	static boolean launchFlag = false;
	Color bgGray = new Color(35, 35, 35);
	
	double starXPos;
	double starYPos;
	double starRadius;
	int rocketHeight;
	int rocketWidth;
	int initialX, initialY, xScale, yScale;
	
	
	
	DrawPanel dPanel;
			
		
	
	// The constructor for this class.
	public AssignmentFour9jc54 () {
		
		// We should call the constructor for JFrame
		super();
		String path = "C:\\Users\\9jc54\\workspace\\AssignmentFour9jc54\\src\\";
		setIconImage(new ImageIcon(path + "drake.jpg").getImage());
		Color bgColour = new Color(0, 0, 0);
		
		// Set the basic characteristics of the window.
		setLayout(new BorderLayout());
		setTitle("Drizzy's Fireworks Adventure");
		// Clicking on the "X" close button will close the window and end the program.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contain = getContentPane();
		contain.setBackground(bgColour);
		setSize(WIDTH, HEIGHT);
		// Move the initial startup position of our window down a bit.
		setLocation(50, 50);

		// Two different font sizes are used here.
		Font fTop = new Font("Arial", Font.PLAIN, 20);
		Font fRest = new Font("Arial", Font.BOLD, 16);
		

		//DrawPanel
		dPanel = new DrawPanel();
		
		//sliders
		windSlider = new JSlider(JSlider.HORIZONTAL, windMin, windMax, 0);
		angleSlider = new JSlider(JSlider.HORIZONTAL, angleMin, angleMax, 0);
		JLabel windSetting = new DefaultLabel("Wind Speed (km/h right)");
		JLabel angleSetting = new DefaultLabel("Launch Angle (degrees)");

		//botom panel
		JPanel bottomPanel = new JPanel(new GridLayout(3,2));
		bottomPanel.setBackground(bgColour);
		bottomPanel.add(windSetting);
		bottomPanel.add(windSlider);
		bottomPanel.add(angleSetting);
		bottomPanel.add(angleSlider);
		
		//more slider settings
		windSetting.setForeground(Color.WHITE);
		angleSetting.setForeground(Color.WHITE);
		
		windSlider.setMajorTickSpacing(5);
		windSlider.setMinorTickSpacing(1);
		windSlider.setPaintLabels(true);
		windSlider.setPaintTicks(true);
		windSlider.setBackground(bgColour);
		windSlider.setForeground(Color.WHITE);
		windSlider.addChangeListener(new WindListener());
		
		angleSlider.setMajorTickSpacing(5);
		angleSlider.setMinorTickSpacing(1);
		angleSlider.setPaintLabels(true);
		angleSlider.setPaintTicks(true);
		angleSlider.setBackground(bgColour);
		angleSlider.setForeground(Color.WHITE);
		angleSlider.addChangeListener(new AngleListener());
		
		//exit button
		JButton exitButton = new JButton("#exit");
		exitButton.setFont(fRest);
		exitButton.setBackground(Color.RED);
		exitButton.setForeground(Color.WHITE);
		exitButton.addActionListener(new EndingListener());
		Box exitButtonBox = Box.createVerticalBox();
		exitButtonBox.add(exitButton);
		exitButtonBox.add(Box.createRigidArea(new Dimension(130,15)));
		bottomPanel.add(exitButtonBox);
		
		// LAUNCH BUTTON
		JButton launchButton = new JButton("LAUNCH");
		launchButton.setFont(fRest);	
		Box launchButtonBox = Box.createVerticalBox();
		launchButtonBox.add(launchButton);
		launchButtonBox.add(Box.createRigidArea(new Dimension(130,15)));
		bottomPanel.add(launchButtonBox);
		launchButton.addActionListener(new ActionListener(){	
		public void actionPerformed(ActionEvent e){
			 windSpeed = windSlider.getValue();
	         launchAngle = angleSlider.getValue();
	         
	         try {
	            	manager = new ParticleManager(windSpeed, launchAngle);
	            } catch (EnvironmentException except) {
	            	System.out.println(except.getMessage());
	            	return;
	            } catch (EmitterException except) {
	            	System.out.println(except.getMessage());			
	            	return;
	            }
	         
			launchFlag = true;
			
			 startTime = System.currentTimeMillis();
	            manager.start(0);
	            fireworks = manager.getFireworks(0);
	            
	            candle = new  BufferedImage(dPanel.getWidth(), dPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
	            gDraw = candle.createGraphics();
	 			transform = new AffineTransform();
	 			clockTimer.start();
			
		}
		});
		
		// Combine the left and right panels into centreBox
		dPanel.setSize(1000, 600);
		dPanel.setBackground(bgColour);
		dPanel.repaint();
		//centreBox.add(Box.createRigidArea(new Dimension(1,800)));
		
		// LAYOUTTT
		add(dPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		//Timer
		int delay = 17; // milliseconds
        clockTimer = new Timer(delay, new ClockListener());
		
		
	} // end AmplifierWindowV2 constructor

	private class DrawPanel extends JPanel{
		
		public DrawPanel (){
			super();	
			
			//attempt to add background
			/*
			try{
				image = ImageIO.read(new File("bg_pic.jpg"));
			} catch (IOException ex){
				//handle exception...
			}
			}
			*/
		}
		
		/*
		public DrawPanel (LayoutManager lm){
			super(lm);
		}
		*/
		
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g2D = (Graphics2D)g;
			
			
			//int topleft = (int)(getSize().width / 1000);
			
			//.getSize().width;
			/*rocketHeight = (int)(0.1*height);
			rocketWidth = (int)(rocketHeight/3);
			
				
				*/
				
				//rocket
				g.setColor(Color.MAGENTA);
				g.fillRect((dPanel.getWidth()-candleWidth)/2,dPanel.getHeight()-candleLength,candleWidth,candleLength);
				
				
			g.setColor(Color.MAGENTA);
			//g.drawImage(image, 0, 0, null);
			
			g2D.drawImage(candle, 0,0, null);
			if (candle != null) {	
				transform.setToRotation(Math.toRadians(launchAngle), candle.getWidth()/2, candle.getHeight());
				
				//clear DrawPanel
				g.setColor(bgGray);
				g.fillRect(0,0,dPanel.getWidth(), dPanel.getHeight());
					
					
				//rocket
				g.setColor(Color.MAGENTA);
				g.fillRect((dPanel.getWidth()-candleWidth)/2,dPanel.getHeight()-candleLength,candleWidth,candleLength);
				
				if (fireworks.size() > 0){
					for (Particle firework : fireworks) {
						if (firework instanceof Star){
							g.setColor(firework.getColour());
							g.fillOval((int)(initialX + firework.getPosition()[0]*xScale), (int)(initialY - firework.getPosition()[1]*yScale - rocketHeight/2), firework.getRenderSize(), firework.getRenderSize());
						}
						else if (firework instanceof StarSpark){
							g.setColor(firework.getColour());
							g.fillOval((int)(initialX + firework.getPosition()[0]*xScale), (int)(initialY - firework.getPosition()[1]*yScale - rocketHeight/2), firework.getRenderSize(), firework.getRenderSize());
						}
						else if (firework instanceof LaunchSpark){
							g.setColor(firework.getColour());
							g.fillOval((int)(initialX + firework.getPosition()[0]*xScale), (int)(initialY - firework.getPosition()[1]*yScale - rocketHeight/2), firework.getRenderSize(), firework.getRenderSize());
						}
						else{
							g.setColor(firework.getColour());
							g.fillOval((int)(initialX + firework.getPosition()[0]*xScale), (int)(initialY - firework.getPosition()[1]*yScale - rocketHeight/2), firework.getRenderSize(), firework.getRenderSize());
						}
					}
				}
				
				else{
					clockTimer.stop();
				}
				
				}
				
				
			
		}
		
	}
	
	
	private class DefaultLabel extends JLabel {
		public DefaultLabel(String label) {
			super(label);
			Font fRest = new Font("Arial", Font.BOLD, 16);
			setFont(fRest);
			setHorizontalAlignment(JLabel.CENTER);
		}
	} // end DefaultLabel
	
	
	private class DefaultEditableTextField extends JTextField {
		public DefaultEditableTextField(int size) {
			super(size);
			Font fRest = new Font("Arial", Font.BOLD, 16);
			setFont(fRest);
		}
	} // end DefaultEditableTextField
	
	private class DefaultNonEditableTextField extends DefaultEditableTextField {
		public DefaultNonEditableTextField(int size) {
			super(size);
			setEditable(false);
		}
	} // end DefaultNonEditableTextField
	
	
	// Carried out by exit button
	private class EndingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Just close the window and halt the program.
			clockTimer.stop();
			System.exit(0);
		} // end actionPerformed method
	} // end EndingListener class

	
	private class WindListener implements ChangeListener {
		public void stateChanged(ChangeEvent cEvent) {
			windSpeed = windSlider.getValue();
			
		}
		
	}
	
	private class AngleListener implements ChangeListener {
		public void stateChanged(ChangeEvent cEvent) {
			launchAngle = angleSlider.getValue();
			
		}
		
	}
	
	
	private class ClockListener implements ActionListener {
		
    	
    	public ClockListener() {
        	super();
        	//diameter = 0;
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		
    		
    		gDraw.setColor(Color.RED);
			gDraw.fillRect(dPanel.getWidth()/2 - candleWidth/2, dPanel.getHeight()-candleLength, candleWidth, candleLength);
			
			int width = (int)(dPanel.getWidth());
			int height = (int)(dPanel.getHeight());
			initialX = dPanel.getWidth()/2;
			initialY = dPanel.getHeight()-(candleLength/2);
			xScale = dPanel.getWidth()/25;
			yScale = dPanel.getHeight()/25;
			
			
			fireworks = manager.getFireworks((System.currentTimeMillis() - startTime)/1000);
    		dPanel.repaint();
    		
    		
    		
        }
        
    } // end ClockListener

	public static void main(String[] args) {
		
		//start screen
		float alphaLevel = 0.85F; 	// 0 is transparent, 1 is fully opaque
		Java7Frame j7f = new Java7Frame(alphaLevel);
		
		while(j7f.startScreen){
		j7f.setVisible(true);
		}
		j7f.setVisible(false);
		
		
		AssignmentFour9jc54 dispWindow = new AssignmentFour9jc54();
		dispWindow.setVisible(true);
	}
	
} // end AmplifierWindowV2 class
