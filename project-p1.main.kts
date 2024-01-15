import khoury.CapturedResult
import khoury.EnabledTest
import khoury.captureResults
import khoury.fileReadAsList
import khoury.isAnInteger
import khoury.linesToString
import khoury.reactConsole
import khoury.runEnabledTests
import khoury.testSame

// -----------------------------------------------------------------
// Project: Part 1, Summary
// -----------------------------------------------------------------

// TODO: Add a comment here saying whom you worked with (people or
// AI), and how it helped. If you did not work with anyone (which
// would be surprising), say that.

// Lab with Eddie explained some parts (noted below)
// I talked a little with Will Legault about keepIfValid, and he
// told me that he used IntRange instead of List<Int>. I figured out
// that I needed to use IntRange after I looked at the Kotlin docs
// for indices.

// You are going to design an application to allow a user to
// self-study using flash cards. In this part of the project,
// a user will...

// 1. Be prompted to choose from a menu of available flash
//    card decks; this menu will repeat until a valid
//    selection is made.
//
// 2. Proceed through each card in the selected deck,
//    one-by-one. For each card, the front is displayed,
//    and the user is allowed time to reflect; then the
//    back is displayed; and the user is asked if they
//    got the correct answer.
//
// 3. Once the deck is exhausted, the program outputs the
//    number of self-reported correct answers and ends.
//

// Of course, we'll design this program step-by-step, AND
// you've already done pieces of this in homework!!
// (Note: you are welcome to leverage your prior work and/or
// code found in the sample solutions & lecture notes.)
//

// Lastly, here are a few overall project requirements...
// - Since mutation hasn't been covered in class, your design is
//   NOT allowed to make use of mutable variables and/or lists.
// - As included in the instructions, all interactive parts of
//   this program MUST make effective use of the reactConsole
//   framework.
// - Staying consistent with our Style Guide...
//   * All functions must have:
//     a) a preceding comment specifying what it does
//     b) an associated @EnabledTest function with sufficient
//        tests using testSame
//   * All data must have:
//     a) a preceding comment specifying what it represents
//     b) associated representative examples
// - You will be evaluated on a number of criteria, including...
//   * Adherence to instructions and the Style Guide
//   * Correctly producing the functionality of the program
//   * Design decisions that include choice of tests, appropriate
//     application of list abstractions, and task/type-driven
//     decomposition of functions.
//

// -----------------------------------------------------------------
// Data design
// (Hint: see Homework 3, Problem 2)
// -----------------------------------------------------------------

// TODO 1/2: Design the data type FlashCard to represent a single
//           flash card. You should be able to represent the text
//           prompt on the front of the card as well as the text
//           answer on the back. Include at least 3 example cards
//           (which will come in handy later for tests!).
//

// flashcard with string prompt and string answer
data class FlashCard(val front: String, val back: String)

val fc1 = FlashCard("How many protons does hydrogen have?", "1")
val fc2 = FlashCard("What is the symbol for hydrogen?", "H")
val fc3 = FlashCard("How many protons does helium have?", "2")
val fc4 = FlashCard("What is the symbol for helium?", "He")

// TODO 2/2: Design the data type Deck to represent a deck of
//           flash cards. The deck should have a name, as well
//           as a Kotlin list of flash cards.
//
//           Include at least 2 example decks based upon the
//           card examples above.
//

// deck with a name and list of flashcards
data class Deck(val name: String, val fcList: List<FlashCard>)

val deck1 = Deck("protons", listOf(fc1, fc3))
val deck2 = Deck("symbols", listOf(fc2, fc4))

// -----------------------------------------------------------------
// Generating flash cards
// -----------------------------------------------------------------

// One benefit of digital flash cards is that sometimes we can
// use code to produce cards that match a known pattern without
// having to write all the fronts/backs by hand!
//

// TODO 1/1: Design the function perfectSquares that takes a
//           count (assumed to be positive) and produces the
//           list of flash cards that tests that number of the
//           first squares.
//
//           For example, the first three perfect squares...
//
//            1. front (1^2 = ?), back (1)
//            2. front (2^2 = ?), back (4)
//            3. front (3^2 = ?), back (9)
//
//           have been supplied as named values.
//
//           Hint: you might consider combining your
//                 kthPerfectSquare function from Homework 1
//                 with the list constructor in Homework 3.
//

