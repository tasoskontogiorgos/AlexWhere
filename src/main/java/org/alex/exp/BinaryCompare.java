package org.alex.exp;

import org.antlr.v4.runtime.Token;
import util.Util;

import java.util.Objects;

/**
 * Created by tdk on 8/20/16.
 */
public class BinaryCompare extends Binary
{
    public enum Oper
    {
        LE,
        LS,
        GT,
        GE,
        EQ,
        NEQ
    }

    private final Oper      m_oper;

    public BinaryCompare(Token token, Exp left, Exp right, Oper op )
    {
        super( token, left, right );

        m_oper = op;
    }

    @Override
    public Class getType()
    {
        return boolean.class;
    }

    @Override
    public void resolve( ResolveContext ctx )
    {
        getLeft().resolve( ctx );
        getRight().resolve( ctx );
        Class l = getLeft().getType();
        Class r = getRight().getType();

        if( !( l.isAssignableFrom( Comparable.class ) && r.isAssignableFrom( Comparable.class )))
        {
            throw new RuntimeException( "Illegal types " + Util.At(getToken()));
        }

    }


    @Override
    public Object eval( EvalContext ctx )
    {
        Comparable l = (Comparable) getLeft().eval( ctx );
        Comparable r = (Comparable) getRight().eval( ctx );
        int x = l.compareTo( r );
        switch( m_oper )
        {
            case LS:
            {
                return x < 0;
            }

            case LE:
            {
                return x <= 0;
            }

            case GT:
            {
                return x > 0;
            }

            case GE:
            {
                return x >= 0;
            }

            case EQ:
            {
                return x == 0;
            }

            case NEQ:
            {
                return x != 0;
            }
        }
        throw new RuntimeException( "Internal error " + Util.At( getToken() ));

    }


    @Override
    public String toString()
    {
        return Objects.toString( getLeft() ) + " " + m_oper +  " " + Objects.toString( getRight() );
    }

}
