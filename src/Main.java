import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import javafx.util.Pair;
import model.*;
import model.graph.PredicateNode;
import util.arguments.Arguments;
import util.parser.ClausuresParser;
import util.printer.ResultPrinter;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static util.parser.KnowledgeBaseExtender.extendKnowledgeBase;

public class Main {

    public static void main(String[] args){
        try {
            Arguments arguments = new Arguments();

            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(args);

            //Read model defined in input files
            ClausuresParser parser = new ClausuresParser();

            List<Constant> constants = parser.getConstants(arguments.constantsFilePath);
            if(constants==null)
                return;
            List<Variable> variables = parser.getVariables(arguments.variablesFilePath);
            if(variables==null)
                return;
            Predicate predicateToBeProven = parser.getPredicateToProve(arguments.argumentFilePath,
                    constants, variables);
            if(predicateToBeProven==null)
                return;

            ClausureSet knowledgeBase = parser.getClausures(arguments.knowledgeBaseFilePath,
                    constants, variables);
            if(knowledgeBase==null)
                return;
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
                System.out.println("Argument can not be proved by algorithm.");
            }

        } catch(ParameterException parameterException) {
            System.err.println(parameterException.getMessage());
            parameterException.getJCommander().usage();
        } catch(NoSuchFileException fileException) {
            System.err.println("Cannot find file:\t" + fileException.getFile());
        } catch (IOException e){
            System.err.println("Input file error:\t"+e.getMessage());
        }

    }
}

