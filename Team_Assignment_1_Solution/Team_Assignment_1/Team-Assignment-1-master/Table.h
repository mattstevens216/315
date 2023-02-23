#ifndef TABLE_H
#define TABLE_H

#include "Record.h"
#include <initializer_list>

class Table {
private:
	
	std::vector<Record> v;
	
	std::string key;

public:
	Record attribute;
	// make constructor that takes list of args
	Table(std::initializer_list<std::string> args);
	Table(); //Empty constructor not allowed
	Table(Record r);


	//Add atribute to end of table
	void add_attribute(std::string name);
	
	void set_attribute(Record r);

	//Delete attribute from table
	void delete_attribute(std::string name);

	//insert record into table
	void insert_record(Record r);

	//return list of names
	Record get_attributes();

	//return size of Table
	int size();

	//set key
	void set_key(std::string attribute);

	//cross join
	Table cross_join(Table table_1, Table table_2);

	//natural join
	Table natural_join(Table table_1, Table table_2);

	int get_count(std::string name);
	std::string get_min(std::string name);
	std::string get_max(std::string name);
	
	Record& operator[](int index);
	Table& operator=(const Table & t);

};

#endif