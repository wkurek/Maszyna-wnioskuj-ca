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

    private static void extendKnowledgeBase(ClausureSet knowledgeBase, List<Constant> constants) {//A=>B, więc trzeba dodać -B=>-A
        int end = knowledgeBase.getClousuresCount();
        for(int i = 0; i<end; i++)
        {
            if(knowledgeBase.getClausures(i).hasPremise())
            {
                ClausureSet allExtensions = knowledgeBase.getClausures(i).generateExtensions(constants);
                for(int k = 0; k<allExtensions.getClousuresCount(); k++)
                {
                    if(!existsExtendedClausure(knowledgeBase, allExtensions.getClausures(k)))
                    {
                        knowledgeBase.add(allExtensions.getClausures(k));

                    }//there is no Clausure like the generated one yet
                }//for through all combinations

            }//if hasPremise
        }//for knowledgeSet
    }

    private static boolean existsExtendedClausure(ClausureSet knowledgeBase, Clausure clausure) {
        for(int i = 0; i<knowledgeBase.getClousuresCount(); i++)
        {
            if(clausure.sameAs(knowledgeBase.getClausures(i)))
                return true;
        }
        return false;
    }


}

