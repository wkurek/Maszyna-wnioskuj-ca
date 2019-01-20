package model.graph;

import javafx.util.Pair;
import model.ClausureSet;
import model.SubstitutionSet;

public interface Node {
    Pair<SubstitutionSet, ClausureSet> getSolution(ClausureSet solutionClausureSet);
}
