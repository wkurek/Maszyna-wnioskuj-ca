package model;

public class Clausure implements Expression {
    private Predicate conclusion;
    private Goal premise;

    public Clausure(Predicate predicate, Goal goal) {
        conclusion = predicate;
        premise = goal;
    }

    Predicate getConclusion() {
        return conclusion;
    }

    Goal getPremise() {
        return premise;
    }

    private boolean hasPremise() {
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
