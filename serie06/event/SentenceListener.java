package serie06.event;

import java.util.EventListener;

public interface SentenceListener extends EventListener {
    void sentenceSaid(SentenceEvent e);
}
