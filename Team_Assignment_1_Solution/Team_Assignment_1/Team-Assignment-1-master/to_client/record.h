//-----------------------------------------------------------
// FILENAME: record.h
// AUTHOR: Nahiyan Khandker, Mason Clemmenson, Austin Gloyna
// Written for team project 1 - CSCE 315
// Instructor: Dr.Keyser
//-----------------------------------------------------------

//-----------------------------------------------------------
//INCLUDES / DEFINITIONS / NAMESPACE
//-----------------------------------------------------------

#ifndef RECORD_H
#define RECORD_H

#include <string>
#include <vector>

//-----------------------------------------------------------
// CLASS: Record
//-----------------------------------------------------------

class Record{
public:
	Record();
	Record(int sz);
	int get_size();
	void push_back(std::string str);
	void remove(int index);

	//indexing operators
	std::string& operator[](size_t index);
	const std::string& operator[](size_t index) const;

private:
	std::vector<std::string> rec;
	int size;

};
#endif /* RECORD_H */
