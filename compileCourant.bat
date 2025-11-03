cd .\courant\
mvn clean package 
echo Updating centrale lib...
del ..\centrale\lib\courant-1.0.0.jar
copy .\target\courant-1.0.0.jar ..\centrale\lib\
