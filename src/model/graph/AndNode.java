package model.graph;

import model.*;

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
    public SubstitutionSet getSolution() {
        for(int i = 0; i < clausureSet.getClousuresCount(); ++i) {
            Clausure clausure = clausureSet.getClausures(i);
            Unifable conclusion = clausure.getConclusion();

            SubstitutionSet newSubstitutionSet = ((Unifable) currentGoal.getFirstOperand()).unify(conclusion, substitutionSet);

            if(newSubstitutionSet != null) {
                if(!currentGoal.hasTailOperands()) return newSubstitutionSet;

                ClausureSet newClausureSet = new ClausureSet(clausureSet);
                newClausureSet.remove(i);

                return currentGoal.getTailOperator().getNode(newSubstitutionSet, newClausureSet).getSolution();
            }

        }

        return null;
    }

}
