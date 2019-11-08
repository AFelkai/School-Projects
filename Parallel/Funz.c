#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define N       1000000

int main(int argc, char *argv[])
{
	int nthreads, tid, tstart, tend, i, count;
	float a[N], sum, psum;
	double ctime1, ctime2;


	for (i = 0; i < N; i += 1)
		a[i] = i * 1.0;

	  sum = 0;
	  ctime1 = omp_get_wtime();
#pragma omp parallel for private(i)   

	for (i = 0; i < N; i += 1)
#pragma omp critical
		sum += a[i];

	ctime2 = omp_get_wtime();
	printf("Critical for took: %f\n", ctime2 - ctime1);


	sum = 0;
	ctime1 = omp_get_wtime();
#pragma omp parallel for private(i)   

	for (i = 0; i < N; i += 1)
#pragma omp atomic
		sum += a[i];

	ctime2 = omp_get_wtime();
	printf("Atomic for took: %f\n", ctime2 - ctime1);

	sum = 0;
	ctime1 = omp_get_wtime();
#pragma omp parallel shared(a,nthreads,sum) private(i,tid,psum, tstart, tend)
	{
		tid = omp_get_thread_num();
		nthreads = omp_get_num_threads();

		psum = 0;
		count = 0;
		tstart = tid * (int)ceil(((double)N / nthreads));
		tend = (tid + 1) * (int)ceil(((double)N / nthreads));
		if (tend < N)
		{
			for (i = tstart; i < tend; i += 1)
				psum += a[i];
		}
		else
		{
			for (i = tstart; i < N; i += 1)
				psum += a[i];
		}

		//printf("The partial sum is: %f in thread %d \n", psum, tid); 
#pragma omp critical
		sum += psum;
	}
	ctime2 = omp_get_wtime();
	printf("Sloppyjoe took: %f\n", ctime2 - ctime1);

	sum = 0;
	ctime1 = omp_get_wtime();
#pragma omp parallel for private(i) reduction(+:sum)  

	for (i = 0; i < N; i += 1)
		sum += a[i];

	ctime2 = omp_get_wtime();
	printf("reduction took: %f\n", ctime2 - ctime1);

	sum = 0;
	int chunk = (int)((double)N / nthreads);
	ctime1 = omp_get_wtime();
#pragma omp parallel for simd schedule(static,chunk) reduction(+:sum)
	for (i = 0; i < N; i++) {
		sum += a[i];
	}
	ctime2 = omp_get_wtime();
	printf("Vectroized reduction took: %f\n", ctime2 - ctime1);



}