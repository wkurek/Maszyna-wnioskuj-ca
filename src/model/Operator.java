package model;

import java.util.ArrayList;
import java.util.List;

class Operator implements Goal {
    private ArrayList<Goal> operands;

    Operator(List<Goal> operands) {
        this.operands = new ArrayList<>(operands);
    }

    Goal getFirstOperand() {
        return operands.get(0);
    }

    Operator getTailOperator() {
        ArrayList<Goal> tailOpernads = new ArrayList<>(operands);
        tailOpernads.remove(0); //remove first operand

        return new Operator(tailOpernads);
    }

    boolean hasTailOperands() {
        return operands.size() > 1;
    }

    int getOperandsCount() {
        return operands.size();
    }

    Goal getOpernad(int i) {
        return operands.get(i);
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        ArrayList<Goal> newOperands = new ArrayList<>();

        for(int i = 0; i < getOperandsCount(); ++i) {
            newOperands.add((Goal) getOpernad(i).replaceVariables(substitutionSet));
        }

        return new Operator(newOperands);
    }

    @Override
    public String toString() {
        String string = "";

        for(int i = 0; i < getOperandsCount(); ++i) {
            string = string.concat(getOpernad(i).toString());

            if(i != (getOperandsCount() - 1)) string = string.concat(" OPERATOR ");
        }

        return string;
    }
}
