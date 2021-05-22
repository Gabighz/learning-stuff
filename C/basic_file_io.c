/*
    The purpose of this program is to receive some numbers as command-line arguments,
    perform some calculations, and output the result to STDOUT and a file.

*/

#include <stdio.h>
#include <stdlib.h>

double average(int data[], int array_size);

int main(int argc, char* argv[])
{
    int array_size = atoi(argv[1]);

    int data[array_size];
    int j = 0;

    // because argv[0] is the command (e.g. ./a.out) and argv[1] is the array_size
    for (int i = 2; i < argc; i++)
    {
        data[j] = atoi(argv[i]);
        printf("%d ", data[j]);
    }

    int sum = 0;
    int max = 0;

    for (int i = 0; i < array_size; i++)
    {
        sum += data[i];

        if (data[i] > max)
            max = data[i];
    }

    double average = sum / array_size;

    printf("Maximum is: %d", max);
    printf("Average is: %f", avg);


}