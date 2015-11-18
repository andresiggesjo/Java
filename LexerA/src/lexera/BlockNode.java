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
public class BlockNode implements INode{
    
    Lexeme lftBrack;
    INode stmtn;
    Lexeme rhtBrack;
    int i;
    int counteru = 0;
   
    Object[] objs;
    
    
    

    
    @Override
    public Object evaluate(Object[] args) throws Exception {
        objs = new Object[i];

       return stmtn.evaluate(objs);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("BlockNode\n");
        tabs = 1;
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(lftBrack+"\n"); 
        
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        stmtn.buildString(builder, tabs+1);
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(rhtBrack+"\n");
        
        
    
    }
    
}
