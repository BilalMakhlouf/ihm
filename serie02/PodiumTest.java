package serie02;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PodiumTest {
    private JFrame frame;
    private JButton test, change;
    private Podium<AnimalColor> podium;
    
    PodiumTest() {
        createView();
        placeComponents();
        createController();
    }
    
    void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createView() {
        PodiumModel<AnimalColor> pm =
            new StdPodiumModel<AnimalColor>(listInit(3), 5);
        podium = new Podium<AnimalColor>(pm);
        frame = new JFrame();
        test = new JButton("Test");
        change = new JButton("Change Model");
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(test);
            p.add(change);
        }
        frame.add(p, BorderLayout.NORTH);
        p = new JPanel(); {
            p.add(podium);
        }
        frame.add(p);
    }
    
    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PodiumModel<AnimalColor> m = podium.getModel();
                AnimalColor anim = m.bottom();
                m.removeBottom();
                m.addTop(anim);
            }
        });
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = (int) (Math.random() * 4) + 3;
                podium.setModel(
                    new StdPodiumModel<AnimalColor>(listInit(n), n + 2)
                );
                ((JPanel) podium.getParent()).revalidate();
                frame.pack();
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PodiumTest().display();
            }
        });
    }
}
