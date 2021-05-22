-module(spawning).
-export([do/1]).
-export([p1/0]).
-export([p2/0]).


do ({output, E}) ->
    io : format ( " ~~~p ~n " ,[E]);
do ({input , E}) ->
    io : format ( " ~p ~n " ,[E]);
do ([ ]) ->
    ok;
do ([E | MoreEs ]) ->
    do ( E ) ,
    do ( MoreEs ).

nil () ->
    ok.

% P1 = a.b.a.b.0
p1 () ->
    do ({input, a}) ,
    do ({input, b}) ,
    do ({output, a}) ,
    do ({output, b}) ,
    nil().

% P2 = a.b.P2
p2 () ->
    do ({input, a}) ,
    do ({output, b}) ,
    p2().