#include "Record.h"

    Record::Record () {} //constructor
	
	Record::Record(int size){
		for(int i = 0; i < size; i++)
			v.push_back("");
	}
	void Record::add(std::string name){
	v.push_back(name);
	}
	void Record::remove(std::string name){
		for(int i = 0; i < v.size(); i++){
			if(name == v[i]){
				v.erase(v.begin() + i);
			}
		}
	}
    int Record::size(){
		return v.size();
	}
	std::string& Record::operator[](int index){
		return v[index];
	}
	Record& Record::operator=(const Record & r){
		v = r.v;
		return *this;
	}
	
	Record Record::merge(Record r1, Record r2){
		
		Record r3;
		for(int i = 0; i < r1.size(); ++i){
			r3.add(r1[i]);
		}
		for(int i=0;i<r2.size();++i){
			if(!(r1.find(r2[i])))
				r3.add(r2[i]);
		}
		return r3;
	}
	
	Record Record::getSubRecord(Record r, std::vector<int> sub_Vec){
		Record out_record;
		
		for(int i = 0 ;i < sub_Vec.size();++i){
			out_record.add(r[sub_Vec[i]]);
		}
		
		return out_record;
	}
	
	bool Record::find(std::string s){
		for(int i = 0; i < v.size();++i){
			if(v[i]==s)
				return true;
		}
		return false;
	}
	
	int Record::findIndex(std::string s){
		for(int i = 0; i < v.size();++i){
			if(v[i]==s)
				return i;
		}
		return -1;	
	}