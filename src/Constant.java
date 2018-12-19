public class Constant implements Expression {
    private static int idCounter = 0;

    private int id;
    private String name;

    Constant() {
        this.id = idCounter++;
    }

    Constant(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public String toString() {
        String string = "CONSTANT_";

        if(name != null) string = string.concat(name);

        return string.concat(Integer.toString(id));
    }
}
