#include "catch.hpp"
#include "table.h"
#include <string>

TEST_CASE("Table: Add Attribute"){
    std::vector<std::string> init_attributes{"a", "b", "c"};
    std::vector<std::string> attributes{"flavor", "colour", "texture"};
    std::vector<std::string> invalid_attributes{"", "texture"};
    Table t(init_attributes);

    //proper initialization
    REQUIRE(t.get_attributes().size() == init_attributes.size());

    //valid attributes
    for (int i = 0; i < attributes.size(); i++){
        REQUIRE(t.add_attribute(attributes[i]) == 1);
    }

    //expected failures
    for (int i = 0; i < invalid_attributes.size(); i++){
        REQUIRE(t.add_attribute(invalid_attributes[i]) == 0);
    }

    //final size check
    REQUIRE(t.get_attributes().size() == (init_attributes.size() + attributes.size()));

}

TEST_CASE("Table: Delete Attribute"){
    std::vector<std::string> attributes{"a", "b", "c", "d"};
    Table t(attributes);

    //pre-check
    REQUIRE(t.get_attributes().size() == attributes.size());

    //deletion tests
    REQUIRE(t.delete_attribute(attributes[0]) == 1);
    REQUIRE(t.delete_attribute(attributes[1]) == 1);
    REQUIRE(t.delete_attribute(attributes[2]) == 1);

    //expected failures
    REQUIRE(t.delete_attribute("e") == 0);
    REQUIRE(t.delete_attribute("") == 0);

    //final size check
    REQUIRE(t.get_attributes().size() == 1);

}

TEST_CASE("Table: Insert Record"){
    Table t;
    Record r;

    //single insertion
    REQUIRE(t.insert_record(r) == 1);
    REQUIRE(t.get_size() == 1);

    //multiple insertion
    for (int i=0; i<20; i++){
        REQUIRE(t.insert_record(r) == 1);
    }

    //final size check
    REQUIRE(t.get_size() == 21);
}

TEST_CASE("Table: Get Record"){
    //dummy attribute to increase the size of the table so that we can put 2 unique
    //records into the table for testing
    std::vector<std::string> attributes{"test_string"};
    Table t(attributes);
    
    Record r1(0);
    Record r2(1);

    //insertion
    REQUIRE(t.insert_record(r1) == 1);
    REQUIRE(t.insert_record(r2) == 1);
    REQUIRE(t.get_size() == 2);

    //indexing test
    REQUIRE((*t.get_record(1)).get_size() == 1);

    //expected failure
    REQUIRE(t.get_record(-1) == NULL);

}

TEST_CASE("Table: Specify Key"){
    std::vector<std::string> attributes{"a", "b", "c"};
    Table t(attributes);

    //build records
    Record r1(3); r1[0] = "test1"; r1[1] = "test"; r1[2] = "test";
    Record r2(3); r2[0] = "test2"; r2[1] = "test"; //r2[2] is null
    Record r3(3); r3[0] = "test3"; r3[1] = "test"; //r3[2] is null

    //add records to table
    REQUIRE(t.insert_record(r1) == 1);
    REQUIRE(t.insert_record(r2) == 1);
    REQUIRE(t.get_size() == 2);

    //valid key specification
    REQUIRE(t.specify_key(attributes[0]) == 1);

    //adding record after key
    REQUIRE(t.insert_record(r3) == 1);
    //invalid record insertion for key
    REQUIRE(t.insert_record(r1) == 0);
    //size check
    REQUIRE(t.get_size() == 3);
    
    //expected failure
    REQUIRE(t.specify_key("") == 0);
    REQUIRE(t.specify_key(attributes[1]) == 0);
    REQUIRE(t.specify_key(attributes[2]) == 0);
}

TEST_CASE("Table: count"){
    std::vector<std::string> attributes{"temp1", "temp2"};
    Table t(attributes);

    //build records
    Record r1(2); r1[0] = "test";
    Record r2(2); r2[1] = "test";

    //add records to table
    REQUIRE(t.insert_record(r1) == 1);
    REQUIRE(t.insert_record(r2) == 1);
    REQUIRE(t.get_size() == 2);

    //invalid count
    REQUIRE(t.count("") == 0);

    //valid counts
    REQUIRE(t.count(attributes[0]) == 1);
    REQUIRE(t.count(attributes[1]) == 1);
}

