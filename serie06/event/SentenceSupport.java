package serie06.event;

import javax.swing.event.EventListenerList;

public class SentenceSupport {
    
    private EventListenerList listenersList;
    private final Object owner;
    
    public SentenceSupport(Object owner) {
        this.owner = owner;
    }

    public synchronized SentenceListener[] getSentenceListeners() {
        if (listenersList == null) {
            return new SentenceListener[0];
        }
        return listenersList.getListeners(SentenceListener.class);
    }
    
    public synchronized void addSentenceListener(SentenceListener listener) {
        if (listener == null) {
            return;
        }
        if (listenersList == null) {
            listenersList = new EventListenerList();
        }
        listenersList.add(SentenceListener.class, listener);
    }
    
    public synchronized void removeSentenceListener(SentenceListener listener) {
        if (listener == null || listenersList == null) {
            return;
        }
        listenersList.remove(SentenceListener.class, listener);
    }
    
    public void fireSentenceSaid(String sentence) {
        SentenceListener[] list = getSentenceListeners();
        if (list.length > 0) {
            SentenceEvent e = new SentenceEvent(owner, sentence);
            for (SentenceListener lst : list) {
                lst.sentenceSaid(e);
            }
        }
    }
}
