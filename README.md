# ebi-biosamples-client
Command line client for EBI BioSamples database API


## How to run
1. Clone from the github  
    git clone https://github.com/theisuru/ebi-biosamples-client.git

2. Move to project directory  
    cd ./ebi-biosamples-client

3. Build the project using Maven. (Note: Here actual api is called from tests. 
Therefore, active internet connection is need for tests to succeed. 
Otherwise, you can build without tests using -DskipTests flag)  
    mvn clean install (or mvn clean install -DskipTests)

4. There are two options to run the program. 
   1. Use Maven exec plugin  
        mvn exec:exec
   2. Go to target folder after building the project and start java application in terminal  
cd ./target  
        java -jar ebi-biosamples-client-1.0.0.jar

5. Once the application is started follow the instructions in the terminal