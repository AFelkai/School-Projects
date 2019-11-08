//Aaron Felkai
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>


void main(int argc, char* argv[])
{
    pid_t cpid, mypid;
    int i;

    for(i = 1; i < argc; i++)
    {
        cpid = fork();

        if(cpid == 0)
        {
            printf("\tFilename: %s\tPID: %d\n", argv[i], getpid());
            sleep(1);
            _exit(0);
        }
        else if(cpid < 0)
        {
            perror("Fork failed!");
            exit(1);
        }
    }
    for(i = 1; i < argc; i++)
    {
        wait(NULL);
    }
    system("ps -H");
    exit(0);
}