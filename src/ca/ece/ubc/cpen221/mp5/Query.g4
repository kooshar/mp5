grammar Query;

// This puts "package formula;" at the top of the output Java files.
@header {
package ca.ece.ubc.cpen221.mp5;
}

// This adds code to the generated lexer and parser. Do not change these lines.
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a RuntimeException.
    public void reportErrorsAsExceptions() {
        //removeErrorListeners();
        
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *   *** Antlr requires tokens to be CAPITALIZED, like START_ITALIC, END_ITALIC, and TEXT.
 */
OR : ' '? '||' ' '?;
AND : ' '? '&&' ' '? ;
IN : 'in';
NAME : 'name';
RATING : 'rating';
CATEGORY : 'category';
PRICE : 'price';
RPAREN : ')';
LPAREN : '(';
NUMBER : [1-5];
TO : '..';
STRING : '"' ([a-z]|[A-Z])* '"';

/*
 * These are the parser rules. They define the structures used by the parser.
 *    *** Antlr requires grammar nonterminals to be lowercase, like html, normal, and italic.
 */
orExpr : andExpr(OR andExpr)*;
andExpr : atom (AND atom)*;
atom : in | category | rating | price | name | LPAREN orExpr RPAREN;
in : IN LPAREN STRING RPAREN;
category : CATEGORY LPAREN STRING RPAREN;
name : NAME LPAREN STRING RPAREN;
rating : RATING LPAREN range RPAREN;
price : PRICE LPAREN range RPAREN;
range: leftNum TO rightNum;
leftNum : NUMBER;
rightNum : NUMBER;
