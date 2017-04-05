package square.model.appli;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import square.util.Contract;
import square.util.Coord;
import square.util.Player;
import square.util.Turn;
import square.util.Zone;

public class SquareGameModel implements ISquareGameModel {
    
    // ATTRIBUTS
    
    private final Player[][] data;
    private final int size;
    private final int nbOfCells;
    private final Map<Player, Integer> squaresNb;
    private Player firstPlayer;
    private Player currentPlayer;
    private int emptyCells;
    private PropertyChangeSupport pcs;
    private Turn turn;
    private State state;
    private StrategyManager aiManager;
    private Coord lastPlay;
    
    // CONSTRUCTEURS
    
    public SquareGameModel(int s, Player first) {
        Contract.checkCondition(s >= MIN_SIZE && first != null);
        
        firstPlayer = first;
        currentPlayer = firstPlayer;
        size = s;
        data = new Player[s][s];
        nbOfCells = size * size;
        emptyCells = nbOfCells;
        squaresNb = new EnumMap<Player, Integer>(Player.class);
        
        pcs = new PropertyChangeSupport(this);
        pcs.firePropertyChange(PLAYER, null, currentPlayer);
        
        aiManager = new StrategyManager();
        
        aiManager.addPropertyChangeListener(ISquareGameModel.TURN, 
        		new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						turn = (Turn) arg0.getNewValue();
						setPlayerAt(turn.position());
						if (isFull()) {
							(new Thread(new Runnable() {
								public void run() {
									stopPlaying();
								}
							})).start();
						} else {
							setPlayer();
							aiManager.playWith(currentPlayer);
						}
						pcs.firePropertyChange(TURN, null, turn);
					}
        		}
        );
    }
    
    // REQUETES

    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player getPlayer() {
        return currentPlayer;
    }

    @Override
    public Player getPlayerAt(Coord k) {
        Contract.checkCondition(k != null);
        
        return data[k.row()][k.column()];
    }

    @Override
    public int getScoreOf(Player p) {
        Contract.checkCondition(p != null);
        
        Integer n = squaresNb.get(p);
        return n == null ? 0 : n;
    }

    @Override
    public boolean isEmpty() {
        return emptyCells == nbOfCells;
    }

    @Override
    public boolean isFull() {
        return emptyCells == 0;
    }
    
    @Override
    public boolean isValidIndex(int n) {
        return 0 <= n && n < size;
    }

    @Override
    public boolean isValidPosition(Coord k) {
        Contract.checkCondition(k != null);
        
        return isValidIndex(k.row()) && isValidIndex(k.column());
    }

    @Override
    public int size() {
        return size;
    }
    
    // COMMANDES

    @Override
    public void addPropertyChangeListener(String n, PropertyChangeListener o) {
        Contract.checkCondition(n != null && o != null);
        pcs.addPropertyChangeListener(n, o);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l) {
    	pcs.removePropertyChangeListener(l);
    }

    @Override
    public void setFirstPlayer(Player p) {
        Contract.checkCondition(p != null && isEmpty());
        
        firstPlayer = p;
    }
    
    @Override
    public void setPlayer() {
    	Player oldPlayer = currentPlayer;
        currentPlayer = currentPlayer.getNext();
        
        pcs.firePropertyChange(PLAYER, oldPlayer, currentPlayer);
    }

    @Override
    public void setPlayerAt(Coord k) {
        Contract.checkCondition(k != null && isValidPosition(k));
        Contract.checkCondition(getPlayerAt(k) == null);
        boolean oldEmpty = isEmpty();
        boolean oldFull = isFull();
        int oldScore = getScoreOf(currentPlayer);
        updateWith(k);
        pcs.firePropertyChange(EMPTY, oldEmpty, isEmpty());
        pcs.firePropertyChange(FULL, oldFull, isFull());
        pcs.firePropertyChange(SCORE_OF, oldScore, getScoreOf(currentPlayer));
    }

    @Override
    public void startPlaying() {
    		if (state == state.STARTED) {
    			(new Thread(new Runnable() {

    				@Override
    				public void run() {
			    		stopPlaying();
    			    	
    			    	SwingUtilities.invokeLater(new Runnable() {

    						@Override
    						public void run() {
    			            	boolean oldFull = isFull();
    			                reinit();
    			                pcs.firePropertyChange(EMPTY, false, isEmpty());
    			                pcs.firePropertyChange(FULL, oldFull, isFull());
    			                State oldState = state;
    			                state = State.STARTED;
    			                pcs.firePropertyChange(STATE, oldState, state);
    			                aiManager.startStrategies();
    						}
    			    		
    			    	});

    				}
        			
        		})).start();
    		} else {
    			boolean oldFull = isFull();
                reinit();
                pcs.firePropertyChange(EMPTY, false, isEmpty());
                pcs.firePropertyChange(FULL, oldFull, isFull());
                State oldState = state;
                state = State.STARTED;
                pcs.firePropertyChange(STATE, oldState, state);
                aiManager.startStrategies();
    		}
    }
    
    // OUTILS
    
    /**
     * Effectue toutes les mises à jour concernant le placement du joueur
     *  courant en position k dans la grille.
     */
    private void updateWith(Coord k) {
        assert k != null && isValidPosition(k);
        
        data[k.row()][k.column()] = currentPlayer;
        emptyCells = emptyCells - 1;
        int s = 0;
        for (Zone z : Zone.values()) {
            if (belongToNewSquare(k, z)) {
                s = s + 1;
            }
        }
        squaresNb.put(currentPlayer, getScoreOf(currentPlayer) + s);
    }
    
    /**
     * Indique si le coup du joueur courant en position k complète le carré
     *  en zone z dans la grille.
     */
    private boolean belongToNewSquare(Coord k, Zone z) {
        assert k != null && isValidPosition(k);
        assert data[k.row()][k.column()] == currentPlayer;
        assert z != null;
        
        int r = k.row();
        int c = k.column();
        for (int i = 0; i < z.offsets().length; i++) {
            int r2 = r + z.offsets()[i][0];
            int c2 = c + z.offsets()[i][1];
            if (isValidIndex(r2) && isValidIndex(c2)) {
                if (data[r2][c2] != data[r][c]) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Réinitialise le modèle.
     */
    private void reinit() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                data[r][c] = null;
            }
        }
        emptyCells = nbOfCells;
        squaresNb.clear();
        currentPlayer = firstPlayer;
    }

	@Override
	public State getState() {
		return state;
	}

	/**
     * Associe à p la stratégie s.
     * @pre
     *     p != null
     *     strategyName != null
     *     isEmpty()
     * @post
     *     p est doté de la stratégie strategyName
     */
	@Override
	public void plugStrategy(String strategyName, Player p) {
		Contract.checkCondition(p != null 
				&& strategyName != null && isEmpty());
		aiManager.setStrategy(strategyName, p, p == getFirstPlayer(), size);
	}
	
	/**
     * Arrête une partie.
     * @post
     *     arrête la partie en cours
     */
	@Override
	public void stopPlaying() {
		State oldState = state;
		state = State.STOPPING;
		pcs.firePropertyChange(STATE, oldState, state);
		aiManager.stopStrategies();
		oldState = state;
		state = State.STOPPED;
		pcs.firePropertyChange(STATE, oldState, state);
	}
}
