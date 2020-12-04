package com.theReuben.twentytwenty.daytwo;

import com.theReuben.twentytwenty.dayone.ReportRepair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PasswordPhilosophy {

    private final static Logger LOG = LoggerFactory.getLogger(PasswordPhilosophy.class);

    public static void main(String[] args) {

        List<Triple<Pair<Integer, Integer>, String, String>> inputList = readInTxt("src/com/theReuben/twentytwenty/daytwo/PasswordPhilosophy.txt");

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

    private static boolean isValidPassword(Triple<Pair<Integer, Integer>, String, String> triple) {
        long count = countChars(triple.getRight(), triple.getMiddle().charAt(0));

        Integer lb = triple.getLeft().getLeft();
        Integer up = triple.getLeft().getRight();

        return count >= lb && count <= up;
    }

    private static long countValidPassword(List<Triple<Pair<Integer, Integer>, String, String>> input) {
        return input.stream().mapToLong(in -> (isValidPassword(in) ? 1 : 0)).sum();
    }

    // PART TWO
    private static boolean isValidPasswordPartTwo(Triple<Pair<Integer, Integer>, String, String> triple) {
        return triple.getRight().charAt(triple.getLeft().getLeft()-1) == triple.getMiddle().charAt(0)
                ^ triple.getRight().charAt(triple.getLeft().getRight()-1) == triple.getMiddle().charAt(0);
    }

    private static long countValidPasswordPartTwo(List<Triple<Pair<Integer, Integer>, String, String>> input) {
        return input.stream().mapToLong(in -> (isValidPasswordPartTwo(in) ? 1 : 0)).sum();
    }

    // Util
    private static List<Triple<Pair<Integer, Integer>, String, String>> readInTxt(String path) {
        List<Triple<Pair<Integer, Integer>, String, String>> result = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataArray = data.split(" ");
                // Extract Range
                String[] dataArrayRange = dataArray[0].split("-");
                Pair<Integer, Integer> pair = Pair.of(Integer.valueOf(dataArrayRange[0]), Integer.valueOf(dataArrayRange[1]));

                // Extract char
                String character = dataArray[1].split(":")[0];

                // Extract password
                String password = dataArray[2];

                result.add(Triple.of(pair, character, password));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

}
