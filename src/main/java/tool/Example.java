package tool;

import org.alex.exp.Exp;
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
    }
}
