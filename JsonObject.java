package JoweCore.Middle.Base.Json;

//:#cplusplus   
//:$
//: #include <stdio.h>
//: #include <stdlib.h>
//: #include <string.h>
//: #include "cJSON.h"
//:$

public class JsonObject extends Object {
  private Unknown cJSONdelegate;

  private void setDelegate(Unknown delegate){
    this.cJSONdelegate = delegate;
  }

  public boolean setJson(String del){
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
    //TODO: Implement
    return 1;
  }

  public void free(){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_Delete(j);
    //:$
  }
}