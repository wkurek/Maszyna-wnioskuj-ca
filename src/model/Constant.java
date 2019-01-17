package model;

public class Constant implements Unifable {
    private static int idCounter = 0;

    private int id;
    private String name;
    private boolean predicateName;

    public Constant() {
        this.id = idCounter++;
    }

    public Constant(String name, boolean predicateName) {
        this.name = name;
        this.id = idCounter++;
        this.predicateName=predicateName;
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
        String string = "";
        if(name != null) string = string.concat(name);

        if(!predicateName)
            string+="_"+id;
        return string;
    }

}
