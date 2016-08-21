package org.alex.exp;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tdk on 8/20/16.
 */
public class FuncCall extends Exp
{
    private final String        m_name;
    private final List< Exp >   m_actuals = new ArrayList();
    private Class               m_type;


    public  FuncCall(Token token, String name, Exp ... actuals )
    {
        super( token );
        m_name = name;
        for( Exp a : actuals )
        {
            m_actuals.add( a );
        }
    }

    @Override
    public void resolve( ResolveContext ctx )
    {
        for( Exp e : m_actuals )
        {
            e.resolve( ctx );
        }
        m_type = ctx.resolve( this );
    }

    @Override
    public Class getType( )
    {
        return m_type;
    }

    @Override
    public Object eval( EvalContext ctx )
    {
       return ctx.eval( this );
    }

    public String getName()
    {
        return m_name;
    }

    public List< Exp > getAtuals()
    {
        return m_actuals;
    }


    @Override
    public String toString()
    {
        String s = "( ";
        for( Exp e : getAtuals())
        {
            if( s.length() > 2 )
            {
                s += ", ";
            }
            s += Objects.toString( e );
        }
        s += " )";
        return getName() + s;
    }

}
