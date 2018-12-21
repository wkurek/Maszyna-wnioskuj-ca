package model;

import java.util.ArrayList;

public class Predicate implements Expression, Unifable, Goal {
    private ArrayList<Unifable> arguments;

    Predicate(Constant name, ArrayList<Unifable> args) {
        arguments = new ArrayList<>(args);
        arguments.add(0, name);
    }

    private int getSize() {
        return arguments.size();
    }

    private Unifable getArgument(int index) {
        return arguments.get(index);
    }

    public String toString() {
        String string = "";

        for(Unifable expression : arguments) {
            string = string.concat(expression.toString() + ", ");
        }

        return "( " + string + ")";
    }

    @Override
    public SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet) {
        if((expression instanceof Predicate) && (((Predicate) expression).getSize() == getSize())) {
            SubstitutionSet newSubstitutionSet = new SubstitutionSet();

            for(int i = 0; i < getSize(); ++i) {
                Unifable unifiable1 = this.getArgument(i);
                Unifable unifiable2 = ((Predicate) expression).getArgument(i);

                newSubstitutionSet = unifiable1.unify(unifiable2, newSubstitutionSet);
            }
        } else if (expression instanceof Variable) {
            return expression.unify(this, substitutionSet);
        }

        return null;
    }
}
