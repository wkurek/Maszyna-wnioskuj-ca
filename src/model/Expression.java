package model;

public interface Expression {
    Expression replaceVariables(SubstitutionSet substitutionSet);
}
