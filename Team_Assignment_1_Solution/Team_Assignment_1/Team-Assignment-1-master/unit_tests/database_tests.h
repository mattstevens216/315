#include "catch.hpp"
#include "database.h"

TEST_CASE("database: Add Table"){
    Database d;
    Table t;

    //valid table insertion
    REQUIRE(d.add_table("test", t) == 1);

    //invlaid table insertion
    REQUIRE(d.add_table("test", t) == 0);
    REQUIRE(d.add_table("", t) == 0);

}

TEST_CASE("database: drop Table"){
    Database d;
    Table t;

    //valid table insertion
    REQUIRE(d.add_table("test", t) == 1);

    //valid table drop
    REQUIRE(d.drop_table("test") == 1);

    //invalid table drop
    REQUIRE(d.drop_table("") == 0);

}

TEST_CASE("database: list tables"){
    Database d;
    Table t;
    std::vector<std::string> names{"test1", "test2", "test3"};

    //valid table insertion
    REQUIRE(d.add_table(names[0], t) == 1);
    REQUIRE(d.add_table(names[1], t) == 1);
    REQUIRE(d.add_table(names[2], t) == 1);

    //check length
    REQUIRE(d.list_tables().size() == names.size());

    //check table names
    for (int i = 0; i < d.list_tables().size(); i++){
        REQUIRE(d.list_tables()[i] == names[i]);
    }
}

TEST_CASE("database: get tables"){
    Database d;
    Table t;
    std::vector<std::string> names{"test1", "test2", "test3"};

    //valid table insertion
    REQUIRE(d.add_table(names[0], t) == 1);
    REQUIRE(d.add_table(names[1], t) == 1);
    REQUIRE(d.add_table(names[2], t) == 1);

    //check length
    REQUIRE(d.get_tables().size() == names.size());

    //check table names
    for (int i = 0; i < d.get_tables().size(); i++){
        REQUIRE(d.get_tables()[i].name == names[i]);
    }
}

TEST_CASE("database: Query"){
    Database d;
    std::vector<std::string> attributes{"moo", "foo"};
    Table t(attributes);

    Record r1(2); r1[0] = "bar"; r1[1] = "c";
    Record r2(2); r2[0] = "not bar"; r2[1] = "c";  
    Record r3(2); r3[0] = "bar"; r3[1] = "a";
    Record r4(2); r4[0] = "not bar"; r4[1] = "a";

    REQUIRE(t.insert_record(r1) == 1);
    REQUIRE(t.insert_record(r2) == 1);
    REQUIRE(t.insert_record(r3) == 1);
    REQUIRE(t.insert_record(r4) == 1);
    REQUIRE(t.get_size() == 4);

    REQUIRE(d.add_table("bar", t) == 1);

    Table result = d.query(" moo, foo ", "bar", "( moo <> bar) AND (( foo > b ) OR foo <= a)");

    REQUIRE(result.get_attributes().size() == 2);
    REQUIRE(result.get_size() == 2);

}