package square;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import square.model.appli.ISquareGameModel;
import square.model.appli.ISquareGameModel.State;
import square.model.appli.SquareGameModel;
import square.model.appli.strategy.Strategies;
import square.model.gui.SquareTableModel;
import square.util.Coord;
import square.util.Player;
import square.view.GameColor;
import square.view.SquareRenderer;

public class SquareGame {
    
    private static final int SIZE = 30;
    
    private JFrame frame;
    private JTable board;
    private JButton replay;
    private Map<Player, JLabel> playerNames;
    private Map<Player, JLabel> playerScores;
    private ISquareGameModel appliModel;
    
    public SquareGame(int n) {
        createModel(n);
        createView();
        placeComponents();
        createController();
    }
    
    public void display() {
        initializeDisplay();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createModel(int n) {
        appliModel = new SquareGameModel(n, Player.X);
        appliModel.plugStrategy(Strategies.NAIVE, Player.X);
        appliModel.plugStrategy(Strategies.NAIVE, Player.O);
        appliModel.startPlaying();
        
        appliModel.addPropertyChangeListener(ISquareGameModel.STATE, 
        		new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (State.STOPPING == (State) evt.getNewValue()) {
							replay.setEnabled(false);
						} else {
							replay.setEnabled(true);
						}
					}
        	
        		}
        );
        
        appliModel.addPropertyChangeListener(ISquareGameModel.TURN, 
        		new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								board.repaint();
							}
							
						});
					}
        	
        		}
        );
    }
    
    private void createView() {
        frame = new JFrame("Square Game");
        replay = new JButton("Recommencer");
        playerScores = createRelation();
        playerNames = createRelation();
        configureLabelNames();
        board = new JTable();
        configureBoard();
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(new GridLayout(1, 0)); {
            for (Player player : Player.values()) {
                JPanel q = new JPanel(); {
                    q.add(playerNames.get(player));
                    q.add(playerScores.get(player));
                }
                p.add(q);
            }
        }
        frame.add(p, BorderLayout.NORTH);
        
        frame.add(board, BorderLayout.CENTER);
        
        p = new JPanel(); {
            p.add(replay);
        }
        frame.add(p, BorderLayout.SOUTH);
    }
    
    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        replay.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		appliModel.startPlaying();
        		SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						board.repaint();
					}
				});
        	}
        	
        });
        
//        board.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				appliModel.setPlayerAt(new Coord(board.getSelectedRow(),
//						board.getSelectedColumn()));
//				SwingUtilities.invokeLater(new Runnable() {
//					@Override
//					public void run() {
//						board.repaint();
//					}
//				});
//				appliModel.setPlayer();
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//        	
//        });
        
        appliModel.addPropertyChangeListener(ISquareGameModel.PLAYER, 
    		new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					displayOnlyPlayer((Player) arg0.getNewValue());
				}
        	
        	}
        );
        
        appliModel.addPropertyChangeListener(ISquareGameModel.SCORE_OF, 
    		new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					refreshScoreOf(appliModel.getPlayer(), 
							(Integer) arg0.getNewValue());
				}
        	
        	}
        );
        
        appliModel.addPropertyChangeListener(ISquareGameModel.EMPTY, 
    		new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					refreshAllScores();
				}
        	
        	}
        );
        
        appliModel.addPropertyChangeListener(ISquareGameModel.FULL, 
    		new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if ((Boolean) arg0.getNewValue()) {
						displayAllPlayers();
						JOptionPane.showMessageDialog(null, 
								"Partie terminée !");
					}
				}
        	
        	}
        );
        
        board.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				if (board.getHeight() > board.getRowCount()) {
					board.setRowHeight(board.getHeight() / board.getRowCount());
				}
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
    }
    
    private void initializeDisplay() {
        displayOnlyPlayer(appliModel.getPlayer());
        refreshAllScores();
    }
    
    private void refreshAllScores() {
        for (Player p : Player.values()) {
            refreshScoreOf(p, appliModel.getScoreOf(p));
        }
    }
    
    private void refreshScoreOf(Player p, int s) {
        JLabel label = playerScores.get(p);
        label.setText(String.valueOf(s));
    }
    
    private void displayOnlyPlayer(Player current) {
        for (Player p : Player.values()) {
            Color c;
            if (p == current) {
                c = GameColor.forPlayer(p);
            } else {
                c = frame.getBackground();
            }
            playerNames.get(p).setBackground(c);
        }
    }
    
    private Map<Player, JLabel> createRelation() {
        Map<Player, JLabel> map = new EnumMap<Player, JLabel>(Player.class);
        for (Player p : Player.values()) {
            JLabel label = new JLabel();
            map.put(p, label);
        }
        return map;
    }
    
    private void configureLabelNames() {
        for (Player p : Player.values()) {
            JLabel label = playerNames.get(p);
            label.setText("Joueur " + p + " :");
            label.setOpaque(true);
        }
    }
    
    private void configureBoard() {
    	board.setDefaultRenderer(Player.class, new SquareRenderer());
    	board.setBorder(BorderFactory.createLineBorder(Color.black));
    	SquareTableModel sqm = new SquareTableModel(appliModel);
    	board.setModel(sqm);
    	board.setRowHeight(SIZE);
    	for (int i = 0; i < board.getColumnCount(); i++) {
    		board.getColumn(board.getColumnName(i)).setPreferredWidth(SIZE);
    	}
    }
    
    private void displayAllPlayers() {
        for (Player p : Player.values()) {
            Color c = GameColor.forPlayer(p);
            playerNames.get(p).setBackground(c);
        }
    }
    
    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // un jeu de 8 lignes sur 8 colonnes
                new SquareGame(8).display();
            }
        });
    }
}
