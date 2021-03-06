/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class FactorNode implements INode{
    Lexeme inten;
    INode exp;
    Lexeme vp;
    Lexeme hp;
    double value;

    
    
   

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if(vp == null){
            value = (double)inten.value();
        }
        else{
            value =  (double)exp.evaluate(null);
                    
        }
        return value;
    }
    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("FactorNode\n");
        
        if(inten != null){

            for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
           builder.append(inten+"\n"); 
           
        }
        
        
        else if(vp != null){
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }    
        builder.append(vp+"\n");
        
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        exp.buildString(builder, tabs+1);
        
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(hp+"\n");
        
        
        }      
        
    }
    
}
