# ebi-biosamples-client
Command line client for [EBI BioSamples database API](https://www.ebi.ac.uk/biosamples/docs/references/api/overview).


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

## Instructions for functionality

* Enter the relevant number from below, followed by enter key.  
1 to view total number of samples  
2 to view sample name of the given accession  
3 to get accessions of custom query  
4 to run example, attribute query: organism+part:liver  
5 to run example, attribute query: organism+part:liver:10:5  
6 to run example, attribute query: disease:leukaemia:1:5  
7 to run example, sample name from accession = SAMD00000001  

* Entering number 1 lists the total number of available samples
* After entering number 2, enter the accession to retrieve the sample name
* After entering number 3, attribute query must be entered it must be entered according to following format. 
Here, page and size parameters are optional
  >attribute:value:page:size  
    
    Example query 1: organism+part:liver  
    Example query 2: organism+part:liver:10:5
    
* Numbers 4 to 7 contains example queries
