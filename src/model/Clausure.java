package model;

public class Clausure implements Expression {
    private Conclusion conclusion;
    private Goal premise;

    public Clausure(Conclusion conc, Goal goal) {
        conclusion = conc;
        premise = goal;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public Goal getPremise() {
        return premise;
    }

    public boolean hasPremise() {
        return premise != null;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        Predicate predicate = (Predicate) conclusion.replaceVariables(substitutionSet);
        Goal goal = (Goal) premise.replaceVariables(substitutionSet);

        return new Clausure(predicate, goal);
    }

    @Override
    public String toString() {
        String string = conclusion.toString();

        if(hasPremise()) {
            string = string.concat(" <= ");
            string = string.concat(premise.toString());
        }

        return string;
    }
}
