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

    public void remove(int i) {
        clausures.remove(i);
    }

}
