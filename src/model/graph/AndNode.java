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
    public SubstitutionSet getSolution(ClausureSet solutionClausureSet) {
        for(int i = 0; i < clausureSet.getClousuresCount(); ++i) {
            Clausure clausure = clausureSet.getClausures(i);
            Unifable conclusion = clausure.getConclusion();

            SubstitutionSet newSubstitutionSet = ((Unifable) currentGoal.getFirstOperand()).unify(conclusion, substitutionSet);

            if(newSubstitutionSet != null) {
                ClausureSet newClausureSet = new ClausureSet(clausureSet);
                newClausureSet.remove(i);

                newSubstitutionSet = currentGoal.getTailOperator()
                        .getNode(newSubstitutionSet, newClausureSet)
                        .getSolution(solutionClausureSet);

                if(!clausure.hasPremise()) {
                    if(solutionClausureSet == null) solutionClausureSet = new ClausureSet();
                    solutionClausureSet.add(clausure);

                    return newSubstitutionSet;
                }


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
