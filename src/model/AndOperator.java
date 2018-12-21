package model;

import java.util.ArrayList;
import java.util.Arrays;

public class AndOperator extends Operator {
    public AndOperator(ArrayList<Goal> operands) {
        super(operands);
    }

    public AndOperator(Goal... operands) {
        super(Arrays.asList(operands));
    }

    @Override
    public String toString() {
        String string = "";

        for(int i = 0; i < getOperandsCount(); ++i) {
            string = string.concat(getOpernad(i).toString());

            if(i != (getOperandsCount() - 1)) string = string.concat(" ^ ");
        }

        return string;
    }
}
