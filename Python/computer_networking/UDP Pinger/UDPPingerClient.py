# Requirements:
# The client should send 10 pings to the server. Because UDP is an unreliable protocol, a packet sent
# from the client to the server may be lost in the network, or vice versa. For this reason, the client
# cannot wait indefinitely for a reply to a ping message. You should get the client wait up to one second
# for a reply; if no reply is received within one second, your client program should assume that the
# packet was lost during transmission across the network. You will need to look up the Python
# documentation to find out how to set the timeout value on a datagram socket.
# Specifically, your client program should
# (1) send the ping message using UDP (Note: Unlike TCP, you do not need to establish a connection
# first, since UDP is a connectionless protocol.)
# (2) print the response message from server, if any
# (3) calculate and print the round trip time (RTT), in seconds, of each packet, if server responses
# (4) otherwise, print “Request timed out”

from socket import *
from datetime import datetime

MAX_PINGS = 10

serverName = 'localhost'
serverPort = 12000

clientSocket = socket(AF_INET, SOCK_DGRAM)
clientSocket.settimeout(1)

packets_received = 0
packets_lost = 0
rtt_times = []

for ping_sequence in range(MAX_PINGS):
    try:
        time_when_sent = datetime.now()
        message = 'Ping %i %s' % (ping_sequence, time_when_sent.strftime('%X'))
        clientSocket.sendto(message.encode(), (serverName, serverPort))

        data, serverAddress = clientSocket.recvfrom(2048)
        if data:
            print(data)
        print(message)
        round_trip_time = (datetime.now() - time_when_sent).total_seconds()
        print("RTT: %s seconds \n" % round_trip_time)

        packets_received += 1
        rtt_times.append(round_trip_time)

    except timeout:
        print("Request timed out. ")
        packets_lost += 1

percentage_lost = int(100 * (packets_lost / MAX_PINGS))

clientSocket.close()
print("Closing socket. \n")

print("Ping statistics for %s: \n"
      "\tPackets: Sent = %s, Received = %s, Lost = %s (%s%% Loss)" % (serverName, MAX_PINGS, packets_received,
                                                                      packets_lost, percentage_lost))

avg_rtt = sum(rtt_times) / len(rtt_times)

print("Approximate round-trip times in seconds: \n"
      "\t Maximum = %s, Minimum = %s, Average = %s" % (max(rtt_times), min(rtt_times), avg_rtt))
