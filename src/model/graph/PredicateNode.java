package model.graph;

import model.*;

public class PredicateNode implements Node {
    private ClausureSet clausureSet;
    private SubstitutionSet substitutionSet;
    private Unifable currentGoal;

    public PredicateNode(ClausureSet clausures, SubstitutionSet substitutions, Goal goal) {
        clausureSet = clausures;
        substitutionSet = substitutions;
        currentGoal = (Unifable) goal;
    }

    @Override
    public SubstitutionSet getSolution() {
        for(int i = 0; i < clausureSet.getClousuresCount(); ++i) {
            Clausure clausure = clausureSet.getClausures(i);
            Conclusion conclusion = clausure.getConclusion();

            SubstitutionSet newSubstitutionSet = currentGoal.unify(conclusion, substitutionSet);

            if(newSubstitutionSet != null) {
                if(!clausure.hasPremise()){
                    System.out.println(clausure);
                    return newSubstitutionSet;
                }

                ClausureSet newClausureSet = new ClausureSet(clausureSet);
                newClausureSet.remove(i);

                SubstitutionSet result = clausure.getPremise().getNode(newSubstitutionSet, newClausureSet).getSolution();
                if(result != null) {
                    System.out.println(clausure);
                    return result;
                }
            }
        }

        return null;
    }
}