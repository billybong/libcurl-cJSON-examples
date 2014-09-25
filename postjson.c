#include <stdio.h>
#include <string.h>
#include <curl/curl.h>
#include "httpclient.h"
#include "cJSON.h"

int Xmain(void){
    cJSON* root = cJSON_CreateObject();
    cJSON_AddStringToObject(root, "id", "ingela");
    cJSON_AddNumberToObject(root, "amount", 18.0d);
    cJSON_AddStringToObject(root, "note", "för glass!");
    
    httppost("http://localhost:8080", cJSON_Print(root));
    cJSON_Delete(root);
    
    return 0;
}