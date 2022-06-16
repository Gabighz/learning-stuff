Architecture:
- TCP Listener (i.e. reads bytes from incoming request)
- HTTP Parser (i.e. interprets requests; most of the code is here)
- Handler (i.e. routing logic based on http method and path)

Single-threaded

Done as part of [Learn Rust by Building Real Applications](https://www.udemy.com/course/rust-fundamentals/)

Will be continued with:
- Handling headers
- Making it multi-threaded (see std::thread, std::sync and tokio.rs)