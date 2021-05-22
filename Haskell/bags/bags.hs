module Bags where

  --definition of Occurances datatype
  type Occurances = Int
  --definition of Bag datatype
  type Bag a = [(a, Occurances)]

  --listToBag
  -- Counts the occurances of an item in the list,
  -- then adds an (item, occurances) pair
  -- to a list which doesn't contain the item.
  listToBag :: Eq a => [a] -> Bag a
  listToBag [] = []
  listToBag list @ (h:t) = (h, countOccurances h list) : listToBag (removeFromList h list)

  --auxiliary function which counts occurances of a distinct value
  countOccurances :: Eq a => a -> [a] -> Occurances
  countOccurances value list = length (filter (\element -> (element == value)) list)

  --auxiliary function which removes all occurances of a value from a list
  removeFromList :: Eq a => a -> [a] -> [a]
  removeFromList _ [] = []
  removeFromList value (h:t)
    | value == h = removeFromList value t
    | otherwise = (h:removeFromList value t)

  --bagEqual
  bagEqual :: Eq a => Bag a -> Bag a -> Bool
  bagEqual bag1 bag2 = bag1 == bag2

  --bagInsert
  -- If an item isn't in the bag, it will be inserted with an Occurances of 1.
  -- Otherwise, the Occurances of the item in the bag is incremented.
  bagInsert :: Eq a => a -> Bag a -> Bag a
  bagInsert value [] = [(value, 1)]
  bagInsert value bag @ (h @ (element, counter):t)
    | value == fst h = (fst h, counter + 1) : removeFromBag h bag
    | otherwise = (h:bagInsert value t)

  --auxiliary function which removes all occurances of a bag item
  removeFromBag :: Eq a => (a, Occurances) -> Bag a -> Bag a
  removeFromBag _ [] = []
  removeFromBag bag (h:t)
    | bag == h = removeFromBag bag t
    | otherwise = (h:removeFromBag bag t)

  --bagSum
  -- If an item from the first bag isn't in the second bag, it is inserted.
  -- Otherwise, the counters of the identical items are added up.
  bagSum :: Eq a => Bag a -> Bag a -> Bag a
  bagSum [] [] = []
  bagSum _ [] = []
  bagSum [] _ = []
  bagSum bag1@(h1  @ (element1, counter1) :t1) bag2@(h2 @ (element2, counter2) : t2)
    | element2 /= element1 = h1 : bag2
    | otherwise = (element2, counter1 + counter2) : bagSum t1 (removeFromBag h2 bag2)

  --bagIntersection
  -- Returns the intersection of items between two bags.
  -- The smallest Occurances of each such item is kept.
  bagIntersection :: Eq a => Bag a -> Bag a -> Bag a
  bagIntersection [] _ = []
  bagIntersection _ [] = []
  bagIntersection bag1@(h1  @ (element1, counter1) : t1) bag2@(h2 @ (element2, counter2) : t2)
    | memberBag element1 bag2 = ((element1, minimumBag h1 bag2) : bagIntersection t1 bag2)
    | otherwise = bagIntersection t1 bag2

  --auxiliary function which determines if a bag exists with the value
  memberBag :: Eq a => a -> Bag a -> Bool
  memberBag value [] = False
  memberBag value (h:t)
    | value==fst h = True
    | otherwise = memberBag value t

  --auxiliary function which determines the smallest Occurances of equal items of a bag
  minimumBag :: Eq a => (a, Occurances) -> Bag a -> Int
  minimumBag value @ (element1, counter1) bag@(h  @ (element2, counter2) : t)
    | element1 == element2 = min counter1 counter2
    | otherwise = minimumBag value t
