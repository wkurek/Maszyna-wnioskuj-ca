import model.*;

public class Main {
    public static void main(String[] args) {
        Constant friend = new Constant("friend");
        Constant merry = new Constant("merry");
        Constant johnny = new Constant("johnny");

        Variable x = new Variable("X");
        Variable y = new Variable("Y");

        Predicate predicate1 = new Predicate(friend, x, merry);
        Predicate predicate2 = new Predicate(friend, johnny, y);

        NotOperator notPredicate1 = new NotOperator(predicate1);
        NotOperator notPredicate2 = new NotOperator(predicate2);

        SubstitutionSet substitutionSet = notPredicate1.unify(notPredicate2, new SubstitutionSet());

        predicate1 = (Predicate) predicate1.replaceVariables(substitutionSet);
        predicate2 = (Predicate) predicate2.replaceVariables(substitutionSet);

        System.out.println(predicate1);
        System.out.println(predicate2);
    }
}
