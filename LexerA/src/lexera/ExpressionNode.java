/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexera;



/**
 *
 * @author andre
 */
public class ExpressionNode implements INode {

    INode tn;
    Lexeme plusorminus;
    INode en;
    double value;
    
    

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if(plusorminus == null){
            value = (double)tn.evaluate(args);
            
        }
        else{
            value = (double)tn.evaluate(args);
            if(plusorminus.value().equals('+')){

                value = value + (double)en.evaluate(args);
            }
            else{

                value = value - (double)en.evaluate(args);
            }
            
        }
        
        return value;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("ExpressionNode\n");
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        tn.buildString(builder, tabs + 1);
        
        if (plusorminus != null) {
            for (int i = 0; i < tabs; i++) {
                builder.append("\t");
            }
            builder.append(plusorminus + "\n");
            
            for (int i = 0; i < tabs; i++) {
                builder.append("\t");
            }
            en.buildString(builder, tabs + 1);
            
        }
        
    }

}
