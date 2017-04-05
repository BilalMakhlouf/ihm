package square.model.appli.strategy;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

import square.model.appli.IStrategy;
import square.util.Contract;
import square.util.Coord;
import square.util.Player;
import square.util.Turn;

abstract class AbstractStrategy implements IStrategy {

    // CONSTANTES
    
    private static final int DELAY = 100;
    
    // ATTRIBUTS

    private final SwingPropertyChangeSupport pcs;
    private final Player player;
    private final Play play;
    private final boolean isFirstPlayer;
    
    private Thread playThread;
    
    public static final Object LOCK = new Object();
    
    // CONSTRUCTEURS
    
    /**
     * Crée une stratégie associée au joueur p.
     * Le paramètre first indique si p est le premier joueur (true)
     *  ou non (false).
     */
    AbstractStrategy(Player p, boolean first) {
        Contract.checkCondition(p != null);
        
        pcs = new SwingPropertyChangeSupport(this, true);
        player = p;
        isFirstPlayer = first;
        play = new Play();
    }
    
    // REQUETES
    
    @Override
    public Player getPlayer() {
        return player;
    }
    
    @Override
    public String toString() {
        String[] parts = getClass().getSimpleName().split("[a-z0-9]");
        String result = "";
        for (String s : parts) {
            result = result + s;
        }
        return player + "(" + result + ")";
    }
    
    // COMMANDES

    @Override
    public void addPropertyChangeListener(String n, PropertyChangeListener o) {
        Contract.checkCondition(n != null && o != null);
        
        pcs.addPropertyChangeListener(n, o);
    }
    
    @Override
    public void setMyTurn() {
        play.setMyTurn(true);
    }
    
    @Override
    public void start() {
        if (playThread != null) {
            return;
        }
        
        playThread = new Thread(play);
        reinit();
        play.setMyTurn(isFirstPlayer);
        playThread.start();
    }
    
    @Override
    public void stop() {
    	if (playThread == null) {
            return;
        }
        
        if (!play.isStopped()) {
            play.setStopped(true);
            playThread.interrupt();
        }
        
        try {
			playThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        playThread = null;
    }
    
    // OUTILS
    
    /**
     * Calcule la prochaine position à jouer en fonction du contexte.
     * @post
     *     result != null
     *     La prochaine position vient d'être déterminée
     */
    protected abstract Coord computeNextPosition() throws InterruptedException;

    /**
     * Pour ralentir les calculs...
     */
    protected void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            // rien, on s'en va
        }
    }
    
    /**
     * Permet de réinitialiser les données de la stratégie juste avant son
     *  démarrage.
     */
    protected abstract void reinit();

    /**
     * Joue un tour de jeu.
     * Ca peut prendre du temps et c'est interruptible...
     */
    private void playOneTurn() throws InterruptedException {
        Coord k = computeNextPosition();
        Turn t = new Turn(player, k);
        // on perd du temps pour justifier l'utilisation des threads ;-)
        delay();
        pcs.firePropertyChange(TURN, null, t);
    }
    
    // TYPES IMBRIQUES
    
    /**
     * Code cible d'un thread interne de stratégie.
     */
    private class Play implements Runnable {
        private volatile boolean stopped;
        private volatile boolean myTurn;
        
        public void run() {
            stopped = false;
            while (!stopped) {
    			synchronized (AbstractStrategy.LOCK) {
	            	while (!myTurn && !stopped) {
	            		try {
	            				AbstractStrategy.LOCK.wait();
	                    } catch (InterruptedException e) {
	                        // rien, l'arrêt est géré avec stopped
	                    }
	        		}
    			}
    			if (!stopped) {
    				try {
                        playOneTurn();
                    } catch (InterruptedException e) {
                        // rien, l'arrêt est géré avec stopped
                    }
    			}
            	setMyTurn(false);
            }
        }
        
        private boolean isStopped() {
            return stopped;
        }
        
        private void setStopped(boolean b) {
        	stopped = b;
        }
        
        private void setMyTurn(boolean b) {
        	myTurn = b;
        	synchronized (AbstractStrategy.LOCK) {
               AbstractStrategy.LOCK.notifyAll();
            }
        }
    }
}
