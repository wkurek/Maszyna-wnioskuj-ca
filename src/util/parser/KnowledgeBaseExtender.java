package util.parser;

import model.Clausure;
import model.ClausureSet;
import model.Constant;

import java.util.List;

public class KnowledgeBaseExtender {
    public static void extendKnowledgeBase(ClausureSet knowledgeBase, List<Constant> constants) {//A=>B, więc trzeba dodać -B=>-A
        int end = knowledgeBase.getClousuresCount();
        for(int i = 0; i<end; i++)
        {
            if(knowledgeBase.getClausures(i).hasPremise())
            {
                ClausureSet allExtensions = knowledgeBase.getClausures(i).generateExtensions(constants);
                for(int k = 0; k<allExtensions.getClousuresCount(); k++)
                {
                    if(!existsExtendedClausure(knowledgeBase, allExtensions.getClausures(k)))
                    {
                        knowledgeBase.add(allExtensions.getClausures(k));

                    }//there is no Clausure like the generated one yet
                }//for through all combinations

            }//if hasPremise
        }//for knowledgeSet
    }

    private static boolean existsExtendedClausure(ClausureSet knowledgeBase, Clausure clausure) {
        for(int i = 0; i<knowledgeBase.getClousuresCount(); i++)
        {
            if(clausure.sameAs(knowledgeBase.getClausures(i)))
                return true;
        }
        return false;
    }
}
