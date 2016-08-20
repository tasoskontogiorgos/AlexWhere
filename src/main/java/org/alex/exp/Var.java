package org.alex.exp;

import org.antlr.v4.runtime.Token;
import util.Util;

import java.util.Objects;

/**
 * Created by tdk on 8/20/16.
 */
public class Var extends Exp
{
    private final String    m_name;
    private Class           m_type;


    public Var(Token token, String name )
    {
        super( token );
        m_name = name;
    }


    public String getName()
    {
        return m_name;
    }

    @Override
    public Class getType()
    {

        return m_type;
    }

    @Override
    public Object eval( EvalContext ctx )
    {
        return ctx.getValue( m_name );
    }

    @Override
    public void resolve( ResolveContext ctx )
    {
        m_type = ctx.getType( m_name );
        if( m_type == null )
        {
            throw new RuntimeException( "Undefined variable " + m_name + Util.At( getToken() ));
        }
    }


    @Override
    public String toString()
    {
        return Objects.toString( getName() );
    }


}
