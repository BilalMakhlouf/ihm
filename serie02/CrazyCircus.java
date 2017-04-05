package serie02;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import serie02.PodiumManager.Order;
import serie02.PodiumManager.Rank;

public class CrazyCircus<E extends Drawable> {
    private JFrame frame;
    private JButton test,ki,lo,ma,ni,so;
    private PodiumManager<E> manager;
    
    CrazyCircus(Set<E> drawables) {
    	createModel(drawables);
        createView();
        placeComponents();
        createController();
    }
    
    void createModel(Set<E> drawables) {
    	manager = new StdPodiumManager(drawables);
    }
    
    void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createView() {
        
        frame = new JFrame();
        test = new JButton("Nouvelle Partie");
        ki = new JButton(Order.KI.toString());
        lo = new JButton(Order.LO.toString());
        ma = new JButton(Order.MA.toString());
        ni = new JButton(Order.NI.toString());
        so = new JButton(Order.SO.toString());
        
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(new GridLayout(0,1)); {
        	p.add(lo);
        	p.add(ki);
        	p.add(ma);
        	p.add(ni);
        	p.add(so);
            p.add(test);
        }
        frame.add(p, BorderLayout.EAST);
        p = new JPanel(); {
        	p.add(manager.getPodiums().get(Rank.WRK_LEFT));
        	p.add(manager.getPodiums().get(Rank.WRK_RIGHT));
        	p.add(manager.getPodiums().get(Rank.OBJ_LEFT));
        	p.add(manager.getPodiums().get(Rank.OBJ_RIGHT));
        }
        frame.add(p);
    }
    
    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	manager.reinit();
            }
        });
        
        lo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					manager.executeOrder(Order.LO);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        ki.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					manager.executeOrder(Order.KI);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        ma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					manager.executeOrder(Order.MA);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        ni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					manager.executeOrder(Order.NI);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        so.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					manager.executeOrder(Order.SO);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
    }
    
    private List<AnimalColor> listInit(int n) {
        List<AnimalColor> animals = new ArrayList<AnimalColor>();
        AnimalColor[] vals = AnimalColor.values();
        for (int i = 0; i < Math.min(n, vals.length); i++) {
            animals.add(vals[i]);
        }
        return animals;
    }
}
