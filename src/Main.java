import model.*;
import model.graph.PredicateNode;
import model.operator.AndOperator;
import model.operator.NotOperator;
import util.printer.ResultPrinter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath_to_prove = new File("src/example_to_prove").getAbsolutePath();
        String toProveString = Files.readAllLines(Paths.get(filePath_to_prove)).get(0);


        ArrayList<Constant> constrants = new ArrayList<Constant>();
        String filePath_constrant = new File("src/example_constrant").getAbsolutePath();
        FileReader fileReader_constrant = new FileReader(filePath_constrant);
        BufferedReader bufferedReader_constrant = new BufferedReader(fileReader_constrant);
        ArrayList<String> lines_constrant = new ArrayList<String>();
        String line_read_constrant = null;
        while ((line_read_constrant = bufferedReader_constrant.readLine()) != null) {
            lines_constrant.add(line_read_constrant);
        }
        bufferedReader_constrant.close();

        for (String s : lines_constrant) {
            constrants.add(new Constant(s));
        }


        ArrayList<Variable> variables = new ArrayList<Variable>();

        String filePath_variable = new File("src/example_variables").getAbsolutePath();
        FileReader fileReader_variable = new FileReader(filePath_variable);
        BufferedReader bufferedReader_variable = new BufferedReader(fileReader_variable);
        ArrayList<String> lines_variable = new ArrayList<String>();
        String line_read_variable = null;
        while ((line_read_variable = bufferedReader_variable.readLine()) != null) {
            lines_variable.add(line_read_variable);
        }
        bufferedReader_variable.close();

        for (String s : lines_variable) {
            variables.add(new Variable(s));
        }


        String filePath = new File("src/examples").getAbsolutePath();
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line_read = null;
        while ((line_read = bufferedReader.readLine()) != null) {
            lines.add(line_read);
        }
        bufferedReader.close();

        ArrayList<Clausure> clausures= new ArrayList<Clausure>();

        for(int i =0; i<lines.size(); i++){
            String line = lines.get(i);
            line = line.replace(")=>", "=");
            String[] splitted_line = line.split("[(),=]");

            String a1 = splitted_line[0];
            String a2 = splitted_line[1];
            String a3 = splitted_line[2];
            String a4 = null;
            String a5 = null;
            String a6 = null;

            if(splitted_line.length == 6) {
                a4 = splitted_line[3];
                a5 = splitted_line[4];
                a6 = splitted_line[5];
            }

            ArrayList<String> splitted = new ArrayList<String>();
            splitted.add(a1);
            splitted.add(a2);
            splitted.add(a3);
            if(splitted_line.length==6){
                splitted.add(a4);
                splitted.add(a5);
                splitted.add(a6);
            }


            if (splitted.size() == 6){
                Constant cons1 = getConstrant(constrants, a1);
                Constant cons2 = getConstrant(constrants,a2);
                Constant cons3 = getConstrant(constrants,a3);
                Variable var2 = getVariable(variables, a2);
                Variable var3 = getVariable(variables, a3);

                Constant cons4 = getConstrant(constrants, a4);
                Constant cons5 = getConstrant(constrants,a5);
                Constant cons6 = getConstrant(constrants,a6);
                Variable var5 = getVariable(variables, a5);
                Variable var6 = getVariable(variables, a6);

                NotOperator npredicate1=null, npredicate2=null;
                Predicate predicate1=null, predicate2=null;
                if(a1.startsWith("N")) {
                    if(cons2 != null) {
                        npredicate1 = new NotOperator(new Predicate(cons1, cons2, var3 ));
                    }
                    else {
                        npredicate1 = new NotOperator(new Predicate(cons1, var2, cons3 ));
                    }
                } else {
                    if(cons2 != null) {
                        predicate1 = new Predicate(cons1, cons2, var3 );
                    }
                    else {
                        predicate1 = new Predicate(cons1, var2, cons3 );
                    }
                }

                if(a4.startsWith("N")) {
                    if(cons5 != null) {
                        npredicate2 = new NotOperator(new Predicate(cons4, cons5, var6 ));
                    }
                    else {
                        npredicate2 = new NotOperator(new Predicate(cons4, var5, cons6 ));
                    }
                } else {
                    if(cons5 != null) {
                        predicate2 = new Predicate(cons4, cons5, var6 );
                    }
                    else {
                        predicate2 = new Predicate(cons4, var5, cons6 );
                    }
                }

                if(npredicate1 != null && predicate2 != null ){
                    clausures.add(new Clausure(npredicate1, predicate2));
                }

                if(npredicate1 != null && npredicate2 != null ){
                    clausures.add(new Clausure(npredicate1, npredicate2));
                }

                if(predicate1 != null && npredicate2 != null ){
                    clausures.add(new Clausure(predicate1, npredicate2));
                }

                if(predicate1 != null && predicate2 != null ){
                    clausures.add(new Clausure(predicate1, predicate2));
                }
            }

            if(splitted.size() == 3) {
                Constant cons1 = getConstrant(constrants, a1);
                Constant cons2 = getConstrant(constrants,a2);
                Constant cons3 = getConstrant(constrants,a3);
                Variable var2 = getVariable(variables, a2);
                Variable var3 = getVariable(variables, a3);

                NotOperator npredicate1=null;
                Predicate predicate1=null;
                if(a1.startsWith("N")) {
                    npredicate1 = new NotOperator(new Predicate(cons1, cons2, cons3 ));

                } else {
                    predicate1 = new Predicate(cons1, cons2, cons3);
                }

                if(predicate1 != null) {
                    clausures.add(new Clausure(predicate1, null));
                } else {
                    clausures.add(new Clausure(npredicate1, null));
                }

            }
        }

        toProveString = toProveString.replace(")=>", "=");
        String[] splitted_line_to_prove = toProveString.split("[(),=]");

        String a1 = splitted_line_to_prove[0];
        String a2 = splitted_line_to_prove[1];
        String a3 = splitted_line_to_prove[2];
        Constant cons1 = getConstrant(constrants, a1);
        Constant cons2 = getConstrant(constrants,a2);
        Constant cons3 = getConstrant(constrants,a3);

        NotOperator npredicateToProve=null;
        Predicate predicateToProve=null;
        if(a1.startsWith("N")) {
            npredicateToProve = new NotOperator(new Predicate(cons1, cons2, cons3 ));
        } else {
            predicateToProve = new Predicate(cons1, cons2, cons3);
        }

        SubstitutionSet substitutionSet = new SubstitutionSet();
        ClausureSet knowledgeBase = new ClausureSet();
        for (Clausure clausure : clausures) {
            knowledgeBase.add(clausure);
        }

        ClausureSet usedClausures = new ClausureSet();

        if(npredicateToProve != null){
            substitutionSet = new PredicateNode(knowledgeBase, substitutionSet, npredicateToProve).getSolution(usedClausures);
        } else {
            substitutionSet = new PredicateNode(knowledgeBase, substitutionSet, predicateToProve).getSolution(usedClausures);
        }

        ResultPrinter.print(usedClausures, substitutionSet);
        
    System.out.print("END");
    }

    private static Constant getConstrant(ArrayList<Constant> set, String name){
        if(name.startsWith("N") && Character.isUpperCase(name.charAt(1))) {
            name = name.substring(1);
        }
        for (Constant constant : set) {
            if (constant.getName().equals(name)) {
                return constant;
            }
        }
        return null;
    }

    private static Variable getVariable(ArrayList<Variable> set, String name){
        for(Variable variable : set){
            if(variable.getName().equals(name)){
                return  variable;
            }
        }
        return null;
    }
}
