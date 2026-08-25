package com.example.carbonfootprint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Identifies which survey questions the user's cluster performs worst on.
 *
 * For each question, the cluster whose centroid is closest to the highest
 * (most carbon-intensive) option is found; if that is the user's cluster,
 * the question is flagged as an improvement area for the user.
 *
 * Questions are kept in survey order (question 1 to 17), so entry j
 * corresponds to column j of the centroid matrix and to MAX_OPTION[j].
 */
public class ClusterAnalysis {

    /** Highest option index per question, in question order (1-17). */
    private static final int[] MAX_OPTION = {
            5, 2, 5, 3, 4, 3, 3, 4, 4, 3, 3, 3, 4, 2, 5, 5, 5
    };

    /** Survey questions in question-ID order, mapped to their category. */
    static Map<String, String> questionCategories() {
        Map<String, String> questions = new LinkedHashMap<>();
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
        return questions;
    }

    private final double[][] centroids;
    private final int userCluster;
    private final Map<String, String> questions = questionCategories();

    public ClusterAnalysis(double[][] centroids, int userCluster) {
        this.centroids = centroids;
        this.userCluster = userCluster;
    }

    /**
     * Returns the questions (with their category) on which the user's cluster
     * is the one closest to the most carbon-intensive answer.
     */
    public Map<String, String> compareWithMaxValues() {
        Map<String, String> matchingCategories = new LinkedHashMap<>();

        int j = 0;
        for (Map.Entry<String, String> entry : questions.entrySet()) {
            int closestCluster = 0;
            double smallestDiff = Double.MAX_VALUE;
            for (int i = 0; i < centroids.length; i++) {
                if (j >= centroids[i].length) {
                    continue;
                }
                double diff = Math.abs(MAX_OPTION[j] - centroids[i][j]);
                if (diff < smallestDiff) {
                    smallestDiff = diff;
                    closestCluster = i;
                }
            }
            if (closestCluster == userCluster) {
                matchingCategories.put(entry.getKey(), entry.getValue());
            }
            j++;
        }

        return matchingCategories;
    }
}
