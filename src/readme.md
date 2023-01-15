Description
Files are used to save progress and restore it the next time the user runs the program. It's tedious to print the actions manually. Sometimes you can just forget to do it! Let's add run arguments that define which file to read at the start and which file to save at the exit.

Objectives
When provided with command-line arguments, your program should do the following:

If -import IMPORT is passed, where IMPORT is the file name, read the initial card set from the external file and print the message n cards have been loaded. as the first line of the output, where n is the number of cards loaded from the external file. If such an argument is not provided, the set of cards should initially be empty and no message about card loading should be output.
If -export EXPORT is passed, where EXPORT is the file name, write all cards that are in the program memory into this file after the user has entered exit, and the last line of the output should be n cards have been saved., where n is the number of flashcards in the set.
Run arguments examples
java Flashcards -import derivatives.txt
java Flashcards -export animals.txt
java Flashcards -import words13june.txt -export words14june.txt
java Flashcards -export vocab.txt -import vocab.txt 