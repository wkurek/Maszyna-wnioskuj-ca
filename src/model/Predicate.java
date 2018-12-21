package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Predicate implements Unifable, Goal {
    private ArrayList<Unifable> arguments;

    public Predicate(Constant name, List<Unifable> args) {
        arguments = new ArrayList<>(args);
        arguments.add(0, name);
    }

    public Predicate(Constant name, Unifable... args) {
        this(name, Arrays.asList(args));
    }

    private int getSize() {
        return arguments.size();
    }

    private Unifable getArgument(int index) {
        return arguments.get(index);
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

            return newSubstitutionSet;
        } else if (expression instanceof Variable) {
            return expression.unify(this, substitutionSet);
        }

        return null;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        ArrayList<Unifable> args = new ArrayList<>();

        for(Unifable arg : arguments) {
            args.add((Unifable) arg.replaceVariables(substitutionSet));
        }

        Constant name = (Constant) args.remove(0);

        return new Predicate(name, args);
    }

    @Override
    public String toString() {
        String string = "(";

        for(int i = 0; i < getSize(); ++i) {
            string = string.concat(getArgument(i).toString());
            if(i != (getSize() - 1)) string = string.concat(", ");
        }

        return string.concat(")");
    }
}
