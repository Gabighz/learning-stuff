module Solitaire where
  import System.Random
  import Data.List
  import Data.List.Split
  import Debug.Trace

  -- definition of the Suit datatype
  data Suit = Diamonds | Clubs | Hearts | Spades
              deriving (Eq, Show)

  -- definition of the Pip datatype
  data Pip = Ace | Two | Three | Four | Five | Six | Seven | Eight | Nine | Ten | Jack | Queen | King
            deriving (Eq, Show, Enum)

  -- definition of the Card datatype
  type Card = (Suit, Pip)

  -- definition of the Deck datatype
  -- It is a list of the 52 cards.
  type Deck = [Card]

  -- definition of the Foundations datatype
  -- It can contain 4 piles of cards.
  -- The game starts with empty Foundations.
  type Foundations = [[Card]]

  -- definition of the Foundations datatype
  -- it has 8 columns, initially of 6 cards each.
  type Columns = [[Card]]

  -- definition of the Reserve datatype
  -- It has 8 cells in which individual cards can be placed
  -- The game starts with 4 filled cells.
  type Reserve = [Card]

  -- definition of the EOBoard datatype
  type EOBoard = (Foundations, Columns, Reserve)

  -- Returns a list of all the cards
  pack :: Deck
  pack = [(suit, pip) | suit <- [Diamonds, Clubs, Hearts, Spades],
                        pip <- [Ace ..]]

  -- Takes a card and returns the successor card
  sCard :: Card -> Card
  sCard (suit, pip) = (suit, succ pip)

  -- Takes a card and returns the predecessor card
  pCard :: Card -> Card
  pCard (suit, pip) = (suit, pred pip)

  -- Returns true if a given card is an Ace
  isAce :: Card -> Bool
  isAce (suit, pip) = pip == Ace

  -- Returns true if a given card is a King
  isKing :: Card -> Bool
  isKing (suit, pip) = pip == King

  -- Returns a shuffled Deck
  shuffle :: Deck
  randomNumbers = take 52 (randoms (mkStdGen 1) :: [Int])
  zippedList = zip pack randomNumbers
  sortedList = sortBy (\ (_, firstNumber) (_, secondNumber) -> compare firstNumber secondNumber) zippedList
  shuffle = map fst sortedList

  -- Returns a shuffled EOBoard
  eODeal :: EOBoard
  reserve = fst (splitAt 4 shuffle)
  columns = chunksOf 6 (snd (splitAt 4 shuffle))
  eODeal = ([], columns, reserve)

  -- Takes an EOBoard and makes all possible moves to the Foundations
  toFoundations :: EOBoard -> EOBoard
  toFoundations board = toFoundationsA (acesFromColumns (acesFromReserve board))

  -- Takes an EOBoard with all the reserve aces already moved to Foundations and moves everything else to Foundations
  toFoundationsA :: EOBoard -> EOBoard
  toFoundationsA board
   | board /= columnsToFoundations board = toFoundationsA (columnsToFoundations board)
   | board /= reserveToFoundations board = toFoundationsA (reserveToFoundations board)
   | otherwise = board

  -- Takes an EOBoard and moves all Aces from Reserve to Foundations
  acesFromReserve :: EOBoard -> EOBoard
  acesFromReserve (foundations, columns, reserve) = (chunksOf 1 (filter (\card -> isAce card == True) (reserve ++ flatFoundations)),
                                                     columns,
                                                     (filter (\card -> isAce card == False) reserve))
                                                     where flatFoundations = concat foundations

  -- Takes an EOBoard and moves all Aces from Columns to Foundations
  acesFromColumns :: EOBoard -> EOBoard
  acesFromColumns (foundations, columns, reserve) = (chunksOf 1 (filter (\card -> isAce card == True) (flatColumns ++ flatFoundations)),
                                                     chunksOf 6 ((filter (\card -> isAce card == False) flatColumns)),
                                                     reserve)
                                                    where flatColumns = concat columns
                                                          flatFoundations = concat foundations

  -- Takes an EOBoard and makes all possible moves from Reserve to Foundations
  reserveToFoundations :: EOBoard -> EOBoard
  reserveToFoundations (foundations, columns, reserve) = ((map (map (\foundationCard -> if elem(sCard foundationCard) reserve then (sCard foundationCard) else foundationCard)) foundations),
                                                          columns,
                                                          (filter (\card -> notElem(pCard card) flatFoundations) reserve))
                                                        where flatFoundations = concat foundations

  -- Takes an EOBoard and makes all possible moves from Columns to Foundations
  columnsToFoundations :: EOBoard -> EOBoard
  columnsToFoundations (foundations, columns, reserve) = ((map (map (\foundationCard -> if elem(sCard foundationCard) flatColumns then (sCard foundationCard) else foundationCard)) foundations),
                                                                  chunksOf 6 (filter (\card -> notElem(pCard card) flatFoundations) flatColumns),
                                                                  reserve)
                                                                where flatColumns = concat columns
                                                                      flatFoundations = concat foundations
