package model;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String toString() {
        if(substitutions.isEmpty()) return "[]";

        String string = "{";

        for(Map.Entry<Variable, Unifable> substitution : substitutions.entrySet()) {
            string = string.concat(substitution.getKey().toString());
            string = string.concat("/");
            string = string.concat(substitution.getValue().toString());
            string = string.concat(", ");
        }

        return string.substring(0, string.length() - 2).concat("}");
    }
}