// notes from lab with Eddie
// listOf(1, 2, 3)
// List<Int>(3, ::addOne)
// [0, 1, 2] -> [addOne(0), addOne(1), addOne(2)] -> [1, 2, 3]
//
// creates one card for the multiples of 13 deck, based on given index
// fun createOneCardMult13(givenIndex: Int): FlashCard {
//     val indexPlus1 = givenIndex + 1

//     val front: String = "$indexPlus1 * 13 = ???"
//     val back: String = "${indexPlus * 13}"

//     val result: Int = indexPlus1 * 13
//     val backAlt: String = "$result"

//     return FlashCard(front, back)
// }

// // create a list of flashcards that help study multiples of 13
// // (based on the given count)
// // the given count will determine the number of cards
// fun multBy13(count: Int): List<FlashCard> {
//     return List<FlashCard>(count, ::someFunction)
// }

val square1Front = "1^2 = ?"
val square2Front = "2^2 = ?"
val square3Front = "3^2 = ?"

val square1Back = "1"
val square2Back = "4"
val square3Back = "9"

// creates a perfect square card based on given index
fun createOneCard(index: Int): FlashCard {
    val indexPlusOne = index + 1
    val front = "$indexPlusOne^2 = ?"
    val back = "${ indexPlusOne * indexPlusOne }"
    return FlashCard(front, back)
}

@EnabledTest
fun testingCreateOneCard() {
    testSame(createOneCard(0), FlashCard(square1Front, square1Back), "one")
    testSame(createOneCard(1), FlashCard(square2Front, square2Back), "two")
    testSame(createOneCard(4), FlashCard("5^2 = ?", "25"), "five")
}

// create a list of flashcards with perfect squares
// given count determines the number of cards
fun createPerfectSquareCards(count: Int): List<FlashCard> {
    return List<FlashCard>(count, ::createOneCard)
}

@EnabledTest
fun testingCreatePerfectSquareCards() {
    testSame(createPerfectSquareCards(0), emptyList<FlashCard>(), "empty")
    testSame(createPerfectSquareCards(1), listOf(FlashCard(square1Front, square1Back)), "one")
    testSame(createPerfectSquareCards(2), listOf(FlashCard(square1Front, square1Back), FlashCard(square2Front, square2Back)), "two")
}

val perfectSquareDeck = Deck("perfect squares", createPerfectSquareCards(3))
// println(perfectSquareDeck)

// gets factorial of given number
fun getFactorial(num: Int): Int {
    return when (num > 1) {
        false -> 1
        true -> num * getFactorial(num - 1)
    }
}

@EnabledTest
fun testingGetFactorial() {
    testSame(getFactorial(0), 1, "zero")
    testSame(getFactorial(1), 1, "one")
    testSame(getFactorial(2), 2, "two")
    testSame(getFactorial(3), 6, "three")
    testSame(getFactorial(4), 24, "four")
}

// creates a factorial card based on given index
fun createFactorialCard(index: Int): FlashCard {
    // val front = "What is $index!"
    // val back = getFactorial(index)
    return FlashCard("What is $index!?", getFactorial(index).toString())
}

@EnabledTest
fun testingCreateFactorialCard() {
    testSame(createFactorialCard(0), FlashCard("What is 0!?", "1"), "zero")
    testSame(createFactorialCard(1), FlashCard("What is 1!?", "1"), "one")
    testSame(createFactorialCard(2), FlashCard("What is 2!?", "2"), "two")
    testSame(createFactorialCard(3), FlashCard("What is 3!?", "6"), "three")
}

// creates list of factorial flash cards based on given size
fun createFactorialCardList(count: Int): List<FlashCard> {
    return List<FlashCard>(count, ::createFactorialCard)
}

@EnabledTest
fun testingCreateFactorialCardList() {
    testSame(createFactorialCardList(0), emptyList<FlashCard>(), "empty")
    testSame(createFactorialCardList(1), listOf(FlashCard("What is 0!?", "1")), "one")
    testSame(createFactorialCardList(2), listOf(FlashCard("What is 0!?", "1"), FlashCard("What is 1!?", "1")), "two")
}

val factorialDeck = Deck("factorial", createFactorialCardList(5))
// println(factorialDeck)

// -----------------------------------------------------------------
// Files of cards
// -----------------------------------------------------------------

// Consider a simple format for storing flash cards in a file:
// each card is a line in the file, where the front comes first,
// separated by a "pipe" character ('|'), followed by the text
// on the back of the card.
//

val charSep = "|"

