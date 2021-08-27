/*
	Made as part of the 'Go: The Complete Developer's Guide' course on Udemy,
	taught by Stephen Grinder.
*/

package main

import (
	"fmt"
	"net/http"
	"time"
)

func main() {
	links := []string{
		"http://google.com",
		"http://facebook.com",
		"http://stackoverflow.com",
		"http://golang.org",
		"http://amazon.com",
	}

	c := make(chan string)

	for _, link := range links {
		// The 'go' keyboard tells the main go routine to create a new thread
		// i.e. child go routine to execute the code in that function.
		// Keep in mind that the go scheduler runs one thread on each logical cpu core
		// and that concurrency is not parallelism.
		go checkLink(link, c)
	}

	// Extra cool stuff - for each message from channel, assign it to link
	for link := range c {
		go func(l string) {
			time.Sleep(time.Second) // it's here so that the main routine won't have to be paused
			// Note: Sending a message over a channel is a blocking operation
			checkLink(l, c)
		}(link) // this call the function literal (i.e. lambda expression)
	}
}

func checkLink(link string, c chan string) {
	_, err := http.Get(link) // blocking call - takes quite some time
	if err != nil {
		fmt.Println(link, "might be down!")
		c <- link
		return
	}

	fmt.Println(link, "is up!")
	c <- link
}
