#ifndef DATABASE_H
#define DATABASE_H

#include "Table.h"
#include <unordered_map>





class Database {
private:
	std::unordered_map<std::string,Table> table_map;

public:
	Database();
	~Database();

	//Add table
	void add_table(std::string name, Table table);

	//drop table name
	void drop_table(std::string name);

	//list table
	std::vector<std::string> list_tables();

	std::vector<Table> get_tables();

	Table query(std::string select, std::string from, std::string where);

};

#endif