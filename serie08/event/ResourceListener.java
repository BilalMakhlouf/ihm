package serie08.event;

import java.util.EventListener;

public interface ResourceListener extends EventListener {
    
    void resourceLoaded(ResourceEvent<String> e);
    
    void resourceSaved(ResourceEvent<String> e);
    
    void progressUpdated(ResourceEvent<Integer> e);
    
    void errorOccured(ResourceEvent<Exception> e);
}
