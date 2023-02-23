#include "Table.h"


Table::Table(std::initializer_list<std::string> args){
	std::initializer_list<std::string>::iterator it;
	for(it = args.begin(); it != args.begin(); it++){
		attribute.add(*it);
	}
}

Table::Table(Record r){
	attribute = r;
}


void Table::add_attribute(std::string name){
	attribute.add(name);
}


Table::Table(){}


void Table::delete_attribute(std::string name){
	attribute.remove(name);
}


void Table::insert_record(Record r){
	v.push_back(r);
}

Record Table::get_attributes(){
	return attribute;
}

int Table::size(){
	int size = 0;
	for(int i = 0; i < v.size(); i++){
		size += 1;
	}
	return size;
}

void Table::set_key(std::string attribute){
	key = attribute;
}

Record& Table::operator[](int index){
	return v[index];
}

Table& Table::operator=(const Table & t){
	attribute = t.attribute;
	v = t.v;
	key = t.key;
	return *this;
}

Table Table::cross_join(Table table_1, Table table_2){
	Table table_3(Record::merge(table_1.get_attributes(),table_2.get_attributes()));
	
	for(int i = 0; i < table_1.size(); i++){
		for(int j = 0; j < table_2.size(); j++){
			table_3.insert_record(Record::merge(table_1[i],table_2[j]));
		}
	} 
	
	return table_3;
}

Table Table::natural_join(Table table_1, Table table_2){
	Table t;
	if(!(table_1.attribute.find(table_2.key)))
		return t;

	Table table_3(Record::merge(table_1.get_attributes(),table_2.get_attributes()));
	Record r;
	
	for(int i = 0; i < table_1.size(); i++){
			 int j = table_2[i].findIndex(table_2.key);
			if(j != -1)
				r = Record::merge(table_1[i], table_2[j]);
	}
}

void Table::set_attribute(Record r){
	attribute = r;
}


int Table::get_count(std::string name){
	int index = 0;
	int sum = 0;
	for(int i = 0; i < attribute.size(); i++){
		if(attribute[i] == name){
			index = i;
			break;
		}
	}
	for(int j = 0; j < v.size(); j++){
		if(v[j][index] != ""){
			sum++;
		}
	}
	return sum;
}

std::string Table::get_min(std::string name){
	int index = 0;
	std::string min;
	for(int i = 0; i < attribute.size(); i++){
		if(attribute[i] == name){
			index = i;
			break;
		}
	}
	min = v[index][0];
	for(int j = 1; j < v.size(); j++){
		if(v[j][index] < min){
			min = v[j][index];
		}
	}
	return min;
}

std::string Table::get_max(std::string name){
	int index = 0;
	std::string max;
	for(int i = 1; i < attribute.size(); i++){
		if(attribute[i] == name){
			index = i;
			break;
		}
	}
	max = v[index][0];
	for(int j = 0; j < v.size(); j++){
		if(v[j][index] > max){
			max = v[j][index];
		}
	}
	return max;
}