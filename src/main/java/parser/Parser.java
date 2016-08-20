package parser;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.alex.WhereLexer;
import org.alex.WhereParser;
import org.alex.exp.Exp;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


/**
 * Created by tdk on 8/20/16.
 */
public class Parser
{
    private static List<Exp> _Parse(String fileName ) throws Exception
    {
        InputStream is = new FileInputStream( fileName );

        ANTLRInputStream input = new ANTLRInputStream( is );
        WhereLexer lexer = new WhereLexer( input );
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        WhereParser parser = new WhereParser( tokens );
        ParseTree tree = parser.wheres(); // parse; start at prog <label id="code.tour.main.6"/>
        //System.out.println( tree.toStringTree( parser ) ); // print tree as text <label id="code.tour.main.7"/>

        Productions prod = new Productions();

        prod.visit( tree );
        return prod.getExps();
    }

    public static List<Exp>  Parse( String fileName )
    {
        try
        {
            return _Parse( fileName );
        } catch( Exception x )
        {
            throw new RuntimeException( x.getMessage() );
        }
    }
}
