module SolitaireGame where

  import Solitaire

  -- Finds all possible moves
  findMoves :: EOBoard -> [EOBoard]
  findMoves board
    | board /= findAcesReserve = [findAcesReserve]
    | board /= findAcesColumns = [findAcesColumns]
    | board /= findCardsReserve = [findCardsReserve]
    | board /= findCardsColumns = [findCardsColumns]
    | otherwise = []
    where
      findAcesReserve = reserveAcesToFoundations board
      findAcesColumns = colAcesToFoundations board
      findCardsReserve = reserveToFoundations board
      findCardsColumns = colHeadsToFoundations board

  -- Chooses a move
  chooseMove :: EOBoard -> Maybe EOBoard
  chooseMove board
    | (not.null) possibleMoves = Just (toFoundations chosenMove)
    | otherwise = Nothing
    where
      chosenMove = bestMove possibleMoves
      possibleMoves = findMoves board

  -- Finds the best move, given a list of EOBoards
  bestMove :: [EOBoard] -> EOBoard
  bestMove boards @ (h @ (foundations, columns, reserve) : t)
    | or (map isAce foundations) == True = h
    | or (map (\foundationCard -> elem (pCard foundationCard) reserve) foundations) == True = h
    | or (map (\foundationCard -> elem (pCard foundationCard) columnHeads) foundations) == True = h
    | otherwise = bestMove t
      where
        columnHeads = map head columns

  -- Uses chooseMove to play a game to completion. Returns a score.
  -- This score is determined by how many cards are in Foundations
  eOGame :: EOBoard -> Int
  eOGame board @ (foundations, columns, reserve)
    | (null nextMove) = length foundations
    | otherwise = eOGame (resultMaybe nextMove)
    where
      nextMove = chooseMove board

  -- Plays 100 games given an initial random seed
  -- Returns number of wins and average score
  eOExpt :: Int -> (Int, Int)
  eOExpt seed = (numberWins, average)
    where
      numberWins = length (filter (\score -> score == 52) hundredGames)
      hundredGames = take 100 (repeat (eOGame (eODeal seed)))
      average = sum(hundredGames) `div` 100

  -- Maybe helper
  resultMaybe :: (Maybe a) -> a
  resultMaybe (Just x) = x
