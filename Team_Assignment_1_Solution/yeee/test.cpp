#include "database.h"
#include "table.h"
#include "record.h"

#include <stdio.h>
#include <fstream>
#include <vector>
#include <string>

#include "json.hpp"



using json = nlohmann::json;

int main(){
    
	std::vector<std::string> checkin_t = {"time","business_id","type"};
	std::vector<std::string> user_t = {"user_id","name","review_count","yelping_since","friends","useful","funny","cool","fans","elite","average_stars",
"compliment_hot","compliment_more","compliment_profile","compliment_cute","compliment_list","compliment_note","compliment_plain","compliment_cool",
"compliment_funny","compliment_writers","compliment_photos","type"};
	std::vector<std::string> review_t = {"review_id","user_id","buisness_id","stars","date","text","useful","funny","cool","type"};
	std::vector<std::string> tip_t = {"text","date","likes","business_id","user_id","type"};
	std::vector<std::string> business_t = {"business_id","name","neighborhood","city","state","postal code","latitude","longitude","stars",
			"review_count","is_open","attributes","categories","hours","types"};

	Database yelp;
	Table tip(tip_t);
	Table user(user_t);
	Table review(review_t);
	Table checkin(checkin_t);
	Table business(business_t);

    std::ifstream infile("data/yelp_academic_dataset_checkin.json");
    std::string line;

	while (std::getline(infile, line))
	{
	    auto j = json::parse(line);
	    std::cout << j << std::endl;
	}
	
	yelp.add_table("tip",tip);

	return 0;
}
