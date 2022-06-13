Architecture:
- TCP Listener (e.g. reads bytes from incoming request)
- HTTP Parser (most of the code will go)
- Handler (e.g. routing logic based on http method and path)

Single-threaded