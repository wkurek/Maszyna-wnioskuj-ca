package util;

import model.*;
import model.operator.AndOperator;
import util.parser.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClausuresParser implements Parser {

    @Override
    public List<Constant> getConstants(String filePath) throws IOException {
        String filePath_constant = new File(filePath).getAbsolutePath();
        FileReader fileReader_constant = new FileReader(filePath_constant);
        BufferedReader bufferedReader_constant = new BufferedReader(fileReader_constant);
        ArrayList<String> lines_constant = new ArrayList<String>();
        String line_read_constant = null;
        while ((line_read_constant = bufferedReader_constant.readLine()) != null) {
            lines_constant.add(line_read_constant);
        }
        bufferedReader_constant.close();

        List<Constant> resultSet = new ArrayList<>();
        for (String s : lines_constant) {
            if(s.matches("[A-Z]+$"))
                resultSet.add(new Constant(s, true));
            else
                resultSet.add(new Constant(s, false));
        }
        return resultSet;
    }

    @Override
    public List<Variable> getVariables(String filePath) throws IOException {
        String filePath_variable = new File(filePath).getAbsolutePath();
        FileReader fileReader_variable = new FileReader(filePath_variable);
        BufferedReader bufferedReader_variable = new BufferedReader(fileReader_variable);
        ArrayList<String> lines_variable = new ArrayList<>();
        String line_read_variable = null;
        while ((line_read_variable = bufferedReader_variable.readLine()) != null) {
            lines_variable.add(line_read_variable);
        }
        bufferedReader_variable.close();

        List<Variable> resultSet = new ArrayList<>();
        for (String s : lines_variable) {
            resultSet.add(new Variable(s));
        }
        return resultSet;
    }

    @Override
    public Predicate getPredicateToProve(String filePath, List<Constant> constants, List<Variable> variables) throws IOException {
        String filePath_to_prove = new File(filePath).getAbsolutePath();
        String toProveString = Files.readAllLines(Paths.get(filePath_to_prove)).get(0);
        String[] splitted_line_to_prove = toProveString.split("[(),]");
        Constant predicate_name = getConstant(constants, splitted_line_to_prove[0]);
        List<Unifable> arguments = new ArrayList<>();
        if (!getPredicateArguments(constants, variables, splitted_line_to_prove, arguments))
        {
            System.out.println("Niepoprawne wejście");
            return null;
        }
        return new Predicate(predicate_name, arguments);
    }

    @Override
    public ClausureSet getClausures(String filePath, List<Constant> constants, List<Variable> variables) throws IOException {
        String filePathClausures = new File(filePath).getAbsolutePath();
        FileReader fileReader = new FileReader(filePathClausures);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line_read = null;
        while ((line_read = bufferedReader.readLine()) != null) {
            lines.add(line_read);
        }
        bufferedReader.close();

        ArrayList<Clausure> clausures= new ArrayList<Clausure>();

        for(int i =0; i<lines.size(); i++) {
            String line = lines.get(i);
            line = line.replace(")=>", "=");
            if (line.contains("=")) {
                List<Predicate> premise_predicates = new ArrayList<>();
                String premise_line = line.substring(0, line.indexOf('='));
                String[] splitted_predicates = premise_line.split("[\\^]");

                for (String s : splitted_predicates) {
                    if(s.charAt(0)=='(')
                        s=s.substring(1);
                    String[] expressions = s.split("[(),]");
                    Constant predicate_name = getConstant(constants, expressions[0]);
                    List<Unifable> arguments = new ArrayList<>();
                    if (!getPredicateArguments(constants, variables, expressions, arguments))
                    {
                        System.out.println("Niepoprawne wejście");
                        return null;
                    }
                    premise_predicates.add(new Predicate(predicate_name, arguments));
                }//for (String s : splitted_predicates) - tworzymy predykat
                //Premise predicates ready

                String conclusion_line = line.substring(line.indexOf('=') + 1);
                String[] expressions = conclusion_line.split("[(),]");
                Constant predicate_name = getConstant(constants, expressions[0]);
                List<Unifable> arguments = new ArrayList<>();
                if (!getPredicateArguments(constants, variables, expressions, arguments))
                {
                    System.out.println("Niepoprawne wejście");
                    return null;
                }
                Predicate conclusion_predicate = new Predicate(predicate_name, arguments);
                if(premise_predicates.size()==1)
                    clausures.add(new Clausure(conclusion_predicate, premise_predicates.get(0)));
                else
                    clausures.add(new Clausure(conclusion_predicate, new AndOperator(premise_predicates)));
            }//if(line.contains("="))
            else //simple clausure
            {
                String[] expressions = line.split("[(),]");
                Constant predicate_name = getConstant(constants, expressions[0]);
                List<Unifable> arguments = new ArrayList<>();
                if (!getPredicateArguments(constants, variables, expressions, arguments))
                {
                    System.out.println("Niepoprawne wejście");
                    return null;
                }
                clausures.add(new Clausure(new Predicate(predicate_name, arguments), null));
            }
        }
        ClausureSet clausureSet = new ClausureSet(clausures);
            return clausureSet;
    }

    private boolean getPredicateArguments(List<Constant> constants, List<Variable> variables, String[] expressions, List<Unifable> arguments) {
        for (int k = 1; k < expressions.length; k++) {
            Constant cons = getConstant(constants, expressions[k]);
            if (cons == null) {
                Variable var = getVariable(variables, expressions[k]);
                if (var == null) {
                    System.out.println("Niepoprawne wejście.");
                    return false;
                }
                arguments.add(var);
            } else
                arguments.add(cons);
        }
        return true;
    }

    private Constant getConstant(List<Constant> set, String name){
        for (Constant constant : set) {
            if (constant.getName().equals(name)) {
                return constant;
            }
        }
        return null;
    }
    private Variable getVariable(List<Variable> set, String name){
        for(Variable variable : set){
            if(variable.getName().equals(name)){
                return  variable;
            }
        }
        return null;
    }
}
