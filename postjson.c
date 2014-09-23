#include <stdio.h>
#include <string.h>
#include <curl/curl.h>
#include "cJSON.h"
 
struct WriteThis {
  const char *readptr;
  long sizeleft;
};
 
static size_t read_callback(void *ptr, size_t size, size_t nmemb, void *userp)
{
  struct WriteThis *pooh = (struct WriteThis *)userp;
 
  if(size*nmemb < 1)
    return 0;
 
  if(pooh->sizeleft) {
    *(char *)ptr = pooh->readptr[0]; /* copy one single byte */ 
    pooh->readptr++;                 /* advance pointer */ 
    pooh->sizeleft--;                /* less data left */ 
    return 1;                        /* we return 1 byte at a time! */ 
  }
 
  return 0;                          /* no more data left to deliver */ 
}
 
int postJson(const char* data)
{
  CURL *curl;
  CURLcode res;
 
  struct WriteThis pooh;
 
  pooh.readptr = data;
  pooh.sizeleft = (long)strlen(data);
 
  /* In windows, this will init the winsock stuff */ 
  res = curl_global_init(CURL_GLOBAL_DEFAULT);
  /* Check for errors */ 
  if(res != CURLE_OK) {
    fprintf(stderr, "curl_global_init() failed: %s\n",
            curl_easy_strerror(res));
    return 1;
  }
 
  /* get a curl handle */ 
  curl = curl_easy_init();
  if(curl) {
      
    struct curl_slist *headers = NULL;
    headers = curl_slist_append(headers, "Accept: application/json");
    headers = curl_slist_append(headers, "Content-Type: application/json");
    headers = curl_slist_append(headers, "charsets: utf-8");
    headers = curl_slist_append(headers, "Transfer-Encoding: chunked");
    
    curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
    
    /* First set the URL that is about to receive our POST. */ 
    curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:8080/invoice");
 
    /* Now specify we want to POST data */ 
    curl_easy_setopt(curl, CURLOPT_POST, 1L);
 
    /* we want to use our own read function */ 
    curl_easy_setopt(curl, CURLOPT_READFUNCTION, read_callback);
 
    /* pointer to pass to our read function */ 
    curl_easy_setopt(curl, CURLOPT_READDATA, &pooh);
 
    /* get verbose debug output please */ 
    curl_easy_setopt(curl, CURLOPT_VERBOSE, 1L);
 
    /* Perform the request, res will get the return code */ 
    res = curl_easy_perform(curl);
    
    /* clear http header list */
    curl_slist_free_all(headers);
            
    /* Check for errors */ 
    if(res != CURLE_OK)
      fprintf(stderr, "curl_easy_perform() failed: %s\n",
              curl_easy_strerror(res));
 
    /* always cleanup */ 
    curl_easy_cleanup(curl);
  }
  curl_global_cleanup();
  return 0;
}

int main(void){
    cJSON* root = cJSON_CreateObject();
    cJSON_AddStringToObject(root, "id", "ingela");
    cJSON_AddNumberToObject(root, "amount", 18.0d);
    cJSON_AddStringToObject(root, "note", "för glass!");
    
    postJson(cJSON_Print(root));
    cJSON_Delete(root);
    
    return 0;
}