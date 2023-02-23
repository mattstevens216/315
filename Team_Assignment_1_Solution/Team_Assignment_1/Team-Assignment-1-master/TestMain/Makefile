
   CC=g++
   STD=-std=c++11
   CFLAGS=

   all: main.cpp libdb.so
      $(CC) $(STD) main.cpp -L. libdb.so -o main.out
#build .so file
   libdb.so: Record.o Table.o Database.o
      $(CC) -shared $(STD) Database.o Table.o Record.o -o libdb.so
#build .o files

   run:
      export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:. && ./main.out
   clean:
      rm *.o *.out
