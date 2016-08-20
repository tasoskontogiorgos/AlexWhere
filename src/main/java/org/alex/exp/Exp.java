package org.alex.exp;

import org.antlr.v4.runtime.Token;

/**
 * Created by tdk on 8/20/16.
 */
public abstract class  Exp
{
    private final Token         m_token;

    public Exp( Token token )
    {
        m_token = token;
    }


    public Token getToken()
    {
        return m_token;
    }


    public abstract Class getType();

    public abstract void        resolve( ResolveContext ctx );
    public abstract Object      eval( EvalContext ctx );

}
