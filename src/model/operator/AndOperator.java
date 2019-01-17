package model.operator;

import model.*;
import model.graph.AndNode;
import model.graph.Node;
import model.graph.PredicateNode;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndOperator extends Operator {
    public AndOperator(List<Predicate> operands) {
        super(operands);
    }

    public AndOperator(Predicate... operands) {
        super(Arrays.asList(operands));
    }

    @Override
    public Operator getTailOperator() {
        ArrayList<Predicate> tailOpernads = new ArrayList<>(operands);
        tailOpernads.remove(0); //remove first operand

        return new AndOperator(tailOpernads);
    }

    @Override
    public String toString() {
        String string = "";

        for(int i = 0; i < getOperandsCount(); ++i) {
            string = string.concat(getOperand(i).toString());

            if(i != (getOperandsCount() - 1)) string = string.concat(" ^ ");
        }

        return string;
    }


    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        ArrayList<Predicate> newOperands = new ArrayList<>();

        for(int i = 0; i < getOperandsCount(); ++i) {
            newOperands.add((Predicate)getOperand(i).replaceVariables(substitutionSet));
        }

        return new AndOperator(newOperands);
    }

    @Override
    public Node getNode(SubstitutionSet substitutionSet, ClausureSet clausureSet) {
        if(!hasTailOperands() && (getFirstOperand() instanceof Conclusion)) {
            return new PredicateNode(clausureSet, substitutionSet, getFirstOperand());
        }

        return new AndNode(clausureSet, substitutionSet, this);
    }
}
