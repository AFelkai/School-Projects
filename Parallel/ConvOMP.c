#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#define N       1000000

double * convolution(double *f, double *g, int sf, int sg)
{
	int sc = sf + sg - 1;
	int i, j;
	double * c = (double*)malloc(sc * sizeof(double));
	#pragma omp parallel for
	for (i = 0; i < sc; i += 1)
	{
		c[i] = 0.0;
	}
	#pragma omp parallel for
	for (i = 0; i < sf; i += 1)
	{
		#pragma omp parallel for
		for (j = 0; j < sg; j += 1)
		{
			#pragma omp atomic
			c[i + j] += f[i] * g[j];
		}
	}
	return c;
}



void main(int argc, char *argv[])
{
	time_t t;
	int m = 10;
	int n = 20;
	int r;
	int i;

	double x[10];
	double h[20];
	double ctime1, ctime2;

	for (i = 0; i < m; i += 1)
	{
		srand(i + 1);
		r = rand() % 100;
		x[i] = (double)r;
	}

	for (i = 0; i < n; i += 1)
	{
		srand(i + 1);
		r = rand() % 100;
		h[i] = (double)r;
	}

	ctime1 = omp_get_wtime();
	double *y = convolution(x, h, m, n);
	ctime2 = omp_get_wtime();

	printf("Took %f\n", ctime2 - ctime1);
}
