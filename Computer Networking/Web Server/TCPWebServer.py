# Requirements:
# You will develop a web server that handles one HTTP request at a time. Your web server should accept
# and parse the HTTP request, get the requested file from the server’s file system, create an HTTP response
# message consisting of the requested file preceded by header lines, and then send the response directly to
# the client. If the requested file is not present in the server, the server should send an HTTP “404 Not
# Found” message back to the client.

# import socket module
from socket import *
import sys


serverName = 'localhost'
serverPort = 6000

serverSocket = socket(AF_INET, SOCK_STREAM)

# Prepare a server socket
serverSocket.bind((serverName, serverPort))
serverSocket.listen()

while True:
    # Establish the connection
    print('Ready to serve...')
    connectionSocket, address = serverSocket.accept()

    try:
        message = connectionSocket.recv(2048).decode()
        filename = message.split()[1]
        file = open(filename[1:])
        output_data = file.read()

        # Send one HTTP header line into socket
        header = 'Content-Type: text\html \r\n'
        connectionSocket.send(header.encode())

        # Send the content of the requested file to the client
        connectionSocket.sendall(output_data.encode())
        connectionSocket.send('\r\n'.encode())

        connectionSocket.close()

    except IOError:
        # Send response message for file not found
        connectionSocket.send('HTTP/1.1 404 Not found'.encode())

        # Close client socket
        connectionSocket.close()

serverSocket.close()

sys.exit()  # Terminate the program after sending the corresponding data
