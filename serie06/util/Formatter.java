package serie06.util;

public class Formatter {
    
    // CONSTANTES
    
    // numéro d'instance des formateurs, permettant de calculer une indentation
    // unique pour chaque formateur
    private static int indentFactor = 0;
    // initialement -1, mémorise l'instant initial d'une exécution
    private static long startTime;
    // un formateur possède un bloc d'indentation équivalent à
    // indentFactor * INDENT
    private static final String INDENT = "               |"; // 15 espaces
    // ratio pour convertir les nanosecondes en millisecondes
    private static final int NANO_TO_MILLI = 1000000;

    // ATTRIBUTS

    // indentation de ce formateur
    private final String indent;
    // nom de l'acteur associé à ce formateur
    private final String name;
    
    // CONSTRUCTEURS
    
    public Formatter(String n) {
        name = n;
        StringBuffer idt = new StringBuffer();
        for (int i = 0; i < indentFactor; i++) {
            idt.append(INDENT);
        }
        indent = idt.toString();
        indentFactor += 1;
    }

    // REQUETES
    
    public String format(String m) {
        return prefix() + m + "\n";
    }
    
    public String prefix() {
        return getTime() + " ms  " + indent + name + " : ";
    }
    
    // METHODES STATIQUES
    
    public static synchronized void resetTime() {
        startTime = System.nanoTime();
    }
    
    private static synchronized int getTime() {
        return (int) ((System.nanoTime() - startTime) / NANO_TO_MILLI);
    }
}
