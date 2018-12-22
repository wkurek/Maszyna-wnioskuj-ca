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
    public SubstitutionSet getSolution(ClausureSet solutionClausureSet) {
        for(int i = 0; i < clausureSet.getClousuresCount(); ++i) {
            Clausure clausure = clausureSet.getClausures(i);
            Conclusion conclusion = clausure.getConclusion();

            SubstitutionSet newSubstitutionSet = currentGoal.unify(conclusion, substitutionSet);

            if(newSubstitutionSet != null) {
                if(!clausure.hasPremise()){
                    if(solutionClausureSet == null) solutionClausureSet = new ClausureSet();
                    solutionClausureSet.add(clausure);

                    return newSubstitutionSet;
                }

                ClausureSet newClausureSet = new ClausureSet(clausureSet);
                newClausureSet.remove(i);

                SubstitutionSet result = clausure.getPremise().getNode(newSubstitutionSet, newClausureSet).getSolution(solutionClausureSet);
                if(result != null) {
                    if(solutionClausureSet == null) solutionClausureSet = new ClausureSet();
                    solutionClausureSet.add(clausure);

                    return result;
                }
            }
        }

        return null;
    }
}