package org.alex.exp;

import org.antlr.v4.runtime.Token;

import java.util.*;

/**
 * Created by tdk on 8/20/16.
 */
public class ExpList extends Exp
{
    private final List< Exp >   m_actuals = new ArrayList();


    public ExpList(Token token,  Exp ... actuals )
    {
        super( token );
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
    }

    @Override
    public Class getType( )
    {
        return Set.class;
    }

    @Override
    public Object eval( EvalContext ctx )
    {
        Set s = new HashSet();
        for( Exp e : m_actuals )
        {
            s.add( e.eval( ctx ));
        }
        return s;
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
        return s;
    }

}
