package serie06;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import serie06.event.SentenceEvent;
import serie06.event.SentenceListener;
import serie06.model.Actor;
import serie06.model.Box;
import serie06.model.StdConsumer;
import serie06.model.StdProducer;
import serie06.model.UnsafeBox;

public class ProdCons {
    
    // ATTRIBUTS
    
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    
    private JFrame frame;
    private JButton btStart;
    private JTextArea jta;
    private ArrayList<Actor> actors;
    private Box box;

    // CONSTRUCTEURS
    
    public ProdCons() {
        createView();
        createModel();
        placeComponents();
        createController();
    }
    
    // COMMANDES
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // OUTILS
    
    private void createView() {
        frame = new JFrame();
        btStart = new JButton("Demarrer");
        
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        jta = new JTextArea();
        
        jta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        jta.setEditable(false);
    }
    
    public void createModel() {
    	box = new UnsafeBox();
    	actors = new ArrayList<Actor>();
    	
    	actors.add(new StdProducer(box, 10, "prod1"));
		actors.add(new StdProducer(box, 10, "prod2"));
		actors.add(new StdProducer(box, 10, "prod3"));
		actors.add(new StdConsumer(box, 10, "cons1"));
		actors.add(new StdConsumer(box, 10, "cons2"));
		actors.add(new StdConsumer(box, 10, "cons3"));
    	
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
            p.add(btStart);
        }
        frame.add(p, BorderLayout.NORTH);
        JScrollPane jsp = new JScrollPane(jta);
        p = new JPanel(new GridLayout(1, 1)); {
        	p.add(jsp);
        }
        frame.add(p, BorderLayout.CENTER);
    }
    
    private void createController() {
        // FENETRE
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int i = 0; i < 5; i++) {
        	actors.get(i).addSentenceListener(new SentenceListener() {

				@Override
				public void sentenceSaid(SentenceEvent e) {
					jta.append(e.getSentence());
				}
        		
        	});
        }
        
        btStart.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int i = 0; i < actors.size(); i++) {
        			try {
        				actors.get(i).interruptAndWaitForInactivation();
        			} catch (AssertionError ex) {
        				//RIP
        			}
        			actors.get(i).start();
        		}
        	}
        });

    }
    
    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProdCons().display();
            }
        });
    }
}
