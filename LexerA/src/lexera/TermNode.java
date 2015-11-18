/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexera;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class TermNode implements INode {

    INode fn;
    Lexeme multordiv;
    INode tn;
    double value;
    

    @Override
    public Object evaluate(Object[] args) throws Exception {      
       if(multordiv == null){
            value = (double)fn.evaluate(args);
            
        }
        else{
            value = (double)fn.evaluate(args);
            if(multordiv.value().equals('*')){
                value = value * (double)tn.evaluate(args);
            }
            else{
                value = value / (double)tn.evaluate(args);
            }
            
        }
        
        return value;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("TermNode\n");

        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        fn.buildString(builder, tabs + 1);
        
        if (multordiv != null) {
            for (int i = 0; i < tabs; i++) {
                builder.append("\t");
            }
            builder.append(multordiv + "\n");
            
            for (int i = 0; i < tabs; i++) {
                builder.append("\t");
            }
            tn.buildString(builder, tabs + 1);
            

        }
       
    }

}
