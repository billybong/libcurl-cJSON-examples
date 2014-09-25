
package JoweCore.Middle.Base.Http;

//:#cplusplus   
//:$
//: #include <stdio.h>
//: #include <stdlib.h>
//: #include <string.h>
//: #include "httpcaller.h"
//:$

public class Http extends Object
{
    static public String get(String url)
    {
        String x;

        //:$
        //: char* response = httpget(kStrToChar($url$));
        //: $
        //: x := $kPtrToStr(response, strlen(response))$;
        //: $free(response);$
        return x;
    }

    static public String post(String url, String text){

    //:$
    //: httppost(kStrToChar($url$), kStrToChar($text$))
    //:$    
    }
}