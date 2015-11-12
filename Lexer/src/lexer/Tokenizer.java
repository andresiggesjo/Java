/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author andre
 */
public class Tokenizer implements ITokenizer {

    Scanner s = new Scanner();
    Lexeme lex;

    /**
     * Opens a file for tokenizing.
     *
     * @param fileName
     */
    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        //en stream med chars
        s.open(fileName);

    }

    /**
     * Returns the current token in the stream.
     */
    public Lexeme current() {

        return lex;
    }

    /**
     * Moves current to the next token in the stream.
     */
    @Override
    public void moveNext() throws IOException {

        char ch = s.current();
        Token t = null;

        String str = String.valueOf(ch);
        if (s.current() == ' ') {
            s.moveNext();
            moveNext();
        } else if (s.current() == Scanner.NULL) {

            t = t.NULL;
            lex = new Lexeme(ch, t);
            s.moveNext();

        } else if (str.matches("^[a-zA-Z]")) {

            t = t.IDENT;
            String sx = "";

            while (str.matches("^[a-zA-Z]")) {

                sx += ch;

                s.moveNext();
                ch = s.current();
                str = String.valueOf(ch);

            }
            lex = new Lexeme(sx, t);

        } else if (str.matches("^[0-9]")) {

            t = t.INT_LIT;
            String sx = "";

            while (str.matches("^[0-9]")) {

                sx += ch;

                s.moveNext();

                ch = s.current();
                str = String.valueOf(ch);

            }
            double value = Double.parseDouble(sx);
            lex = new Lexeme(value, t);

        } else {

            switch (ch) {

                case '(':

                    t = t.LEFT_PAREN;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case ')':

                    t = t.RIGHT_PAREN;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;

                case '+':

                    t = t.ADD_OP;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case '-':

                    t = t.SUB_OP;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case '*':

                    t = t.MULT_OP;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case '/':

                    t = t.DIV_OP;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case ';':

                    t = t.SEMICOLON;
                    lex = new Lexeme(ch, t);
                    s.moveNext();

                    break;
                case '{':
                    t = t.LEFT_CURLY;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case '}':
                    t = t.RIGHT_CURLY;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                case '=':

                    t = t.ASSIGN_OP;
                    lex = new Lexeme(ch, t);
                    s.moveNext();
                    break;
                default:
                    t = t.EOF;
                    lex = new Lexeme(s.current(), t);
                    s.moveNext();
                    break;
            }
        }

    }

    @Override
    public void close() throws IOException {
        s.close();
    }

}
