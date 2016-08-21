package org.alex.exp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tdk on 8/20/16.
 */
public class FuncDecl
{

    private static Map< String, FuncDecl >      s_all = new HashMap<>();

    private static void  Define( FuncDecl fd )
    {
        if( s_all.containsKey( fd.getName() ))
        {
            throw new RuntimeException( "Duplicate function " + fd.getName() );
        }
        s_all.put( fd.getName(), fd );
    }


    static
    {
        Define( new FuncDecl( Boolean.class, "BETWEEN",    Integer.class, Integer.class, Integer.class ));
        Define( new FuncDecl( Boolean.class, "EQUALS",     Integer.class, Integer.class, Integer.class ));
        Define( new FuncDecl( String.class,  "SUBSTR",     String.class, Integer.class, Integer.class ));
        Define( new FuncDecl( Boolean.class, "LIKE",       String.class, String.class ));
        Define( new FuncDecl( Boolean.class, "NOTLIKE",    String.class, String.class ));
        Define( new FuncDecl( Boolean.class, "IN",         String.class, Set.class ));
        Define( new FuncDecl( Boolean.class, "NOTIN",      String.class, Set.class ));
        Define( new FuncDecl( Set.class,     "SETOF",      List.class ));
    }


    static Class Resolve( FuncCall fc )
    {
        String name = fc.getName();
        if( s_all.containsKey( name ))
        {
            FuncDecl fd = s_all.get( name );
            Class[] formals = fd.getFormalArguments();
            List< Exp > actuals = fc.getAtuals();
            if( formals.length == actuals.size() )
            {
                for( int i = 0; i<formals.length; i++ )
                {
                    Class<?> f  = formals[ i ];
                    Class<?> a = actuals.get( i ).getType();

                    if( !f.isAssignableFrom( a))
                    {
                        return null;
                    }
                }
            }
            return fd.returnType();
        }
        return null;
    }

    public static Object Eval( String name, Object[] args  )
    {
        switch( name )
        {
            case "BETWEEN":
            {
                Integer a = ( Integer )args[ 0 ];
                Integer b = ( Integer )args[ 1 ];
                Integer c = ( Integer )args[ 2 ];

                return a >= b && a <= c;
            }

            case "EQUALS":
            {
                Integer a = ( Integer )args[ 0 ];
                Integer b = ( Integer )args[ 1 ];
                Integer c = ( Integer )args[ 2 ];

                return a.equals(b) || a.equals( c );
            }

            case "SUBSTR":
            {
                String a =  ( String )args[ 0 ];
                Integer b = ( Integer )args[ 1 ];
                Integer c = ( Integer )args[ 2 ];

                return a.substring( b, c );
            }

            case "IN":
            {
                String a =  ( String )args[ 0 ];
                Set b =     ( Set )args[ 1 ];

                return b.contains( a );
            }

            case "NOTIN":
            {
                String a =  ( String )args[ 0 ];
                Set b =     ( Set )args[ 1 ];

                return !b.contains( a );
            }
        }

        throw new RuntimeException( "Can't execute " + name );
    }

    private final String        m_name;
    private final Class[]       m_formalArgs;
    private final Class         m_returnType;

    public FuncDecl( Class ret, String name, Class ... args )
    {
        m_name = name;
        m_formalArgs = args;
        m_returnType = ret;

    }

    public Class returnType()
    {
        return m_returnType;
    }

    public String getName()
    {
        return m_name;
    }

    public Class[] getFormalArguments()
    {
        return m_formalArgs;
    }

    @Override
    public String toString()
    {
        String s = getName();
        Class[] args = getFormalArguments();
        s += "( ";
        for( Class c : args )
        {
            if( s.length() > 2 )
            {
                s += ", ";
            }
            s += c.getSimpleName();
        }
        s += " )";
        return s;

    }
}
