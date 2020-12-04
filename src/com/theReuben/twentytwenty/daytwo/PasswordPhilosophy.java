package com.theReuben.twentytwenty.daytwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PasswordPhilosophy {

    private final static Logger LOG = LoggerFactory.getLogger(PasswordPhilosophy.class);

    public static void main(String[] args) {

        List<PasswordData> inputList = readInTxt("src/com/theReuben/twentytwenty/daytwo/PasswordPhilosophy.txt");

        LOG.info("===============PART ONE===============");

        long numberValid = countValidPassword(inputList);

        LOG.info("Number of valid passwords : {}", numberValid);

        LOG.info("===============PART TWO===============");

        long numberValidPartTwo = countValidPasswordPartTwo(inputList);

        LOG.info("Number of valid passwords : {}", numberValidPartTwo);

    }

    // PART ONE
    private static long countChars(String str, char c) {
        return str.chars().filter(ch -> ch == c).count();
    }

    private static boolean isValidPassword(PasswordData passwordData) {
        long count = countChars(passwordData.getPassword(), passwordData.getCharacter());

        int lb = passwordData.getLowerBound();
        int up = passwordData.getUpperBound();

        return count >= lb && count <= up;
    }

    private static long countValidPassword(List<PasswordData> input) {
        return input.stream().mapToLong(in -> (isValidPassword(in) ? 1 : 0)).sum();
    }

    // PART TWO
    private static boolean isValidPasswordPartTwo(PasswordData passwordData) {
        return passwordData.getPassword().charAt(passwordData.getLowerBound()-1) == passwordData.getCharacter()
                ^ passwordData.getPassword().charAt(passwordData.getUpperBound()-1) == passwordData.getCharacter();
    }

    private static long countValidPasswordPartTwo(List<PasswordData> input) {
        return input.stream().mapToLong(in -> (isValidPasswordPartTwo(in) ? 1 : 0)).sum();
    }

    // Util
    private static List<PasswordData> readInTxt(String path) {
        List<PasswordData> result = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                PasswordData passwordData = new PasswordData();
                String[] dataArray = data.split(" ");

                // Extract Range
                String[] dataArrayRange = dataArray[0].split("-");
                passwordData.setLowerBound(Integer.parseInt(dataArrayRange[0]));
                passwordData.setUpperBound(Integer.parseInt(dataArrayRange[1]));

                // Extract char
                passwordData.setCharacter(dataArray[1].split(":")[0].charAt(0));

                // Extract password
                passwordData.setPassword(dataArray[2]);

                result.add(passwordData);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

    private static class PasswordData {
        private int lowerBound;
        private int upperBound;
        private char character;
        private String password;

        public PasswordData() {
        }

        public int getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(int lowerBound) {
            this.lowerBound = lowerBound;
        }

        public int getUpperBound() {
            return upperBound;
        }

        public void setUpperBound(int upperBound) {
            this.upperBound = upperBound;
        }

        public char getCharacter() {
            return character;
        }

        public void setCharacter(char character) {
            this.character = character;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
