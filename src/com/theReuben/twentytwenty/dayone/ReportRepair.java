package com.theReuben.twentytwenty.dayone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportRepair {

    private final static Integer total = 2020;

    private final static Logger LOG = LoggerFactory.getLogger(ReportRepair.class);

    public static void main(String[] args) {
        List<Integer> inputList = readInTxt("src/com/theReuben/twentytwenty/dayone/ReportRepair.txt");

        LOG.info("===============PART ONE===============");

        Pair<Integer, Integer> resultPair = determineSummingPair(inputList);

        LOG.info("Pair determined : {}, {}", resultPair.getLeft(), resultPair.getRight());

        LOG.info("Sum : {}", resultPair.getLeft() + resultPair.getRight());

        Integer result = multiplyPair(resultPair);

        LOG.info("Final result : {}", result);

        LOG.info("===============PART TWO===============");

        Map<Integer, Pair<Integer, Integer>> tripleMap = generateCompoundList(inputList);

        Triple<Integer, Integer, Integer> resultTriple = determineSummingTriple(tripleMap, inputList);

        LOG.info("Triple determined : {}, {}, {}", resultTriple.getLeft(), resultTriple.getMiddle(), resultTriple.getRight());

        LOG.info("Sum : {}", resultTriple.getLeft() + resultTriple.getMiddle() + resultTriple.getRight());

        Integer resultPartTwo = multiplyTriple(resultTriple);

        LOG.info("Final result : {}", resultPartTwo);
    }


    // PART ONE
    private static Pair<Integer, Integer> determineSummingPair(List<Integer> numbers) {
        Map<Integer, Integer> resultMap = new HashMap<>();

        for (Integer number : numbers) {
            if (resultMap.containsKey(number)) {
                return Pair.of(number, resultMap.get(number));
            } else if (!resultMap.containsKey(number)) {
                resultMap.put(total-number, number);
            }
        }

        return Pair.of(total, 0);
    }

    private static Integer multiplyPair(Pair<Integer, Integer> pair) {
        return pair.getLeft() * pair.getRight();
    }


    // PART TWO
    private static Integer multiplyTriple(Triple<Integer, Integer, Integer> triple) {
        return triple.getLeft() * triple.getMiddle() * triple.getRight();
    }

    private static Map<Integer, Pair<Integer, Integer>> generateCompoundList(List<Integer> list) {
        Map<Integer, Pair<Integer, Integer>> compoundMap = new HashMap<>();

        IntStream.range(0, list.size()).forEach( i -> {
            Integer currentInt = list.get(i);
            IntStream.range(i+1, list.size()).forEach( j -> {
                compoundMap.put(currentInt + list.get(j), Pair.of(currentInt, list.get(j)));
            });
        });

        return compoundMap;
    }

    private static Triple<Integer, Integer, Integer> determineSummingTriple(Map<Integer, Pair<Integer, Integer>> map, List<Integer> numbers) {
        Map<Integer, Integer> resultMap = new HashMap<>();

        for (Integer number : numbers) {
            if (map.containsKey(total - number)) {
                return Triple.of(number, map.get(total - number).getLeft(), map.get(total - number).getRight());
            } else if (!map.containsKey(total - number)) {
                resultMap.put(total-number, number);
            }
        }

        return Triple.of(total, 0, 0);
    }


    // Util
    private static List<Integer> readInTxt(String path) {
        List<Integer> result = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.add(Integer.valueOf(data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

}
