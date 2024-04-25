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
        questions.put("How many people live in your household?", "home");
        questions.put("What is the source of energy?", "home");
        questions.put("How much energy do you approximately consume monthly?", "home");
        questions.put("How do you take a bath on daily basis?", "home");
        questions.put("How often do you do laundry?", "home");
        questions.put("How much waste you throw per week? (In kg)", "waste");
        questions.put("Where do you usually purchase groceries?", "waste");
        questions.put("How much do you usually spend on groceries weekly?", "waste");
        questions.put("How frequently do you eat at a restaurant on a weekly basis?", "waste");
        questions.put("Do you pack the leftover food in a restaurant when you can't finish?", "waste");
        questions.put("If the food that you have prepared is not finished, what will you do?", "waste");
        questions.put("Will you try your best to finish the food on your plate?", "waste");
        questions.put("How frequently do you bring your bag whenever you go shopping?", "waste");
        questions.put("Do you or your family member own a Hybrid or electric car?", "Transportation");
        questions.put("How many fuel consumption on weekly basis?", "Transportation");
        questions.put("How do you go to school?", "Transportation");
        questions.put("What means of transport do you use the most?", "Transportation");
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
