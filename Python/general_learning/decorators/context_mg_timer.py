from contextlib import contextmanager
from time import perf_counter, sleep

@contextmanager
def timer():
    stats = dict()
    start = perf_counter()
    stats['start'] = start

    try:
        yield stats
    finally:
        end = perf_counter()
        stats['end'] = end
        stats['elapsed'] = end - start

with timer() as stats:
    sleep(2)

print(stats)