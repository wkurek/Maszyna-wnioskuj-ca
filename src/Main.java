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
        constants = parser.getConstants("src/example_constant");
        variables = parser.getVariables("src/example_variables");
        predicateToProve = parser.getPredicateToProve("src/example_to_prove", constants, variables);
        knowledgeBase = parser.getClausures("src/examples", constants, variables);
        extendKnowledgeBase(knowledgeBase);


        SubstitutionSet substitutionSet = new SubstitutionSet();
        ClausureSet usedClausures = new ClausureSet();

        substitutionSet = new PredicateNode(knowledgeBase, substitutionSet, predicateToProve).getSolution(usedClausures);

        ResultPrinter.print(knowledgeBase, predicateToProve, usedClausures, substitutionSet);

        System.out.print("END");
    }

    private static void extendKnowledgeBase(ClausureSet knowledgeBase) {
        for(int i = 0; i<knowledgeBase.getClousuresCount(); i++)
        {

        }
    }


}

