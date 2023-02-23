#include "Database.h"
	
Database::Database(){}

void Database::add_table(std::string name, Table t){
	table_map[name] = t;
}
	

void Database::drop_table(std::string name){
	
	table_map.erase(name);
}

std::vector<std::string> Database::list_tables(){
	std::vector<std::string> list;
	
	for(auto it = table_map.begin(); it !=table_map.end(); ++it)
		list.push_back(it->first);
	return list;
}

std::vector<Table> Database::get_tables(){
	
	std::vector<Table> list;
	
	for(auto it = table_map.begin(); it !=table_map.end(); ++it)
		list.push_back(it->second);
	return list;
	
	
}
Table Database::query(std::string select, std::string from, std::string where){
	Table t;
	Table qTable;
	std::vector<std::string> sel_Vec;
	std::vector<std::string> where_Vec;
	std::stringstream ss(select);
	std::stringstream ww(where);
	std::vector<int> sel_index;
	
	std::string n;
	if(select != "*"){
		while(ss >> n){
			sel_Vec.push_back(n);
			if(ss.peek() == ','){
				ss.ignore();
			}
		}
	} else {
		sel_Vec.push_back(select);
	}
	
	while(ww >> n){
			where_Vec.push_back(n);
			if(ss.peek() == ' '){
				ss.ignore();
			}
		}
	
	t = table_map[from];
	for(int i = 0; i < sel_Vec.size(); i++){
		qTable.add_attribute(sel_Vec[i]);
	}
	

	int index = t.attribute.findIndex(where_Vec[0]);
	
	if(sel_Vec[0]=="*"){
		for(int i = 0 ; i < t.attribute.size(); ++i)
			sel_index.push_back(i);
	}
	else{
		for(int i = 0; i < sel_Vec.size(); ++i){
			int j = t.attribute.findIndex(sel_Vec[i]);
			if(j!=0)
				sel_index.push_back(j);
			
				
		}
	}
	
	for(int i =  0; i < where_Vec.size();++i)
		std::cout << where_Vec[i] << std::endl;
	for(int j = 0; j < t.size(); j++){
		//=
		if(where_Vec[1] == "="){
			if(t[j][index] == where_Vec[2]) {
				Record r = Record::getSubRecord(t[j],sel_index);
				qTable.set_attribute(r);
			}
		}
		//<>
		if(where_Vec[1] == "<>"){
			if(t[j][index] != where_Vec[2]) {
				Record r = Record::getSubRecord(t[j],sel_index);
				qTable.set_attribute(r);
			}
		}
		//>
		if(where_Vec[1] == ">"){
			if(t[j][index] > where_Vec[2]) {
				Record r = Record::getSubRecord(t[j],sel_index);
				qTable.set_attribute(r);
			}
		}
		//<
		if(where_Vec[1] >= ">="){
			if(t[j][index] == where_Vec[2]) {
				Record r = Record::getSubRecord(t[j],sel_index);
				qTable.set_attribute(r);

			}
		}
		//>=
		if(where_Vec[1] < "<"){
			if(t[j][index] == where_Vec[2]) {
				Record r = Record::getSubRecord(t[j],sel_index);
				qTable.set_attribute(r);
			}
		}
		//<=
		if(where_Vec[1] <= "<="){
			if(t[j][index] == where_Vec[2]) {
				Record r = Record::getSubRecord(t[j],sel_index);
				qTable.set_attribute(r);
			}
		}
	}
	
	return qTable;
}