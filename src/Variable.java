public class Variable implements Expression, Unifable {
    private static int idCounter = 0;

    private int id;
    private String name;

    Variable() {
        this.id = idCounter++;
    }

    Variable(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public String toString() {
        String string = "VARIABLE_";

        if(name != null) string = string.concat(name);

        return string.concat(Integer.toString(id));
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
}
