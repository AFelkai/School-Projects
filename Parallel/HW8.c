#include <stdio.h>
#include <stdlib.h>

int* mahFunc(int* input[])
{
    int i, j;
    int size = sizeof(input)/sizeof(int);
    int *array = malloc(size * sizeof(int));

    for(i = 0; i < size; i++)
    {
        for(j = i; j < size; i++)
        {
            array[i] += array[j];
        }
    }
    for(i = 0; i < size; i++)
    {
        printf("%d ", array[i]);
    }
}

void main(int argc, char* argv[])
{
    int x[] = {1, 2, 3};
    mahFunc(x);
    exit(1);
}