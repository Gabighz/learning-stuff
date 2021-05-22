from socket import *

mail_from = '<example@example.com>'
rcpt_to = '<example@example.com>'

def send_command(command):
    clientSocket.send(command.encode())

    recv = clientSocket.recv(1024).decode()
    print(recv)

# Choose a mail server (e.g. Google mail server) and call it mailserver
mailserver = 'aspmx.l.google.com.'
mailserver_port = 25

# Create socket called clientSocket and establish a TCP connection with mailserver
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((mailserver, mailserver_port))

recv = clientSocket.recv(1024).decode()
print(recv)

# Send HELO command and print server response.
send_command('HELO localhost.org\r\n')

# Send MAIL FROM command and print server response.

send_command('MAIL FROM: %s\r\n' % mail_from)

# Send RCPT TO command and print server response.
send_command('RCPT TO: %s\r\n' % rcpt_to)

# Send DATA command and print server response.
send_command('DATA\r\n')

# Send message data.
message = 'From: %s\r\n' \
        'To: %s\r\n' \
        'Subject: Python script test\r\n' \
        'Hello world\r\n' \
        '.\r\n' % (mail_from, rcpt_to)

send_command(message)

# Send QUIT command and get server response.
send_command('QUIT')

clientSocket.close()