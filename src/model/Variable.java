package model;

public class Variable implements Unifable {
    private static int idCounter = 0;

    private int id;
    private String name;

    public Variable() {
        this.id = idCounter++;
    }

    public Variable(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    @Override
    public SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet) {
        if(expression == this) {
            return new SubstitutionSet(substitutionSet);
        }

        if(substitutionSet.isBound(this)) {
            Unifable binding = substitutionSet.getBinding(this);
            return expression.unify(binding, substitutionSet);
        }

        SubstitutionSet newSubstitutionSet = new SubstitutionSet(substitutionSet);
        newSubstitutionSet.addSubstitution(this, expression);
        return newSubstitutionSet;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        if(substitutionSet.isBound(this)) return substitutionSet.getBinding(this);
        else return this;
    }

    @Override
    public String toString() {
        String string = "VAR_";
        if(name != null) string = string.concat(name.toUpperCase());

        return string.concat("_" + id);
    }
}
