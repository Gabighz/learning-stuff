-module (enigma).
-include("enigma.hrl").
-import(lists, [nth/2]).

-export([setup/5, crypt/2, kill/1]).

% Setup accepts 5 arguments: a reflector name, a triple of rotor names, a triple of ring-settings, 
% a list of plugboard pairs, and an initial setting, and returns a PID of an enigma machine;
setup(reflector_name, rotor_names, ring_settings, plugboard_pairs, initial_setting) ->
    Keyboard = spawn(?MODULE, keyboard, []),
    Reflector = spawn(?MODULE, reflector, [reflector_name]),
    RotorI = spawn(?MODULE, rotor, [nth(1, rotor_names), nth(1, ring_settings)]),
    RotorII = spawn(?MODULE, rotor, [nth(2, rotor_names), nth(2, ring_settings)]),
    RotorIII = spawn(?MODULE, rotor, [nth(3, rotor_names), nth(3, ring_settings)]),
    Plugboard = spawn(?MODULE, plugboard, plugboard_pairs),
    Enigma = spawn(?MODULE, enigma, [Keyboard, Reflector, RotorI, RotorII, RotorIII, Plugboard]).

% Crypt takes two arguments: PID of an Enigma machine (as produced by setup) and a text to encrypt
% handles all communications with the machine and returns the string of responses from the machine.
crypt(Enigma, Plaintext) ->

    Plaintext_upper = map(uppercase(Letter) - > Letter end, Plaintext),

    send_text(Plaintext_upper, Keyboard),

    receive
        {finished, Ciphertext} ->
            io:format("The encrypted version of the string is ~p ~n", [Ciphertext])
    end.

% Helper function meant to recurse through the plaintext string
% and send each letter to the Keyboard
send_text([], Keyboard) -> ok;
send_text([Letter|Plaintext], Keyboard) ->
    Keyboard ! Letter
    send_text(Plaintext, Keyboard).

% Kill accepts the PID of an Enigma machine and shuts it down, terminating all the internal processes.
kill(Enigma) ->
    exit(Enigma, kill).

% This includes my change to the original model, which includes restrictions
% To conform to the CCS model, I think the parts of the machine should've been spawned inside
% this function, but then I wasn't sure how to cleanly use the setup function.
%
% Enigma =
% | Rotor(c3,p3)[ref/l,m1/r,i3/incr]
% | Rotor(c2,p2)[m1/l,m2/r,i3/incl,i2/incr]
% | Rotor(c1,p1)[m2/l,m3/r,i2/incl,i1/incr]
% | Plugboard [m3/l, keys/r]
% | Keyboard [keys/key, keys/lamp, i1/inc]
% \{keys, ref, i1, i2, i3, m1, m2, m3}
enigma(Reflector, RotorI, RotorII, RotorIII, Plugboard) ->

    receive
        % Very much not sure if this is the right approach
        {key, Letter} ->
            Plugboard ! {r, Letter};
        i1 ->
            RotorI ! incr;
        i2 ->
            RotorII ! incr;
        i3 ->
            RotorIII ! incr;
        % The Plugboard sends the letter on its left channel
        {l, m3, Letter} ->
            % The first rotor receives it on its right channel
            RotorI ! {r, Letter}
        {l, m2, Letter} ->
            RotorII ! {r, Letter}
        {l, m1, Letter} ->
            RotorIII ! {r, Letter}
        {l, ref, Letter} ->
            Reflector ! {in, Letter}
        {out, Letter} ->
            RotorIII ! {r, Letter}

% Reflector = in(x).~out⟨f_refl(x)⟩.Reflector
reflector() ->
    receive
        Letter ->
            Enigma ! {reflector_out, f_refl(Letter)}
    end,
    reflector().

% Keyboard = ~key⟨x⟩.~inc.lamp(y).Keyboard
keyboard() ->

    receive
        Plain_letter ->
            Enigma ! {key, Plain_letter},
            Enigma ! i1,
        {lamp, Cipher_letter} ->
            io:format("The encrypted letter is ~p ~n", [Cipher_letter])
    end,

    keyboard().

% Plugboard = r(x).~l⟨f_plug(x)⟩.Plugboard + l(x).~r⟨f_plug(x)⟩.Plugboard
plugboard() ->

    receive
        {r, Letter} ->
            Enigma ! f_plug(Letter);
        {l, Letter} ->
            Enigma ! f_plug(Letter)
    end,

    plugboard().

% Rotor(26,p) = incr.~incl.Rotor(0, p − 26) + RotorFunction(p)
% Rotor(c,p) = incr.Rotor(c + 1, p + 1) + RotorFunction(p)
rotor(26, p) ->
    receive
        incr ->
            Enigma ! incl,
            rotor(0, p - 26);
        % if received the letter on right channel
        {r, p} ->
            Rotor_function ! {r, p};
        % if received the letter on left channel
        {l, p} ->
            Rotor_function ! {l, p}
    end.

rotor(c, p) ->    
    receive
        incr ->
            rotor(c + 1, p + 1);
        {r, p} ->
            Rotor_function ! {r, p};
        % if received the letter on left channel
        {l, p} ->
            Rotor_function ! {l, p}
    end.

% RotorFunction (p) = 
% l(x).~r⟨f_rotor(p, x)⟩.RotorFunction(p) 
% + r(x).~l⟨f_rotor(p, x)⟩.RotorFunction(p)
rotor_function(p) ->
    receive
        {r, p} ->
            Enigma ! {l, f_rotor(p)}
        {l, p} ->
            Enigma ! {r, f_rotor(p)}
    end,

    rotor_function(p).
        
        
