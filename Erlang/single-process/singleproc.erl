-module(singleproc).
-export([do/1]).
-export([f/0]).

do({output, E}) ->
    io:format("~~~p~n", [E]);
do({input, E}) ->
    io:format("~p~n", [E]);
do([]) ->
    ok;
do([E|MoreEs]) ->
    do(E),
    do(MoreEs);
do(_) ->
    io:format("Unknown argument to do/1 ~n").

f() ->
    X = 5,
    Y = X + 1,
    Z = Y + 1,
    Y + Z.