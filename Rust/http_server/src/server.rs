use crate::http::{Request, Response, StatusCode, ParseError};
use std::io::{Read};
use std::convert::TryFrom;
use std::net::TcpListener;

pub trait Handler {
    fn handle_request(&mut self, request: &Request) -> Response;

    fn handle_bad_request(&mut self, e: &ParseError) -> Response {
        println!("Failed to parse request: {}", e);
        Response::new(StatusCode::BadRequest, None)
    }
}

pub struct Server {
    addr: String,
}

impl Server {
    // Associated function (in this case constructor) to struct Server
    // it's static, independent of an instance, so doesn't need 'self' keyword
    pub fn new(addr: String) -> Self {
        Self { addr }
    }

    // Note: takes ownership of the instance, borrow with &self (reference, ie pointer)
    pub fn run(self, mut handler: impl Handler) {
        println!("Listening on {}", self.addr);

        // Leave it as an unrecoverable error
        // in contrast with the recoverable error in the loop
        let listener = TcpListener::bind(&self.addr).unwrap();

        loop {
            match listener.accept() {
                Ok((mut stream, _)) => {
                    let mut buffer = [0; 1024];
                    match stream.read(&mut buffer) {
                        Ok(_) => {
                            println!("Received a request: {}", String::from_utf8_lossy(&buffer));

                            let response = match Request::try_from(&buffer[..]) {
                                Ok(request) => handler.handle_request(&request),
                                Err(e) => handler.handle_bad_request(&e) 
                            };

                            if let Err(e) = response.send(&mut stream) {
                                println!("Failed to send response: {}", e)
                            }
                        },
                        Err(e) => println!("Failed to read from connection: {}", e),
                    }
                }
                Err(e) => println!("Failed to establish a connection: {}", e),
            }
        }
    }
}