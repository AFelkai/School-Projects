//Aaron Felkai
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>

void writeStuff(char* name)
{
    struct stat fileInfo;

    stat(name, &fileInfo);

    printf("Filename: %s\n", name);

    if(getuid() == fileInfo.st_uid)
    {
        printf("You have User Permissions: ");

        if(fileInfo.st_mode & 400) printf("Read ");
        if(fileInfo.st_mode & 200) printf("Write ");
        if(fileInfo.st_mode & 100) printf("Execute");
    }
    else if(getgid() == fileInfo.st_gid)
    {
        printf("You have Group Permissions: ");

        if(fileInfo.st_mode & 40) printf("Read ");
        if(fileInfo.st_mode & 20) printf("Write ");
        if(fileInfo.st_mode & 10) printf("Execute");
    }
    else
    {
        printf("You have General Permissions: ");

        if(fileInfo.st_mode & 4) printf("Read ");
        if(fileInfo.st_mode & 2) printf("Write ");
        if(fileInfo.st_mode & 1) printf("Execute");
    }
    printf("\n\n");
}

void main(int argc, char* argv[])
{
    if(argc == 1)
    {
        DIR *dp;
        struct dirent *entry;

        dp = opendir(".");

        while((entry = readdir(dp)) != NULL)
        {
            writeStuff(entry->d_name);
        }
        closedir(dp);
    }
    else
    {
        int i;
        pid_t cpid;

        for(i = 1; i < argc; i++)
        {
            cpid = fork();

            if(cpid == 0) //child process
            {
                writeStuff(argv[i]);
                _exit(0);
            }
            else if(cpid < 0) //failure
            {
                perror("Fork failed!");
                exit(1);
            }
        }
        for(i = 1; i < argc; i++)
        {
            wait(NULL);
        }
    }
    exit(0);
}