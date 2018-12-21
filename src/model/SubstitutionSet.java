package model;

import java.util.HashMap;

public class SubstitutionSet {
    private HashMap<Variable, Unifable> substitutions;

    public SubstitutionSet(SubstitutionSet substitutionSet) {
        substitutions = new HashMap<>(substitutionSet.substitutions);
    }

    public SubstitutionSet() {
        substitutions = new HashMap<>();
    }

    void addSubstitution(Variable variable, Unifable unifable) {
        substitutions.put(variable, unifable);
    }

    void clear() {
        substitutions.clear();
    }

    boolean isBound(Variable variable) {
        return substitutions.containsKey(variable);
    }

    Unifable getBinding(Variable variable) {
        return substitutions.get(variable);
    }

    boolean isEmpty() {
        return substitutions.isEmpty();
    }
}
