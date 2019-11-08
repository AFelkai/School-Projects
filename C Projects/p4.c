//Aaron Felkai
#include <stdio.h>
#include <time.h>

void randomwait();
void barber_run();
void customer_run();

int seats[2];
int customers[2];
int barber[2];
int freeseats[2];

void V(int pd[]) {
	int a=1;
	write(pd[1],&a,sizeof(int));
}

void P(int pd[]) {
	int a;
	read(pd[0],&a,sizeof(int));
}

void main() {
	pipe(seats); // lock to modify freeseats
	pipe(customers); // how many customers are waiting for the barber
	pipe(barber); // barber ready to cut?  0=no 1=yes	
	
	pipe(freeseats); // store the integer value for number of seats
	
	V(seats); // release the lock on seats by setting to 1
	
	int num = 3; // Free Seats = 3
	write(freeseats[1],&num,sizeof(int));
	
	if (fork() == 0) {  // Start 1 barber
		srand(time(0)+11); // Randomized seed
		barber_run();
		return;
	}
	int i;
	for (i=1;i<=10;i++) { // Start 10 customers randomly
		if (fork() == 0) {
			randomwait(i); // random wait before next customer arrives
			srand(time(0)+ i); // different seed than barber for different delays
			customer_run();
			return;
		}
	}
}

void barber_run() {
	int num,i;
	int seatsAvailable;
	for (i=1;i<=10;++i) { // at most 10 customers will get their hair cut
		printf("i is %d\n", i);
		P(customers);
		printf("got lock on customers, i is %d\n", i);
		P(seats);
		printf("Barber %d is trying to get a customer\n",i);

		read(freeseats[0], &seatsAvailable, sizeof(int));
		seatsAvailable = seatsAvailable + 1;
		write(freeseats[1], &seatsAvailable, sizeof(int));
		V(barber);
		V(seats);

		printf("Barber is now cutting hair %d\n",i);
		randomwait(1); // random wait before finishing haircut

	}
}

void customer_run() {
	int seatsAvailable;
	printf("- New customer trying to find a seat\n");
	P(seats);
	read(freeseats[0], &seatsAvailable, sizeof(int));
	if(seatsAvailable > 0)
	{
		seatsAvailable = seatsAvailable - 1;
		printf("- Customer is decreasing the number of free seats to %d\n", seatsAvailable);
		write(freeseats[1], &seatsAvailable, sizeof(int));
		V(customers);
		V(seats);
		printf("- Customer is now waiting for the barber\n");
		P(barber);
	}
	else
	{
		write(freeseats[1], &seatsAvailable, sizeof(int));
		V(seats);
		printf("* Customer giving up: No free chairs in waiting room\n");
	}
}

void randomwait(int d) { // random 0 to d sec delay
	int delay;
	struct timespec tim, tim2;
    tim.tv_sec = 0;
	delay = abs(rand() % 1000000000) * d;
	tim.tv_nsec = delay;
	nanosleep(&tim,&tim2);
}