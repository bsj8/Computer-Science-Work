# first change the file to executable by: chmod +x ./test.sh
# run as ./test.sh <filename to compress>
javac *.java
java LZWmod - < $1 > $1.zip
java LZWmod + < $1.zip > $1.recovered
diff $1 $1.recovered
