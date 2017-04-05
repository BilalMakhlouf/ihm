package serie06.model;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import serie06.event.SentenceEvent;
import serie06.event.SentenceListener;
import serie06.util.Formatter;
import util.Contract;


/**
 * Un acteur est un objet manipulant une boite.
 * Sa tâche consiste à faire au plus <code>getMaxIterNb()</code> fois
 *  quelquechose sur cette boîte.
 * On démarre l'acteur avec <code>start()</code>, on peut stopper l'acteur
 *  avant qu'il n'ait terminé sa tâche avec
 *  <code>interruptAndWaitForInactivation()</code>.
 * Sinon, l'acteur s'arrête de lui-même quand il a fini sa tâche.
 * Un acteur est un objet qui « fonctionne » tout seul, c'est-à-dire qu'il est
 *  animé par un thread interne, inaccessible de l'extérieur, seulement
 *  pilotable par le biais de certaines méthodes de cette classe.
 * Quand un acteur fait quelquechose avec une boite, il émet des SentenceEvent
 *  qui décrivent ce qu'il fait.
 * @inv
 *     getMaxIterNb() > 0
 *     getBox() != null
 *     getSentenceListeners() != null
 */
public abstract class AbstractActor implements Actor {

	
	private Box b;
	private int max;
	private EventListenerList listenerListe;
	private boolean active;
	private boolean waiting;
	private boolean stopped;
	private Thread th;
	private Formatter fmt;
	
	public class TaskCode implements Runnable {
		public void run() {
			System.out.println(getMaxIterNb());
			int i = 0;
			fireSentenceSaid("Naissance");
			while (i < getMaxIterNb() && !stopped) {
				active = true;
				i = i + 1;
				fireSentenceSaid("Debut de l'etape " + i);
				while (!canUseBox() && !stopped) {
					waiting = true;
					fireSentenceSaid("Suspendu");
					try {
						synchronized (b) {
							b.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						active = false;
						waiting = false;
						fireSentenceSaid("Mort subite");
					}
					fireSentenceSaid("Réactivé");
				}
				waiting = false;
				
				synchronized (b) {
					if (canUseBox()) {
						useBox();
					}
					b.notifyAll();
				}
				fireSentenceSaid("Fin de l'etape" + i);
			}
			if (stopped) {
				active = false;
				waiting = false;
				fireSentenceSaid("Mort naturelle");
			}
		}
		
	}
	
	public AbstractActor(Box box, int m, String name) {
		b = box;
		max = m;
		waiting = false;
		active = false;
		stopped = false;
		listenerListe = new EventListenerList();
		fmt = new Formatter(name);
	}
	
	/**
     * La boite associée à cet acteur.
     */
	@Override
	public Box getBox() {
		return b;
	}
	
	/**
     * Le nombre maximal de fois que l'acteur peut faire quelque chose avant
     *  de s'arrêter.
     */
	@Override
	public int getMaxIterNb() {
		return max;
	}

	/**
     * La séquence des SentenceListeners enregistrés auprès de cet acteur.
     */
	@Override
	public SentenceListener[] getSentenceListeners() {
		return listenerListe.getListeners(SentenceListener.class);
	}
	
	/**
     * Indique si l'acteur est en train de travailler, c'est-à-dire si le thread
     *  qui l'anime a été démarré et n'a pas encore terminé son exécution.
     */
	@Override
	public boolean isActive() {
		return active;
	}
	
	/**
     * Indique si l'acteur est en attente sur la boite au cours de son action.
     */
	@Override
	public boolean isWaitingOnBox() {
		return waiting;
	}

	// COMMANDES
    
    /**
     * Abonne un SentenceListener auprès de cet acteur.
     */
	@Override
	public void addSentenceListener(SentenceListener listener) {
		listenerListe.add(SentenceListener.class, listener);
	}
	
	/**
     * Désabonne un SentenceListener de cet acteur.
     */
	@Override
	public void removeSentenceListener(SentenceListener listener) {
		listenerListe.remove(SentenceListener.class, listener);
	}
	
	/**
     * Démarre un acteur, c'est-à-dire crée un nouveau thread interne et lance
     *  son exécution.
     * L'acteur commence à parler.
     * @pre
     *     !isActive()
     * @post
     *     l'action est démarrée
     */
	@Override
	public void start() {
		Contract.checkCondition(!active);
		th = new Thread(new TaskCode());
		waiting = false;
		stopped = false;
		th.start();
	}
	
	/**
     * Interrompt l'acteur puis force le thread courant à attendre la mort du
     *  thread interne de l'acteur avant de continuer.
     * Dès que le thread interne va entrer dans une méthode bloquante, il va
     *  être interrompu et se terminer.
     * En théorie, si le thread interne n'était pas bloqué lors de l'appel de
     *  cette méthode, l'attente pourrait être longue ; en pratique ce ne sera
     *  pas le cas.
     * @pre
     *     isActive()
     * @post
     *     !isActive()
     */
	@Override
	public void interruptAndWaitForInactivation() {
		Contract.checkCondition(active);
		synchronized (b) {
			b.notifyAll();
		}
		active = false;
		stopped = true;
		try {
			th.join();
		} catch (InterruptedException e) {
			//Hello interruption my old friend
		}
		
	}
	
	protected abstract boolean canUseBox();
	protected abstract void useBox();
	protected void fireSentenceSaid(final String st) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
						SentenceListener[] lst 
						= listenerListe.getListeners(SentenceListener.class);
						for (int i = 0; i < lst.length; i++) {
							lst[i].sentenceSaid(new SentenceEvent(this, 
									fmt.format(st)));
						}
		     		}
		 		});
		}
	}

}
