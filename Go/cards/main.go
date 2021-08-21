/*
	Made as part of the 'Go: The Complete Developer's Guide' course on Udemy,
	taught by Stephen Grinder.
*/

package main

func main() {
	cards := newDeck()
	cards.shuffle()
	cards.print()
}