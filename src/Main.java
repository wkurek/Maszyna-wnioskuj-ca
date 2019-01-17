import model.*;
import model.graph.PredicateNode;
import model.operator.AndOperator;
import model.operator.NotOperator;
import util.ClausuresParser;
import util.printer.ResultPrinter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Constant> constants;
        List<Variable> variables;
        Predicate predicateToProve;
        ClausureSet knowledgeBase;

        ClausuresParser parser = new ClausuresParser();
        constants = parser.getConstants("src/michal_example_constant.txt");
        variables = parser.getVariables("src/michal_example_variables.txt");
        predicateToProve = parser.getPredicateToProve("src/michal_to_prove.txt", constants, variables);
        knowledgeBase = parser.getClausures("src/michal_example.txt", constants, variables);


        SubstitutionSet substitutionSet = new SubstitutionSet();
        ClausureSet usedClausures = new ClausureSet();

        substitutionSet = new PredicateNode(knowledgeBase, substitutionSet, predicateToProve).getSolution(usedClausures);

        ResultPrinter.print(usedClausures, substitutionSet);

        System.out.print("END");
    }





}

