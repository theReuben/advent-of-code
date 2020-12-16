package com.theReuben.twentytwenty.dayfour;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PassportProcessing {

    private final static Logger LOG = LoggerFactory.getLogger(PassportProcessing.class);
    
    private final static Pattern HGT_PATTERN = Pattern.compile("^(1([5-8][0-9]|(9[0-3])))cm|(59|6[0-9]|7[0-6])in$");
    private final static Pattern ECL_PATTERN = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$");
    private final static Pattern HCL_PATTERN = Pattern.compile("^#[0-9a-f]{6}$");
    private final static Pattern PID_PATTERN = Pattern.compile("^[0-9]{9}$");

    public static void main(String[] args) {

        List<EnumMap<PassportCredentials, String>> input = readInTxt("src/com/theReuben/twentytwenty/dayfour/PassportProcessingFlat.txt");

        LOG.info("===============PART ONE===============");

        long result = countValidPassports(input);

        LOG.info("Number of valid passports : {}", result);

        LOG.info("===============PART TWO===============");

        long resultTwo = countValidPassportsTwo(input);

        LOG.info("Number of valid passports : {}", resultTwo);

    }

    // PART ONE
    private static boolean isValid(EnumMap<PassportCredentials, String> map) {
        return map.size() == 8 || (map.size() == 7 && !map.containsKey(PassportCredentials.CID));
    }

    private static long countValidPassports(List<EnumMap<PassportCredentials, String>> list) {
        return list.stream().filter(PassportProcessing::isValid).count();
    }

    // PART TWO
    private static boolean isValidTwo(EnumMap<PassportCredentials, String> map) {
        return (map.size() == 8 || (map.size() == 7 && !map.containsKey(PassportCredentials.CID)))
                && Integer.parseInt(map.get(PassportCredentials.BYR)) >= 1920 && Integer.parseInt(map.get(PassportCredentials.BYR)) <= 2002
                && Integer.parseInt(map.get(PassportCredentials.IYR)) >= 2010 && Integer.parseInt(map.get(PassportCredentials.IYR)) <= 2020
                && Integer.parseInt(map.get(PassportCredentials.EYR)) >= 2020 && Integer.parseInt(map.get(PassportCredentials.EYR)) <= 2030
                && HGT_PATTERN.matcher(map.get(PassportCredentials.HGT)).find()
                && ECL_PATTERN.matcher(map.get(PassportCredentials.ECL)).find()
                && HCL_PATTERN.matcher(map.get(PassportCredentials.HCL)).find()
                && PID_PATTERN.matcher(map.get(PassportCredentials.PID)).find();
    }

    private static long countValidPassportsTwo(List<EnumMap<PassportCredentials, String>> list) {
        return list.stream().filter(PassportProcessing::isValidTwo).count();
    }

    // Util
    private static List<EnumMap<PassportCredentials, String>> readInTxt(String path) {
        List<EnumMap<PassportCredentials, String>> result = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                EnumMap<PassportCredentials, String> enumMap = new EnumMap<>(PassportCredentials.class);
                while (!data.isBlank()) {
                    String[] keyValue = data.split("\\:");
                    enumMap.put(PassportCredentials.valueOf(keyValue[0].toUpperCase()), keyValue[1]);
                    if (!myReader.hasNextLine()) {
                        break;
                    }
                    data = myReader.nextLine();
                }
                result.add(enumMap);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

    private enum PassportCredentials {

        BYR,
        IYR,
        EYR,
        HGT,
        HCL,
        ECL,
        PID,
        CID;

    }

}
