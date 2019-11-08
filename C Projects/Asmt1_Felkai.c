#include <stdio.h>
#include <stdlib.h>

int totalInstructions, totalCycles;
float frequency;

void Enter_Parameters() {
    totalInstructions = 0;
    totalCycles = 0;
    int numInstructionClasses, i, instructions, cycles;
    printf("\nEnter the number of instruction classes: ");
    scanf("%d", &numInstructionClasses);

    printf("\nEnter the frequency of the machine (MHz): ");
    scanf("%f", &frequency);

    for(i = 1; i <= numInstructionClasses; i++) {
        printf("\nEnter CPI of class %d: ", i);
        scanf("%d", &cycles);

        printf("\nEnter instruction count of class %d (millions): ", i);
        scanf("%d", &instructions);

        totalInstructions += instructions;
        totalCycles += cycles*instructions;
    }
}

void Option2() {
    float averageCPI = (1.0*totalCycles) / totalInstructions;
    printf("\nThe average CPI of the sequence is: %.2f\n", averageCPI);
}

void Option3() {
    float totalCPUTime = (1000.0*totalCycles) / frequency;
    printf("\nThe total CPU time of the sequence is: %.2f msec\n", totalCPUTime);
}

void Option4() {
    float totalMIPS = totalInstructions / ((1.0*totalCycles) / frequency);
    printf("\nThe total MIPS of the sequence is: %.2f\n", totalMIPS);
}

int main() {
    int choice = 0;
    while(choice != 5) {
        printf("\nMenu of Options:\n--------------\n1) Enter parameters\n2) Calculate average CPI of a sequence of instructions\n3) Calculate total execution time of a sequence of instructions\n4) Calculate MIPS of a sequence of instructions\n5) Quit\n\nEnter selection: ");
        scanf("%d", &choice);
        switch (choice) {
            case 1: Enter_Parameters(); break;
            case 2: Option2(); break;
            case 3: Option3(); break;
            case 4: Option4(); break;
            case 5: break;
            default: printf("Invalid Input!!!!!!!");
        }
    }
    return 1;
}