package FileHandelling_2;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileManipulation {

    //method to create as it has been used multilple time
    static public void createFile(String fileName) {
        File file = new File(fileName);
        // Check if the file already exists
        if (file.exists()) {
            System.out.println("File already exists! and will over ride the data");
        } else {
            System.out.println("file create");
        }
    }

    //Read buffer reader in method as it used multiple time
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


    //write the data to the file by taking input from the user
    static void writeFile(String fileName) {

        createFile(fileName);

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner write = new Scanner(System.in);
        System.out.println("Please enter the number of entries");
        int numberOfIntries = write.nextInt();

        String[][] entries = new String[numberOfIntries][2];
        write.nextLine();

        //logic to take input in the array
        for (int indexOfRow = 0; indexOfRow < numberOfIntries; indexOfRow++) {
            for (int indexOfColoumn = 0; indexOfColoumn < 2; indexOfColoumn++) {
                int indexCount = indexOfRow;
                if (indexOfColoumn == 0) {
                    System.out.println("enter name for " + (indexCount + 1) + " entery");
                } else {
                    System.out.println("enter age for " + (indexCount + 1) + " entry");
                }
                entries[indexOfRow][indexOfColoumn] = write.nextLine();
            }
        }

        //logic to write from array to file
        for (int indexOfRow = 0; indexOfRow < numberOfIntries; indexOfRow++) {

            for (int indexOfColoumn = 0; indexOfColoumn < 2; indexOfColoumn++) {
                try {
                    fileWriter.write(entries[indexOfRow][indexOfColoumn]);

                    if (indexOfColoumn != numberOfIntries - 1) { // Don't print comma after last element in row
                        fileWriter.write(", ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                fileWriter.write("\n");
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

        //reader function invoked
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
                str = str.replaceAll(" ", "");
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
    static void writeInOtherfile(String readfile, String writeFile) {

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
                line = line.replaceAll(" ", "");
                line = line.concat(")");
                fileWriter.write(line + "\n");
            }
            fileWriter.close();
        } catch (Exception catchException) {
            System.out.println(catchException + "\n");
        }
    }


    public static void main(String[] args) throws IOException {

        Scanner readValues = new Scanner(System.in);

        System.out.println("enter the name of the file you want to create");
        String fileName = readValues.nextLine();
        writeFile(fileName);


        //average calculator
        System.out.println("average age of all the entries is = " + calculateAvarage(fileName));


        System.out.println("Input the name of the output file");
        String outputFile = readValues.nextLine();

        //Writing in another other file
        writeInOtherfile(fileName, outputFile);


    }
}
