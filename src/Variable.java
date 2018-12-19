public class Variable implements Expression {
    private static int idCounter = 0;

    private int id;
    private String name;

    Variable() {
        this.id = idCounter++;
    }

    Variable(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public String toString() {
        String string = "VARIABLE_";

        if(name != null) string = string.concat(name);

        return string.concat(Integer.toString(id));
    }
}
