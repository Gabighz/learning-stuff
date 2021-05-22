from socket import *

serverPort = 12000

serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', serverPort))
serverSocket.listen()
print('The server is ready to receive')

while True:
    connectionSocket, address = serverSocket.accept()

    message = connectionSocket.recv(2048).decode()
    capitalizedMessage = message.upper()

    connectionSocket.send(capitalizedMessage.encode())
    connectionSocket.close()
