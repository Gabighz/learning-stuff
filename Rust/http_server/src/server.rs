use crate::http::Request;
use std::io::Read;
use std::convert::TryFrom;
use std::convert::TryInto;
use std::net::TcpListener;

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
    pub fn run(self) {
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

                            match Request::try_from(&buffer[..]) {
                                Ok(request) => {
                                    dbg!(request);
                                },
                                Err(e) => println!("Failed to parse a request, {}", e)
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