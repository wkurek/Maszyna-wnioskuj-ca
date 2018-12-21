package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClausureSet {
    private ArrayList<Clausure> clausures;

    ClausureSet(List<Clausure> clausureArrayList) {
        clausures = new ArrayList<>(clausureArrayList);
    }

    ClausureSet(Clausure... clausures) {
        this(Arrays.asList(clausures));
    }

    Clausure getClausures(int i) {
        return clausures.get(i);
    }

    int getClousuresCount() {
        return clausures.size();
    }

}
