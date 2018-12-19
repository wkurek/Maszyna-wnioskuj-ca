import java.util.ArrayList;

public class Predicate implements Expression {
    private ArrayList<Expression> arguments;

    Predicate(Constant name, ArrayList<Expression> args) {
        arguments = new ArrayList<>(args);
        arguments.add(0, name);
    }

    int getSize() {
        return arguments.size();
    }

    Expression getArgument(int index) {
        return arguments.get(index);
    }

    public String toString() {
        String string = "";

        for(Expression expression : arguments) {
            string = string.concat(expression.toString() + ", ");
        }

        return "( " + string + ")";
    }
}