// TODO 1/3: Design the function cardToString that takes a flash
//           card as input and produces a string according to the
//           specification above ("front|back"). Make sure to
//           test all your card examples!
//

// converts given flash card into string using separator
fun cardToString(fc: FlashCard): String {
    return "${fc.front}$charSep${fc.back}"
}

@EnabledTest
fun testingCardToString() {
    testSame(cardToString(fc1), "How many protons does hydrogen have?|1")
    testSame(cardToString(fc2), "What is the symbol for hydrogen?|H")
    testSame(cardToString(fc3), "How many protons does helium have?|2")
    testSame(cardToString(fc4), "What is the symbol for helium?|He")
}

// TODO 2/3: Design the function stringToCard that takes a string,
//           assumed to be in the format described above, and
//           produces the corresponding flash card.
//
//           Hints:
//           - look back to how we extracted data from CSV
//             (comma-separated value) files (such as in
//             Homework 3)!
//           - a great way to test: for each of your card
//             examples, pass them through the function in TODO
//             1 to convert them to a string; then, pass that
//             result to this function... you *should* get your
//             original flash card back :)
//

// converts from string to flash card
fun stringToCard(str: String): FlashCard {
    val stringList = str.split(charSep)
    return FlashCard(stringList[0], stringList[1])
}

@EnabledTest
fun testingStringToCard() {
    testSame(stringToCard(cardToString(fc1)), fc1, "fc1")
    testSame(stringToCard(cardToString(fc2)), fc2, "fc2")
    testSame(stringToCard(cardToString(fc3)), fc3, "fc3")
    testSame(stringToCard(cardToString(fc4)), fc4, "fc4")
}

// TODO 3/3: Design the function readCardsFile that takes a path
//           to a file and produces the corresponding list of
//           flash cards found in the file.
//
//           If the file does not exist, return an empty list.
//           Otherwise, you can assume that every line is
//           formatted in the string format we just worked with.
//
//           Hint:
//           - Think about how HW3-P1 effectively used an
//             abstraction to process all the lines in a
//             file assuming a known pattern.
//           - We've provided an "example.txt" file that you can
//             use for testing if you'd like; also make sure to
//             test your function when the supplied file does not
//             exist!
//

// creates flash cards from the given text file
fun readCardsFile(filePath: String): List<FlashCard> {
    return fileReadAsList(filePath).map(::stringToCard)
}

@EnabledTest
fun testingReadCardsFile() {
    testSame(readCardsFile("example.txt"), listOf(FlashCard("front 1", "back 1"), FlashCard("front 2", "back 2")), "example")
    testSame(readCardsFile("no_file.txt"), emptyList<FlashCard>(), "does not exist")
}

val shapeDeck = Deck("shapes", readCardsFile("shapes.txt"))
// println(shapeDeck)

// -----------------------------------------------------------------
// Processing a self-report
// (Hint: see Homework 2)
// -----------------------------------------------------------------

// In our program, we will ask for a self-report as to whether
// the user got the correct answer for a card, SO...

// TODO 1/1: Finish designing the function isPositive that
//           determines if the supplied string starts with
//           the letter "y" (either upper or lowercase).
//
//           You've been supplied with a number of tests - make
//           sure you understand what they are doing!
//

// returns true if given string starts with y (case-insensitive)
fun isPositive(str: String): Boolean {
    return str.lowercase().startsWith("y")
    // return str.startsWith("y", true)
}

@EnabledTest
fun testIsPositive() {
    fun helpTest(
        str: String,
        expected: Boolean,
    ) {
        testSame(isPositive(str), expected, str)
    }

    helpTest("yes", true)
    helpTest("Yes", true)
    helpTest("YES", true)
    helpTest("yup", true)

    helpTest("nope", false)
    helpTest("NO", false)
    helpTest("nah", false)
    helpTest("not a chance", false)

    // should pass,
    // despite doing the wrong thing
    helpTest("indeed", false)
}

// -----------------------------------------------------------------
// Choosing a deck from a menu
// -----------------------------------------------------------------

// Now let's work on providing a menu of decks from which a user
// can choose what they want to study.

// TODO 1/2: Finish design the function choicesToText that takes
//           a list of strings (assumed to be non-empty) and
//           produces the textual representation of a menu of
//           those options.
//
//           For example, given...
//
//           ["a", "b", "c"]
//
//           The menu would be...
//
//           "1. a
//            2. b
//            3. c
//
//            Enter your choice"
//
//            As you have probably guessed, this will be a key
//            piece of our rendering function :)
//
//            Hints:
//            - Think back to Homework 3 when we used a list
//              constructor to generate list elements based
//              upon an index.
//            - If you can produce a list of strings, the
//              linesToString function in the Khoury library
//              will bring them together into a single string.
//            - Make sure to understand the supplied tests!
//

