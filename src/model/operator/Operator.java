package model.operator;

import model.*;
import model.graph.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator implements Goal {
    ArrayList<Predicate> operands;

    Operator(List<Predicate> operands) {
        this.operands = new ArrayList<>(operands);
    }

    public Predicate getFirstOperand() {
        return operands.get(0);
    }

    boolean hasTailOperands() {
        return operands.size() > 1;
    }

    public int getOperandsCount() {
        return operands.size();
    }

    public Predicate getOperand(int i) {
        return operands.get(i);
    }

    public abstract Operator getTailOperator();

    @Override
    public String toString() {
        String string = "";

        for(int i = 0; i < getOperandsCount(); ++i) {
            string = string.concat(getOperand(i).toString());

            if(i != (getOperandsCount() - 1)) string = string.concat(" OPERATOR ");
        }

        return string;
    }


    @Override
    public abstract Node getNode(SubstitutionSet substitutionSet, ClausureSet clausureSet);

    @Override
    public abstract Expression replaceVariables(SubstitutionSet substitutionSet);

}
