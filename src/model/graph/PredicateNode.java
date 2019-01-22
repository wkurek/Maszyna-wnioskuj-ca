package model.graph;

import javafx.util.Pair;
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
    public Pair<SubstitutionSet, ClausureSet> getSolution(ClausureSet solutionClausureSet) {
        if(solutionClausureSet == null) solutionClausureSet = new ClausureSet();
        final int startSolutionClausureSetCount = solutionClausureSet.getClousuresCount();

        for(int i = 0; i < clausureSet.getClousuresCount(); ++i) {
            Clausure clausure = clausureSet.getClausures(i);
            Conclusion conclusion = clausure.getConclusion();

            SubstitutionSet newSubstitutionSet = currentGoal.unify(conclusion, substitutionSet);

            if(newSubstitutionSet != null) {
                ClausureSet newClausureSet = new ClausureSet(clausureSet);
                if(clausure.hasPremise())
                    newClausureSet.remove(i);

                if(!clausure.hasPremise()){
                    solutionClausureSet.add(clausure);
                    return new Pair<>(newSubstitutionSet, newClausureSet);
                }

                Pair<SubstitutionSet, ClausureSet> result = clausure.getPremise().getNode(newSubstitutionSet, newClausureSet).getSolution(solutionClausureSet);
                if(result != null) {
                    solutionClausureSet.add(clausure);
                    return result;
                }
            }

            if(solutionClausureSet.getClousuresCount() != startSolutionClausureSetCount) {
                int diff = solutionClausureSet.getClousuresCount() - startSolutionClausureSetCount;
                for(int j = 0; j < diff; ++j) {
                    solutionClausureSet.remove(solutionClausureSet.getClousuresCount() - 1);
                }
            }
        }



        return null;
    }
}