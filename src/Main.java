import model.*;
import model.graph.PredicateNode;

public class Main {
    public static void main(String[] args) {
        Constant KOCHA = new Constant("KOCHA");
        Constant LUBI = new Constant("LUBI");
        Constant Wspin = new Constant("Wspin");
        Constant Narty = new Constant("Narty");
        Constant Descz = new Constant("Descz");
        Constant Snieg = new Constant("Snieg");
        Constant Abacki = new Constant("Abacki");
        Constant Babacki = new Constant("Babacki");

        Variable x0 = new Variable("x0");
        Variable x1 = new Variable("x1");
        Variable x2 = new Variable("x2");
        Variable x3 = new Variable("x3");
        Variable x4 = new Variable("x4");
        Variable x5 = new Variable("x5");
        Variable x6 = new Variable("x6");
        Variable x7 = new Variable("x7");
        Variable x8 = new Variable("x8");
        Variable x9 = new Variable("x9");


        //1a) NKOCHA(x0,Wspin) ⇒ KOCHA(x0,Narty)
        NotOperator predicate1 = new NotOperator(new Predicate(KOCHA, x0, Wspin));
        Predicate predicate2 = new Predicate(KOCHA, x0, Narty);
        Clausure clausure1 = new Clausure(predicate2, predicate1);


        //1b) NKOCHA(x1,Narty) ⇒ KOCHA(x1,Wspin)
        NotOperator predicate3 = new NotOperator(new Predicate(KOCHA, x1, Narty));
        Predicate predicate4 = new Predicate(KOCHA, x1, Wspin);
        Clausure clausure2 = new Clausure(predicate4, predicate3);

        //2a) KOCHA(x2,Wspin) ⇒ NLUBI(x2,Descz)
        Predicate predicate5 = new Predicate(KOCHA, x2, Wspin);
        NotOperator predicate6 = new NotOperator(new Predicate(LUBI, x2, Descz));
        Clausure clausure3 = new Clausure(predicate6, predicate5);

        //2b) LUBI(x3,Deszcz) ⇒ NKOCHA(x3,Wspin)
        Predicate predicate7 = new Predicate(LUBI, x3, Descz);
        NotOperator predicate8 = new NotOperator(new Predicate(KOCHA, x3, Wspin));
        Clausure clausure4 = new Clausure(predicate8, predicate7);

        //3a) KOCHA(x4,Narty) ⇒ LUBI(x4, Snieg)
        Predicate predicate9 = new Predicate(KOCHA, x4, Narty);
        Predicate predicate10 = new Predicate(LUBI, x4, Snieg);
        Clausure clausure5 = new Clausure(predicate10, predicate9);

        //3b) NLUBI(x5, Snieg) ⇒ NKOCHA(x5,Narty)
        NotOperator predicate11 = new NotOperator(new Predicate(LUBI, x5, Snieg));
        NotOperator predicate12 = new NotOperator(new Predicate(KOCHA, x5, Narty));
        Clausure clausure6 = new Clausure(predicate12, predicate11);

        //4a) LUBI(Abacki, x6) ⇒ NLUBI(Babacki, x6)
        Predicate predicate13 = new Predicate(LUBI, Abacki, x6);
        NotOperator predicate14 = new NotOperator(new Predicate(LUBI, Babacki, x6));
        Clausure clausure7 = new Clausure(predicate14, predicate13);

        //4b) NLUBI(Babacki, x7) ⇒ LUBI(Abacki, x7)
        NotOperator predicate15 = new NotOperator(new Predicate(LUBI, Babacki, x7));
        Predicate predicate16 = new Predicate(LUBI, Abacki, x7);
        Clausure clausure8 = new Clausure(predicate16, predicate15);

        //4c) LUBI(Babacki, x8) ⇒ NLUBI(Abacki, x8)
        Predicate predicate17 = new Predicate(LUBI, Babacki, x8);
        NotOperator predicate18 = new NotOperator(new Predicate(LUBI, Abacki, x8));
        Clausure clausure9 = new Clausure(predicate18, predicate17);

        //4d) NLUBI(Abacki, x9) ⇒ LUBI(Babacki, x9)
        NotOperator predicate19 = new NotOperator(new Predicate(LUBI, Abacki, x9));
        Predicate predicate20 = new Predicate(LUBI, Babacki, x9);
        Clausure clausure10 = new Clausure(predicate20, predicate19);

        //5) LUBI(Abacki,Deszcz)
        Predicate predicate21 = new Predicate(LUBI, Abacki, Descz);
        Clausure clausure11 = new Clausure(predicate21, null);

        //6) LUBI(Abacki, Snieg)
        Predicate predicate22 = new Predicate(LUBI, Abacki, Snieg);
        Clausure clausure12 = new Clausure(predicate22, null);

        //7) KOCHA(Babacki,Wspin) <---- DOWÓD
        Predicate predicate23 = new Predicate(KOCHA, Babacki, Wspin);

        ClausureSet knowledgeBase = new ClausureSet(clausure1, clausure2, clausure3, clausure4, clausure5, clausure6, clausure7,
                clausure8, clausure9, clausure10, clausure11, clausure12);

        //ClausureSet knowledgeBase = new ClausureSet(clausure1, clausure2, clausure3, clausure4, clausure5, clausure6, clausure7, clausure7, clausure11);

        SubstitutionSet substitutionSet = new SubstitutionSet();

        substitutionSet = new PredicateNode(knowledgeBase, substitutionSet, predicate23).getSolution();

        System.out.println(substitutionSet);
    }
}
