package serie09;

import util.Contract;


public class Person {
    
    // ATTRIBUTS
    
    private Gender gender;
    private String name;
    
    // CONSTRUCTEURS
    
    public Person(Gender g) {
        Contract.checkCondition(g != null);
        
        gender = g;
        name = g.name();
    }
    
    public Person(String n, Gender g) {
        Contract.checkCondition(n != null && g != null);
        
        gender = g;
        name = n;
    }

    // REQUETES
    
    public Gender getGender() {
        return gender;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return getName();
    }

    // COMMANDES
    
    public void setGender(Gender g) {
        Contract.checkCondition(g != null);
        
        gender = g;
    }
    
    public void setName(String n) {
        Contract.checkCondition(n != null);
        
        name = n;
    }
}
