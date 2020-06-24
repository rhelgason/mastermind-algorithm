# Mastermind Algorithm
An implementation of Donal Knuth's algorithm to always win the Mastermind game in five moves or fewer.

## How to Play
Mastermind is generally played with two players, the codemaker and the codebreaker. In this case, the computer will act as the codebreaker. The codemaker creates a pattern of length 4, consisting of six different colors. The codebreaker then attempts to guess the secret pattern in as few guesses as possible.

After every guess, the codemaker gives hints in the form of colored pegs. Black pegs mean that a correct color was placed in the correct position. White pegs mean that a correct color was placed, but not in the correct position. Duplicate colors are not awarded multiple pegs unless the secret pattern also contains the same number of duplicates for that color. Play continues until the codemaker responds with four black pegs, indicating that the secret pattern has been cracked.

For more information about the game, visit [the Wikipedia page.](https://en.wikipedia.org/wiki/Mastermind_(board_game))

## How the Algorithm Works (adapted from [the Wikipedia page](https://en.wikipedia.org/wiki/Mastermind_(board_game)#Worst_case:_Five-guess_algorithm))
Imagine that each of the colors represents a number 1 through 6.

1. Create the set S of 1296 possible codes (1111, 1112 ... 6665, 6666).

2. Start with initial guess 1122 (Knuth gives examples showing that other first guesses such as 1123, 1234 do not win in five tries on every code).

3. Play the guess to get a response of colored and white pegs.

4. If the response is four colored pegs, the game is won, the algorithm terminates.

5. Otherwise, remove from S any code that would not give the same response if the current guess were the code.

6. Apply minimax technique to find a next guess as follows:
For each possible guess, that is, any unused code of the 1296 not just those in S, calculate how many possibilities in S would be eliminated for each possible colored/white peg score. The score of a guess is the minimum number of possibilities it might eliminate from S.

    A single loop through S for each unused code of the 1296 will provide a 'hit count' for each of the possible colored/white peg scores. Create a set of guesses with the smallest max score (hence minmax).
From the set of guesses with the minimum (max) score, select one as the next guess, choosing a member of S whenever possible.

    Knuth follows the convention of choosing the guess with the least numeric value e.g. 2345 is lower than 3456. Knuth also gives an example showing that in some cases no member of S will be among the highest scoring guesses and thus the guess cannot win on the next turn, yet will be necessary to assure a win in five.
