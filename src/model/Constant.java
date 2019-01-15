package model;

public class Constant implements Unifable {
    private static int idCounter = 0;

    private int id;
    private String name;

    public Constant() {
        this.id = idCounter++;
    }

    public Constant(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public String getName() {
        return name;
    }

    @Override
    public SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet) {
        if (expression == this) {
            return new SubstitutionSet(substitutionSet);
        } else if (expression instanceof Variable) {
            return expression.unify(this, substitutionSet);
        }

        return null;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        return this;
    }

    @Override
    public String toString() {
        String string = "CONST_";
        if(name != null) string = string.concat(name.toUpperCase());

        return string.concat("_" + id);
    }
}
