#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <regex.h>
#include <string.h>
#include <sys/stat.h>
#include <regex.h>     
#include <stdbool.h>

printUsage() {
  printf("%s\n", "Usage: iscsi-utils discovery <ip> | iscsi-utils login <store> <username> <password> | iscsi-utils logout <store> | iscsi-utils getdev <store> | iscsi-utils devown <devpath> <user>");
}

int _system(const char *template, ...) {
  char buffer[256];
  va_list args;
  va_start(args, template);
  vsprintf(buffer, template, args);
  int exitValue = system(buffer);
  va_end(args);
  return exitValue;
}

int discovery(char *ip) {
  return _system("iscsiadm -m discovery -t sendtargets -p %s", ip);
}

int update(char *store, char *sessionKey, char *value) {
  return _system("iscsiadm -m node -T %s --op=update --name node.session.auth.%s --value=%s", store, sessionKey, value);
}

int login(char *store) {
  return _system("iscsiadm -m node -T %s -l", store);
}

int logout(char *store) {
	return _system("iscsiadm -m node -T %s -u", store);
}

char *trim(char *s) {
    char *ptr;
    if (!s)
        return NULL;   // handle NULL string
    if (!*s)
        return s;      // handle empty string
    for (ptr = s + strlen(s) - 1; (ptr >= s) && isspace(*ptr); --ptr);
    ptr[1] = '\0';
    return s;
}

int getdev(char *store) {
  char *command = "iscsiadm -m session -P 3";

  char regexTargetStr[1035];
  sprintf(regexTargetStr, "Target: %s", store);

  FILE *fp;
  char *path = NULL;
  size_t len = 0;
  fp = popen(command, "r");

  bool foundTarget = false;
  bool foundDev = false;

  while (getline(&path, &len, fp) != -1) {
    if (foundTarget) {
      regmatch_t match[2];
      regex_t regexDev;
      regcomp(&regexDev, ".*Attached scsi disk (\\S+).*", REG_ICASE|REG_EXTENDED);
      if (regexec(&regexDev, path, 2, match, 0) == 0) {
        printf("/dev/%.*s", match[1].rm_eo - match[1].rm_so, &path[match[1].rm_so]);
        foundDev = true;
        break;
      }
      regfree(&regexDev);
    } else {
      foundTarget = strcmp(regexTargetStr, trim(path)) == 0;
    }
  }
  if (foundDev) {
    return pclose(fp);
  }  
  return 1;
}

int devown(char *devpath, char *user) {
	return _system("chown %s %s", user, devpath);
}

int main(int argc, char *argv[]) {
  if (argc < 2) {
    printUsage();
    return 1;
  }

  const char *method = argv[1];
  if (strcmp(method, "discovery") == 0) {
    if (argc < 3) {
      printUsage();
      return 1;  
    }
    if (discovery(argv[2]) != 0) {
      return 1;
    }
    return 0;
  }

  if (strcmp(method, "login") == 0) {
    if (argc < 5) {
      printUsage();
      return 1;  
    }
    if (update(argv[2], "username", argv[3]) != 0) {
      return 1;
    }
    if (update(argv[2], "password", argv[4]) != 0) {
      return 1;
    }
    if (login(argv[2]) != 0) {
      return 1;
    }
    return 0;
  }

  if (strcmp(method, "getdev") == 0) {
    if (argc < 3) {
      printUsage();
      return 1;  
    }
    if (getdev(argv[2]) != 0) {
      return 1;
    }
    return 0;
  }
  
  if (strcmp(method, "devown") == 0) {
    if (argc < 4) {
      printUsage();
      return 1;  
    }
    if (devown(argv[2], argv[3]) != 0) {
      return 1;
    }
    return 0;
  }
  
  if (strcmp(method, "logout") == 0) {
    if (argc < 3) {
      printUsage();
      return 1;  
    }
    if (logout(argv[2]) != 0) {
      return 1;
    }
    return 0;
  }

  printUsage();
  return 1;
}