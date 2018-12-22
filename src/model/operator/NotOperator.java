package model.operator;

import model.*;

import java.util.Collections;

public class NotOperator extends Operator implements Unifable, Conclusion {
    public NotOperator(Predicate predicate) {
        super(Collections.singletonList(predicate));
    }

    @Override
    public SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet) {
        if(expression instanceof NotOperator) {
            Predicate predicate1 = (Predicate) getFirstOperand();
            Predicate predicate2 = (Predicate) ((NotOperator) expression).getFirstOperand();

            return predicate1.unify(predicate2, substitutionSet);
        } else if (expression instanceof Variable) {
            return expression.unify(this, substitutionSet);
        }

        return null;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        Predicate predicate = (Predicate) ((Predicate) getFirstOperand()).replaceVariables(substitutionSet);

        return new NotOperator(predicate);
    }

    @Override
    public Operator getTailOperator() {
        return null;
    }

    @Override
    public String toString() {
        return "~" + getFirstOperand().toString();
    }

    @Override
    public Unifable getArgument(int index) {
        Conclusion predicate = (Conclusion) getFirstOperand();

        return predicate.getArgument(index);
    }
}
