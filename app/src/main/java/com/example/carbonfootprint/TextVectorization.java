package com.example.carbonfootprint;

import java.util.*;

public class TextVectorization {
    private Map<String, Integer> wordIndexMap = new HashMap<>();
    private List<String[]> documents;

    public TextVectorization(List<String[]> documents) {
        this.documents = documents;
        buildWordIndexMap();
    }

    private void buildWordIndexMap() {
        int index = 0;
        for (String[] doc : documents) {
            for (String word : doc) {
                if (!wordIndexMap.containsKey(word)) {
                    wordIndexMap.put(word, index++);
                }
            }
        }
    }

    public double[][] transform() {
        double[][] tfidfMatrix = new double[documents.size()][wordIndexMap.size()];
        int docIndex = 0;
        for (String[] doc : documents) {
            Map<String, Integer> wordCount = new HashMap<>();
            int totalWords = doc.length;
            for (String word : doc) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                String word = entry.getKey();
                int wordIdx = wordIndexMap.get(word);
                double tf = entry.getValue() / (double) totalWords;
                double idf = Math.log((double) documents.size() / getDocCountContainingWord(word));
                tfidfMatrix[docIndex][wordIdx] = tf * idf;
            }
            docIndex++;
        }
        return tfidfMatrix;
    }

    private int getDocCountContainingWord(String word) {
        int count = 0;
        for (String[] doc : documents) {
            for (String term : doc) {
                if (term.equals(word)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public double[][] transform1(List<String[]> singleDocument) {
        double[][] tfidfMatrix = new double[1][wordIndexMap.size()];
        Map<String, Integer> wordCount = new HashMap<>();
        String[] doc = singleDocument.get(0);
        int totalWords = doc.length;
        for (String word : doc) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            String word = entry.getKey();
            int wordIdx = wordIndexMap.get(word);
            double tf = entry.getValue() / (double) totalWords;
            double idf = Math.log((double) documents.size() / getDocCountContainingWord(word));
            tfidfMatrix[0][wordIdx] = tf * idf;
        }
        return tfidfMatrix;
    }
}
