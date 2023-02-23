
clean:
	@rm -rf ./build
	@rm -rf ./unit_tests

build_dir: clean
	@mkdir build
	@cp ../../src/* build

build_lib: build_dir
	g++ -std=c++11 build/*.cpp -shared -fPIC -o build/libdb.so
	mv build/libdb.so ../../to_client

build_tests: build_dir
	@cp *.* build
	g++ -std=c++11 build/*.cpp -o unit_tests
	@rm -rf ./build

run_tests: build_tests
	./unit_tests