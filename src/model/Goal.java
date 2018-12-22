package model;

import model.graph.Node;

public interface Goal extends Expression {
    Node getNode(SubstitutionSet substitutionSet, ClausureSet clausureSet);
}