val promptMenu = "Enter your choice"

// turns given strings into menu of choices
fun choicesToText(strList: List<String>): String {
    // converts given string into choice format (adding number)
    fun stringToChoice(index: Int): String {
        val indexPlusOne = index + 1
        return "$indexPlusOne. ${ strList[index] }"
    }
    val choiceList = List<String>(strList.size, ::stringToChoice)
    return linesToString(linesToString(choiceList), "", promptMenu)
}

@EnabledTest
fun testChoicesToText() {
    val optA = "apple"
    val optB = "banana"
    val optC = "carrot"

    testSame(
        choicesToText(listOf(optA)),
        linesToString(
            "1. $optA",
            "",
            promptMenu,
        ),
        "one",
    )

    testSame(
        choicesToText(listOf(optA, optB, optC)),
        linesToString(
            "1. $optA",
            "2. $optB",
            "3. $optC",
            "",
            promptMenu,
        ),
        "three",
    )
}

// TODO 2/2: Finish designing the program chooseOption that takes
//           a list of decks, produces a corresponding numbered
//           menu (1-# of decks, each showing its name), and
//           returns the deck corresponding to the number entered.
//           (Of course, keep displaying the menu until a valid
//           number is entered.)
//
//           Hints:
//            - Review the "Valid Number Example" of reactConsole
//              as one example of how to validate input. In this
//              case, however, since we know that we have a valid
//              range of integers, we can simplify the state
//              representation significantly :)
//            - To help you get started, the chooseOption function
//              has been written, but you must complete the helper
//              functions; look to the comments below for guidance.
//              You can then play "signature detective" to figure
//              out the parameters/return type of the functions you
//              need to write :)
//            - Lastly, as always, don't forget to sufficiently
//              test all the functions you write in this problem!
//

// returns the name of a given deck
fun getDeckName(deck: Deck): String {
    return deck.name
}

@EnabledTest
fun testingGetDeckName() {
    testSame(getDeckName(deck1), "protons", "protons")
    testSame(getDeckName(deck2), "symbols", "symbols")
}

// returns deck number input if it is a valid index
fun keepIfValid(
    input: String,
    indices: IntRange,
): Int {
    val deckNums = indices.map({ it + 1 })
    return when (isAnInteger(input)) {
        false -> -1
        true ->
            when (input.toInt() in deckNums) {
                true -> input.toInt() - 1
                false -> -1
            }
    }
}

@EnabledTest
fun testingKeepIfValid() {
    testSame(keepIfValid("0", 0..2), -1, "before")
    testSame(keepIfValid("1", 0..3), 0, "in-first")
    testSame(keepIfValid("4", 0..2), -1, "after")
    testSame(keepIfValid("-1", 0..3), -1, "negative")
    testSame(keepIfValid("3", 0..2), 2, "in-end")
    testSame(keepIfValid("0", 0..2), -1, "before")
    testSame(keepIfValid("test", 0..2), -1, "text")
}

// returns announcement for the given deck name
fun choiceAnnouncement(deckName: String): String {
    return "Thou hast chosen $deckName. A most interesting decision."
}

@EnabledTest
fun testingChoiceAnnouncement() {
    testSame(choiceAnnouncement(deck1.name), "Thou hast chosen protons. A most interesting decision.", "protons")
    testSame(choiceAnnouncement(deck2.name), "Thou hast chosen symbols. A most interesting decision.", "symbols")
}

