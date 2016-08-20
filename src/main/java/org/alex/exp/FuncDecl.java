package org.alex.exp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by tdk on 8/20/16.
 */
public class FuncDecl
{

    private static Map< String, FuncDecl >      s_all = new HashMap();

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
        Define( new FuncDecl( "BETWEEN",    Integer.class, Integer.class, Integer.class ));
        Define( new FuncDecl( "EQUALS",     Integer.class, Integer.class, Integer.class ));
        Define( new FuncDecl( "SUBSTR",     String.class, Integer.class, Integer.class ));
        Define( new FuncDecl( "LIKE",       String.class, String.class ));
        Define( new FuncDecl( "NOTLIKE",    String.class, String.class ));
        Define( new FuncDecl( "IN",         String.class, Set.class ));
        Define( new FuncDecl( "NOTIN",      String.class, Set.class ));
    }

    private final String        m_name;
    private final Class[]       m_formalArgs;

    public FuncDecl( String name, Class ... args )
    {
        m_name = name;
        m_formalArgs = args;
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
