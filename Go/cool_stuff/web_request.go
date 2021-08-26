/*
	Made as part of the 'Go: The Complete Developer's Guide' course on Udemy,
	taught by Stephen Grinder.
*/

package main

import (
	"fmt"
	"io"
	"net/http"
	"os"
)

type logWriter struct{}

func main() {
	resp, err := http.Get("http://google.com")
	if err != nil {
		fmt.Println("Error:", err)
		os.exit(1)
	}

	lw := logWriter{}

	// Copy func takes types that implement the Writer and Reader interfaces
	// i.e. Copy(dst Writer, src Reader)
	io.Copy(lw, resp.Body) // or os.Stdout instead of lw
}

// A custom implementation of the Writer interface
func (logWriter) Write(bs []byte) (int, error) {
	fmt.Println(string(bs))
	fmt.Println("Just wrote this many bytes:", len(bs))

	return len(bs), nil
}