package serie03;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import util.Contract;

public class GraphicAnimator {
	
    private SharedState state;
    
    private EventListenerList listenerList;
    
    private ChangeEvent changeEvent;
    
    private Thread animThread;
    
    private final int maxMovesPerSecond;
    
    private Animable animable;
    
    //REQUETES
    
    public GraphicAnimator(Animable an, int i) {
    	state = new SharedState();
    	listenerList = new EventListenerList();
		animable = an;
		changeEvent = null;
		this.maxMovesPerSecond = i;
		state.setSpeed(i);
		animThread = new AnimLoop();
	}

	public boolean isAnimationPaused() {
        return state.isPaused();
    }
    
	public boolean isAnimationRunning() {
    	return (!state.isStopped() && state.isStarted());
    }
    
    public boolean isAnimationStopped() {
    	return state.isStopped();
    }
    
    public boolean isAnimationStarted() {
    	return state.isStarted();
    }
    
    public int getSpeed() {
    	return state.getSpeed();
    }
    
    public int getMaxSpeed() {
    	return maxMovesPerSecond;
    }
    
    public void setSpeed(int d) {
    	state.setSpeed(d);
    }
    
    //COMMANDES
    
    public void startAnimation() {
    	state.start();
    }
    
    public void pauseAnimation() {
    	state.pause();
    }
    
    public void resumeAnimation() {
    	state.resume();
    }
    
    public void stopAnimation() {
    	state.stop();
    }
    
    public void addChangeListener(ChangeListener listener) {
    	this.listenerList.add(ChangeListener.class, listener);
    }
    
    public void removeChangeListener(ChangeListener listener) {
    	this.listenerList.remove(ChangeListener.class, listener);
    }
    
    public void fireStateChanged() {
    	if (!SwingUtilities.isEventDispatchThread()) {
    		 SwingUtilities.invokeLater(new Runnable() {
    		     public void run() {
    		         fireStateChanged();
    		     }
    		 });
    	} else {
    		ChangeListener[] listeners = 
            		listenerList.getListeners(ChangeListener.class);
            for (int i = listeners.length - 1; i >= 0; i -= 1) {
            	if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                (listeners[i]).stateChanged(changeEvent);
            }
    	}
    }
    
    
    private class SharedState {
        private boolean started;
        private boolean stopped;
        private boolean paused;
        private volatile int movesPerSecond;
        
        SharedState() {
        	started = false;
        	stopped = false;
        	paused = false;
        	movesPerSecond = 0;
        }
        
        synchronized boolean isPaused() {
            return started && !stopped && paused;
        }
        
        synchronized boolean isStopped() {
        	return stopped;
        }
        
        synchronized boolean isStarted() {
        	return started;
        }
        
        synchronized int getSpeed() {
        	return movesPerSecond;
        }
        
        synchronized void setSpeed(int sp) {
        	this.movesPerSecond = sp;
        	fireStateChanged();
        }
        
        synchronized void start() {
        	Contract.checkCondition(!stopped && !started);
        	started = true;
        	animThread.start();
        	fireStateChanged();
        }
        
        synchronized void pause() {
        	Contract.checkCondition(started && !paused 
        			&& !stopped);
        	paused = true;
        	fireStateChanged();
        }
        
        synchronized void resume() {
        	Contract.checkCondition(paused && started 
        			&& !stopped);
        	paused = false;
        	state.notify();
        	fireStateChanged();
        }
        
        synchronized void stop() {
        	Contract.checkCondition(started);
    		animThread.interrupt();
    		paused = false;
        	stopped = true;
    		fireStateChanged();
        }
        
        
    }
    
    private class AnimLoop extends Thread {
    	
    	public void run() {
    		while (!state.isStopped()) {
    			if (state.isPaused()) {
    				freeze();
    			} else {
    				play();
    			}
    		}
    	}
    	
    	void play() {
    		if (!SwingUtilities.isEventDispatchThread()) {
    			SwingUtilities.invokeLater(new Runnable() {
    				public void run() {
    					play();
       		     	}
       		 	});
       		 	try {
       		 		if (getSpeed() > 0) {
           		 		Thread.sleep(1000 / getSpeed());	
       		 		} else {
       		 			Thread.sleep(1000);
       		 		}
       		 	} catch (InterruptedException e) {
       		 		//NOTHING
       		 	}
			} else {
				animable.animate();
			}
    	}
    	
    	void freeze() {
   			synchronized (state) {
  	    		while (state.isPaused()) {
    				try {
        				state.wait();
    				} catch (InterruptedException e) {
    					if (state.isPaused()) {
    						state.resume();
    					}
    				}
    			}
    		}
    	}
    }
    
}
