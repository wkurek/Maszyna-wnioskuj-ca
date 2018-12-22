package model;

import java.util.ArrayList;
import java.util.Arrays;
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

        String string = "[";

        for(Clausure clausure : clausures) {
            string = string.concat(clausure.toString());
            string = string.concat(",\n");
        }

        return string.substring(0, string.length() - 2).concat("]");
    }
}
