import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import javafx.util.Pair;
import model.*;
import model.graph.PredicateNode;
import util.arguments.Arguments;
import util.parser.ClausuresParser;
import util.printer.ResultPrinter;

import java.io.IOException;
import java.util.List;

import static util.parser.KnowledgeBaseExtender.extendKnowledgeBase;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            Arguments arguments = new Arguments();

            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(args);

            //Read model defined in input files
            ClausuresParser parser = new ClausuresParser();

            List<Constant> constants = parser.getConstants(arguments.constantsFilePath);
            List<Variable> variables = parser.getVariables(arguments.variablesFilePath);
            Predicate predicateToBeProven = parser.getPredicateToProve(arguments.argumentFilePath,
                    constants, variables);

            ClausureSet knowledgeBase = parser.getClausures(arguments.knowledgeBaseFilePath,
                    constants, variables);
            extendKnowledgeBase(knowledgeBase, constants);

            //Prove using algorithm
            SubstitutionSet substitutionSet = new SubstitutionSet();
            ClausureSet usedClausures = new ClausureSet();

            Pair<SubstitutionSet, ClausureSet> result = new PredicateNode(knowledgeBase,
                    substitutionSet, predicateToBeProven)
                    .getSolution(usedClausures);

            if(result != null) {
                //Argument proved by algorithm

                substitutionSet = result.getKey();

                ResultPrinter.print(knowledgeBase, predicateToBeProven, usedClausures, substitutionSet);
            } else {
                //Argument not proved by algorithm
                //TODO: print that algorithm cannot prove
                System.out.println("Argument can not be proved by algorithm.");
            }

        } catch(ParameterException parameterException) {
            System.err.println(parameterException.getMessage());
            parameterException.getJCommander().usage();
        }
    }
}

