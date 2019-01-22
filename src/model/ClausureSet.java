package model;

import model.operator.AndOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClausureSet {
    private ArrayList<Clausure> clausures;

    public ClausureSet(List<Clausure> clausureArrayList) {
        clausures = new ArrayList<>(clausureArrayList);
    }

    public ClausureSet(ClausureSet clausureSet) {
        clausures = new ArrayList<>(clausureSet.clausures);
    }

    public ClausureSet(Clausure... clausures) {
        this(Arrays.asList(clausures));
    }

    public Clausure getClausures(int i) {
        return clausures.get(i);
    }

    public int getClousuresCount() {
        return clausures.size();
    }

    public void add(Clausure clausure) {
        clausures.add(clausure);
    }

    public void remove(int i) {
        clausures.remove(i);
    }

    @Override
    public String toString() {
        if(clausures.isEmpty()) return "[]";

        String string = "";
        for(Clausure clausure : clausures) {
            string = string.concat("K"+clausure.getID()+": ");
            string = string.concat(clausure.toString());
            string = string.concat("\n");
        }

        return string.substring(0, string.length() - 2).concat(")");
    }

    public String toString2() {
        if(clausures.isEmpty()) return "[]";

        String string = "";
        for(Clausure clausure : clausures) {
            string = string.concat("K"+clausure.getID()+": ");
            string = string.concat(clausure.toString2());
            string = string.concat("\n");
        }

        return string.substring(0, string.length() - 2).concat(")");
    }

    public String toString3(SubstitutionSet substitutionSet) {
        if(clausures.isEmpty()) return "";
        ArrayList<Clausure> reversed = new ArrayList<>(clausures);
        Collections.reverse(reversed);
        String string = "";
        string=string.concat("("+((Clausure)reversed.get(0).replaceVariables(substitutionSet)).getConclusion().toString() + ") \n\t^\n\t|\n(" + ((Clausure)reversed.get(0).replaceVariables(substitutionSet)).getPremise().toString()+")");
        for(int i = 1; i<reversed.size(); i++) {
            if(((Clausure)reversed.get(i).replaceVariables(substitutionSet)).hasPremise()) {
                string = string.concat("\n\t^\n\t|\n");
                string = string.concat("("+((Clausure) reversed.get(i).replaceVariables(substitutionSet)).getPremise().toString()+")");
            }
            else
            {
                if(!(((Clausure) reversed.get(i-1).replaceVariables(substitutionSet)).hasPremise()) || (((Clausure) reversed.get(i-1).replaceVariables(substitutionSet)).getPremise() instanceof AndOperator)) {
                    string = string.concat("\n\t^\n\t|\n");
                    string = string.concat("(" + ((Clausure) reversed.get(i).replaceVariables(substitutionSet)).getConclusion().toString() + ")");
                }
            }
        }
       // string = string.replaceAll("(.{190})", "$1\n\n");
        return string;
    }
}
