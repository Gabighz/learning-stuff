from socket import *

serverName = 'localhost'
serverPort = 12000

clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName, serverPort))

message = input('Input message: ')
clientSocket.send(message.encode())

modifiedMessage = clientSocket.recv(2048)
print('From Server: ', modifiedMessage.decode())

clientSocket.close()
