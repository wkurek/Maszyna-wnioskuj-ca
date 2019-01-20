package model.graph;

import javafx.util.Pair;
import model.*;
import model.operator.Operator;

public class AndNode implements Node {
    private ClausureSet clausureSet;
    private SubstitutionSet substitutionSet;
    private Operator currentGoal;

    public AndNode(ClausureSet clausures, SubstitutionSet substitutions, Goal goal) {
        clausureSet = clausures;
        substitutionSet = substitutions;
        currentGoal = (Operator) goal;
    }

    @Override
    public Pair<SubstitutionSet, ClausureSet> getSolution(ClausureSet solutionClausureSet) {
        if(solutionClausureSet == null) solutionClausureSet = new ClausureSet();
        final int startSolutionClausureSetCount = solutionClausureSet.getClousuresCount();

        for(int i = 0; i < clausureSet.getClousuresCount(); ++i) {
            Clausure clausure = clausureSet.getClausures(i);
            Unifable conclusion = clausure.getConclusion();

            SubstitutionSet newSubstitutionSet = (currentGoal.getFirstOperand()).unify(conclusion, substitutionSet);

            if(newSubstitutionSet != null) {
                ClausureSet newClausureSet = new ClausureSet(clausureSet);
                newClausureSet.remove(i);

                Pair<SubstitutionSet, ClausureSet> result = currentGoal.getTailOperator()
                        .getNode(newSubstitutionSet, newClausureSet)
                        .getSolution(solutionClausureSet);

                if(result == null) continue;

                newSubstitutionSet = result.getKey();
                newClausureSet = result.getValue();


                if(!clausure.hasPremise()) {
                    solutionClausureSet.add(clausure);
                    return new Pair<>(newSubstitutionSet, newClausureSet);
                }

                result = clausure.getPremise().getNode(newSubstitutionSet, newClausureSet).getSolution(solutionClausureSet);
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
