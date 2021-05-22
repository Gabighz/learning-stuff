-module (pingpong).
-export ([start/0, ping/2, pong/0]).

% Ping(0) = ~finished.0
ping(0, Pong_PID) ->
    Pong_PID!finished,
    io:format("Ping finished ~n", []);

% Ping(N) = ~ping.pong.Ping(N-1)
ping(N , Pong_PID ) ->
    Pong_PID!{ping, self()},
    receive
        pong ->
            io:format("Ping received pong ~n", [])
    end,
    ping (N-1, Pong_PID).

% Pong = (finished.0) + (ping.~pong.Pong)
pong() ->
    receive
        finished ->
            io:format("Pong finished ~n", []);
        {ping, Ping_PID } ->
            io:format("Pong received ping ~n", []),
            Ping_PID!pong,
            pong()
    end.

% Start = (Pong|Ping(3))\{ping, pong, finished}
start() ->
    Pong_PID = spawn(pingpong, pong, []) ,
    spawn(pingpong, ping, [3, Pong_PID]).