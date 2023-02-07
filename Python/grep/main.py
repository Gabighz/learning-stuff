#!usr/bin/python
import argparse, os, requests
from collections.abc import Generator

def _file_reader(filepath: str) -> Generator[str]:
    with open(filepath, mode='r') as f:
        try:
            yield from f.readlines()
        except UnicodeDecodeError:
            # Choosing to silence the exception given that, for example,
            # we might have files such as .pyc in the search path
            pass

def _outputter(searched_word: str, line_num: int, raw_line: str, path: str) -> None:
    if is_case_insensitive:
        line = raw_line.lower()
    else:
        line = raw_line

    if searched_word in line if not is_reverse_search else searched_word not in line:
        print(f'{path}: line {line_num}: {raw_line}\n')

def filesystem_search(searched_word: str, path: str) -> None:
    if not path:
        return

    try:
        for line_num, raw_line in enumerate(_file_reader(path), 1):
            _outputter(searched_word, line_num, raw_line, path)

    except IsADirectoryError:
        for root, subdirs, files in os.walk(path):
            for file in files:
                filesystem_search(searched_word, os.path.join(root, file))
            
            for subdir in subdirs:
                filesystem_search(searched_word, os.path.join(root, subdir, file))

    except FileNotFoundError:
        # Choosing to silence the exception given the recursive nature of this function
        pass

def url_search(searched_word: str, path: str) -> None:
    try:
        req = requests.get(path)
        if req.status_code != 200:
            print(f'Status code returned: {req.status_code} !')
            return

        for line_num, line in enumerate(req.text.split('\n'), 1):
            _outputter(searched_word, line_num, line, path)

    except requests.exceptions.MissingSchema:
        print('Invalid URL!')

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-v", help = "Perform reverse search", action = 'store_true')
    parser.add_argument("-i", help = "Perform case-insensitive search", action = 'store_true')
    parser.add_argument('searched_word', help = 'The word to search for')
    parser.add_argument('--files_to_search', help = 'Relative path to the file(s) or folders that we want to search in', nargs = '+', default = [])
    parser.add_argument('--urls_to_search', help = 'The URL(s) from which we want to get our strings to search in', nargs = '+', default = [])
    args = parser.parse_args()

    # Essentially renaming the flags for better code readibility
    is_reverse_search = args.v
    is_case_insensitive = args.i

    for file_to_search in args.files_to_search:
        filesystem_search(args.searched_word, file_to_search)

    for url_to_search in args.urls_to_search:
        url_search(args.searched_word, url_to_search)