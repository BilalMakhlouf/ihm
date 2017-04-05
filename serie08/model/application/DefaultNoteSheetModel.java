package serie08.model.application;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import serie08.event.ResourceEvent;
import serie08.event.ResourceListener;
import serie08.model.gui.ColumnFeatures;
import serie08.model.gui.NoteTableModel;
import serie08.model.technical.ResourceManager;
import util.Contract;

public class DefaultNoteSheetModel implements NoteSheetModel {
	
	private ResourceManager lowLevelModel;
	private ResourceListener rl;
	private PropertyChangeSupport pcs;
	
	private class SaverWorker implements Runnable {

		private File f;
		private NoteTableModel m;
		
		SaverWorker(File f, NoteTableModel m) {
			super();
			this.f = f;
			this.m = m;
		}
		
		/**
	     * Enregistre un fichier de notes.
	     * Au cours de l'enregistrement, des modifications de valeur pour les
	     *  (pseudo-)propriétés saved, progress et error auront lieu.<br />
	     * <em>Les concrétisations de cette méthode doivent garantir que :
	     * <ul>
	     *   <li>elles ne surchargent pas EDT</li>
	     *   <li>les mises à jour du modèle se font sur EDT</li>
	     * </ul>
	     * </em>
	     * @pre
	     *     f != null
	     * @post
	     *     f est un fichier texte contenant toutes les données du modèle 
	     *     f commence par :
	     *         COMMENT_CHAR + " "
	     *          + ColumnFeatures.SUBJECT.header() + FIELD_SEP
	     *          + ColumnFeatures.COEF.header() + FIELD_SEP
	     *          + ColumnFeatures.MARK.header()
	     *     suivi d'une ligne vierge
	     *     suivi de getRowCount() lignes reconnues par LINE_PAT
	     */
		@Override
		public void run() {
			Contract.checkCondition(this.f != null);
			ArrayList<String> res = new ArrayList<String>();
			res.add(NoteSheetModel.COMMENT_CHAR + " "
					+ ColumnFeatures.SUBJECT.header() + FIELD_SEP
					+ ColumnFeatures.COEF.header() + FIELD_SEP
					+ ColumnFeatures.MARK.header());
			res.add("");
			for (int i = 0; i < m.getRowCount(); i++) {
				String temp = "";
				for (int j = 0; j < m.getColumnCount(); j++) {
					if (j == m.getColumnCount() - 1) {
						temp += m.getValueAt(i, j);
					} else {
						temp += m.getValueAt(i, j) + NoteSheetModel.FIELD_SEP;
					}
				}
				res.add(temp);
			}
			lowLevelModel.saveResource(f, res);
		}
		
	}
	
	private class LoaderWorker implements Runnable {

		private File f;
		
		LoaderWorker(File f) {
			super();
			this.f = f;
		}
		
		@Override
		public void run() {
			lowLevelModel.loadResource(f);
		}
		
	}
	
