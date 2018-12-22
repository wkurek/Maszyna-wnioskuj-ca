package model.graph;

import model.ClausureSet;
import model.SubstitutionSet;

public interface Node {
    SubstitutionSet getSolution(ClausureSet solutionClausureSet);
}
