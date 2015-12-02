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
public class AssignmentNode implements INode{
    Lexeme identifier;
    Lexeme assign;
    ExpressionNode expn;
    Lexeme semicolon;
    
    
   
    
    
    @Override
    public Object evaluate(Object[] args) throws Exception {
        return  expn.evaluate(args);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("AssignmentNode\n");
        
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(identifier+"\n"); 
        
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(assign+"\n");
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        expn.buildString(builder, tabs+1);
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(semicolon+"\n");
    }
    
}
