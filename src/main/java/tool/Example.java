package tool;

import org.alex.exp.*;
import parser.Parser;

import java.util.List;

/**
 * Created by tdk on 8/20/16.
 */
public class Example
{
    public static void main( String ... args )
    {
        List<Exp> exps = Parser.Parse( "src/main/resources/where/Example.w" );
        System.out.println( exps );

        ResolveContext rctx = new DefaultResolver();
        for( Exp e : exps )
        {
            e.resolve( rctx );
        }

        EvalContext ectx = new DefaultEvaluator();
        for( Exp e : exps )
        {
            Object v = e.eval( ectx  );
            System.out.println( e + " --> " + v );
        }


    }
}
