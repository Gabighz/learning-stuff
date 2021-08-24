/*
	Made as part of the 'Go: The Complete Developer's Guide' course on Udemy,
	taught by Stephen Grinder.
*/

package main

import "fmt"

type bot interface {
	// If there's any other type inside of our program that has a function
	// getGreeting() associated to it, then it will also be an honorary member
	// of the 'bot' type
	getGreeting() string
}

type englishBot struct {}
type spanishBot struct {}

func main() {
	eb := englishBot{}
	sb := spanishBot{}

	printGreeting(eb)
	printGreeting(sb)
}

func printGreeting(b bot) {
	fmt.Println(b.getGreeting())
}

func (englishBot) getGreeting() string {
	// Pretending that this has some very custom logic
	return "Hello there!"
}

func (spanishBot) getGreeting() string {
	// Pretending the same
	return "Hola!"
}

