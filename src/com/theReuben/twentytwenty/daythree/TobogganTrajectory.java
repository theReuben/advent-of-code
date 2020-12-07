package com.theReuben.twentytwenty.daythree;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TobogganTrajectory {

    private static final Logger LOG = LoggerFactory.getLogger(TobogganTrajectory.class);

    private static final char TREE = '#';

    public static void main(String[] args) {

        List<String> inputList = readInTxt("src/com/theReuben/twentytwenty/daythree/TobogganTrajactory.txt");

        LOG.info("===============PART ONE===============");

        int result = countTrees(inputList, 3, 1);

        LOG.info("Total number of trees : {}", result);

        LOG.info("===============PART TWO===============");

        List<Pair<Integer, Integer>> slopeList = new ArrayList<>();
        slopeList.add(Pair.of(1, 1));
        slopeList.add(Pair.of(3, 1));
        slopeList.add(Pair.of(5, 1));
        slopeList.add(Pair.of(7, 1));
        slopeList.add(Pair.of(1, 2));

        List<Integer> resultList = countMultipleSlopes(inputList, slopeList);

        resultList.forEach(res -> LOG.info("Number of trees : {}", res));

        long resultTwo = multipleResults(resultList);

        LOG.info("Total number of trees : {}", resultTwo);

    }

    private static boolean isTree(String str, int position) {
        return str.charAt(position) == TREE;
    }

    // PART ONE
    private static int determineCurrentPosition(int[] currentPosition, int movesRight, String str) {
        return ((currentPosition[0] += movesRight) % (str.length()));
    }

    private static int countTrees(List<String> list, int movesRight, int movesDown) {
        int totalTrees = 0;
        final int[] currentPosition = {0};
        for (int level = 1; level < list.size(); level += movesDown) {
            totalTrees += isTree(list.get(level), determineCurrentPosition(currentPosition, movesRight, list.get(level))) ? 1 : 0;
        }
        return totalTrees;
    }

    // PART TWO
    private static List<Integer> countMultipleSlopes(List<String> input, List<Pair<Integer, Integer>> slopeList) {
        List<Integer> results = new ArrayList<>();
        slopeList.forEach(slope -> results.add(countTrees(input, slope.getLeft(), slope.getRight())));
        return results;
    }

    private static long multipleResults(List<Integer> results) {
        return results.stream().mapToLong(i -> i).reduce(1, (a, b) -> a * b);
    }


    // Util
    private static List<String> readInTxt(String path) {
        List<String> result = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

}
