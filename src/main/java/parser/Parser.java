package parser;


import java.io.ByteArrayInputStream;
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
    private static List<Exp> _Parse(InputStream is ) throws Exception
    {

        ANTLRInputStream input = new ANTLRInputStream( is );
        WhereLexer lexer = new WhereLexer( input );
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        WhereParser parser = new WhereParser( tokens );
        ParseTree tree = parser.wheres();

        Productions prod = new Productions();

        prod.visit( tree );
        return prod.getExps();
    }

    public static List<Exp>  Parse( String fileName )
    {
        try
        {
            InputStream is = new FileInputStream( fileName );
            return _Parse( is );
        } catch( Exception x )
        {
            throw new RuntimeException( x.getMessage() );
        }
    }

    public static List<Exp>  ParseString( String str )
    {
        try
        {
            InputStream is = new ByteArrayInputStream(str.getBytes());
            return _Parse( is );
        } catch( Exception x )
        {
            throw new RuntimeException( x.getMessage() );
        }
    }
}
