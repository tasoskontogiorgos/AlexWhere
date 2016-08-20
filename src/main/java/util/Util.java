package util;

import org.antlr.v4.runtime.Token;

/**
 * Created by tdk on 8/20/16.
 */
public class Util
{
    public static String At( Token token )
    {
        if( token == null )
        {
            return "?";
        }
        return "@line: " + token.getLine();
    }
}
