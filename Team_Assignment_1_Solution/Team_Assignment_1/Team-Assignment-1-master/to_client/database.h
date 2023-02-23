//-----------------------------------------------------------
// FILENAME: database.h
// AUTHOR: Nahiyan Khandker, Mason Clemmenson, Austin Gloyna
// Written for team project 1 - CSCE 315
// Instructor: Dr.Keyser
//-----------------------------------------------------------

//-----------------------------------------------------------
//INCLUDES / DEFINITIONS / NAMESPACE
//-----------------------------------------------------------

#ifndef DATABASE_H
#define DATABASE_H

#include <QtGui>
#include <iostream>
#include <vector>
#include <string>
#include "table.h"

//-----------------------------------------------------------
//CLASS: Database
//-----------------------------------------------------------

class Database{
public:

    Database();
    ~Database();
    int add_table(std::string table_name, Table t);
    int drop_table(std::string table_name);
    std::vector<std::string> list_tables();
    std::vector<Table> get_tables();
    Table query(char* select, char* from, char* where);

private:
    std::vector<Table> tables;
};

#endif /* DATABASE_H */
