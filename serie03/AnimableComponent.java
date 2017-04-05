package serie03;

import java.awt.Color;
import java.awt.Point;

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
public interface AnimableComponent extends Animable {
    
    // ATTRIBUTS
    
    /**
     * La couleur (bleu) du rectangle statique du modèle.
     */
    Color STAT_COLOR = Color.BLUE;
    
    /**
     * La couleur (rouge) du rectangle mobile du modèle.
     */
    Color MOV_COLOR = Color.RED;

    // REQUETES
    
    /**
     * La position courante du centre du disque.
     */
    Point getDiscCenter();
    
    /**
     * La valeur de la composante horizontale du déplacement élémentaire
     *  du disque.
     * Vaut zéro si le disque est capturé.
     */
    int getHorizontalShift();
    
    /**
     * Le modèle de ce MobileComponent.
     */
    Mobile getModel();
    
    /**
     * La valeur de la composante verticale du déplacement élémentaire
     *  du disque.
     * Vaut zéro si le disque est capturé.
     */
    int getVerticalShift();
    
    /**
     * Indique si le disque est capturé ou non avec la souris.
     */
    boolean isDiscCaught();

    // COMMANDES
    
    /**
     * Déplace le disque d'un mouvement élémentaire.
     * @post
     *     !discIsCaught() ==> le disque a été déplacé (avec un éventuel rebond)
     */
    void animate();
    
    /**
     * Fixe le centre du disque.
     * @post
     *     Si p était une position admissible le centre du disque est en p
     *     Sinon le disque n'a pas bougé
     */
    void setDiscCenter(Point p);
    
    /**
     * Fixe le déplacement élémentaire du disque sur le fond.
     * Les déplacements sont exprimés en pixels.
     * @post
     *     getHorizontalShift() == dx
     *     getVerticalShift() == dy
     */
    void setDiscShift(int dx, int dy);
}
