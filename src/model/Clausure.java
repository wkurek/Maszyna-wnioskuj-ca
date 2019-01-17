package model;

public class Clausure implements Expression {
    private Conclusion conclusion;
    private Goal premise;
    private int ID;
    private static int count = 0;

    public Clausure(Conclusion conc, Goal goal) {
        conclusion = conc;
        premise = goal;
        ID=++count;
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
        Goal goal=null;
        if(premise!=null)
          goal = (Goal) premise.replaceVariables(substitutionSet);

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

    public int getID() {
        return ID;
    }

    public String toString2() {

        String string="";
        if(hasPremise()) {
            string = string.concat(premise.toString());
            string = string.concat(" => ");
        }
        string = string.concat(conclusion.toString());
        return string;
    }
}
