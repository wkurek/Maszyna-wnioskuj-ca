package model;

public interface Unifable extends Expression{
    SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet);
}
