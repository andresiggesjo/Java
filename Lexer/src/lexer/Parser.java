/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author andre
 */
public class Parser implements IParser {

    Tokenizer tokenizer;


    public Parser() {
        tokenizer = new Tokenizer();
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer.open(fileName);

    }

    @Override
    public INode parse() throws IOException, TokenizerException, ParserException {
        //ska kalla på parseRulae1 parseRule2 parseRule3 osv osv osv

        //null
        AssignmentNode asn = new AssignmentNode();

        tokenizer.moveNext();
        tokenizer.moveNext();

        if (tokenizer.current().token() == Token.IDENT) {
            asn.identifier = tokenizer.current();
            tokenizer.moveNext();

            if (tokenizer.current().token() == Token.ASSIGN_OP) {
                asn.assign = tokenizer.current();
                tokenizer.moveNext();
                asn.expn = parseExpressionNode();

            }
        }

        asn.semicolon = tokenizer.current();
        return asn;
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }

    private INode parseExpressionNode() throws IOException {
        ExpressionNode expn = new ExpressionNode();

        
            expn.tn = parseTermNode();
            while (tokenizer.current().token() == Token.ADD_OP
                    || tokenizer.current().token() == Token.SUB_OP) {
                expn.plusorminus = tokenizer.current();
                tokenizer.moveNext();
                expn.en = parseExpressionNode();
            }
      
        return expn;

    }

    private INode parseTermNode() throws IOException {
        TermNode tn = new TermNode();
        tn.fn = parseFactorNode();
        while (tokenizer.current().token() == Token.MULT_OP
                || tokenizer.current().token() == Token.DIV_OP) {
            tn.multordiv = tokenizer.current();
            tokenizer.moveNext();
            tn.tn = parseTermNode();

        }
        return tn;
    }

    private INode parseFactorNode() throws IOException {
        FactorNode fn = new FactorNode();
        if (tokenizer.current().token() == Token.INT_LIT) {
            fn.inten = tokenizer.current();
            

            tokenizer.moveNext();
        } else {
            if (tokenizer.current().token() == Token.LEFT_PAREN) {
                fn.vp = tokenizer.current();
                tokenizer.moveNext();
                fn.exp = parseExpressionNode();

            }
            if (tokenizer.current().token() == Token.RIGHT_PAREN) {
                fn.hp = tokenizer.current();
                tokenizer.moveNext();

            }
        }
        
        return fn;
    }

}
