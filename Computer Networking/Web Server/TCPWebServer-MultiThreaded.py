import socket

from _thread import *
import threading

print_lock = threading.Lock()


def threaded(connection_socket):
    while True:

        message = connection_socket.recv(2048)
        if not message:
            print('Bye')

            print_lock.release()
            break

        filename = message.split()[1]
        file = open(filename[1:])
        output_data = file.read()

        # Send one HTTP header line into socket
        header = 'Content-Type: text\html \r\n'
        connection_socket.send(header.encode())

        # Send the content of the requested file to the client
        connection_socket.sendall(output_data.encode())
        connection_socket.send('\r\n'.encode())

    connection_socket.close()


def main():
    host = ""

    port = 6000
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))
    print("socket binded to port", port)

    server_socket.listen(5)
    print("socket is listening")

    while True:
        connection_socket, addr = server_socket.accept()

        print_lock.acquire()
        print('Connected to :', addr[0], ':', addr[1])

        start_new_thread(threaded, (connection_socket,))
    s.close()


if __name__ == '__main__':
    main()
