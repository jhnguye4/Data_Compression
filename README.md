# Data_Compression
CSC 316 Project 1: Move-to-Front Lists and Data Compression

## Learning Objective
Your first programming task is to write a program to compress and decompress text files.
In normal English text – or in Java programs – some words appear much more often than others. For effective compression, we would like to represent frequent words by small numbers. The move- to-front method causes frequent words to be near the front of the list most of the time, therefore these words are likely to be represented by small numbers.
Move-to-front also exploits “locality of reference.” In a long English text, the set of frequently occurring words shifts as the author moves from topic to topic, or, in a document with coauthors, as authorship passes from person to person. In a Java program, variable names are used frequently in the method in which they are declared, but not at all outside it. Furthermore, uses tend to clump together even within methods. The move-to-front technique adapts well to these changes in relative frequency. Words gradually move further back in the list as they fall into disuse.
The objectives of this project are to:
1. implement an algorithm to compress and decompress text files using linked lists with the move-to-front heuristic; and
2. collect experimental data to evaluate the performance of the compression algogorithm and compare it to well-known file compression utilities.

## Specifications
### Description
For simplicity, we restrict the input files as follows:
* The file may occupy many lines.
* The lines consist of “words” made up of upper- and lower-case letters separated by blanks and special characters such “.,/%&!@#$?-_><”. Special characters are not to be compressed, they are simply copied from input file to output file.
* The file contains no digits 0123456789.
The output (compressed) file is identical to the input (uncompressed) one, except as follows:
* A zero and a blank (“0⊔”) are prepended to the first line
* The first occurrence of each word remains as is, but all subsequent occurrences are replaced
by positive integers, as specified further on.
* After the last line of text, a new line beginning with “0⊔” is added. The rest of this line is a comment containing statistics on the compression (as shown in the example below).
## Running Program
In order to run with input files:

* javac proj1.java
* java proj1
* Enter a filename or Q to quit: input1.txt
* Results will be in input1-output.txt
