package org.alex.exp;

import org.antlr.v4.runtime.Token;

import java.util.Objects;

/**
 * Created by tdk on 8/20/16.
 */
public class Literal extends Exp
{
    private final Object    m_value;


    public Literal(Token token, Object value )
    {
        super( token );
        m_value = value;
    }


    @Override
    public Class getType()
    {
        if( m_value == null )
        {
            return Object.class;
        }
        return m_value.getClass();
    }

    @Override
    public Object eval( EvalContext ctx )
    {
        return m_value;
    }

    @Override
    public void resolve( ResolveContext ctx )
    {
    }


    @Override
    public String toString()
    {
        return Objects.toString( m_value );
    }


}
