# ---------------------------------------------------------------------------- #
# Developer: Andrew Kirfman, Margaret Baxter                                   #
# Project: CSCE-313 Machine Problem #1                                         #
#                                                                              #
# File: ./MP1/Part_2/Makefile                                                  #
# ---------------------------------------------------------------------------- #

CC=g++
STD=-std=c++11
CFLAGS=

all: 
	$(CC) $(STD) -o main -L. -lrary TestCase.cpp
	export LD_LIBRARY_PATH=.:$$LD_LIBRARY_PATH && ./main

library: 
	#$(CC) $(STD) -O2 -c -fpic *.cpp
	$(CC) -shared -fpic $(STD) -o library.so *.cpp

clean:
	rm -rf *.o data *.gch
