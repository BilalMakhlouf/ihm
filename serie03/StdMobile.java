package serie03;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import util.Contract;

/**
 * Implantation standard de <code>Mobile</code>.<br />
 * Evidemment un StdMobile est observable par le biais d'un ChangeListener,
 *  afin d'implanter la partie modèle de l'architecture MVC.
 */
public class StdMobile implements Mobile {
    
    // ATTRIBUTS
    
    /**
     * Le rectangle statique : le grand rectangle à l'intérieur duquel
     *  le rectangle mobile évolue.
     */
    private Rectangle statRect;
    
    /**
     * Le rectangle mobile : le petit rectangle qui se meut à l'intérieur
     *  du rectangle statique.
     */
    private Rectangle movRect;
    
    /**
     * L'accroissement horizontal lors du déplacement.
     * positif : déplacement vers la droite de l'écran
     * négatif : déplacement vers la gauche de l'écran
     */
    private int dx;
    
    /**
     * L'accroissement vertical lors du déplacement.
     * positif : déplacement vers le bas de l'écran
     * négatif : déplacement vers le haut de l'écran
     */
    private int dy;
    
    /**
     * Détermine si le rectangle mobile est déplaçable.
     */
    private boolean movable;
    
    private final EventListenerList listeners;
    private final ChangeEvent changeEvent;

    // CONSTRUCTEURS
    
    public StdMobile(Rectangle sr, Rectangle mr) {
        Contract.checkCondition(sr != null && sr.width >= 0 && sr.height >= 0);
        Contract.checkCondition(mr != null && mr.width >= 0 && mr.height >= 0);
        Contract.checkCondition(sr.contains(mr));

        statRect = sr;
        movRect = mr;
        dx = 0;
        dy = 0;
        movable = true;
        listeners = new EventListenerList();
        changeEvent = new ChangeEvent(this);
    }

    // REQUETES
    
    public Point getCenter() {
        Point c = movRect.getLocation();
        c.x += movRect.width / 2;
        c.y += movRect.height / 2;
        return c;
    }
    
    public ChangeListener[] getChangeListeners() {
        return listeners.getListeners(ChangeListener.class);
    }
    
    public int getHorizontalShift() {
        return dx;
    }
    
    public Rectangle getMovingRect() {
        return new Rectangle(movRect);
    }
    
    public Rectangle getStaticRect() {
        return new Rectangle(statRect);
    }
    
    public int getVerticalShift() {
        return dy;
    }
    
    public boolean isMovable() {
        return movable;
    }
    
    public boolean isValidCenterPosition(Point p) {
        if (p == null) {
            return false;
        }
        return
            getStaticRect().contains(
                p.x - movRect.width / 2,
                p.y - movRect.height / 2,
                movRect.width,
                movRect.height);
    }
    
    // COMMANDES
    
    public void addChangeListener(ChangeListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(ChangeListener.class, listener);
    }
    
    public void move() {
        Contract.checkCondition(movable);

        /*
         * Position du coin sup gauche du rectangle mobile avant déplacement.
         */
        Point oldTlc = movRect.getLocation();
        /*
         * Position du coin sup gauche du rectangle mobile après déplacement
         *  d'un pas (dx, dy).
         * Initialement, on imagine qu'il n'y a pas de choc avec la paroi.
         * Si c'est faux, on rectifiera avant de déplacer effectivement movRect.
         */
        Point newTlc = new Point(oldTlc.x + dx, oldTlc.y + dy);
        /*
         * Position du coin inférieur droit du rectangle mobile après
         *  déplacement, en imaginant qu'il n'y a pas de choc avec la paroi.
         */
        Point newBrc = new Point(
            newTlc.x + movRect.width,
            newTlc.y + movRect.height
        );
        if (newTlc.y <= statRect.y) {
            // movRect veut sortir de statRect par le bord haut
            newTlc.y = statRect.y;
            // on change de sens dans la direction verticale
            dy = -dy;
        } else if (newBrc.y >= statRect.y + statRect.height) {
            // movRect veut sortir de statRect par le bord bas
            newTlc.y = statRect.y + statRect.height - movRect.height;
            // on change de sens dans la direction verticale
            dy = -dy;
        }
        if (newTlc.x <= statRect.x) {
            // movRect veut sortir de statRect par le bord gauche
            newTlc.x = statRect.x;
            // on change de sens dans la direction horizontale
            dx = -dx;
        } else if (newBrc.x >= statRect.x + statRect.width) {
            // movRect veut sortir de statRect par le bord droit
            newTlc.x = statRect.x + statRect.width - movRect.width;
            // on change de sens dans la direction horizontale
            dx = -dx;
        }
        movRect.setLocation(newTlc);
        fireStateChanged();
    }
    
    public void removeChangeListener(ChangeListener listener) {
        if (listener == null) {
            return;
        }
        listeners.remove(ChangeListener.class, listener);
    }
    
    public void setCenter(Point c) {
        Contract.checkCondition(c != null);
        Contract.checkCondition(isValidCenterPosition(c));

        Point tlc = new Point(
                c.x - movRect.width / 2,
                c.y - movRect.height / 2
        );
        movRect.setLocation(tlc);
        fireStateChanged();
    }
    
    public void setHorizontalShift(int hs) {
        dx = hs;
    }
    
    public void setVerticalShift(int vs) {
        dy = vs;
    }
    
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    // OUTILS
    
    protected void fireStateChanged() {
        ChangeListener[] listnrs = getChangeListeners();
        for (ChangeListener lst : listnrs) {
            lst.stateChanged(changeEvent);
        }
    }
}
