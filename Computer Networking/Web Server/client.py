# HTTP client to test the server

from socket import *
import argparse

parser = argparse.ArgumentParser(description='Send a HTTP request')
parser.add_argument('server name', metavar='serverName', type=str,
                    help='The name of the host')
parser.add_argument('port', metavar='portNumber', type=int,
                    help='The port that the application uses')
parser.add_argument('file', metavar='fileName', type=str,
                    help='The file to be requested')

args = vars(parser.parse_args())

serverName, serverPort, filename = args['server name'], args['port'], args['file']

clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName, serverPort))

clientSocket.send(('GET /%s HTTP/1.1\r\n' % filename).encode())

reply = clientSocket.recv(2048)
print('From Server: ', reply.decode())

clientSocket.close()
