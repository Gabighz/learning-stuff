import pytest
from main import Lightshows, Upgraded_Lightshows

def test_lower():
    lightshow_event = Lightshows()
    lightshow_event.from_instruction('turn on 0,0 through 1,1')
    assert(lightshow_event.lights_on() == 4)

def test_upper():
    lightshow_event = Lightshows()
    lightshow_event.from_instruction('turn on 0,0 through 999,999')
    assert(lightshow_event.lights_on() == 1000000)

def test_one():
    lightshow_event = Lightshows()
    lightshow_event.from_instruction('turn on 0,0 through 0,0')
    assert(lightshow_event.lights_on() == 1)

def test_set_of_instructions_files():
    lightshow_event = Lightshows()
    lightshow_event.from_file('test_input.txt')
    assert(lightshow_event.lights_on() == 998004)

    upgraded_lightshow_event = Upgraded_Lightshows()
    upgraded_lightshow_event.from_file('test_input.txt')
    assert(upgraded_lightshow_event.lights_on() == 1003996)

def test_set_of_instructions_directly():
    lightshow_event = Lightshows()
    lightshow_event.from_instruction('turn on 0,0 through 999,999')
    assert(lightshow_event.lights_on() == 1000000)
    lightshow_event.from_instruction('turn off 499,499 through 500,500')
    assert(lightshow_event.lights_on() == 999996)
    lightshow_event.from_instruction('toggle 0,499 through 999,500')
    assert(lightshow_event.lights_on() == 998004)

    upgraded_lightshow_event = Upgraded_Lightshows()
    upgraded_lightshow_event.from_instruction('turn on 0,0 through 999,999')
    assert(upgraded_lightshow_event.lights_on() == 1000000)
    upgraded_lightshow_event.from_instruction('turn off 499,499 through 500,500')
    assert(upgraded_lightshow_event.lights_on() == 999996)
    upgraded_lightshow_event.from_instruction('toggle 0,499 through 999,500')
    assert(upgraded_lightshow_event.lights_on() == 1003996)

def test_empty_instr():
    lightshow_event = Lightshows()

    with pytest.raises(ValueError):
        lightshow_event._process_format([])

def test_invalid_instr():
    lightshow_event = Lightshows()

    with pytest.raises(ValueError):
        lightshow_event.from_instruction("don't turn off 0,0 through 0,0")

    with pytest.raises(ValueError):
        lightshow_event.from_instruction("asd")

def test_big_set():
    lightshow_event = Lightshows()
    lightshow_event.from_file('coding_challenge_input.txt')
    assert(lightshow_event.lights_on() == 385705)

    upgraded_lightshow_event = Upgraded_Lightshows()
    upgraded_lightshow_event.from_file('coding_challenge_input.txt')
    assert(upgraded_lightshow_event.lights_on() == 1716513)