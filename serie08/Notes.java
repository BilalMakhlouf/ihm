package serie08;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import serie08.gui.NoteSheet;
import serie08.model.gui.ColumnFeatures;

public class Notes {

    // ATTRIBUTS

    private static final String DEFAULT_DIR = System.getProperty("user.home");
    private static final int TOTAL = 100;

    private NoteSheet noteSheetBean;
    private JButton load;
    private JButton save;
    private JLabel mean;
    private JLabel points;
    private JFrame frame;
    private JFileChooser chooser;
    private JProgressBar progress;
    
    // CONSTRUCTEURS
    
    public Notes() {
        createView();
        placeComponents();
        createController();
    }

    // REQUETES
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // COMMANDES

    // OUTILS
    
    private void createView() {
        frame = new JFrame("Gestionnaire de notes");
        noteSheetBean = new NoteSheet();
        load = new JButton("Load...");
        save = new JButton("Save as...");
        mean = new JLabel();
        points = new JLabel();
        chooser = new JFileChooser(DEFAULT_DIR);
        progress = new JProgressBar(0, TOTAL);
        progress.setStringPainted(true);
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(load);
            p.add(save);
        }
        frame.add(p, BorderLayout.NORTH);
        frame.add(noteSheetBean, BorderLayout.CENTER);
        p = new JPanel(new GridLayout(2, 0)); {
            JPanel q = new JPanel(new GridLayout(1, 0)); {
                JPanel r = new JPanel(); {
                    r.add(new JLabel("Moyenne :"));
                    r.add(mean);
                }
                q.add(r);

                r = new JPanel(); {
                    r.add(new JLabel("Total des points :"));
                    r.add(points);
                }
                q.add(r);
            }
            p.add(q);
            q = new JPanel(new GridLayout(0, 1)); {
                q.add(progress);
            }
            p.add(q);
        }
        frame.add(p, BorderLayout.SOUTH);
    }
    
    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File f = askForFile(OpenCommands.LOAD);
                if (f != null) {
                    noteSheetBean.loadFile(f);
                }
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File f = askForFile(OpenCommands.SAVE);
                if (f != null) {
                    noteSheetBean.saveFile(f);
                }
            }
        });
        noteSheetBean.addProgressListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                progress.setValue(noteSheetBean.getProgress());
            }
        });
        noteSheetBean.getTableModel().addTableModelListener(
            new TableModelListener() {
                public void tableChanged(final TableModelEvent e) {
                    if (e.getColumn() != ColumnFeatures.SUBJECT.ordinal()) {
                        mean.setText(String.valueOf(noteSheetBean.getMean()));
                        points.setText(
                                String.valueOf(noteSheetBean.getPoints()));
                    }
                }
            }
        );
    }
    
    /**
     * Demande à l'utilisateur une nouvelle référence de fichier.
     * Retourne une valeur non null uniquement si l'utilisateur a bien choisi
     *  un fichier à la souris ou tapé un nom de fichier.
     */
    private File askForFile(OpenCommands type) {
        int result = (type == OpenCommands.LOAD)
                ? chooser.showOpenDialog(null)
                : chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }
    
    // TYPES IMBRIQUES
    
    private static enum OpenCommands { LOAD, SAVE }

    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Notes().display();
            }
        });
    }
}
