package util.parser;

import model.*;
import model.operator.AndOperator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClausuresParser implements Parser {

    @Override
    public List<Constant> getConstants(String filePath) throws FileNotFoundException {
        String filePath_constant = new File(filePath).getAbsolutePath();
        FileReader fileReader_constant = new FileReader(filePath_constant);
        BufferedReader bufferedReader_constant = new BufferedReader(fileReader_constant);
        ArrayList<String> lines_constant = new ArrayList<>();
        String line_read_constant;
        while (true) {
            try {
                if((line_read_constant = bufferedReader_constant.readLine()) != null)
                     lines_constant.add(line_read_constant);
                else
                    break;
            } catch (IOException e) {
                System.err.println("Constants input file error:\t"+e.getMessage());
                return null;
            }
        }
        try {
            bufferedReader_constant.close();
        } catch (IOException e) {
            System.err.println("Error occured when tried to close the constants file:\t"+e.getMessage());
            return null;
        }

        List<Constant> resultSet = new ArrayList<>();
        for (String s : lines_constant) {
            if(s.matches("[A-Z~_]+$"))
                resultSet.add(new Constant(s, true));
            else if(s.matches("[A-Za-z0-9_~ąćóśłńźżę]+$"))
                resultSet.add(new Constant(s, false));
            else
            {
                System.err.println("Incorrect constants syntax. Constants symbols allowed:\n A-Z a-z 0-9 _ ~ ą ć ó ś ł ń ź ż ę\n Spaces not allowed.");
                return null;
            }
        }
        return resultSet;
    }

    @Override
    public List<Variable> getVariables(String filePath) throws FileNotFoundException {
        String filePath_variable = new File(filePath).getAbsolutePath();
        FileReader fileReader_variable = new FileReader(filePath_variable);
        BufferedReader bufferedReader_variable = new BufferedReader(fileReader_variable);
        ArrayList<String> lines_variable = new ArrayList<>();
        String line_read_variable;
        while (true) {
            try {
                if((line_read_variable = bufferedReader_variable.readLine()) != null) {
                    lines_variable.add(line_read_variable);
                }
                else
                    break;
            } catch (IOException e) {
                System.err.println("Variables input files error:\t"+e.getMessage());
            }
        }
        try {
            bufferedReader_variable.close();
        } catch (IOException e) {
            System.err.println("Error occured when tried to close the variables file:\t"+e.getMessage());
            return null;
        }

        List<Variable> resultSet = new ArrayList<>();
        for (String s : lines_variable) {
            if(s.matches("[a-z0-9_~ąćóśłńźżę]+$"))
                resultSet.add(new Variable(s));
            else
            {
                System.err.println("Incorrect variables syntax. Variables symbols allowed:\n a-z 0-9 _ ~ ą ć ó ś ł ń ź ż ę\n Spaces and capital letters not allowed.");
                return null;
            }
        }
        return resultSet;
    }

    @Override
    public Predicate getPredicateToProve(String filePath, List<Constant> constants, List<Variable> variables) throws IOException {
        String filePath_to_prove = new File(filePath).getAbsolutePath();
        String toProveString = Files.readAllLines(Paths.get(filePath_to_prove)).get(0);
        String[] splitted_line_to_prove = toProveString.split("[(),]");
        Constant predicate_name = getConstant(constants, splitted_line_to_prove[0]);
        if(predicate_name==null)
        {
            System.err.println("PredicateToProve name not specified in 'constants' file or bad syntax.\n Example of correct syntax: PREDICATE(Constant,variable).\n Remember that spaces are not allowed.");
            return null;
        }
        List<Unifable> arguments = new ArrayList<>();
        if (getPredicateArguments(constants, variables, splitted_line_to_prove, arguments))
        {
            System.err.println("PredicateToProve argument not specified in 'variables' or 'constant' file or bad syntax.");
            return null;
        }
        return new Predicate(predicate_name, arguments);
    }

    @Override
    public ClausureSet getClausures(String filePath, List<Constant> constants, List<Variable> variables) throws FileNotFoundException {
        String filePathClausures = new File(filePath).getAbsolutePath();
        FileReader fileReader = new FileReader(filePathClausures);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<>();
        String line_read;
        while (true) {
            try {
                if((line_read = bufferedReader.readLine()) != null)
                    lines.add(line_read);
                else
                    break;
            } catch (IOException e) {
                System.err.println("KnowledgeBase input file error:\t"+e.getMessage());
                return null;
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Error occured when tried to close the knowledgeBase file:\t"+e.getMessage());
            return null;
        }

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
                    if(predicate_name==null)
                    {
                        System.err.println("KnowledgeBase input file: cannot find a constant name in 'constants' file.\n Remember that spaces are not allowed.");
                        return null;
                    }
                    List<Unifable> arguments = new ArrayList<>();
                    if (getPredicateArguments(constants, variables, expressions, arguments))
                    {
                        System.err.println("KnowledgeBase file: predicate's argument not specified in 'variables' or 'constants' file or bad syntax.\n Remember that spaces are not allowed.");
                        return null;
                    }
                    premise_predicates.add(new Predicate(predicate_name, arguments));
                }//for (String s : splitted_predicates) - tworzymy predykat
                //Premise predicates ready

                String conclusion_line = line.substring(line.indexOf('=') + 1);
                String[] expressions = conclusion_line.split("[(),]");
                Constant predicate_name = getConstant(constants, expressions[0]);
                if(predicate_name==null)
                {
                    System.err.println("KnowledgeBase input file: cannot find a constant name in 'constants' file.\n Remember that spaces are not allowed.");
                    return null;
                }
                List<Unifable> arguments = new ArrayList<>();
                if (getPredicateArguments(constants, variables, expressions, arguments))
                {
                    System.err.println("KnowledgeBase file: predicate's argument not specified in 'variables' or 'constants' file or bad syntax.\n Remember that spaces are not allowed.");
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
                if(predicate_name==null)
                {
                    System.err.println("KnowledgeBase input file: cannot find a constant name in 'constants' file.\n Remember that spaces are not allowed.");
                    return null;
                }
                List<Unifable> arguments = new ArrayList<>();
                if (getPredicateArguments(constants, variables, expressions, arguments))
                {
                    System.err.println("KnowledgeBase file: predicate's argument not specified in 'variables' or 'constants' file or bad syntax.\n Remember that spaces are not allowed.");
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
                    return true;
                }
                arguments.add(var);
            } else
                arguments.add(cons);
        }
        return false;
    }

    public static Constant getConstant(List<Constant> set, String name){
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
