import java.io.*;
import java.util.*;

public class longestcompoundword {

    public static void main(String[] args) {
        String fileName1 = "Input_01.txt";
        String fileName2 = "Input_02.txt";

        try {
            // Process Input_01.txt
            System.out.println("Processing " + fileName1 + ":");
            processFile(fileName1);

            // Process Input_02.txt
            System.out.println("\nProcessing " + fileName2 + ":");
            processFile(fileName2);

        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    private static void processFile(String fileName) throws IOException {
        // Reading words from the input file
        List<String> wordsList = readWordsFromFile(fileName);

        // Start timing the process
        long startTime = System.currentTimeMillis();

        // Find longest and second longest compounded words
        String[] results = longestcompoundword(wordsList);

        // End timing
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Print results
        System.out.println("Longest Compound Word: " + results[0]);
        System.out.println("Second Longest Compound Word: " + results[1]);
        System.out.println("Time taken to process file: " + timeTaken + " milliseconds");
    }

 private static List<String> readWordsFromFile(String fileName) throws IOException {
    List<String> words = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(fileName))) {
        while (scanner.hasNextLine()) {
            words.add(scanner.nextLine().trim());
        }
    }
    return words;
}

    private static String[] longestcompoundword(List<String> wordsList) {
        // Use a Set 
        Set<String> wordsSet = new HashSet<>(wordsList);
        // Sort words by length in descending order
        wordsList.sort((a, b) -> b.length() - a.length());

        String longest = "";
        String secondLongest = "";

        for (String word : wordsList) {
            if (isCompoundWord(word, wordsSet, true)) {
                if (longest.isEmpty()) {
                    longest = word;
                } else if (secondLongest.isEmpty()) {
                    secondLongest = word;
                    break;
                }
            }
        }

        return new String[]{longest, secondLongest};
    }

    private static boolean isCompoundWord(String word, Set<String> wordsSet, boolean isOriginalWord) {
        if (!isOriginalWord && wordsSet.contains(word)) {
            return true;
        }

        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);

            if (wordsSet.contains(prefix) && isCompoundWord(suffix, wordsSet, false)) {
                return true;
            }
        }

        return false;
    }
}
