/* 
 * File:   testjson.c
 * Author: bisj
 *
 * Created on den 23 september 2014, 09:09
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "httpclient.h"
#include "cJSON.h"

int main(void){
    char *res = httpget("https://api.github.com/users/billybong/followers");
    cJSON* root = cJSON_Parse(res);
    int followers = cJSON_GetArraySize(root);
    printf("%i followers", followers);
    printf("%s", res);
    free(res);
    return 0;
}

