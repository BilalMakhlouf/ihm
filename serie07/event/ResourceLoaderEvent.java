package serie07.event;

import java.util.EventObject;

public class ResourceLoaderEvent<R> extends EventObject {
    
    // ATTRIBUTS
    
    private final R data;

    // CONSTRUCTEURS
    
    public ResourceLoaderEvent(Object source, R d) {
        super(source);
        data = d;
    }

    // REQUETES
    
    public R getResource() {
        return data;
    }
}
