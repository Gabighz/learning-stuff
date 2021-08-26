/*
	Made as part of the 'Go: The Complete Developer's Guide' course on Udemy,
	taught by Stephen Grinder.
*/

package main

import "fmt"

type contactInfo struct {
	email string
	zipCode int
}

type person struct {
	firstName string
	lastName string
	contact contactInfo
}

func main() {
	jim := person{
		firstName: "Jim", 
		lastName: "Anderson",
		contact: contactInfo{
			email: "jim@gmail.com",
			zipCode: 94000,
		},
	}

	// This is a shortcut. Alternatively, jimPointer := &val then updateName
	jim.updateName("jimmy")
	jim.print()
}

// Go is a pass-by-value language, so updating structs in functions won't work without pointers
// same applies for value types, except reference types.
func (pointerToPerson *person) updateName(newFirstName string) {
	// *person is a type description. Otherwise, * and & are operators
	(*pointerToPerson).firstName = newFirstName
}

func (p person) print() {
	fmt.Printf("%+v", p)
}