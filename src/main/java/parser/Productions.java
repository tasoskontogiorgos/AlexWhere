package parser;

import org.alex.WhereParser;
import org.alex.exp.*;
import util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk on 8/20/16.
 */
public class Productions extends org.alex.WhereBaseVisitor< Void >
{

    private final List< Exp >     m_exps = new ArrayList<>();

    public List< Exp > getExps()
    {
        return m_exps;
    }

    @Override
    public Void visitWhere(WhereParser.WhereContext ctx)
    {
         m_exps.add( convert( ctx.exp()));
        return visitChildren( ctx );
    }


    Exp convert( WhereParser.ExpContext ctx )
    {
        String s = ctx.getText();

        if( ctx.IN() != null )
        {
            Exp a = convert( ctx.exp( 0 ));
            Exp b = convertList( ctx.expList(  ));
            String name = "IN";
            if( ctx.NOT() != null )
            {
                name = "NOTIN";
            }
            return new FuncCall( ctx.start, name, a, b  );
        }

        if( ctx.LIKE() != null )
        {
            Exp a = convert( ctx.exp( 0 ));
            Exp b = convertList( ctx.expList(  ));
            String name = "LIKE";
            if( ctx.NOT() != null )
            {
                name = "NOTLIKE";
            }
            return new FuncCall( ctx.start, name, a, b  );
        }

        if( ctx.IntegerLiteral() != null )
        {
            String text = ctx.IntegerLiteral().getText();
            Integer val = Integer.parseInt( text );
            return new Literal( ctx.start, val );
        }
        if( ctx.BooleanLiteral() != null )
        {
            String text = ctx.BooleanLiteral().getText();
            Boolean val = Boolean.parseBoolean( text );
            return new Literal( ctx.start, val );
        }

        if( ctx.StringLiteral() != null )
        {
            String text = ctx.StringLiteral().getText();
            text = text.substring( 1, text.length() - 1 );
            return new Literal( ctx.start, text );
        }

        if( ctx.Identifier() != null )
        {
            return new Var( ctx.start, ctx.Identifier().getText() );
        }


        if( ctx.funCall() != null )
        {
            return convert( ctx.funCall() );
        }
        if( ctx.compOper() != null )
        {
            return new BinaryCompare( ctx.start, convert( ctx.exp( 0 )), convert( ctx.exp( 1 )), convert( ctx.compOper()));
        }
        if( ctx.logOper() != null )
        {
            return new BinaryLogical( ctx.start, convert( ctx.exp( 0 )), convert( ctx.exp( 1 )), convert( ctx.logOper()));
        }
        if( ctx.BETWEEN() != null )
        {
            Exp a = convert( ctx.exp( 0 ));
            Exp b = convert( ctx.exp( 1 ));
            Exp c = convert( ctx.exp( 2 ));
            return new FuncCall( ctx.start, "BETWEEN", a, b, c );
        }
        if( ctx.EQUALSW() != null )
        {
            Exp a = convert( ctx.exp( 0 ));
            Exp b = convert( ctx.exp( 1 ));
            Exp c = convert( ctx.exp( 2 ));
            return new FuncCall( ctx.start, "EQUALS", a, b, c );
        }


        if( ctx.expList() != null )
        {
            Exp[] arr = convert( ctx.expList() );

            return new ExpList( ctx.start, arr );
        }

        throw new RuntimeException( "Internal Error " + Util.At( ctx.start ));
    }

    public BinaryLogical.Oper convert(WhereParser.LogOperContext ctx)
    {
        String s = ctx.getText();
        switch( s )
        {
            case "&&":
            case "AND":
            {
                return BinaryLogical.Oper.AND;
            }
            case "||":
            case "OR":
            {
                return BinaryLogical.Oper.OR;
            }


        }
        throw new RuntimeException( "Internal Error " + Util.At( ctx.start ));
    }


    public BinaryCompare.Oper convert(WhereParser.CompOperContext ctx)
    {
        String s = ctx.getText();
        switch( s )
        {
            case ">":
            {
                return BinaryCompare.Oper.GT;
            }
            case ">=":
            {
                return BinaryCompare.Oper.GE;
            }
            case "<":
            {
                return BinaryCompare.Oper.LT;
            }
            case "<=":
            {
                return BinaryCompare.Oper.LE;
            }

            case "==":
            {
                return BinaryCompare.Oper.EQ;
            }

            case "!=":
            {
                return BinaryCompare.Oper.NEQ;
            }

        }
        throw new RuntimeException( "Internal Error " + Util.At( ctx.start ));
    }

    public Exp[] convert(WhereParser.ExpListContext ctx)
    {
        int sz = ctx.exp().size();
        Exp[] l = new Exp[ sz ];
        for( int i = 0; i < sz; i++ )
        {
            l[ i ] = convert( ctx.exp( i ));
        }
        return l;
    }

    public Exp convertList(WhereParser.ExpListContext ctx)
    {
        int sz = ctx.exp().size();
        Exp[] l = new Exp[ sz ];
        for( int i = 0; i < sz; i++ )
        {
            l[ i ] = convert( ctx.exp( i ));
        }
        return new ExpList( ctx.start, l );
    }


    public Exp convert(WhereParser.FunCallContext ctx)
    {
        String name = ctx.Identifier().getText();
        return new FuncCall( ctx.start, name, convert( ctx.expList() ));
    }


}
