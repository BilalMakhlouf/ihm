package serie03;

import java.awt.Color;
import java.awt.Point;

/**
 * Un composant graphique repr�sentant un disque rouge qui peut se d�placer
 *  ind�finiment dans un rectangle bleu.<br />
 * On peut param�trer le d�placement �l�mentaire du disque � l'aide de
 *  <code>setDiscShift(dx, dy)</code> (d�placement �l�mentaire horizontal et
 *  vertical en pixels).
 * Si le d�placement n'est pas nul, on peut translater (avec rebond) le disque
 *  de <code>n</code> fois son d�placement �l�mentaire en commandant
 *  <code>activate(n)</code>.<br />
 * Le mod�le sous-jacent de ce composant graphique est un <code>Mobile</code>
 *  dont le rectangle statique est le fond du composant (rectangle bleu)
 *  et dont le rectangle mobile est le rectangle support du disque rouge.<br />
 * On peut aussi "capturer" le disque � l'aide de la souris et ainsi le
 *  d�placer � la souris (par glisser/d�poser).
 * Lorsqu'on rel�che le disque, sa nouvelle vitesse est calcul�e en fonction de
 *  l'inertie qui a �t� communiqu�e au disque � l'aide de la souris.
 * Notez bien que ce composant n'est qu'un jouet ; si pour une raison
 *  quelconque il devait faire partie d'une biblioth�que de classes, il
 *  faudrait lui rajouter quantit� de fonctionnalit�s (possibilit� de changer
 *  le mod�le, de modifier les couleurs, ...) 
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
 *     $DESC$ Un IMobileComponent avec d�finition de la taille du fond et du
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
public interface AnimableComponent extends Animable {
    
    // ATTRIBUTS
    
    /**
     * La couleur (bleu) du rectangle statique du mod�le.
     */
    Color STAT_COLOR = Color.BLUE;
    
    /**
     * La couleur (rouge) du rectangle mobile du mod�le.
     */
    Color MOV_COLOR = Color.RED;

    // REQUETES
    
    /**
     * La position courante du centre du disque.
     */
    Point getDiscCenter();
    
    /**
     * La valeur de la composante horizontale du d�placement �l�mentaire
     *  du disque.
     * Vaut z�ro si le disque est captur�.
     */
    int getHorizontalShift();
    
    /**
     * Le mod�le de ce MobileComponent.
     */
    Mobile getModel();
    
    /**
     * La valeur de la composante verticale du d�placement �l�mentaire
     *  du disque.
     * Vaut z�ro si le disque est captur�.
     */
    int getVerticalShift();
    
    /**
     * Indique si le disque est captur� ou non avec la souris.
     */
    boolean isDiscCaught();

    // COMMANDES
    
    /**
     * D�place le disque d'un mouvement �l�mentaire.
     * @post
     *     !discIsCaught() ==> le disque a �t� d�plac� (avec un �ventuel rebond)
     */
    void animate();
    
    /**
     * Fixe le centre du disque.
     * @post
     *     Si p �tait une position admissible le centre du disque est en p
     *     Sinon le disque n'a pas boug�
     */
    void setDiscCenter(Point p);
    
    /**
     * Fixe le d�placement �l�mentaire du disque sur le fond.
     * Les d�placements sont exprim�s en pixels.
     * @post
     *     getHorizontalShift() == dx
     *     getVerticalShift() == dy
     */
    void setDiscShift(int dx, int dy);
}