TEST_CASE("Table: min/max"){
    std::vector<std::string> attributes{"temp1", "temp2"};
    Table t(attributes);

    //build records
    Record r1(2); r1[0] = "test1"; //r1[1] is null
    Record r2(2); r2[0] = "test2"; r2[1] = "test2"; 

    //add records to table
    REQUIRE(t.insert_record(r1) == 1);
    REQUIRE(t.insert_record(r2) == 1);
    REQUIRE(t.get_size() == 2);

    //invalid max
    REQUIRE(t.max("") == "");

    //invalid min
    REQUIRE(t.min("") == "");

    //valid min/max
    REQUIRE(t.min(attributes[0]) == "test1");
    REQUIRE(t.max(attributes[0]) == "test2");

    //ignore nulls check
    REQUIRE(t.min(attributes[1]) == t.max(attributes[1]));
}

TEST_CASE("Table: Cross Join"){
    std::vector<std::string> attributes1{"temp1", "temp2"};
    std::vector<std::string> attributes2{"temp3", "temp4"};
    std::vector<std::string> result_attributes{"temp1", "temp2", "temp3", "temp4"};
    Table t1(attributes1);
    Table t2(attributes2);

    //build records
    Record r1(2); r1[0] = "test1"; r1[1] = "test1";
    Record r2(2); r2[0] = "test2"; r2[1] = "test2";
    
    Record r3(2); r3[0] = "test1"; r3[1] = "test1";
    Record r4(2); r4[0] = "test2"; r4[1] = "test2";

    Record res1(4); res1[0] = "test1"; res1[1] = "test1"; res1[2] = "test1"; res1[3] = "test1";
    Record res2(4); res2[0] = "test1"; res2[1] = "test1"; res2[2] = "test2"; res2[3] = "test2";
    Record res3(4); res3[0] = "test2"; res3[1] = "test2"; res3[2] = "test1"; res3[3] = "test1";
    Record res4(4); res4[0] = "test2"; res4[1] = "test2"; res4[2] = "test2"; res4[3] = "test2";

    //build tables
    REQUIRE(t1.insert_record(r1) == 1);
    REQUIRE(t1.insert_record(r2) == 1);
    REQUIRE(t1.get_size() == 2);

    REQUIRE(t2.insert_record(r3) == 1);
    REQUIRE(t2.insert_record(r4) == 1);
    REQUIRE(t2.get_size() == 2);

    Table result = cross_join(t1, t2);

    //test result size
    for (int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE(result.get_attributes()[i] == result_attributes[i]);
    }

    REQUIRE(result.get_size() == 4);

    //testing records match a correct cross join
    for(int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE((*result.get_record(0))[i] == res1[i]);
    }
    for(int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE((*result.get_record(1))[i] == res2[i]);
    }
    for(int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE((*result.get_record(2))[i] == res3[i]);
    }
    for(int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE((*result.get_record(3))[i] == res4[i]);
    }

}

TEST_CASE("Table: Natural Join"){
    std::vector<std::string> attributes1{"temp1", "temp2"};
    std::vector<std::string> attributes2{"temp2", "temp3"};
    std::vector<std::string> result_attributes{"temp2", "temp1", "temp3"};
    Table t1(attributes1);
    Table t2(attributes2);

    //build records
    Record r1(2); r1[0] = "test1"; r1[1] = "test1";
    Record r2(2); r2[0] = "test2"; r2[1] = "test2";
    
    Record r3(2); r3[0] = "test1"; r3[1] = "test1";
    Record r4(2); r4[0] = "test2"; r4[1] = "test2";

    Record res1(3); res1[0] = "test1"; res1[1] = "test1"; res1[2] = "test1";
    Record res2(3); res2[0] = "test2"; res2[1] = "test2"; res2[2] = "test2";

    //build tables
    REQUIRE(t1.insert_record(r1) == 1);
    REQUIRE(t1.insert_record(r2) == 1);
    REQUIRE(t1.get_size() == 2);

    REQUIRE(t2.insert_record(r3) == 1);
    REQUIRE(t2.insert_record(r4) == 1);
    REQUIRE(t2.get_size() == 2);

    Table result = natural_join(t1, t2);

    //size check
    REQUIRE(result.get_attributes().size() == 3);

    //record check
    for(int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE((*result.get_record(0))[i] == res1[i]);
    }
    for(int i = 0; i < result.get_attributes().size(); i++){
        REQUIRE((*result.get_record(1))[i] == res2[i]);
    }
}