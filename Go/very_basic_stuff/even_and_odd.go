package main

import "fmt"

func main() {

	numbers := make([]int, 11)

	for i := range numbers {
		numbers[i] = i
	}

	for _, num := range numbers {
		if num % 2 == 0 {
			fmt.Printf("%v is even\n", num)
		} else {
			fmt.Printf("%v is odd\n", num)
		}
	}

}