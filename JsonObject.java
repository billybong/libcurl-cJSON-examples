package JoweCore.Middle.Base.Json;

import JoweCore.Inner.Base.StringUtil;
import java.lang.Collection;
import User;
import JoweCore.Middle.Base.Http.Http;

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
    String currentName;

    delimiter = Collection.neew();
    if(del == null){
      del = ".";
    }

    delimiter.add(del);

    Collection itemNames;

    ret = this;
    itemNames = StringUtil.split(path, delimiter);

    int i;
    for(i = 1; i <= itemNames.size(); i++){
      currentName = itemNames.getAt(i);
      User.tell("At: " + currentName);

      if(currentName.beginsWith("[") && currentName.endsWith("]")){
        int x;
        x = currentName.replace("[", "").replace("]", "").asInteger();
        ret = ret.getAtArrayIndex(x);
      }else{
        ret = ret.getItem(itemNames.getAt(i));
      }
    }

    return ret;
  }

  public String jsonText(){
    String ret;
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: char *c = cJSON_PrintUnformatted(j)
    //:
    //:$
    //: ret := $kPtrToStr(c, strlen(c))$;
    //: $ free(c); $

    return ret;
  }

  public static JsonObject rootObject(){
    JsonObject ret;

    ret = JsonObject.neew();
    //: ret:setDelegate($kPtrToObj(cJSON_CreateObject())$);

    return ret;
  }

  public static JsonObject rootArray(){
    JsonObject ret;

    ret = JsonObject.neew();
    //: ret:setDelegate($kPtrToObj(cJSON_CreateArray())$);

    return ret;
  }

  public void addString(String name, String value){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_AddStringToObject(j, kStrToChar($name$), kStrToChar($value$))
    //:$  
  }

public void addInt(String name, int value){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_AddNumberToObject(j, kStrToChar($name$), kNumToInt($value$))
    //:$  
  }

  public void addDouble(String name, double value){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_AddNumberToObject(j, kStrToChar($name$), kNumToDouble($value$))
    //:$  
  }

  public void addTrue(String name){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_AddTrueToObject(j, kStrToChar($name$))
    //:$  
  }

  public void addFalse(String name){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_AddFalseToObject(j, kStrToChar($name$))
    //:$  
  }

  public void addBoolean(String name, boolean b){
    if(b){
      this.addTrue(name);
    }else{
      this.addFalse(name);
    }  

  }

  public void addNull(String name){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_AddNullToObject(j, kStrToChar($name$))
    //:$  
  }

  public JsonObject addObject(String name){
    JsonObject ret;
    ret = JsonObject.neew();
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON *child = cJSON_CreateObject();
    //: cJSON_AddItemToObject(j, kStrToChar($name$), child);
    //:$  
    //: ret:setDelegate($kPtrToObj(child)$);

    return ret;
  }

  public JsonObject addArray(String name){
    JsonObject ret;
    ret = JsonObject.neew();
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON *child = cJSON_CreateArray();
    //: cJSON_AddItemToObject(j, kStrToChar($name$), child);
    //:$  
    //: ret:setDelegate($kPtrToObj(child)$);

    return ret;
  }

  public JsonObject addObjectToArray(){
    JsonObject ret;
    ret = JsonObject.neew();
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON *child = cJSON_CreateObject();
    //: cJSON_AddItemToArray(j, child);
    //:$  
    //: ret:setDelegate($kPtrToObj(child)$);

    return ret;
  }

  public void free(){
    //:$
    //: cJSON *j  = (cJSON *) kObjToPtr( $self:cJSONdelegate$ );  
    //: cJSON_Delete(j);
    //:$
    this.cJSONdelegate = null;
  }

  public static void test(){
    JsonObject.testJsonConstruct();
  }

  public static void testGet(){
    JsonObject jsObj;
    jsObj = JsonObject.parse(Http.get("http://localhost:2000/api/person/8006130135?mock=true"));
    User.tell(
      "name = " + jsObj.getItem("firstName").stringValue() + "\n" + 
      "secret = " + jsObj.getItem("secretIdentity").booleanValue() + "\n" + 
      "street = " + jsObj.eval("address.street").stringValue()
      );
  }

  public static void testJsonConstruct(){
   JsonObject jsObj;
   jsObj = JsonObject.rootObject();
   jsObj.addString("name", "Billy");
   jsObj.addInt("age", 34);
   jsObj.addDouble("length", 1.73);
   jsObj.addTrue("hasDaughter");
   jsObj.addFalse("hasSon");
   jsObj.addBoolean("hasCat", true);
   jsObj.addBoolean("hasDog", false);
   jsObj.addNull("cares_about_sports");
   jsObj.addObject("spouse").addString("name", "Maria");
   jsObj.addArray("children").addObjectToArray().addString("name", "Alva");
   jsObj.getItem("children").addObjectToArray().addString("name", "Viggo");
   jsObj.eval("children.[1]").addObject("room").addString("color","green");  // Index starts at 0
   User.tell("json : " + jsObj.jsonText());
   Http.post("http://localhost:2000/person", jsObj.jsonText()); 
  }
}