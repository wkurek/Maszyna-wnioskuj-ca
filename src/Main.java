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

        //predicate1 = (Predicate) predicate1.replaceVariables(substitutionSet);
        //predicate2 = (Predicate) predicate2.replaceVariables(substitutionSet);

        //notPredicate1 = (NotOperator) notPredicate1.replaceVariables(substitutionSet);
       // notPredicate2 = (NotOperator) notPredicate2.replaceVariables(substitutionSet);

        AndOperator andOperator = new AndOperator(predicate1, notPredicate2, notPredicate1, predicate2);

        Clausure clausure = new Clausure(predicate1, andOperator);
        clausure = (Clausure) clausure.replaceVariables(substitutionSet);

        System.out.println(notPredicate1);
        System.out.println(predicate2);
        System.out.println(andOperator);
        System.out.println(clausure);
    }
}
