package util.printer;

import model.ClausureSet;
import model.Predicate;
import model.SubstitutionSet;

public class ResultPrinter {
    public static void print(ClausureSet knowledgeBase, Predicate predicateToProve, ClausureSet usedClausures, SubstitutionSet substitutionSet) {
        //TODO: Implement this method
        System.out.println("Baza wiedzy:\n"+knowledgeBase.toString2());
        System.out.println("\nTeza: "+predicateToProve.toString());
        System.out.println("\nZbiór podstawień: "+substitutionSet);
        System.out.println("\nUżyte klauzule:\n"+usedClausures.toString2());
        System.out.println("\nGraf wnioskowania:\n"+usedClausures.toString3(substitutionSet));
    }
}
