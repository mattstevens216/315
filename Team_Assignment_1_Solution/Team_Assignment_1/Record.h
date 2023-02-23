#ifndef RECORD_H
#define RECORD_H

#include <string>
#include <vector>

class Record {
private:
	int size;
	//ORDERED set of strings
	std::vector<std::string> v;

public:
	Record();
	Record(int size);

	~Record();

	int get_size();
	string& operator[](int index);

};

#endif