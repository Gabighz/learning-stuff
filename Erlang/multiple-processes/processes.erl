-module(processes).
-export([do/1]).
-export([p1/1]).
-export([p2/1]).
-export([p3/1]).
-export([p4/1]).
-export([p5/1]).
-export([q5/0]).

nil() -> 
    ok.

do({output, E}) ->
    io:format("~~~p~n", [E]);
do({input, E}) ->
    do({output, E});
do([]) ->
    nil();
do([E|MoreEs]) ->
    do(E),
    do(MoreEs);
do(_) ->
    io:format("Unknown argument to do/1 ~n").

p1({a,b}) ->
    do({output, a}),
    do({output, b}),
    nil().

p2(a) ->
    do({output, b}),
    p2(a).

p3(E) ->
    % Pretty much a match statement
    % p3(E).([E = a]nil + [E = b]nil)
    if
        E == a ->
            nil();
        E == b ->
            nil();
        true ->
            io:format("Unknown argument to p3/1 ~n")
    end.

p4(E) ->
    if
        E == a ->
            p4(a);
        E == b ->
            nil();
        true ->
            io:format("Unknown argument to p4/1 ~n")
    end.

p5(E) ->
    if 
        E == a ->
            q5();
        E == {output, b} ->
            do({output, b});
        true ->
            io:format("Unknown argument to p5/1 ~n")
    end.

q5() ->
    p5({output, b}).
