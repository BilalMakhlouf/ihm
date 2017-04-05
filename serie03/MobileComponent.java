package serie03;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import util.Contract;


/**
 * Un composant graphique représentant un disque rouge qui peut se déplacer
 *  indéfiniment dans un rectangle bleu.<br />
 * On peut paramétrer le déplacement élémentaire du disque à l'aide de
 *  <code>setDiscShift(dx, dy)</code> (déplacement élémentaire horizontal et
 *  vertical en pixels).
 * Si le déplacement n'est pas nul, on peut translater (avec rebond) le disque
 *  de <code>n</code> fois son déplacement élémentaire en commandant
 *  <code>activate(n)</code>.<br />
 * Le modèle sous-jacent de ce composant graphique est un <code>Mobile</code>
 *  dont le rectangle statique est le fond du composant (rectangle bleu)
 *  et dont le rectangle mobile est le rectangle support du disque rouge.<br />
 * On peut aussi "capturer" le disque à l'aide de la souris et ainsi le
 *  déplacer à la souris (par glisser/déposer).
 * Lorsqu'on relâche le disque, sa nouvelle vitesse est calculée en fonction de
 *  l'inertie qui a été communiquée au disque à l'aide de la souris.
 * Notez bien que ce composant n'est qu'un jouet ; si pour une raison
 *  quelconque il devait faire partie d'une bibliothèque de classes, il
 *  faudrait lui rajouter quantité de fonctionnalités (possibilité de changer
 *  le modèle, de modifier les couleurs, ...) 
 * @inv
 *     getDiscCenter() != null
 *     getModel() != null
 *     discIsCaught()
 *         ==> getHorizontalShift() == 0
 *             getVerticalShift() == 0
 *     !discIsCaught()
 *         ==> getHorizontalShift() == getModel().getHorizontalShift()
 *             getVerticalShift() == getModel().getVerticalShift()
 * 
 * @cons
 *     $DESC$ Un IMobileComponent avec définition de la taille du fond et du
 *      rayon du disque.
 *     $ARGS$ int width, int height, int ray
 *     $PRE$
 *         width > 0 && height > 0
 *         0 < ray <= Math.min(width, height) / 2
 *     $POST$
 *         !discIsCaught()
 *         getModel().getStaticRect().equals(new Rectangle(0, 0, width, height))
 *         getModel().getMovingRect().equals(new Rectangle(0, 0, 2*ray, 2*ray))
 *         getHorizontalShift() == 0
 *         getVerticalShift() == 0
 */
public class MobileComponent extends JComponent implements AnimableComponent {

	private Mobile model;
	
	private boolean discCaught;
	
	public MobileComponent(int width, int height, int ray) {
		Contract.checkCondition(width > 0 && height > 0);
		Contract.checkCondition(0 < ray && ray 
				<= (Math.min(width, height) / 2));
		model = new StdMobile(
				new Rectangle(0, 0, width, height),
				new Rectangle(0, 0, 2 * ray, 2 * ray));
		discCaught = false;
		model.setHorizontalShift(0);
		model.setVerticalShift(0);
		
		createController();
		
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
	}
	
	private void createController() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//RIEN
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//RIEN
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//RIEN
			}

			@Override
			public void mousePressed(MouseEvent e) {
				discCaught = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				discCaught = false;
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (discCaught) {
					setDiscCenter(new Point(e.getX(), e.getY()));
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				//RIEN
			}
			
		});
	}
	
	// REQUETES
	
	/**
     * La position courante du centre du disque.
     */
	@Override
	public Point getDiscCenter() {
		return model.getCenter();
	}

	/**
     * La valeur de la composante horizontale du déplacement élémentaire
     *  du disque.
     * Vaut zéro si le disque est capturé.
     */
	@Override
	public int getHorizontalShift() {
		if (discCaught) {
			return 0;
		} else {
			return model.getHorizontalShift();
		}
	}
	
	/**
     * Le modèle de ce MobileComponent.
     */
	@Override
	public Mobile getModel() {
		return model;
	}

	/**
     * La valeur de la composante verticale du déplacement élémentaire
     *  du disque.
     * Vaut zéro si le disque est capturé.
     */
	@Override
	public int getVerticalShift() {
		if (discCaught) {
			return 0;
		} else {
			return model.getVerticalShift();
		}
	}
	
	/**
     * Indique si le disque est capturé ou non avec la souris.
     */
	@Override
	public boolean isDiscCaught() {
		return discCaught;
	}

	
	// COMMANDES
    
	/**
     * Déplace le disque d'un mouvement élémentaire.
     * @post
     *     !discIsCaught() ==> le disque a été déplacé (avec un éventuel rebond)
     */
	@Override
	public void animate() {
		if (!discCaught) {
			model.move();
			repaint();
		} else {
			repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Rectangle sr = model.getStaticRect();
		Rectangle mr = model.getMovingRect();
		g.setColor(STAT_COLOR);
		g.fillRect(sr.x, sr.y, sr.width, sr.height);
		g.setColor(MOV_COLOR);
		g.fillOval(mr.x, mr.y, mr.width, mr.height);
	}
	
	
	/**
     * Fixe le centre du disque.
     * @post
     *     Si p était une position admissible le centre du disque est en p
     *     Sinon le disque n'a pas bougé
     */
	@Override
	public void setDiscCenter(Point p) {
		if (model.isValidCenterPosition(p)) {
			model.setCenter(p);
		}
	}
	
	/**
     * Fixe le déplacement élémentaire du disque sur le fond.
     * Les déplacements sont exprimés en pixels.
     * @post
     *     getHorizontalShift() == dx
     *     getVerticalShift() == dy
     */
	@Override
	public void setDiscShift(int dx, int dy) {
		model.setHorizontalShift(dx);
		model.setVerticalShift(dy);
	}

}
