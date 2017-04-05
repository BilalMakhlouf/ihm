package serie03;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.event.ChangeListener;

/**
 * Un mobile modélise un petit rectangle qui se déplace à l'intérieur d'un
 *  grand rectangle.<br />
 * Le déplacement du rectangle mobile s'effectue exclusivement à l'intérieur du
 *  rectangle statique : lorsque il touche le bord du rectangle statique, le
 *  rectangle mobile rebondit.
 * À chaque déplacement du rectangle mobile, tous les ChangeListeners
 *  enregistrés auprès du mobile sont notifiés.
 * Le rectangle mobile peut être immobilisé.
 * 
 * @inv
 *     getCenter() != null
 *     getMovingRect() != null
 *     getStaticRect() != null
 *     getChangeListeners() != null
 *     getCenter().x == getMovingRect().x + getMovingRect().width / 2
 *     getCenter().y == getMovingRect().y + getMovingRect().height / 2
 *     getMovingRect().height >= 0
 *     getMovingRect().width >= 0
 *     getStaticRect().height >= 0
 *     getStaticRect().width >= 0
 *     getStaticRect().contains(getMovingRect())
 *     forall i:[0..getChangeListeners().length[ :
 *         getChangeListeners()[i] != null
 *     isValidCenterPosition(c)
 *         == c != null
 *            getStaticRect().contains(
 *                c.x - getMovingRect().width/2,
 *                c.y - getMovingRect().height/2,
 *                getMovingRect().width,
 *                getMovingRect().height
 *            )
 * @cons
 * $DESC$
 *     Un mobile de rectangle statique <code>sr</code>, de rectangle
 *      mobile <code>mr</code>, de déplacement initial null (horizontal et
 *      vertical).
 * $ARGS$ Rectangle sr, Rectangle mr
 * $PRE$
 *     mr != null
 *     mr.height > 0
 *     mr.width > 0
 *     sr != null
 *     sr.contains(mr)
 * $POST$
 *     getStaticRect().equals(sr)
 *     getMovingRect().equals(mr)
 *     getHorizontalShift() == 0
 *     getVerticalShift() == 0
 */
public interface Mobile {

    // REQUETES
    
    /**
     * Le centre du rectangle mobile.
     */
    Point getCenter();
    
    /**
     * Un tableau des ChangeListeners associés à ce mobile.
     */
    ChangeListener[] getChangeListeners();
    
    /**
     * La composante horizontale lors du déplacement du mobile.
     * C'est le nombre de pixels horizontaux dont le rectangle mobile se
     *  déplace au cours d'un appel à <code>move()</code>.
     */
    int getHorizontalShift();
    
    /**
     * Une copie du rectangle mobile.
     */
    Rectangle getMovingRect();
    
    /**
     * Une copie du rectangle statique dans lequel le rectangle mobile
     *  se meut.
     */
    Rectangle getStaticRect();
    
    /**
     * La composante verticale lors du déplacement du mobile.
     * C'est le nombre de pixels verticaux dont le rectangle mobile se
     *  déplace au cours d'un appel à <code>move()</code>.
     */
    int getVerticalShift();
    
    /**
     * Indique si le rectangle mobile peut être déplacé avec move().
     */
    boolean isMovable();
    
    /**
     * Indique si le point <code>p</code> est une position admissible pour
     *  le centre du rectangle mobile.
     * C'est-à-dire si, en plaçant le centre du rectangle mobile en p,
     *  celui-ci est toujours entièrement contenu dans le rectangle statique.
     */
    boolean isValidCenterPosition(Point p);
    
    // COMMANDES
    
    /**
     * Associe un ChangeListener à ce mobile.
     * @post
     *     listener != null
     *         ==> exists i:[0..getChangeListeners().length[ :
     *             listener == getChangeListeners()[i]
     */
    void addChangeListener(ChangeListener listener);
    
    /**
     * Déplace le rectangle mobile à l'intérieur du rectangle statique.
     * Le rectangle mobile tente de se déplacer en accord avec sa quantité
     *  de déplacement (un couple d'entiers défini par
     *  <code>setHorizontalShift()</code> et <code>setVerticalShift()</code>).
     * Si, ce faisant, il rencontre le bord du rectangle statique,
     *  il se colle à celui-ci, prêt à rebondir au prochain déplacement.
     * @pre
     *     isMovable()
     * @post
     *     Le rectangle mobile s'est déplacé dans le rectangle statique
     *     Si le rectangle mobile n'a pas touché le bord il s'est déplacé
     *      de (getHorizontalShift(), getVerticalShift())
     *     Sinon il se colle au rectangle statique et change de direction pour
     *      simuler le rebond
     *     Notife tous ses ChangeListeners
     */
    void move();
    
    /**
     * Retire le ChangeListener spécifié de ce mobile.
     * @post
     *     forall i:[0..getChangeListeners().length[ :
     *         listener != getChangeListeners()[i]
     */
    void removeChangeListener(ChangeListener listener);
    
    /**
     * Définit la position du centre du rectangle mobile.
     * @pre
     *     isValidCenterPosition(c)
     * @post
     *     getMovingRect().width == old getMovingRect().width
     *     getMovingRect().height == old getMovingRect().height
     *     getMovingRect().getLocation().equals(
     *         new Point(
     *             c.x - getMovingRect().width/2,
     *             c.y - getMovingRect().height/2
     *         )
     *     )
     *     Notife tous ses ChangeListeners
     */
    void setCenter(Point c);
    
    /**
     * Définit la composante horizontale (en pixels) pour le déplacement
     *  du mobile.
     * @post
     *     getHorizontalShift() == hs
     */
    void setHorizontalShift(int hs);
    
    /**
     * Fixe la possibilité de déplacement par move().
     * @post
     *     isMovable() == movable
     */
    void setMovable(boolean movable);
    
    /**
     * Définit la composante verticale (en pixel) pour le déplacement
     *  du mobile.
     * @post
     *     getVerticalShift() == vs
     */
    void setVerticalShift(int vs);
}
