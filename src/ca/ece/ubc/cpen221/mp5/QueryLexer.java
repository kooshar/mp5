// Generated from d:/Users/koosha/workspace/mp5/src/ca/ece/ubc/cpen221/mp5/Query.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OR=1, AND=2, IN=3, NAME=4, RATING=5, CATEGORY=6, PRICE=7, RPAREN=8, LPAREN=9, 
		RANGE=10, STRING=11;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'"
	};
	public static final String[] ruleNames = {
		"OR", "AND", "IN", "NAME", "RATING", "CATEGORY", "PRICE", "RPAREN", "LPAREN", 
		"RANGE", "STRING"
	};


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


	public QueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Query.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\r\\\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\5\2\33\n\2\3\2\3\2\3\2\3\2\5\2!\n\2\3\3\5\3$\n\3\3\3\3"+
		"\3\3\3\3\3\5\3*\n\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\7\fV\n\f\f\f\16"+
		"\fY\13\f\3\f\3\f\2\2\r\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\3\2\4\3\2\63\67\4\2C\\c|`\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\3\32\3\2\2\2\5#\3\2\2\2\7+\3\2\2\2\t.\3\2\2"+
		"\2\13\63\3\2\2\2\r:\3\2\2\2\17C\3\2\2\2\21I\3\2\2\2\23K\3\2\2\2\25M\3"+
		"\2\2\2\27S\3\2\2\2\31\33\7\"\2\2\32\31\3\2\2\2\32\33\3\2\2\2\33\34\3\2"+
		"\2\2\34\35\7~\2\2\35\36\7~\2\2\36 \3\2\2\2\37!\7\"\2\2 \37\3\2\2\2 !\3"+
		"\2\2\2!\4\3\2\2\2\"$\7\"\2\2#\"\3\2\2\2#$\3\2\2\2$%\3\2\2\2%&\7(\2\2&"+
		"\'\7(\2\2\')\3\2\2\2(*\7\"\2\2)(\3\2\2\2)*\3\2\2\2*\6\3\2\2\2+,\7k\2\2"+
		",-\7p\2\2-\b\3\2\2\2./\7p\2\2/\60\7c\2\2\60\61\7o\2\2\61\62\7g\2\2\62"+
		"\n\3\2\2\2\63\64\7t\2\2\64\65\7c\2\2\65\66\7v\2\2\66\67\7k\2\2\678\7p"+
		"\2\289\7i\2\29\f\3\2\2\2:;\7e\2\2;<\7c\2\2<=\7v\2\2=>\7g\2\2>?\7i\2\2"+
		"?@\7q\2\2@A\7t\2\2AB\7{\2\2B\16\3\2\2\2CD\7r\2\2DE\7t\2\2EF\7k\2\2FG\7"+
		"e\2\2GH\7g\2\2H\20\3\2\2\2IJ\7+\2\2J\22\3\2\2\2KL\7*\2\2L\24\3\2\2\2M"+
		"N\t\2\2\2NO\7\60\2\2OP\7\60\2\2PQ\3\2\2\2QR\t\2\2\2R\26\3\2\2\2SW\7$\2"+
		"\2TV\t\3\2\2UT\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2XZ\3\2\2\2YW\3\2\2"+
		"\2Z[\7$\2\2[\30\3\2\2\2\t\2\32 #)UW\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}