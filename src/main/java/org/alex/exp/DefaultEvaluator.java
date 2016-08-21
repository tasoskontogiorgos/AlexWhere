package org.alex.exp;

import java.util.List;

/**
 * Created by tdk on 8/21/16.
 */
public class DefaultEvaluator implements EvalContext
{
    @Override
    public Object getValue(String name)
    {
        switch( name )
        {
            case "testString":
            {
                return "abcdefghi";
            }

            case "testInt":
            {
                return 123;
            }
        }
        return null;
    }

    @Override
    public Object eval(FuncCall fc)
    {
        List< Exp >  actuals = fc.getAtuals();
        Object[] values = new Object[ actuals.size() ];
        for( int i  = 0; i < values.length; i++ )
        {
            values[ i ] = actuals.get( i ).eval( this );
        }

        return FuncDecl.Eval( fc.getName(), values );
    }
}
