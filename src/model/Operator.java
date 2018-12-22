package model;

import model.graph.AndNode;
import model.graph.Node;
import model.graph.PredicateNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator implements Goal {
    ArrayList<Goal> operands;

    Operator(List<Goal> operands) {
        this.operands = new ArrayList<>(operands);
    }

    public Goal getFirstOperand() {
        return operands.get(0);
    }

    public boolean hasTailOperands() {
        return operands.size() > 1;
    }

    public int getOperandsCount() {
        return operands.size();
    }

    public Goal getOpernad(int i) {
        return operands.get(i);
    }

    public abstract Operator getTailOperator();

    @Override
    public String toString() {
        String string = "";

        for(int i = 0; i < getOperandsCount(); ++i) {
            string = string.concat(getOpernad(i).toString());

            if(i != (getOperandsCount() - 1)) string = string.concat(" OPERATOR ");
        }

        return string;
    }

    @Override
    public Node getNode(SubstitutionSet substitutionSet, ClausureSet clausureSet) {
        if(!hasTailOperands() && (getFirstOperand() instanceof Conclusion)) {
            return new PredicateNode(clausureSet, substitutionSet, this);
        }

        return new AndNode(clausureSet, substitutionSet, this);
    }

    @Override
    public abstract Expression replaceVariables(SubstitutionSet substitutionSet);

}
