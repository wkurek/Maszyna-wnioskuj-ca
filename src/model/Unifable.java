package model;

public interface Unifable extends Expression{
    public SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet);
}
