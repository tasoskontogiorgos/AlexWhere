package org.alex.exp;

/**
 * Created by tdk on 8/20/16.
 */
public interface ResolveContext
{
    Class   getType( String name );
    Class   resolve( FuncCall fc );

}
