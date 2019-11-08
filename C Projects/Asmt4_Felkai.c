#include <stdlib.h>
#include <stdio.h>

int mainMemorySize, pageSize, replacementPolicy, numOfRows;

struct node {
    int virtualPage;
    int pageFrame;
} *pageTable = NULL;

typedef struct node row;

int *mm = NULL;

void Enter_Parameters() {
    mainMemorySize = 0;
    pageSize = 0;
    replacementPolicy = 0;
    printf("\nEnter main memory size (words): ");
    scanf("%d", &mainMemorySize);

    printf("\nEnter page size (words/page): ");
    scanf("%d", &pageSize);

    printf("\nEnter replacement policy (0=LRU, 1=FIFO: ");
    scanf("%d", &replacementPolicy);

    int i;
   // mm = (int *)malloc(mainMemorySize * sizeof(int));
    //for (i = 0; i < mainMemorySize; i++) {
      //  mm[i] = mainMemorySize - i;
    //}
    numOfRows = mainMemorySize / pageSize;

    pageTable = (row *)malloc(numOfRows * sizeof(row));

    for (i = 0; i < numOfRows; i++) {
        pageTable[i].virtualPage = -1;
    }
}

void Option2() { //Map virtual address
    int tag, block, word, readwrite, mm_address, value, base;

    printf("\nSelect read (0) or write (1): ");
    scanf("%d", &readwrite);

    if (readwrite == 1) {
        printf("\nEnter main memory address to write to: ");
        scanf("%d", &mm_address);

        printf("\nEnter value to write: ");
        scanf("%d", &value);

        tag = mm_address / cacheSize;
        block = (mm_address % cacheSize) / blockSize;
        word = mm_address % blockSize;
        base = (mm_address / blockSize) * blockSize;

        if (cache[block].tag == -1) {
            cache[block].block = (int *)malloc(blockSize * sizeof(int));
        }

        if (cache[block].tag != tag) {
            printf("Write miss!\n");
            cache[block].tag = tag;
            mm[mm_address] = value;
            int i;
            for (i = 0; i < blockSize; i++) {
                cache[block].block[i] = mm[base + i];
            }
        }

        else {
            printf("Write Hit!\n");
        }

        mm[mm_address] = value;
    }

    else if (readwrite == 0) {
        printf("\nEnter main memory address to read from: ");
        scanf("%d", &mm_address);

        tag = mm_address / cacheSize;
        block = (mm_address % cacheSize) / blockSize;
        word = mm_address % blockSize;
        base = (mm_address / blockSize) * blockSize;

        if (cache[block].tag == tag) {
            printf("Read hit!\n");
            value = cache[block].block[word];
        }
        else {

            if (cache[block].tag == -1) {
                cache[block].block = (int *)malloc(blockSize * sizeof(int));
            }

            printf("Read miss!\n");
            value = mm[mm_address];
            cache[block].block[word] = value;
        }
    }

    printf("-------------------------------------------------------------\n|  Tag: %d      |  Block: %d        |  Word: %d (%d)        |\n-------------------------------------------------------------\n", tag, block, word, value);
}
int main() {
    int choice = 0;
    while (choice != 3) {
        printf("\nVirtual memory to Main memory mapping:\n----------------------------\n1) Enter parameters\n2) Map virtual address\n3) Quit\n\nEnter selection: ");
        scanf("%d", &choice);
        switch (choice) {
        case 1: Enter_Parameters(); break;
        case 2: Option2(); break;
        case 3: break;
        default: printf("Invalid Input!!!!!!!");
        }
    }
    return 1;
}