/*
	Made as part of the 'Go: The Complete Developer's Guide' course on Udemy,
	taught by Stephen Grinder.

	Run with go run file_reader.go myfile.txt
*/

package main

import (
	"fmt"
	"io"
	"os"
)

func main() {
	filepath := os.Args[1]

	file, err := os.Open(filepath)
	if err != nil {
		fmt.Println("Error:", err)
		os.Exit(1)
	}

	io.Copy(os.Stdout, file)
}
