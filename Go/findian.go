/*

Write a program which prompts the user to enter a string. 
The program searches through the entered string for the characters ‘i’, ‘a’, and ‘n’. 
The program should print “Found!” if the entered string starts with the character ‘i’, 
ends with the character ‘n’, and contains the character ‘a’. The program should print “Not Found!” 
otherwise. The program should not be case-sensitive, 
so it does not matter if the characters are upper-case or lower-case.

*/

package main

import (
	"fmt"
	"strings"
)

func main() {
	var userInput string

	fmt.Println("Enter a string: ")
	fmt.Scan(&userInput)

	userInput = strings.ToLower(userInput)

	if (userInput[0] != 'i' || userInput[len(userInput) - 1] != 'n')  {
		fmt.Println("Not Found!")
	} else {

		for _, rune := range userInput {

			if rune == 'a' {
				fmt.Println("Found!")
				break
			}
		}
	}


}