//-----------------------------------------------------------
// FILENAME: table.h
// AUTHOR: Nahiyan Khandker, Mason Clemmenson, Austin Gloyna
// Written for team project 1 - CSCE 315
// Instructor: Dr.Keyser
//-----------------------------------------------------------

//-----------------------------------------------------------
//INCLUDES / DEFINITIONS / NAMESPACE
//-----------------------------------------------------------

#ifndef TABLE_H
#define TABLE_H

#include <vector>
#include <string>
#include "record.h"

//-----------------------------------------------------------
// CLASS: Table
//-----------------------------------------------------------

class Table{
    private:
        std::vector<Record> records;
        std::vector<std::string> attributes;
        std::string key;
        int key_index;

    public:
        Table();
        Table(std::vector<std::string> list);
        int add_attribute(std::string s);
        int delete_attribute(std::string s);
        int insert_record(Record r);
        std::vector<std::string> get_attributes();
        int get_size();
        Record* get_record(int i);
        int specify_key(std::string s);
        int count(std::string s);
        std::string max(std::string s);
        std::string min(std::string s);
        
        std::string name;
};

Table cross_join(Table one, Table two);
Table natural_join(Table one, Table two);

#endif
