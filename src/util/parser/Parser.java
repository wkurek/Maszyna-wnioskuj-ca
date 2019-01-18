package util.parser;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Parser {
    List<Constant> getConstants(String filePath) throws IOException;
    List<Variable> getVariables(String filePath) throws IOException;
    Predicate getPredicateToProve(String filePath, List<Constant> constants, List<Variable> variables) throws IOException;
    ClausureSet getClausures(String filePath, List<Constant> constants, List<Variable> variables) throws IOException;
}
