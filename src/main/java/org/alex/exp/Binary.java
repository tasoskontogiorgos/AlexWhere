package org.alex.exp;

import org.antlr.v4.runtime.Token;

/**
 * Created by tdk on 8/20/16.
 */
public abstract class Binary extends Exp
{
    private final Exp       m_left;
    private final Exp       m_right;


    public Binary(Token token, Exp left, Exp right )
    {
        super( token );
        m_left = left;
        m_right = right;
    }

    public Exp getLeft()
    {
        return m_left;
    }

    public Exp getRight()
    {
        return m_right;
    }
}
