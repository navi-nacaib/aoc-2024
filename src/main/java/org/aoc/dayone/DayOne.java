package org.aoc.dayone;

import org.aoc.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DayOne {

    public static void solution() {

        String resourcePath = "day1.txt";

        try {
            int[][] lists = readListsFromResources(resourcePath);

            int[] leftList = lists[0];
            int[] rightList = lists[1];

            int totalDistance = calculateTotalDistance(leftList, rightList);
            int similarityScore = calculateSimilarityScore(leftList, rightList);

            System.out.println("Total Distance (Part 1 solution): " + totalDistance);
            System.out.println("Similarity Score (Part 2 solution): " + similarityScore);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static int[][] readListsFromResources(String resourcePath) throws IOException {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(resourcePath))))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    leftList.add(Integer.parseInt(parts[0]));
                    rightList.add(Integer.parseInt(parts[1]));
                }
            }
        }

        int[] leftArray = leftList.stream().mapToInt(Integer::intValue).toArray();
        int[] rightArray = rightList.stream().mapToInt(Integer::intValue).toArray();

        return new int[][]{leftArray, rightArray};
    }

    public static int calculateTotalDistance(int[] leftList, int[] rightList) {
        if (leftList.length != rightList.length) {
            throw new IllegalArgumentException("Both lists must have the same length");
        }

        Arrays.sort(leftList);
        Arrays.sort(rightList);

        int totalDistance = 0;

        for (int i = 0; i < leftList.length; i++) {
            totalDistance += Math.abs(leftList[i] - rightList[i]);
        }

        return totalDistance;
    }

    public static int calculateSimilarityScore(int[] leftList, int[] rightList) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : rightList) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int similarityScore = 0;

        for (int num : leftList) {
            int countInRight = frequencyMap.getOrDefault(num, 0);
            similarityScore += num * countInRight;
        }

        return similarityScore;
    }
}
