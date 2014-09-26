/* 
 * File:   testjson.c
 * Author: bisj
 *
 * Created on den 23 september 2014, 09:09
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cJSON.h"

int main(void){
    cJSON* root = cJSON_CreateObject();
    
    cJSON_AddStringToObject(root, "name", "billy");
    cJSON_AddFalseToObject(root, "bool1");
    cJSON_AddTrueToObject(root, "bool2");
    cJSON_AddNullToObject(root, "nullable");
    cJSON_AddNumberToObject(root, "number", 12);
    cJSON_AddItemToObject(root, "child", cJSON_CreateObject());
    cJSON_AddItemToObject(root, "childArray", cJSON_CreateArray());
    
    cJSON* childArray = cJSON_GetObjectItem(root, "childArray");
    
    //BEhöver vi ha koll på om parent är en array??
    cJSON* childObject = cJSON_CreateObject();
    
    cJSON_AddItemToArray(childArray, childObject);
    
    printf(cJSON_Print(root));
    
    return 0;
}

