import javafx.util.Pair;
import model.*;
import model.graph.AndNode;
import model.graph.PredicateNode;
import model.operator.AndOperator;
import model.operator.NotOperator;
import model.operator.Operator;
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
        constants = parser.getConstants("src/1_constants.txt");
        variables = parser.getVariables("src/1_variables.txt");
        predicateToProve = parser.getPredicateToProve("src/1_to_prove.txt", constants, variables);
        knowledgeBase = parser.getClausures("src/1_knowledge.txt", constants, variables);
        extendKnowledgeBase(knowledgeBase, constants);


        SubstitutionSet substitutionSet = new SubstitutionSet();
        ClausureSet usedClausures = new ClausureSet();


        Pair<SubstitutionSet, ClausureSet> result = new PredicateNode(knowledgeBase, substitutionSet, predicateToProve)
                .getSolution(usedClausures);

        if(result != null) {
            substitutionSet = result.getKey();

            ResultPrinter.print(knowledgeBase, predicateToProve, usedClausures, substitutionSet);
        } else {
            //TODO: print that algorithm cannot prove
        }

        System.out.print("END");
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
                        if(ClausuresParser.getConstant(constants, allExtensions.getClausures(k).getConclusion().getArgument(0).toString())==null)
                        {   //There is no negated predicate in constants (conclusion)
                            constants.add(new Constant(allExtensions.getClausures(k).getConclusion().getArgument(0).toString(), true));
                        }
                        if(allExtensions.getClausures(k).getPremise() instanceof Operator) {
                            for (int j = 0; j > ((Operator) allExtensions.getClausures(k).getPremise()).getOperandsCount(); j++)
                            {
                                if(ClausuresParser.getConstant(constants,((Operator)allExtensions.getClausures(k).getPremise()).getOperand(j).getArgument(0).toString())==null)
                                //There is no negated predicate in constants (premise)
                                {
                                    constants.add(new Constant(((Operator)allExtensions.getClausures(k).getPremise()).getOperand(j).getArgument(0).toString(), true));
                                }
                            }
                        }
                        else
                        {
                            if(ClausuresParser.getConstant(constants, ((Predicate)allExtensions.getClausures(k).getPremise()).getArgument(0).toString())==null)
                                constants.add(new Constant(((Predicate)allExtensions.getClausures(k).getPremise()).getArgument(0).toString(), true));
                        }
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

