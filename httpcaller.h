#ifndef HTTPCLIENT_H
#define	HTTPCLIENT_H

#ifdef	__cplusplus
extern "C" {
#endif

extern char* httpget(const char* url);
extern int httppost(const char* url, const char* data);

#ifdef	__cplusplus
}
#endif

#endif	/* HTTPCLIENT_H */