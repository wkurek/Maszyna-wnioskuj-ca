package model;

import model.operator.AndOperator;
import model.operator.Operator;
import util.ClausuresParser;

import java.util.ArrayList;
import java.util.List;

public class Clausure implements Expression {
    private Conclusion conclusion;
    private Goal premise;
    private int ID;
    private static int count = 0;

    public Clausure(Conclusion conc, Goal goal) {
        conclusion = conc;
        premise = goal;
        ID=++count;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public Goal getPremise() {
        return premise;
    }

    public boolean hasPremise() {
        return premise != null;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        Predicate predicate = (Predicate) conclusion.replaceVariables(substitutionSet);
        Goal goal=null;
        if(premise!=null)
          goal = (Goal) premise.replaceVariables(substitutionSet);

        return new Clausure(predicate, goal);
    }

    @Override
    public String toString() {
        String string = conclusion.toString();

        if(hasPremise()) {
            string = string.concat(" <= ");
            string = string.concat(premise.toString());
        }

        return string;
    }

    public int getID() {
        return ID;
    }

    public String toString2() {

        String string="";
        if(hasPremise()) {
            string = string.concat(premise.toString());
            string = string.concat(" => ");
        }
        string = string.concat(conclusion.toString());
        return string;
    }

    public ClausureSet generateExtensions(List<Constant> constants) {
        ClausureSet clausureSet = new ClausureSet();
        if(this.getPremise() instanceof Operator)
        {
            for(int i = 0; i<((Operator) this.getPremise()).getOperandsCount(); i++)
            {
                List<Predicate> premise_predicates = new ArrayList<>();
                for(int k = 0; k<((Operator) this.getPremise()).getOperandsCount(); k++) {
                    if (k != i) {
                        Predicate pre = new Predicate(((Operator) this.getPremise()).getOperand(k));
                        for (int j = 0; j < pre.getArguments().size(); j++)
                            if (pre.getArgument(j) instanceof Variable)
                                ((Variable) pre.getArgument(j)).setId(Variable.getIdCounter() + 1);

                        premise_predicates.add(pre);
                    }
                }
                Predicate pre = new Predicate((Predicate)this.conclusion);
                for (int j = 0; j < pre.getArguments().size(); j++)
                    if (pre.getArgument(j) instanceof Variable)
                        ((Variable) pre.getArgument(j)).setId(Variable.getIdCounter() + 1);

                premise_predicates.add(negate(pre, constants));
                Predicate temporary = new Predicate(((Operator) this.getPremise()).getOperand(i));
                for (int j = 0; j < temporary.getArguments().size(); j++)
                    if (temporary.getArgument(j) instanceof Variable)
                        ((Variable) temporary.getArgument(j)).setId(Variable.getIdCounter() + 1);

                Clausure clausure = new Clausure(negate(temporary, constants), new AndOperator(premise_predicates));
                fixVariables(clausure);
                clausureSet.add(clausure);
                Variable.setIdCounter(Variable.getIdCounter()+1);
            }
        }
        else
        {
            Predicate pre = new Predicate((Predicate) this.getPremise());
            for (int j = 0; j < pre.getArguments().size(); j++)
                if (pre.getArgument(j) instanceof Variable)
                    ((Variable) pre.getArgument(j)).setId(Variable.getIdCounter() + 1);


            Predicate temporary = new Predicate((Predicate)this.getConclusion());
            for (int j = 0; j < temporary.getArguments().size(); j++)
                if (temporary.getArgument(j) instanceof Variable)
                    ((Variable) temporary.getArgument(j)).setId(Variable.getIdCounter() + 1);

            Clausure clausure = new Clausure(negate(pre, constants), negate(temporary, constants));
            fixVariables(clausure);
            Variable.setIdCounter(Variable.getIdCounter()+1);
            clausureSet.add(clausure);

        }
        return clausureSet;
    }

    private void fixVariables(Clausure clausure) {
        List<Predicate> predicates = new ArrayList<>();
        if(clausure.hasPremise()) {
            if (clausure.getPremise() instanceof AndOperator)
                for (int k = 0; k < ((AndOperator) clausure.getPremise()).getOperandsCount(); k++)
                    predicates.add(((AndOperator) clausure.getPremise()).getOperand(k));
            else
                predicates.add(((Predicate) clausure.getPremise()));
        }
        predicates.add((Predicate)clausure.getConclusion());
        for(int i = 0; i<predicates.size();i++)
        {
            for(int k = i; k<predicates.size(); k++)
            {
                for(int j = 0; j<predicates.get(i).getArguments().size(); j++)
                {
                    for(int g = 0; g<predicates.get(k).getArguments().size(); g++)
                    {
                        if(predicates.get(i).getArgument(j) instanceof Variable && predicates.get(k).getArgument(g) instanceof Variable)
                            if((predicates.get(i).getArgument(j)).toString().equals((predicates.get(k).getArgument(g)).toString()))
                                predicates.get(k).getArguments().set(g, predicates.get(i).getArgument(j));

                    }
                }
            }
        }
    }

    private Predicate negate(Predicate predicate, List<Constant> constants) {
        if(predicate.getArgument(0).toString().charAt(0)=='~') {
            Constant c = ClausuresParser.getConstant(constants, predicate.getArgument(0).toString().substring(1));
            if (c != null)
                return new Predicate(c, predicate.getArguments().subList(1, predicate.getArguments().size()));
            c = new Constant( predicate.getArgument(0).toString().substring(1), true);
            constants.add(c);
            return new Predicate(c, predicate.getArguments().subList(1, predicate.getArguments().size()));
        }
        Constant c =  ClausuresParser.getConstant(constants, "~"+predicate.getArgument(0).toString());
        if(c!=null)
            return new Predicate(c, predicate.getArguments().subList(1, predicate.getArguments().size()));
        c = new Constant("~"+predicate.getArgument(0).toString(),true);
        constants.add(c);
        return new Predicate(c, predicate.getArguments().subList(1, predicate.getArguments().size()));
    }


    public boolean sameAs(Clausure clausure) {
        if(this.hasPremise())
        {
            if(!clausure.hasPremise())
                return false;
            if(this.getPremise() instanceof Operator)
            {
                if(!(clausure.getPremise() instanceof Operator))
                    return false;
                for(int i = 0; i<((Operator) this.getPremise()).getOperandsCount(); i++)
                {
                    if(!(((Operator) this.getPremise()).getOperand(i).getArgument(0).toString()
                            .equals(((Operator) clausure.getPremise()).getOperand(i).getArgument(0).toString())))
                        return false;
                    for(int k = 1; k<((Operator) this.getPremise()).getOperand(i).getArguments().size(); k++)
                    {
                        if(((Operator) this.getPremise()).getOperand(i).getArgument(k) instanceof Variable && !(((Operator) clausure.getPremise()).getOperand(i).getArgument(k) instanceof Variable))
                            return false;
                        if(((Operator) this.getPremise()).getOperand(i).getArgument(k) instanceof Constant)
                        {
                            if(!(((Operator) clausure.getPremise()).getOperand(i).getArgument(k) instanceof Constant))
                                return false;
                            if(!(((Constant) ((Operator) this.getPremise()).getOperand(i).getArgument(k)).getName()
                                    .equals(((Constant) ((Operator) clausure.getPremise()).getOperand(i).getArgument(k)).getName())))
                                return false;

                        }

                    }
                }
            }
            else
            {
                if(clausure.getPremise() instanceof Operator)
                    return false;
                if(!(((Predicate)this.getPremise()).getArgument(0).toString().equals(((Predicate)clausure.getPremise()).getArgument(0).toString())))
                        return false;
                for(int k = 1; k<((Predicate)this.getPremise()).getArguments().size(); k++)
                {
                    if(((Predicate)this.getPremise()).getArgument(k) instanceof Variable && !(((Predicate)clausure.getPremise()).getArgument(k) instanceof Variable))
                        return false;
                    if(((Predicate)this.getPremise()).getArgument(k) instanceof Constant)
                    {
                        if(!(((Predicate) clausure.getPremise()).getArgument(k) instanceof Constant))
                            return false;
                        if(!(((Constant) ((Predicate) this.getPremise()).getArgument(k)).getName()
                                .equals(((Constant) ((Predicate) clausure.getPremise()).getArgument(k)).getName())))
                            return false;

                    }

                }
            }
        }
        else {

            if (clausure.hasPremise())
                return false;
            if (!((this.getConclusion()).getArgument(0).toString().equals((clausure.getConclusion()).getArgument(0).toString())))
                return false;
            for (int k = 1; k < ((Predicate) this.getConclusion()).getArguments().size(); k++) {
                if ((this.getConclusion()).getArgument(k) instanceof Variable && !(clausure.getConclusion().getArgument(k) instanceof Variable))
                    return false;
                if (this.getConclusion().getArgument(k) instanceof Constant) {
                    if (!(clausure.getConclusion().getArgument(k) instanceof Constant))
                        return false;
                    if (!(((Constant) this.getConclusion().getArgument(k)).getName()
                            .equals(((Constant) clausure.getConclusion().getArgument(k)).getName())))
                        return false;
                }
            }
        }
        return true;
    }
}
