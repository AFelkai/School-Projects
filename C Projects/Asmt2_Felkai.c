#include <stdlib.h>
#include <stdio.h>

int mainMemorySize, cacheSize, blockSize;

struct node {
    int tag;
    int *block;
} *cache = NULL;

typedef struct node line;

int *mm = NULL;

void Enter_Parameters() {
    mainMemorySize = 0;
    cacheSize = 0;
    blockSize = 0;
    printf("\nEnter main memory size (words): ");
    scanf("%d", &mainMemorySize);

    printf("\nEnter cache size (words): ");
    scanf("%d", &cacheSize);

    printf("\nEnter block size (words/block): ");
    scanf("%d", &blockSize);

    int i;
    mm = (int *)malloc(mainMemorySize * sizeof(int));
    for (i = 0; i < mainMemorySize; i++) {
        mm[i] = mainMemorySize - i;
    }
    int numLines = cacheSize / blockSize;

    cache = (line *)malloc(numLines * sizeof(line));

    for (i = 0; i < numLines - 1; i++) {
        cache[i].tag = -1;
    }
}

void Option2() { //Access cache for reading/writing data
    int tag, block, word, readwrite, mm_address, value, base;

    printf("\nSelect read (0) or write (1): ");
    scanf("%d", &readwrite);

	if(readwrite == 1) {
		printf("\nEnter main memory address to write to: ");
		scanf("%d", &mm_address);

		printf("\nEnter value to write: ");
		scanf("%d", value);

		tag = mm_address / cacheSize;
		block = (mm_address % cacheSize) / blockSize;
		word = mm_address % blockSize;
		base = (mm_address / blockSize) * blockSize;

		if(cache[block].tag == -1) {
			cache[block] = (int *)malloc(blockSize * sizeof(int));
		}

		if(cache[block].tag != tag) {
			printf("Write miss!");
			cache[block].tag = tag;

			int i;
			for(i = 0; i < blockSize - 1; i++) {
				cache[block].word
			}
		}
	}
}
int main() {
    int choice = 0;
    while (choice != 5) {
        printf("\nCache memory allocation and mapping:\n----------------------------\n1) Enter parameters\n2) Access cache for reading/writing and transfer data\n3) Quit\n\nEnter selection: ");
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