// a program to allow the user to interactively select
// a deck from the supplied, non-empty list of decks
fun chooseOption(decks: List<Deck>): Deck {
    // since the event handlers will need some info about
    // the supplied decks, the functions inside
    // chooseOption provide info about them while the
    // parameter is in scope

    // TODO: Above chooseOption, design the function
    //       getDeckName, which returns the name of
    //       a supplied deck.
    fun renderDeckOptions(state: Int): String {
        return choicesToText(decks.map(::getDeckName))
    }

    // TODO: Above chooseOption, design the function
    //       keepIfValid, that takes the typed input
    //       as a string, as well as the valid
    //       indices of the decks; note that the list indices
    //       will be in the range [0, size), whereas the
    //       user will see and work with [1, size].
    //
    //       If the user did not type a valid integer,
    //       or not one in [1, size], return -1; otherwise
    //       return the string converted to an integer, but
    //       subtract 1, which makes it a valid list index.
    fun transitionOptionChoice(
        ignoredState: Int,
        kbInput: String,
    ): Int {
        return keepIfValid(kbInput, decks.indices)
    }

    // TODO: nothing, but understand this :)
    fun validChoiceEntered(state: Int): Boolean {
        return state in decks.indices
    }

    // TODO: Above chooseOption, design the function
    //       choiceAnnouncement that takes the selected
    //       deck name and returns an announcement that
    //       makes you happy. For a simple example, given
    //       "fundies" as the chosen deck name, you might
    //       return "you chose: fundies"
    fun renderChoice(state: Int): String {
        return choiceAnnouncement(getDeckName(decks[state]))
    }

    return decks[
        reactConsole(
            initialState = -1,
            stateToText = ::renderDeckOptions,
            nextState = ::transitionOptionChoice,
            isTerminalState = ::validChoiceEntered,
            terminalStateToText = ::renderChoice,
        ),
    ]
}

// // Eddie went over testing react console in lab
// @EnabledTest
// fun testingBudgetCalcForLab6() {
//     // captureResults allows us to simulate user input
//     // CapturedResult is the data class that is returned by captureResults
//     testSame(captureResults(::budgetCalc(), "20", "-5", "50"),
//              // CapturedResult has two parameters:
//              // value that is returned, and
//              // list of console output
//              CapturedResult(65,
//                             listOf("Current balance: 0",
//                                    "Current balance: 20",
//                                    "Current balance: 15",
//                                    "Current Balance: 65")),
//              "testing budget calc for lab 6")
// }

// // Eddie went over testing react console with a function
// // that takes a parameter
// @EnabledTest
// fun testingBudgetCalc2ForLab6() {

//     // helps test same by passing in the input 10 into budgetCalc2
//     fun helpTestInput10(): Int {
//         return budgetCalc2(10)
//     }

//     testSame(captureResults(::helpTestInput10, "-8", "hello", "50"),
//              CapturedResult(52,
//                             listOf("Current balance: 10",
//                                    "Current balance: 2",
//                                    "Current balance: 2",
//                                    "Current balance: 52")),
//               "testing budget calc 2")
// }

@EnabledTest
fun testingChooseOption() {
    fun helpTestChooseOption(): Deck {
        return chooseOption(listOf(deck1, deck2))
    }

    testSame(
        captureResults(::helpTestChooseOption, "1"),
        CapturedResult(
            deck1,
            listOf(
                "1. protons",
                "2. symbols",
                "",
                "Enter your choice",
                "Thou hast chosen ${deck1.name}. A most interesting decision.",
            ),
        ),
        "testing choose option 1",
    )

    testSame(
        captureResults(::helpTestChooseOption, "0", "test", "", "3", "2"),
        CapturedResult(
            deck2,
            listOf(
                "1. protons",
                "2. symbols",
                "",
                "Enter your choice",
                "1. protons",
                "2. symbols",
                "",
                "Enter your choice",
                "1. protons",
                "2. symbols",
                "",
                "Enter your choice",
                "1. protons",
                "2. symbols",
                "",
                "Enter your choice",
                "1. protons",
                "2. symbols",
                "",
                "Enter your choice",
                "Thou hast chosen ${deck2.name}. A most interesting decision.",
            ),
        ),
        "testing choose option 2",
    )
}

// -----------------------------------------------------------------
// Studying a deck
// -----------------------------------------------------------------

// Now let's design a program to allow a user to study through a
// supplied deck of flash cards.

// TODO 1/2: Design the data type StudyState to keep track of...
//           - which card you are currently studying in the deck
//           - are you looking at the front or back
//           - how many correct answers have been self-reported
//             thus far
//
//           Create sufficient examples so that you convince
//           yourself that you can represent any situation that
//           might arise when studying a deck.
//
//           Hints:
//           - Look back to the reactConsole problems in HW2 and
//             HW3; the former involved keeping track of a count
//             of loops (similar to the count of correct answers),
//             and the latter involved options for keeping track
//             of where you are in a list with reactConsole.
//

// keeps track of which card is currently being studied, which side
// is being studied, and how many correct answers have been self-
// reported
data class StudyState(val index: Int, val isFront: Boolean, val numCorrect: Int)

