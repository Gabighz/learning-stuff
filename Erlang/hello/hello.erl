-module (hello).
-export ([say/1]).
-export ([respond/1]).
-export([broken/1]).

say (A) ->
    io:format ("Hello , ~p ~n", [A]).

respond (A) ->
    
    if 
        A == mike ->
            io:format("Hi ~p! ~n", [A]);
        A == 42 ->
            io:format("meaningoflife ~n");
        A == {mytuple, y} ->
            io:format("You gave me a tuple but I ignored half of it ~n")
    end.

broken (A) ->

    if
        A == {1, 0} ->
            io:format("1 ~n");
        A == {1, x} ->
            io:format("1 + ~p ~n", [x])
    end.