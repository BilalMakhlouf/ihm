package serie02;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Contract;

/**
 * @inv <pre>
 *     getPodiums() != null
 *     forall r:Rank : getPodiums().get(r) != null
 *     getShotsNb() >= 0
 *     getTimeDelta() >= 0
 *     !isFinished() ==> getTimeDelta() == 0 </pre>
 * @cons <pre>
 *     $ARGS$ Set<E> drawables
 *     $PRE$ (drawables != null && drawables.size() >= 2
 *     $POST$
 *         les mod�les des 2+2 podiums sont initialis�s al�atoirement
 *         avec les �l�ments de drawables </pre>
 */
public class StdPodiumManager<E extends Drawable> implements PodiumManager<E> {
	
	private Order lastOrder;
	private Map<Rank, Podium<E>> podiums;
	private int shotsNb;
	private long timeDelta;
	private long start;
	private boolean finished;
	private Set<E> drawables;
	private PropertyChangeSupport pcs;
	private VetoableChangeSupport vcs;
	
	public StdPodiumManager(Set<E> drawables) {
		Contract.checkCondition(drawables != null);
		Contract.checkCondition(drawables.size() >= 2);
		podiums = new HashMap<Rank, Podium<E>>();
		this.drawables = drawables;
		List<List<E>> rand = createRandomElements();
		rand.addAll(createRandomElements());
		start = System.currentTimeMillis();
		pcs = new PropertyChangeSupport(this);
		vcs = new VetoableChangeSupport(this);
		for (Rank r : Rank.values()) {
			podiums.put(r, new Podium<E>(new StdPodiumModel<E>(
                    rand.get(r.ordinal()),
                    drawables.size()
            )));
		}
	}
	
	// REQUETES
    
    /**
     * Le dernier ordre donn�.
     * Vaut null en d�but de partie.
     */
	@Override
	public Order getLastOrder() {
		return lastOrder;
	}
	
	/**
     * Les quatre podiums g�r�s par ce gestionnaire.
     */
	@Override
	public Map<Rank, Podium<E>> getPodiums() {
		return podiums;
	}
	
	/**
     * Le nombre d'ordres donn�s au cours d'une partie.
     */
	@Override
	public int getShotsNb() {
		return shotsNb;
	}
	
	/**
     * L'intervalle de temps entre le d�but d'une partie et la fin.
     * Vaut 0 tant que la partie n'est pas finie.
     */
	@Override
	public long getTimeDelta() {
		return timeDelta;
	}
	
	/**
     * Indique si une partie en cours est finie.
     */
	@Override
	public boolean isFinished() {
		return finished;
	}
	
	// COMMANDES
	
	// cette m�thode est appel�e par reinit()
	/**
	 * Construit les 4 s�quences d'�l�ments de E, puis les 4 mod�les de podiums
	 *  bas�s sur ces s�quences.
	 * La concat�nation des deux premi�res s�quences est une permutation des
	 *  �l�ments de drawables.
	 * La concat�nation des deux derni�res s�quences est aussi une permutation
	 *  des �l�ments de drawables.
	 * Il se peut que les permutations soient identiques.
	 */
	private void changePodiumModels() {
	    List<List<E>> lst = createRandomElements();
	    lst.addAll(createRandomElements());
	    for (Rank r : Rank.values()) {
	        podiums.get(r).setModel(
	                new StdPodiumModel<E>(
	                        lst.get(r.ordinal()),
	                        drawables.size()
	                )
	        );
	    }
	}
	
	/**
	 * Construit une s�quence de deux s�quences al�atoires d'�l�ments de E, �
	 *  partir de l'ensemble drawables, un peu comme in distribue des cartes :
	 *  - on commence par m�langer les cartes,
	 *  - puis on les distribue au hasard, une par une, en deux tas.
	 */
	private List<List<E>> createRandomElements() {
	    final double ratio = 0.5;
	    List<E> elements = new LinkedList<E>(drawables);
	    List<E> list = new ArrayList<E>(drawables.size());
	    for (int i = drawables.size(); i > 0; i--) {
	        int k = ((int) (Math.random() * i));
	        list.add(elements.get(k));
	        elements.remove(k);
	    }
	    List<E> elemsL = new ArrayList<E>(drawables.size());
	    List<E> elemsR = new ArrayList<E>(drawables.size());
	    for (E e : list) {
	        if (Math.random() < ratio) {
	            elemsL.add(e);
	        } else {
	            elemsR.add(e);
	        }
	    }
	    ArrayList<List<E>> result = new ArrayList<List<E>>(2);
	    result.add(elemsL);
	    result.add(elemsR);
	    return result;
	}

