-module(farm).

-export([farm/0,submit/2,report/0]).
-export([worker/0,farmer/2]).

% Worker = assign.~complete.Worker
worker() ->
    receive
	{assign,Job,From} ->	    
	    From ! {complete,Job,self()},
	    worker()
    end.

% where J is number of jobs in queue and N is number of available workers,
% Farmer(J, N) = (submit.Farmer(J + 1,N)) + (complete.Farmer(J, N+1)) + 
% (~assign.Farmer(J-1, N-1))
farmer([],Workers) ->
    receive
	{submit,Job} ->
	    farmer([Job],Workers);
	{complete,{J,Sender},W} ->
	    Sender ! {report,J},
	    farmer([],[W | Workers])
    end;

farmer(Jobs,[]) ->
    receive
	{submit,Job} ->
	    farmer([Job|Jobs],[]);
	{complete,{J,Sender},W} ->
	    Sender ! {report,J},
	    farmer(Jobs,[W])
    end;

farmer([J|Jobs],[W|Workers]) ->
    W ! {assign,J,self()},
    farmer(Jobs,Workers).

% Farm = (Farmer(0, 2)|Worker|Worker)\{assign, complete}
farm() ->
    W1 = spawn(?MODULE,worker,[]),
    W2 = spawn(?MODULE,worker,[]),
    spawn(?MODULE,farmer,[[],[W1,W2]]).

%% Jobs need to be paired with our PID so we can get the report back again
%% This will just bind to the Job variable in most places, but the
%% complete event in farmer needs to know its a pair and fish out the return
%% address.
submit(Farmer,Job) ->
    Farmer ! {submit,{Job,self()}},
    ok.

report() ->
    receive
	{report,Job} ->
	    Job
    after 1000 ->
	    no_report
    end.
