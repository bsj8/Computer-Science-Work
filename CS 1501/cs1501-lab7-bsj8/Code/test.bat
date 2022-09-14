:: Usage: test.bat <file to compress>
:: Use any of the files provided inside the "Test files" folder
echo off
javac *.java
java LZWmod - < %1 > %1.zip
java LZWmod + < %1.zip > %1.recovered
fc %1 %1.recovered
