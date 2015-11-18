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
public class StatementNode implements INode {

    INode stmtn;
    INode asn;
    int counteru = 0;
    
    

    @Override
    public Object evaluate(Object[] args) throws Exception {        
        while(stmtn != null){
        args[0] = asn.evaluate(args);
        System.out.println(args[0]);
 
        
            
        return stmtn.evaluate(args);
        }
       
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("StatementNode\n");
        

            for (int i = 0; i < tabs; i++) {
                builder.append("\t");
            }
            asn.buildString(builder, tabs + 1);

            for (int i = 0; i < tabs; i++) {
                builder.append("\t");
            }

            
            if (stmtn != null) {
                stmtn.buildString(builder, tabs + 1);
           
        }else{
                builder.append("StatementNode\n");
            }
           
    }

}
