package org.alex.exp;

import org.antlr.v4.runtime.Token;
import util.Util;

import java.util.Objects;

/**
 * Created by tdk on 8/20/16.
 */
public class BinaryLogical extends Binary
{
    public enum Oper
    {
        AND,
        OR,

    }

    private final Oper      m_oper;

    public BinaryLogical(Token token, Exp left, Exp right, Oper op )
    {
        super( token, left, right );

        m_oper = op;
    }

    @Override
    public Class getType()
    {
        return boolean.class;
    }


    boolean isBoolean( Class c )
    {
        return c == boolean.class || c == Boolean.class;
    }

    @Override
    public void resolve( ResolveContext ctx )
    {
        getLeft().resolve( ctx );
        getRight().resolve( ctx );
        Class l = getLeft().getType();
        Class r = getRight().getType();

        if( !( isBoolean( l ) && isBoolean( r )))
        {
            throw new RuntimeException( "Illegal types " + Util.At(getToken()));
        }

    }


    @Override
    public Object eval( EvalContext ctx )
    {
        Boolean l = (Boolean) getLeft().eval( ctx );
        if( !l && m_oper.equals( Oper.AND ))
        {
            return false;
        }
        if( l && m_oper.equals( Oper.OR ))
        {
            return true;
        }
        Boolean r = (Boolean) getRight().eval( ctx );
        int x = l.compareTo( r );
        switch( m_oper )
        {
            case AND:
            {
                return l && r;
            }

            case OR:
            {
                return l || r;
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
