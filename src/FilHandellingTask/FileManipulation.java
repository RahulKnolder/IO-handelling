package FilHandellingTask;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileManipulation {

    //method to create file as it is used multiple time
    static public void createFile(String fileName) {
        File file = new File(fileName);
        // Check if the file already exists
        if (file.exists()) {
            System.out.println("File already exists!");
            System.out.println("will over ride the data");
        } else {
            System.out.println("file create");
        }
    }


    //Read a file Method and return Bufferred Reader object so can be used multiple times
    static BufferedReader read_file(String fileName) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader;
    }


    //write file method uing IO operations and data Structures
    static void writeFile(String fileName) {

        //invoke create file method
        createFile(fileName);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Please enter the value");
        Scanner write = new Scanner(System.in);
        HashMap<String, Integer> infoMap = new HashMap<>();
        System.out.println("Write in the file");

        //input in map
        for (int indexofInformation = 1; indexofInformation <= 2; indexofInformation++) {
            System.out.print("Enter name" + indexofInformation + ":");
            String key = write.next();
            System.out.print("Enter age " + indexofInformation + ":");
            int value = write.nextInt();
            infoMap.put(key, value);
        }

        // witting the data of map to file
        for (Map.Entry<String, Integer> entry : infoMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            try {
                fileWriter.write(key + "," + value + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        // Close the FileWriter object
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static int calculateAvarage(String fileName) {

        //reading function invoked
        BufferedReader bufferedReader = read_file(fileName);

        int countPersons = 0;
        int totalAge = 0;
        String line;

        try {
            //logic to get the age form the file and convert it into integer and then calculating average
            while ((line = bufferedReader.readLine()) != null) {
                String[] result = line.split(",");
                String age = "";
                String str = String.join("", result[1]);
                int converStringAgeToInteger = Integer.parseInt(str);
                totalAge = totalAge + converStringAgeToInteger;
                countPersons++;
            }

        } catch (IOException catchException) {
            System.out.println(catchException);
        }

        //calculate average of student
        int avgAge = totalAge / countPersons;

        return avgAge;
    }


    //modifying and Writite in other file
    static void writeInotherfile(String readfile, String writeFile) {

        //invoking create file method
        createFile(writeFile);

        //invoking read_file method
        BufferedReader bufferedReader = read_file(readfile);

        try {
            FileWriter fileWriter = new FileWriter(writeFile);
            String line;

            //logic to store in file output file after manipulation
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll(",", "(");
                line = line.concat(")");
                fileWriter.write(line+ "\n");

            }
            fileWriter.close();
        } catch (Exception catchException) {
            System.out.println(catchException);
        }
    }





    public static void main(String[] args) throws IOException {
        Scanner readValues = new Scanner(System.in);


        System.out.println("enter the name of the file you want to create");
        String fileName = readValues.nextLine();
        writeFile(fileName);


        //avagage calculator
        System.out.println("avearage of the age"+calculateAvarage(fileName));



        System.out.println("Input the name of the output file");
        String outputFile = readValues.nextLine();

        //Writing in another other file
        writeInotherfile(fileName, outputFile);

        System.out.println("copied data form "+fileName+" manipulated and pasted to "+outputFile);


    }
}