    /**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a �t� ajout� � la liste des �couteurs
     *     de la propri�t� propName </pre>
     */
	@Override
	public void addPropertyChangeListener(String propName, 
			PropertyChangeListener lst) {
		Contract.checkCondition(lst != null);
		pcs.addPropertyChangeListener(propName, lst);
	}
	
	/**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a �t� ajout� � la liste des �couteurs </pre>
     */
	@Override
	public void addVetoableChangeListener(VetoableChangeListener lst) {
		Contract.checkCondition(lst != null);
		vcs.addVetoableChangeListener(lst);
	}
	
	/**
     * Ex�cute l'ordre o sur ce gestionnaire.
     * @pre <pre>
     *     o != null </pre>
     * @post <pre>
     *     les actions conformes � l'ordre o ont �t� ex�cut�es sur les podiums
     *       g�r�s par ce gestionnaire </pre>
     * @throws
     *     PropertyVetoException si l'ordre o a �t� refus�
     */
	@Override
	public void executeOrder(Order o) throws PropertyVetoException {
		Contract.checkCondition(o != null);
		Map<Rank, Podium<E>> oldMap = podiums;
		PodiumModel<E> left = podiums.get(Rank.WRK_LEFT).getModel(); 
		PodiumModel<E> right = podiums.get(Rank.WRK_RIGHT).getModel();
		try {
			switch(o) {
				case KI:
					left.addTop(right.top());
					right.removeTop();
					break;
				case LO:
					right.addTop(left.top());
					left.removeTop();
					break;
				case MA:
					E bot1 = left.bottom();
					left.removeBottom();
					left.addTop(bot1);
					break;
				case NI:
					E bot = right.bottom(); 
					right.removeBottom();
					right.addTop(bot);
					break;
				case SO:
					E top1 = left.top();
					E top2 = right.top();
					left.removeTop();
					right.removeTop();
					left.addTop(top2);
					right.addTop(top1);
					break;
				default:
					System.out.println("no order found");
					break;
			}
		} catch (AssertionError ae) {
			//DO NOTHING
		}
		Order oldOrder = null;
		lastOrder = o;
		int oldShots = shotsNb;
		shotsNb++;
		pcs.firePropertyChange("shotsNb", oldShots, shotsNb);
		pcs.firePropertyChange("lastOrder", oldOrder, lastOrder);
		pcs.firePropertyChange("podiums", oldMap, podiums);
		checkFinished();
	}
	
	private void checkFinished() {
		if (podiums.get(Rank.WRK_LEFT).getModel().equals(
				podiums.get(Rank.OBJ_LEFT).getModel())
				&& podiums.get(Rank.WRK_RIGHT).getModel().equals(
						podiums.get(Rank.OBJ_RIGHT).getModel())) {
			System.out.println("victoire");
			boolean oldFinished = finished;
			finished = true;
			long oldDelta = timeDelta;
			timeDelta = System.currentTimeMillis() - start;
			pcs.firePropertyChange("timeDelta", oldDelta, timeDelta);
			pcs.firePropertyChange("finished", oldFinished, finished);
		}
	}
	
	/**
     * R�initialise ce gestionnaire.
     * @post <pre>
     *     les podiums g�r�s par ce gestionnaire ont un nouveau mod�le
     *       g�n�r� al�atoirement
     *     getShotsNb() == 0
     *     getTimeDelta() == 0
     *     getLastOrder() == null </pre>
     */
	@Override
	public void reinit() {
		start = System.currentTimeMillis();
		Map<Rank, Podium<E>> oldMap = podiums;
		int oldsh = shotsNb;
		shotsNb = 0;
		long oldtime = timeDelta;
		timeDelta = 0L;
		Order oldord = lastOrder;
		pcs.firePropertyChange("lastOrder", oldord, lastOrder);
		lastOrder = null;
		changePodiumModels();
		pcs.firePropertyChange("shotsNb", oldsh, shotsNb);
		pcs.firePropertyChange("timeDelta", oldtime, timeDelta);
		
		pcs.firePropertyChange("podiums", oldMap, podiums);
	}
	
	/**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a �t� retir� de la liste des �couteurs </pre>
     */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener lst) {
		Contract.checkCondition(lst != null);
		pcs.removePropertyChangeListener(lst);
	}
	
	/**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a �t� retir� de la liste des �couteurs </pre>
     */
	@Override
	public void removeVetoableChangeListener(VetoableChangeListener lst) {
		Contract.checkCondition(lst != null);
		vcs.removeVetoableChangeListener(lst);
	}

}
