package square.util;

public enum Player {
    X, O;
    private static final Player[] VALUES = values();
    public Player getNext() {
        return VALUES[(ordinal() + 1) % VALUES.length];
    }
    public Player getPrevious() {
        return VALUES[(ordinal() - 1 + VALUES.length) % VALUES.length];
    }
}
