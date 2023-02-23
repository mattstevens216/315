#include <string>
#include <iostream>
#include "catch.hpp"
#include "record.h"

TEST_CASE("Record: Size"){
    Record r1;
    Record r2(10);

    //constructor test
    REQUIRE(r1.get_size() == 0);
    REQUIRE(r2.get_size() == 10);
}

TEST_CASE("Record: Addressing"){
    Record r(10);

    for (int i = 0; i < r.get_size(); i++){
        //empty strings
        REQUIRE(r[i] == "");

        //setting string values
        r[i] = std::to_string(i);
        REQUIRE(r[i] == std::to_string(i));

        //changing string vlues
        r[i] = "";
        REQUIRE(r[i] == "");
    }
}

TEST_CASE("Record: Insertion and deletion from a record"){
    Record r;

    r.push_back("0");
    r.push_back("1");
    
    REQUIRE(r[0] == "0");
    REQUIRE(r.get_size() == 2);

    r.remove(0);
    REQUIRE(r[0] == "1");
    REQUIRE(r.get_size() == 1);
}
