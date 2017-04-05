package serie08.event;

import java.util.EventObject;

public class ResourceEvent<R> extends EventObject {
    
    // ATTRIBUTS
    
    private final R data;

    // CONSTRUCTEURS
    
    public ResourceEvent(Object source, R d) {
        super(source);
        data = d;
    }

    // REQUETES
    
    public R getResource() {
        return data;
    }
}
