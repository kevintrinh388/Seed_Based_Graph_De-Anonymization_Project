# Seed_Based_Graph_De-Anonymization_Project

This was my attempt at solving the following problem listed below. (This was a class project from CSC4223/6223 PRIVACY)


##
In this project, you will utilize seed based de-anonymization to solve real problems.

In the seed based de-anonymization part, you have two graphs and a seed node pairs file. The graphs are given in edgelist filetype.
seed_G1.edgelist is the original graph and seed_G2.edgelist is the anonymized graph. The file seed_node_pairs.txt gives some matched node seed pairs.  In the file seed_node_pairs.txt the first column refers the node number in graph G1 and the second column refers the node number in graph G2.
As the answer of this problem, you need to give a file shows full mapping of seed nodes in G1 and G2 and follows the format shown in the file seed_node_pairs.txt.





## Getting Started (VERY IMPORTANT)
Before we can run the program, make sure that the folder has been extracted correctly.
If you decided to place the folder on your desktop, the folder path should look something like Desktop\PrivacyProject .
For example: Since I am on windows the full path will look something like "C:\Users\Mypcname\Desktop\PrivacyProject".
This is to make sure that the read methods will be able to read the files in the input folder.


## Running the program
Once that is done, go ahead and open up your IDE of choice for Java (examples: Eclipse, IntelliJ, VSCode, etc.). It does not matter which IDE you have as long as it works with Java.
After that, add the "java project" folder to your IDE's workstation and run the "Client.java" file that is located in the "src" folder.
Finally, when the program finishes running, a result file will be created in the "output" folder.

NOTE: If you want to change what input files are going to be read, go to the read methods at the bottom of the Client.java code and change the file paths.
(The default input files that are read will be "slide_seed_G1.txt", "slide_seed_G2.txt", "slide_node_pairs.txt")
(The three slide text files above are based off of the seed based de anonymization example slides)
(The three read methods are readG1, readG2,and readNodePairs)



## Folder Structure

The workspace contains multiple folders, where:

- `input`: The folder that will contain the input text files
- `lib`: The folder to maintain dependencies
- `output`: The folder that will contain the end result text file
- `src`: The folder to maintain sources. This folder will hold all of our classes as well as the main client class.


