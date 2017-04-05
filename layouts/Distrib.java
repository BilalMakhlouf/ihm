package layouts;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

abstract class Distrib {
    
    // ATTRIBUTS
    
    private JFrame frame;

    // CONSTRUCTEURS
    
    public Distrib(String title) {
        createView(title);
        placeComponents();
        createController();
    }

    // REQUETES
    
    protected JFrame getFrame() {
        return frame;
    }
    
    // COMMANDES
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // OUTILS
    
    protected void createView(String title) {
        frame = new JFrame(title);
    }
    
    protected abstract void placeComponents();
    
    protected void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // TYPES IMBRIQUES
    
    protected enum LKey {
        BACK("Cet appareil rend la monnaie"),
        CREDIT("Vous disposez d'un crédit de 0 cents"),
        DRINK("Boisson :"),
        MONEY("Monnaie :");
        
        private final JLabel jlabel;
        
        LKey(String lbl) {
            jlabel = new JLabel(lbl);
        }
        
        JLabel jlabel() {
            return jlabel;
        }
    }
    
    protected enum FKey { INS, DRINK, MONEY;
    
        private static final int MIN_FIELD_SIZE = 10;
        private final JTextField jtextfield;
        
        FKey() {
            jtextfield = new JTextField(MIN_FIELD_SIZE);
        }
        
        JTextField jtextfield() {
            return jtextfield;
        }
    }

    protected enum Location { RIGHT, LEFT, NONE };
    
    protected enum BKey {
        ORANGE("Jus d'orange", Location.LEFT, PKey.ORANGE),
        CHOCO("Chocolat", Location.LEFT, PKey.CHOCO),
        COFFEE("Café", Location.LEFT, PKey.COFFEE),
        INS("Insérer", Location.RIGHT, PKey.INS),
        CANCEL("Annuler", Location.RIGHT, null),
        TAKE("Prenez votre boisson et/ou votre monnaie", Location.NONE, null);
        
        private final Location loc;
        private final PKey pkey;
        private final JButton jbutton;
        
        BKey(String lbl, Location loc, PKey pkey) { 
            this.loc = loc; 
            this.pkey = pkey; 
            jbutton = new JButton(lbl);
        }
        
        Location location() {
            return loc;
        }
        
        PKey pkey() {
            return pkey;
        }
        
        JButton jbutton() {
            return jbutton;
        }
    }
    protected enum PKey {
        ORANGE("110 cents"),
        CHOCO("45 cents"),
        COFFEE("30 cents"),
        INS("cents"),
        MONEY("cents");
        
        private final JLabel jlabel;
        
        PKey(String lbl) {
            jlabel = new JLabel(lbl);
        }
        
        JLabel jlabel() {
            return jlabel;
        }
    }
}