	public DefaultNoteSheetModel() {
		lowLevelModel = new ResourceManager();
		pcs = new PropertyChangeSupport(this);
		
		rl = new ResourceListener() {

			@Override
			public void resourceLoaded(ResourceEvent<String> e) {
				String line;
				final ArrayList<Object> res = new ArrayList<Object>();
				line = e.getResource();
				if (LINE_PAT.matcher(line).matches()) {
					String[] temp = line.split(FIELD_SEP);
					res.add(temp[0]);
					res.add(ColumnFeatures.COEF.value(temp[1]));
					res.add(ColumnFeatures.MARK.value(temp[2]));
					if (!SwingUtilities.isEventDispatchThread()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								pcs.firePropertyChange("row", null, res);
							}
						});
					} else {
						pcs.firePropertyChange("row", null, res);
					}
				}
			}

			@Override
			public void resourceSaved(ResourceEvent<String> e) {
				final ResourceEvent<String> e2 = e;
				if (!SwingUtilities.isEventDispatchThread()) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							pcs.firePropertyChange("saved", null, 
									e2.getResource());
						}
					});
				} else {
					pcs.firePropertyChange("saved", null, e.getResource());
				}
			}

			@Override
			public void progressUpdated(ResourceEvent<Integer> e) {
				final ResourceEvent<Integer> e2 = e;
				if (!SwingUtilities.isEventDispatchThread()) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							pcs.firePropertyChange("progress", null,
									e2.getResource());
						}
					});
				} else {
					pcs.firePropertyChange("progress", null, e.getResource());
				}
				
			}

			@Override
			public void errorOccured(ResourceEvent<Exception> e) {
				final ResourceEvent<Exception> e2 = e;
				if (!SwingUtilities.isEventDispatchThread()) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							pcs.firePropertyChange("error", null, 
									e2.getResource());
						}
					});
				} else {
					pcs.firePropertyChange("error", null, e.getResource());
				}
			}
			
		};
		lowLevelModel.addResourceListener(rl);
	}
	
	/**
     * La moyenne des notes du modèle, tenant compte des coefficients.
     * @pre
     *     m != null
     * @post
     *     m.getRowCount() > 0
     *         ==> result ==
     *                 getPoints(m)
     *                 / sum(i:[0..m.getRowCount()[, m.getValueAt(i, COEF))
     *     m.getRowCount() == 0
     *         ==> result == Double.NaN
     */
	@Override
	public double getMean(NoteTableModel m) {
		Contract.checkCondition(m != null);
		if (m.getRowCount() > 0) {
			double res = 0.0;
			for (int i = 0; i < m.getRowCount(); i++) {
				res += (Double) m.getValueAt(i,
								ColumnFeatures.COEF.ordinal());
			}
			return getPoints(m) / res;
		} else {
			return Double.NaN;
		}
	}

	/**
     * Le nombre de points correspondants aux notes stockées dans le modèle,
     *  calculé comme la somme des (coef * note).
     * @pre
     *     m != null
     * @post
     *     result == sum(i:[0..getRowCount()[, m.getValueAt(i, POINTS))
     */
	@Override
	public double getPoints(NoteTableModel m) {
		Contract.checkCondition(m != null);
		double res = 0.0;
		for (int i = 0; i < m.getRowCount(); i++) {
			res += (Double) m.getValueAt(i,
							ColumnFeatures.POINTS.ordinal());
		}
		return res;
	}

	@Override
	public void addPropertyChangeListener(String prop, 
			PropertyChangeListener lst) {
		Contract.checkCondition(prop != null && lst != null);
		pcs.addPropertyChangeListener(prop, lst);
	}
	
	@Override
	public void removePropertyChangeListener(String prop, 
			PropertyChangeListener lst) {
		Contract.checkCondition(prop != null && lst != null);
		pcs.removePropertyChangeListener(prop, lst);
	}

	/**
     * Charge un fichier de notes.
     * Au cours du chargement, des modifications de valeur pour les
     *  (pseudo-)propriétés row, progress et error auront lieu.<br />
     * <em>Les concrétisations de cette méthode doivent garantir que :
     * <ul>
     *   <li>elles ne surchargent pas EDT</li>
     *   <li>les mises à jour du modèle se font sur EDT</li>
     * </ul>
     * </em>
     * @pre
     *     f != null
     * @post
     *     le modèle contient les données (lignes de f reconnues par LINE_PAT)
     */
	@Override
	public void loadFile(File f) {
		(new Thread(new LoaderWorker(f))).start();
	}
	
	/**
     * Enregistre un fichier de notes.
     * Au cours de l'enregistrement, des modifications de valeur pour les
     *  (pseudo-)propriétés saved, progress et error auront lieu.<br />
     * <em>Les concrétisations de cette méthode doivent garantir que :
     * <ul>
     *   <li>elles ne surchargent pas EDT</li>
     *   <li>les mises à jour du modèle se font sur EDT</li>
     * </ul>
     * </em>
     * @pre
     *     f != null
     * @post
     *     f est un fichier texte contenant toutes les données du modèle 
     *     f commence par :
     *         COMMENT_CHAR + " "
     *          + ColumnFeatures.SUBJECT.header() + FIELD_SEP
     *          + ColumnFeatures.COEF.header() + FIELD_SEP
     *          + ColumnFeatures.MARK.header()
     *     suivi d'une ligne vierge
     *     suivi de getRowCount() lignes reconnues par LINE_PAT
     */
	@Override
	public void saveFile(File f, NoteTableModel m) {
		(new Thread(new SaverWorker(f, m))).start();
	}

}