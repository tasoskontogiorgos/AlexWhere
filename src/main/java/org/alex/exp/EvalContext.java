package org.alex.exp;

/**
 * Created by tdk on 8/20/16.
 */
public interface EvalContext
{
    Object  getValue ( String name );
    Object  eval ( FuncCall fc );

}
