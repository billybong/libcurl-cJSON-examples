package JoweCore.Middle.Base.Json;

import JoweCore.Inner.Base.StringUtil;
import java.lang.Collection;
import User;

//:#cplusplus   
//:$
//: #include <stdio.h>
//: #include <stdlib.h>
//: #include <string.h>
//: #include "cJSON.h"
//:$

public class JsonObject extends Object {
  private Unknown cJSONdelegate;

  public static JsonObject parse(String js){
    JsonObject ret;
    ret = new JsonObject();
    if(ret.setJson(js)){
      return ret;
    }
    return null;
  }

  private void setDelegate(Unknown delegate){
    this.cJSONdelegate = delegate;
  }

  private boolean setJson(String del){
    //:$
    //: cJSON *j;
    //: j = cJSON_Parse(kStrToChar($del$));
    //:$
    //: self:cJSONdelegate := $kPtrToObj(j)$
    return this.cJSONdelegate != null;
  }

  public String stringValue(){
    String ret;

    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: char *c = j->valuestring
    //:
    //:$
    //: ret := $kPtrToStr(c, strlen(c))$;
    return ret;
  }

  public double doubleValue(){
    double ret;

    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: double c = j->valuedouble
    //:
    //:$
    //: ret := $kDoubleToNum(c)$;
    return ret;
  }

  public int intValue(){
    int ret;

    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: int c = j->valueint
    //:
    //:$
    //: ret := $kIntToNum(c)$;
    return ret;
  }

  public int booleanValue(){
    return this.intValue() == 1;
  }

  public JsonObject getItem(String name){
    JsonObject ret;
    Unknown childDelegate;
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON *child = cJSON_GetObjectItem(j, kStrToChar($name$))
    //:$
    //: childDelegate := $kPtrToObj(child)$
    if(childDelegate == null){
      return null;
    }
    ret = JsonObject.neew();
    ret.setDelegate(childDelegate);
    return ret;
  }

  public JsonObject getAtArrayIndex(int i){
    JsonObject ret;
    Unknown childDelegate;
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON *child = cJSON_GetArrayItem(j, kNumToInt($i$))
    //:$
    //: childDelegate := $kPtrToObj(child)$
    if(childDelegate == null){
      return null;
    }
    ret = JsonObject.neew();
    ret.setDelegate(childDelegate);
    return ret;
  }

  public int arraySize(){
    int ret;

    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: int c = cJSON_GetArraySize(j)
    //:
    //:$
    //: ret := $kIntToNum(c)$;
    return ret;
  }

/**
* Evaluates a path on i.e. form "myObj.subObj.[3].child" by traversing recursively.
* path - the path to evaluate. Includes list [] and delimiter
* del - the delimiter to use between parent / child. Useful if key contains the default delimiter "."
*/
  public JsonObject eval(String path, String del){
    JsonObject ret;
    Collection delimiter;
    Collection listDelimiters;
    String currentName;

    delimiter = Collection.neew();
    if(del == null){
      del = ".";
    }

    delimiter.add(del);

    listDelimiters = Collection.neew();
    listDelimiters.add("[");
    listDelimiters.add("]");

    Collection itemNames;

    ret = this;
    itemNames = StringUtil.split(path, delimiter);

    int i;
    for(i = 1; i <= itemNames.size(); i++){
      currentName = itemNames.getAt(i);

      if(currentName.beginsWith("[") && currentName.endsWith("]")){
        int x;
        x = StringUtil.split(currentName, listDelimiters).getAt(1).asInteger();
        ret = ret.getAtArrayIndex(x);
      }else{
        ret = ret.getItem(itemNames.getAt(i));
      }
    }

    return ret;
  }

  public String toString(){
    String ret;
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: char *c = j->string
    //:
    //:$
    //: ret := $kPtrToStr(c, strlen(c))$;

    return ret;
  }

  public void free(){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_Delete(j);
    //:$
    this.cJSONdelegate = null;
  }
}