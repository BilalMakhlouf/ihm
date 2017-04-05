package serie07.event;

import java.util.EventListener;

public interface ResourceLoaderListener<R> extends EventListener {
    void resourceLoaded(ResourceLoaderEvent<R> e);
}
