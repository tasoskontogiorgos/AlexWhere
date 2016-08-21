package org.alex.exp;

/**
 * Created by tdk on 8/21/16.
 */
public class DefaultResolver implements ResolveContext
{
    @Override
    public Class getType(String name)
    {
        switch( name )
        {
            case "testString":
            {
                return String.class;
            }

            case "testInt":
            {
                return Integer.class;
            }
        }
        return null;
    }

    @Override
    public Class resolve(FuncCall fc)
    {
        return FuncDecl.Resolve( fc );
    }
}
