package com.theReuben.twentytwenty.dayone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportRepair {

    private final static Integer total = 2020;

    private final static Logger LOG = LoggerFactory.getLogger(ReportRepair.class);

    public static void main(String[] args) {
        List<Integer> inputList = readInTxt("src/com/theReuben/twentytwenty/dayone/ReportRepair.txt");

        Pair<Integer, Integer> resultPair = determineSummingPair(inputList);

        LOG.info("Pair determined : {}, {}", resultPair.getLeft(), resultPair.getRight());

        Integer result = multiplyPair(resultPair);

        LOG.info("Final result : {}", result);
    }

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
