package serie01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class EventsTester {

    // ATTRIBUTS
    
    private JFrame mainFrame;
    private JFrame testFrame;
    private JButton createFrame;
    private JButton razCpt;
    
    private JPanel areasPanel;
    private ArrayList<JTextArea> areas;
    private ArrayList<String> names;
    
    private int cpt;
    private int resetCpt;

    // CONSTRUCTEURS
    
    public EventsTester() {
    	createModel();
        createView();
        placeComponents();
        createController();
    }
    
    // COMMANDES
    
    private void createModel() {
    	cpt = 0;
    	resetCpt = 0;
    	areas = new ArrayList<JTextArea>();
    	for (int i = 0; i < 8; i++) {
    		areas.add(new JTextArea());
    	}
    	
    	names = new ArrayList<String>();
    	names.add("WindowListener");
    	names.add("KeyListener");
    	names.add("MouseListener");
    	names.add("MouseWheelListener");
    	names.add("FocusListener");
    	names.add("MotionListener");
    	names.add("FocusListener");
    	names.add("WindowStateListener");
    }
    
    /**
     * Rend l'application visible au centre de l'écran.
     */
    public void display() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // OUTILS
    
    private void createView() {
        final int frameWidth = 800;
        final int frameHeight = 800;
        
        mainFrame = new JFrame("Test sur les évenements - ZONE D'AFFICHAGE");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        
        createFrame = new JButton("Nouvelle Fenetre");
        razCpt = new JButton("Reset compteur");
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(createFrame);
            p.add(razCpt);
        }
        mainFrame.add(p, BorderLayout.NORTH);

    	areasPanel = new JPanel(new GridLayout(0, 3));
    	for (int i = 0; i < areas.size(); i++) {
    		JScrollPane jsp = new JScrollPane(areas.get(i));
    		jsp.setBorder(
    				BorderFactory.createTitledBorder(
    						BorderFactory.createLineBorder(Color.black),
    						names.get(i)));
    		areasPanel.add(jsp);
    	}
    	mainFrame.add(areasPanel, BorderLayout.CENTER);
    }
    
    private void createNewTestFrame() {
    	if (testFrame != null) {
    		testFrame.dispose();
    	}
		testFrame = new JFrame("ZONE DE TEST");
    	
    	final int frameWidth = 200;
        final int frameHeight = 100;
        
        testFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		
        createEventListeners();
        
		testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
    }
    
    private void createEventListeners() {
    	
    	testFrame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
        		areas.get(0).append(cpt++ + " Window Opened Event \n");
            }

            public void windowClosing(WindowEvent e) {
            	areas.get(0).append(cpt++ + " Window Closing Event \n");
            }

            public void windowClosed(WindowEvent e) {
            	areas.get(0).append(cpt++ + " Window Close Event \n");
            }

            public void windowIconified(WindowEvent e) {
            	areas.get(0).append(cpt++ + " Window Iconified Event \n");
            }

            public void windowDeiconified(WindowEvent e) {
            	areas.get(0).append(cpt++ + " Window Deiconified Event \n");
            }

            public void windowActivated(WindowEvent e) {
            	areas.get(0).append(cpt++ + " Window Activated Event \n");
            }

            public void windowDeactivated(WindowEvent e) {
            	areas.get(0).append(cpt++ + " Window Deactivated Event \n");
            }
    	});
    	
    	testFrame.addKeyListener(new KeyAdapter() {
			 public void keyPressed(KeyEvent e) {
				 areas.get(1).append(cpt++ + " Key Pressed Event \n");
			 }
			 public void keyReleased(KeyEvent e) {
				 areas.get(1).append(cpt++ + " Key Released Event \n");
			 }
			 public void keyTyped(KeyEvent e) {
				 areas.get(1).append(cpt++ + " Key Typed Event \n");
			 }
    	});
    	
    	testFrame.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 areas.get(2).append(cpt++ + " Mouse Clicked Event \n");
			 }
			 public void mouseEntered(MouseEvent e) {
				 areas.get(2).append(cpt++ + " Mouse Entered Event \n");
			 }
			 public void mouseExited(MouseEvent e) {
				 areas.get(2).append(cpt++ + " Mouse Exited Event \n");
			 }
			 public void mousePressed(MouseEvent e) {
				 areas.get(2).append(cpt++ + " Mouse Pressed Event \n");
			 }
			 public void mouseReleased(MouseEvent e) {
				 areas.get(2).append(cpt++ + " Mouse Released Event \n");
			 }
    	});
    	
    	testFrame.addMouseWheelListener(new MouseWheelListener() {
    		@Override
    		public void mouseWheelMoved(MouseWheelEvent e) {
    			areas.get(3).append(cpt++ + " MouseWheel Moved Event \n");
    		}
    	});
    	
    	testFrame.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				areas.get(4).append(cpt++ + " Focus gained \n");
			}

			@Override
			public void focusLost(FocusEvent e) {
				areas.get(4).append(cpt++ + " Focus lost \n");				
			}
    		
    	});
    	
    	testFrame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				areas.get(5).append(cpt++ + " Mouse dragged \n");
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				areas.get(5).append(cpt++ + " Mouse moved \n");
			}
    		
    	});
    	
    	testFrame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				areas.get(6).append(cpt++ + " Window Gained Focus \n");
			}

			@Override
			public void windowLostFocus(WindowEvent arg0) {
				areas.get(6).append(cpt++ + " Window Lost Focus \n");
			}
    		
    	});
    	
    	testFrame.addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent e) {
				areas.get(7).append(cpt++ + " Window state changed \n");
			}
    		
    	});
    }
    
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        createFrame.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		createNewTestFrame();
        	}
        	
        });
        
        razCpt.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		resetCpt++;
        		for (int i = 0; i < areas.size(); i++) {
        			areas.get(i).append("------RAZ " + resetCpt + "-----\n");
        		}
        		cpt = 0;
        	}
        	
        });
        
    }

    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EventsTester().display();
            }
        });
    }
}
