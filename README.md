# Empirical-Analysis
Project of Scientific Methodology - UFCG 2016.1

Title: Empirical investigation of parallelism use the sorting problem
for large volumes of data.

Usage:
sort.sh -a <algorithm> [-i <inputFile>, -o <outputFile>]

Options:
	-a = Number of algorithm
	-i = Input File
	-o = Output File

Algorithms:
 1 - Mergesort Sequential
 2 - Mergesort Concurrent

Examples:
> Sequential mergesort with input and output files
	bash sort.sh -a 1 -i input.txt -o output.txt

> Sequential mergesort without input and output files
	bash sort.sh -a 1

> Concurrent mergesort with input and output files.
	bash sort.sh -a 2 -i input.txt -o output.txt

> Concurrent mergesort without input and output files
	bash sort.sh -a 2
