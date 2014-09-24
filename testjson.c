/* 
 * File:   testjson.c
 * Author: bisj
 *
 * Created on den 23 september 2014, 09:09
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <curl/curl.h>
#include "cJSON.h"

struct MemoryStruct {
  char *memory;
  size_t size;
};
 
 
static size_t
WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp)
{
  size_t realsize = size * nmemb;
  struct MemoryStruct *mem = (struct MemoryStruct *)userp;
 
  mem->memory = (char*)realloc(mem->memory, mem->size + realsize + 1);
  if(mem->memory == NULL) {
    /* out of memory! */ 
    printf("not enough memory (realloc returned NULL)\n");
    return 0;
  }
 
  memcpy(&(mem->memory[mem->size]), contents, realsize);
  mem->size += realsize;
  mem->memory[mem->size] = 0;
 
  return realsize;
}
 
 
char* callhttp(const char* url)
{
  CURL *curl_handle;
  CURLcode res;
 
  struct MemoryStruct chunk;
 
  chunk.memory = (char*)malloc(1);  /* will be grown as needed by the realloc above */ 
  chunk.size = 0;    /* no data at this point */ 
 
  curl_global_init(CURL_GLOBAL_ALL);
 
  /* init the curl session */ 
  curl_handle = curl_easy_init();
 
  /* specify URL to get */ 
  curl_easy_setopt(curl_handle, CURLOPT_URL, url);
 
  /* send all data to this function  */ 
  curl_easy_setopt(curl_handle, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
 
  /* we pass our 'chunk' struct to the callback function */ 
  curl_easy_setopt(curl_handle, CURLOPT_WRITEDATA, (void *)&chunk);
 
  /* some servers don't like requests that are made without a user-agent
     field, so we provide one */ 
  curl_easy_setopt(curl_handle, CURLOPT_USERAGENT, "libcurl-agent/1.0");
 
  /* get it! */ 
  res = curl_easy_perform(curl_handle);
 
  /* check for errors */ 
  if(res != CURLE_OK) {
    fprintf(stderr, "curl_easy_perform() failed: %s\n",
            curl_easy_strerror(res));
  }
  else {
    /*
     * Now, our chunk.memory points to a memory block that is chunk.size
     * bytes big and contains the remote file.
     *
     * Do something nice with it!
     */ 
 
    printf("%lu bytes retrieved\n", (long)chunk.size);
  }
 
  /* cleanup curl stuff */ 
  curl_easy_cleanup(curl_handle);
 
  /* we're done with libcurl, so clean it up */ 
  curl_global_cleanup();
  
  return chunk.memory;
}

int callGit(void) {

    cJSON *root;
    char *avatar_url;
    char *jsonAsText = callhttp("https://api.github.com/repos/vmg/redcarpet/issues?state=closed");
    
    root = cJSON_Parse(jsonAsText);
    printf("%i %s", cJSON_GetArraySize(root), "commits found..\n");
    cJSON* firstCommit = cJSON_GetArrayItem(root, 1);
    
    int size = cJSON_GetArraySize(root);
    int i;
    for(i=0; i < size; i++)
    {
        printf("Title: %s\n", cJSON_GetObjectItem(cJSON_GetArrayItem(root, i), "title")->valuestring);
    }
    
    printf("%s%s", firstCommit->child->valuestring, "\n");
    
    cJSON* user = cJSON_GetObjectItem(firstCommit, "user");
    printf("%s\n", user->string);
    avatar_url = cJSON_GetObjectItem(user, "avatar_url")->valuestring;
    
    printf("%s%s", avatar_url, "\n");
    
    cJSON_Delete(root);
    free(jsonAsText);
    
    return (EXIT_SUCCESS);
}

struct cJSON * callInvoiceService(void){
    char *jsonAsText = callhttp("http://localhost:8080/invoice");
    return cJSON_Parse(jsonAsText);
}

void parse_object(cJSON *item)
{
    if(item->type == cJSON_Object)
        printf("handling %s of type %i\n", item->string, item->type);
    // handle subitem  
    switch(item->type){
        case cJSON_String : printf("%s : %s\n", item->string , item->valuestring); break;
        case cJSON_Number : printf("%s : %f\n", item->string , item->valuedouble); break;
        case cJSON_True : printf("%s : %i\n", item->string , item->valueint); break;
        case cJSON_False : printf("%s : %i\n", item->string , item->valueint); break;
        case cJSON_NULL : printf("%s : %s\n", item->string , "null"); break;
        case cJSON_Array : printf("%s : %i rows\n", item->string, cJSON_GetArraySize(item));
            if(cJSON_GetArraySize(item)>0)
            {
                parse_object(cJSON_GetArrayItem(item, 0));
            }
            break;
        case cJSON_Object : 
            if(item->child){
                parse_object(item->child);
            }
            break;
        default : printf("%s : %i\n", item->string , item->type);
    }
    
    item = item->next;
    
    if(item){
        parse_object(item);
    }
}

int main(void){
    parse_object(callInvoiceService()->child);
    return 0;
}

