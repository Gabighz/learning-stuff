/*
	Program that calculates the next day of a given date.

	Author: Gabe
*/

#include <stdio.h>
#include <assert.h>

#define LEN_ARR(arr) (sizeof(arr) / sizeof((arr)[0]))

typedef enum month {jan=1, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec} month;
typedef struct date
{
	month m;
	int d;
} date;

date nextday(date some_date);
void printdate(date some_date);

int main(void)
{
	
	date some_dates[5] = { {jan, 1}, {feb, 28}, {mar, 14}, {oct, 31}, {dec, 31}}; 

	date correct_next_days[5] = { {jan, 2}, {mar, 1}, {mar, 15}, {nov, 1}, {jan, 1}};

	for (int i = 0; i < LEN_ARR(some_dates); i++)
	{
		date new_date = nextday(some_dates[i]);
		printdate(new_date);
		assert((new_date.m == correct_next_days[i].m) && (new_date.d == correct_next_days[i].d));
	}

	return 0;
}

date nextday(date some_date)
{
	date new_date = {1,1};

	if (some_date.m == feb)
	{
		/*
			because 28 + 1 % 29 = 0 as in the day in the next month
			and if day == 0 then day = 1 a few lines below
		*/
		new_date.d = (some_date.d + 1) % 29;
	}
	else
	{
		// same idea as above
		new_date.d = (some_date.d + 1) % 32;
	}

	if (new_date.d == 0)
	{
		new_date.d = 1;
		new_date.m = some_date.m + 1;
	}

	// as in if month "overflows", it should be assigned jan
	if (new_date.m == 13)
		new_date.m = 1;
	else
		new_date.m = some_date.m;

	return new_date;
}

void printdate(date some_date)
{
	printf("Day: %d Month: %d \n", some_date.d, some_date.m);
}
