package com.example.carbonfootprint;

import java.util.*;

public class ClusterAnalysis {
    private final double[][] clusterData;
    private final int usercluster;

    int[] optionindex = {
            5,
            2,
            5,
            3,
            4,
            3,
            3,
            4,
            4,
            3,
            3,
            3,
            4,
            2,
            5,
            5,
            5
    };

    Map<String, String> questions = new HashMap<>();

    private void initializeQuestions() {
        questions.put("How many people live in your household?", "house");
        questions.put("What is the source of energy?", "house");
        questions.put("How much energy do you approximately consume monthly?", "house");
        questions.put("How do you take a bath on daily basis?", "house");
        questions.put("How often do you do laundry?", "house");
        questions.put("How much waste you throw per week? (In kg)", "food");
        questions.put("Where do you usually purchase groceries?", "food");
        questions.put("How much do you usually spend on groceries weekly?", "food");
        questions.put("How frequently do you eat at a restaurant on a weekly basis?", "food");
        questions.put("Do you pack the leftover food in a restaurant when you can't finish?", "food");
        questions.put("If the food that you have prepared is not finished, what will you do?", "food");
        questions.put("Will you try your best to finish the food on your plate?", "food");
        questions.put("How frequently do you bring your bag whenever you go shopping?", "food");
        questions.put("Do you or your family member own a Hybrid or electric car?", "transportation");
        questions.put("How many fuel consumption on weekly basis?", "transportation");
        questions.put("How do you go to school?", "transportation");
        questions.put("What means of transport do you use the most?", "transportation");
    }


    public ClusterAnalysis(double[][] data,int usercluster) {
        this.clusterData = data;
        this.usercluster = usercluster;
        initializeQuestions();

    }

    public Map<String, String> compareWithMaxValues() {
        List<String> questionKeys = new ArrayList<>(questions.keySet());
        Map<String, String> matchingCategories = new HashMap<>();

        int[] closestClusterIndex = new int[questionKeys.size()];
        double[] smallestDiff = new double[questionKeys.size()];
        Arrays.fill(smallestDiff, Double.MAX_VALUE);

        for (int j = 0; j < questionKeys.size(); j++) {
            for (int i = 0; i < clusterData.length; i++) {
                double diff = Math.abs(optionindex[j] - clusterData[i][j]);
                if (diff < smallestDiff[j]) {
                    smallestDiff[j] = diff;
                    closestClusterIndex[j] = i;
                }
            }
        }

        for (int j = 0; j < questionKeys.size(); j++) {
            if (closestClusterIndex[j] == usercluster) {
                String question = questionKeys.get(j);
                String category = questions.get(question);
                matchingCategories.put(question, category);
            }
        }

        return matchingCategories;
    }
}
