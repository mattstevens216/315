#include <iostream>
#include <stdio.h>
#include "Table.h"
#include "Record.h"
#include "Database.h"
#include <vector>

using namespace std;

int main(){
    
    Database interwebz;
    Table reddit({"User", "del", "Age"});
    Table facebook({"Name", "Age", "Banned"});
    Table fourChan({"User", "Anon"});
    
    reddit.add_attribute("Karma");
    reddit.delete_attribute("del");
    reddit.add_attribute("Frequented");
    
    Record r(4);
    r[0] = "1337sniper";
    r[1] = "2011";
    r[2] = "36";
    r[3] = "r/The_Donald";
    
    Record r2(4);
    r2[0] = "winSauce13";
    r2[1] = "2015";
    r2[2] = "120";
    r2[3] = "r/Christianity";
    
    Record f(3);
    f[0] = "John";
    f[1] = "15";
    f[2] = "True";
    
    Record c(2);
    c[0] = "";
    c[1] = "True";
    
    Record c2(2);
    c[0] = "user1313";
    c[1] = "False";
    
    reddit.insert_record(r);
    reddit.insert_record(r2);
    facebook.insert_record(f);
    fourChan.insert_record(c);
    fourChan.insert_record(c2);
    
    reddit.set_key("reddit");
    facebook.set_key("facebook");
    fourChan.set_key("4chan");
    
    interwebz.add_table("reddit", reddit);
    interwebz.add_table("facebook", facebook);
    interwebz.add_table("4chan", fourChan);

    interwebz.list_tables();
    vector<Table> tables = interwebz.get_tables();
    for (auto t : tables) {
        cout << t.size() << endl;
    }
    interwebz.query("User", "facebook", "Banned == True");
    interwebz.query("*", "reddit", "Karma > 120");
    reddit.cross_join(reddit, facebook);
    reddit.natural_join(fourChan, reddit);
    
    interwebz.get_tables();
    interwebz.drop_table("4chan");
    interwebz.list_tables();
    
    return 0;
}