val ss1 = StudyState(0, true, 0)
val ss2 = StudyState(1, false, 1)
val ss3 = StudyState(2, true, 2)
val ss4 = StudyState(3, false, 3)

// TODO 2/2: Now, using reactConsole, design the program studyDeck
//           that for each card in a supplied deck, allows the
//           user to...
//
//           1. see the front (pause and think)
//           2. see the back
//           3. respond as to whether they got the answer
//
//           At the end, the user is told how many they self-
//           reported as correct (and this number is returned).
//
//           You have been supplied some prompts for steps #1
//           and #2 - feel free to change them if you'd like :)
//
//           Suggestions...
//           - Review the reactConsole videos/examples
//           - Start with studyDeck:
//             * write some tests to convince yourself you know
//               what your program is supposed to do!
//             * figure out how you'll create the initial state
//             * give names to the handlers you'll need
//             * how will you return the number correct?
//             * now comment-out this function, so that you can
//               design/test the handlers without interference :)
//           - For each handler...
//             * Play signature detective: based upon how it's
//               being used with reactConsole, what data will it
//               be given and what does it produce?
//             * Write some tests to convince yourself you know
//               its job.
//             * Write the code and don't move on till your tests
//               pass.
//            - Suggested ordering...
//              1. Am I done studying yet?
//              2. Rendering
//                 - It's a bit simpler to have a separate
//                   function for the terminal state.
//                 - The linesToString function is your friend to
//                   combine the card with the prompts.
//                 - Think about good decomposition when making
//                   the decision about front vs back content.
//              3. Transition
//                 - Start with the two main situations
//                   you'll find yourself in...
//                   > front->back
//                   > back->front
//                 - Then let a helper figure out how to handle
//                   the details of self-report
//
//            You've got this :-)
//

val studyThink = "Think of the result? Press enter to continue"
val studyCheck = "Correct? (Y)es/(N)o"

// I don't know if this decomposition is necessary, but I
// wanted to be able to test some functions instead of
// nesting everything
// returns flashcard given the deck and current state
fun getFlashCard(
    deck: Deck,
    state: StudyState,
): FlashCard {
    return deck.fcList[state.index]
}

@EnabledTest
fun testingGetFlashCard() {
    testSame(getFlashCard(deck1, ss1), fc1, "fc1")
    testSame(getFlashCard(deck1, ss2), fc3, "fc3")
    testSame(getFlashCard(deck2, ss1), fc2, "fc2")
    testSame(getFlashCard(deck2, ss2), fc4, "fc4")
}

// determines next state based on current state
fun nextStudyState(
    state: StudyState,
    typedInput: String,
): StudyState {
    when (state.isFront) {
        true -> return StudyState(state.index, false, state.numCorrect)
        false ->
            when (isPositive(typedInput)) {
                true -> return StudyState(state.index + 1, true, state.numCorrect + 1)
                false -> return StudyState(state.index + 1, true, state.numCorrect)
            }
    }
}

@EnabledTest
fun testingNextStudyState() {
    testSame(nextStudyState(ss1, ""), StudyState(0, false, 0), "ss1-front-empty")
    testSame(nextStudyState(ss1, "yes"), StudyState(0, false, 0), "ss1-front-yes")
    testSame(nextStudyState(ss1, "no"), StudyState(0, false, 0), "ss1-front-no")

    testSame(nextStudyState(ss2, ""), StudyState(2, true, 1), "ss2-back-empty")
    testSame(nextStudyState(ss2, "yes"), StudyState(2, true, 2), "ss2-back-yes")
    testSame(nextStudyState(ss2, "no"), StudyState(2, true, 1), "ss2-back-no")

    testSame(nextStudyState(ss3, ""), StudyState(2, false, 2), "ss3-front-empty")
    testSame(nextStudyState(ss3, "yeah"), StudyState(2, false, 2), "ss3-front-yes")
    testSame(nextStudyState(ss3, "nah"), StudyState(2, false, 2), "ss3-front-no")

    testSame(nextStudyState(ss4, ""), StudyState(4, true, 3), "ss4-back-empty")
    testSame(nextStudyState(ss4, "yeah"), StudyState(4, true, 4), "ss4-back-yes")
    testSame(nextStudyState(ss4, "nah"), StudyState(4, true, 3), "ss4-back-no")
}

// returns a message with the number of correct answers
fun terminalStudyStateToText(state: StudyState): String {
    return "Thou hast answered ${ state.numCorrect } question(s) correctly."
}

