package where;

import junit.framework.TestCase;
import org.alex.exp.*;
import parser.Parser;

import java.util.List;

/**
 * Created by tdk on 8/21/16.
 */
public class TestWhere extends TestCase
{

    static ResolveContext rctx = new DefaultResolver();
    static EvalContext ectx = new DefaultEvaluator();

    static Boolean eval( String expStr )
    {
        List<Exp> exps = Parser.ParseString(expStr );
        assert exps.size() == 1;
        Exp e = exps.get( 0 );
        e.resolve( rctx );
        return ( Boolean ) e.eval( ectx );
    }
    public static void test()
    {
        assert(  eval( "WHERE 1 EQUALS 1 OR 2;" ) );
        assert( !eval( "WHERE 1 EQUALS 3 OR 2;" ) );
        assert(  eval( "WHERE testInt EQUALS 123 OR 2;" ) );
        assert( !eval( "WHERE testInt EQUALS 22 OR 2;" ) );
        assert(  eval( "WHERE testInt BETWEEN 1 AND 222;" ) );
        assert( !eval( "WHERE testInt BETWEEN 1 AND 3;" ) );
        assert(  eval( "WHERE testInt >= 123 && testInt <= 123;" ) );
        assert(  eval( "WHERE testInt >= 123 AND testInt <= 123;" ) );
        assert(  eval( "WHERE SUBSTR( testString, 1, 3) == \"bc\";" ) );
        assert(  eval( "WHERE SUBSTR( testString, 1, 3) IN ( \"bc\" );" ) );
        assert( !eval( "WHERE SUBSTR( testString, 1, 3) NOT IN ( \"bc\" );" ) );

    }
}
