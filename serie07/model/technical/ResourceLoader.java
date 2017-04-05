package serie07.model.technical;

import serie07.event.ResourceLoaderListener;
import serie07.event.ResourceLoaderSupport;

public abstract class ResourceLoader<R> {
    
    // ATTRIBUTS
    
    private final ResourceLoaderSupport<R> resLoadSup;

    // CONSTRUCTEURS
    
    public ResourceLoader() {
        resLoadSup = new ResourceLoaderSupport<R>(this);
    }

    // COMMANDES
    
    public void addResourceLoaderListener(ResourceLoaderListener<R> lst) {
        resLoadSup.addResourceLoaderListener(lst);
    }
    
    public abstract void loadResource() throws Exception;
    
    public void removeResourceLoaderListener(ResourceLoaderListener<R> lst) {
        resLoadSup.removeResourceLoaderListener(lst);
    }

    protected void fireResourceLoaded(R data) {
        resLoadSup.fireResourceLoaded(data);
    }
    
    // OUTILS
    
    // cette méthode sert à ralentir les actions pour qu'on aie le temps
    // de voir que ça rame...
    protected void delayAction() {
        final int delay = 100;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