@EnabledTest
fun testingTerminalStudyStateToText() {
    testSame(terminalStudyStateToText(ss1), "Thou hast answered 0 question(s) correctly.", "ss1")
    testSame(terminalStudyStateToText(ss2), "Thou hast answered 1 question(s) correctly.", "ss2")
    testSame(terminalStudyStateToText(ss3), "Thou hast answered 2 question(s) correctly.", "ss3")
    testSame(terminalStudyStateToText(ss4), "Thou hast answered 3 question(s) correctly.", "ss4")
}

// allows user to see front and back of each card in deck and
// self-report outcome
// returns the number of correct answers
fun studyDeck(deck: Deck): Int {
    // nesting functions to get access to deck
    // returns text based on current state
    fun studyStateToText(state: StudyState): String {
        return when (state.isFront) {
            // true -> linesToString(deck.fcList[state.index].front, studyThink)
            // false -> linesToString(deck.fcList[state.index].back, studyCheck)
            true -> linesToString(getFlashCard(deck, state).front, studyThink)
            false -> linesToString(getFlashCard(deck, state).back, studyCheck)
        }
    }

    // didn't work
    // fun testingStudyStateToText() {
    //     testSame(studyStateToText(ss1),
    //             "How many protons does hydrogen have? $studyThink",
    //             "ss1")
    // }

    // determines if it is on last side of last card
    fun isDone(state: StudyState): Boolean {
        return (state.index == deck.fcList.size && state.isFront)
    }

    return reactConsole(
        initialState = StudyState(0, true, 0),
        stateToText = ::studyStateToText,
        nextState = ::nextStudyState,
        isTerminalState = ::isDone,
        terminalStateToText = ::terminalStudyStateToText,
    ).numCorrect
}

@EnabledTest
fun testingStudyDeck() {
    fun helpTestStudyDeck1(): Int {
        return studyDeck(deck1)
    }

    fun helpTestStudyDeck2(): Int {
        return studyDeck(deck2)
    }

    testSame(
        captureResults(::helpTestStudyDeck1, "", "y", "", "n"),
        CapturedResult(
            1,
            listOf(
                "How many protons does hydrogen have?",
                studyThink,
                "1",
                studyCheck,
                "How many protons does helium have?",
                studyThink,
                "2",
                studyCheck,
                "Thou hast answered 1 question(s) correctly.",
            ),
        ),
        "deck1-1",
    )

    testSame(
        captureResults(::helpTestStudyDeck1, "yes", "yeah", "yup", "you bet"),
        CapturedResult(
            2,
            listOf(
                fc1.front,
                studyThink,
                fc1.back,
                studyCheck,
                fc3.front,
                studyThink,
                fc3.back,
                studyCheck,
                "Thou hast answered 2 question(s) correctly.",
            ),
        ),
        "deck1-2",
    )

    testSame(
        captureResults(::helpTestStudyDeck1, "1", "", "no", "nope"),
        CapturedResult(
            0,
            listOf(
                "How many protons does hydrogen have?",
                studyThink,
                "1",
                studyCheck,
                "How many protons does helium have?",
                studyThink,
                "2",
                studyCheck,
                "Thou hast answered 0 question(s) correctly.",
            ),
        ),
        "deck1-0",
    )

    testSame(
        captureResults(::helpTestStudyDeck2, "y", "n", "", "y"),
        CapturedResult(
            1,
            listOf(
                fc2.front,
                studyThink,
                fc2.back,
                studyCheck,
                fc4.front,
                studyThink,
                fc4.back,
                studyCheck,
                "Thou hast answered 1 question(s) correctly.",
            ),
        ),
        "deck2-1",
    )

    testSame(
        captureResults(::helpTestStudyDeck2, "yep", "yeah", "yes", "you bet"),
        CapturedResult(
            2,
            listOf(
                fc2.front,
                studyThink,
                fc2.back,
                studyCheck,
                fc4.front,
                studyThink,
                fc4.back,
                studyCheck,
                "Thou hast answered 2 question(s) correctly.",
            ),
        ),
        "deck2-2",
    )

    testSame(
        captureResults(::helpTestStudyDeck2, "", "affirmative", "7", "1"),
        CapturedResult(
            0,
            listOf(
                fc2.front,
                studyThink,
                fc2.back,
                studyCheck,
                fc4.front,
                studyThink,
                fc4.back,
                studyCheck,
                "Thou hast answered 0 question(s) correctly.",
            ),
        ),
        "deck2-0",
    )
}

