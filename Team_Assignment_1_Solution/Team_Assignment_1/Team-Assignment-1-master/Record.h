#ifndef RECORD_H
#define RECORD_H

#include <string>
#include <vector>

class Record {
private:
	//ORDERED set of strings
	std::vector<std::string> v;

public:
	Record();
	Record(int size);
	void add(std::string name);
	void remove(std::string name);
	int size();
	std::string& operator[](int index);
	Record& operator=(const Record & r);
	static Record merge(Record r1, Record r2);
	bool find(std::string s);
	int findIndex(std::string s);
	static Record getSubRecord(Record r, std::vector<int> sub_Vec);
};
#endif