// -----------------------------------------------------------------
// Final app!
// -----------------------------------------------------------------

// Now you just get to put this all together 💃

// TODO 1/1: Design the function chooseAndStudy, where you'll
//           follow the comments in the supplied code to leverage
//           your prior work to allow the user to choose a deck,
//           study it, and return the number of correct self-
//           reports.
//
//           Your deck options MUST include at least one from each
//           of the following categories...
//
//           - Coded by hand (such as an example in data design)
//           - Read from a file (ala readCardsFile)
//           - Generated by code (ala perfectSquares)
//
//           Note: while this is an interactive program, you won't
//                 directly use reactConsole - instead, just call
//                 the programs you already designed above :)
//
//           And of course, don't forget to test at least two runs
//           of this completed program!
//
//           (And, consider adding this to main so you can see the
//           results of all your hard work so far this semester!)
//

// I'll make the decks above, close to the relevant to-do's

// lets the user choose a deck and study it,
// returning the number self-reported correct
fun chooseAndStudy(): Int {
    // 1. Construct a list of options
    // (ala the instructions above)
    val deckOptions =
        listOf(
            deck1,
            shapeDeck,
            perfectSquareDeck,
            // TODO: at least...
            // deck from file via readCardsFile,
            // deck from code via perfectSquares
            // deck hand-coded
        )

    // 2. Use chooseOption to let the user
    //    select a deck
    val deckChosen = chooseOption(deckOptions)

    // 3. Let the user study, return the
    //    number correctly answered
    return studyDeck(deckChosen)
}

@EnabledTest
fun testingChooseAndStudy() {
    testSame(
        captureResults(
            ::chooseAndStudy,
            "1",
            "",
            "y",
            "",
            "yes",
        ),
        CapturedResult(
            2,
            listOf(
                "1. protons",
                "2. shapes",
                "3. perfect squares",
                "",
                "Enter your choice",
                "Thou hast chosen ${deck1.name}. A most interesting decision.",
                "How many protons does hydrogen have?",
                studyThink,
                "1",
                studyCheck,
                "How many protons does helium have?",
                studyThink,
                "2",
                studyCheck,
                "Thou hast answered 2 question(s) correctly.",
            ),
        ),
        "deck1",
    )

    testSame(
        captureResults(
            ::chooseAndStudy, "2",
            "yes",
            "yeah",
            "no",
            "nope",
            "",
            "incorrect",
            "9",
            "8",
        ),
        CapturedResult(
            1,
            listOf(
                "1. protons",
                "2. shapes",
                "3. perfect squares",
                "",
                "Enter your choice",
                "Thou hast chosen shapes. A most interesting decision.",
                "How many sides does a triangle have?",
                studyThink,
                "3",
                studyCheck,
                "How many sides does a square have?",
                studyThink,
                "4",
                studyCheck,
                shapeDeck.fcList[2].front,
                studyThink,
                shapeDeck.fcList[2].back,
                studyCheck,
                shapeDeck.fcList[3].front,
                studyThink,
                shapeDeck.fcList[3].back,
                studyCheck,
                "Thou hast answered 1 question(s) correctly.",
            ),
        ),
        "shape deck",
    )

    testSame(
        captureResults(
            ::chooseAndStudy, "0",
            "",
            "test",
            "3",
            "yes",
            "",
            "",
            "correct",
            "no",
            "!",
        ),
        CapturedResult(
            0,
            listOf(
                "1. protons",
                "2. shapes",
                "3. perfect squares",
                "",
                "Enter your choice",
                "1. protons",
                "2. shapes",
                "3. perfect squares",
                "",
                "Enter your choice",
                "1. protons",
                "2. shapes",
                "3. perfect squares",
                "",
                "Enter your choice",
                "1. protons",
                "2. shapes",
                "3. perfect squares",
                "",
                "Enter your choice",
                "Thou hast chosen ${perfectSquareDeck.name}. A most interesting decision.",
                "1^2 = ?",
                studyThink,
                "1",
                studyCheck,
                "2^2 = ?",
                studyThink,
                "4",
                studyCheck,
                perfectSquareDeck.fcList[2].front,
                studyThink,
                perfectSquareDeck.fcList[2].back,
                studyCheck,
                "Thou hast answered 0 question(s) correctly.",
            ),
        ),
        "perfect square deck",
    )
}

// -----------------------------------------------------------------

// fun main() {
//     chooseAndStudy()
// }

runEnabledTests(this)
// main()
