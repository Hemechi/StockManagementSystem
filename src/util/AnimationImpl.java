package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnimationImpl implements Animation{
    @Override
    public void loadData(String fileName) {
        try {
            // Define the animation characters
            String[] animationChars = { ".", "..", "...", "...." };
            String[] animationDots = { "|", "/", "-", "\\" };
            int timesToRepeat = 10;
            System.out.println("#".repeat(25));

            long startTime = System.currentTimeMillis(); // Start time measurement

            // Print the loading animation
            for (int i = 0; i < timesToRepeat; i++) {
                System.out.print("\rData is Loading " + animationDots[i % animationDots.length] + animationChars[i % animationChars.length]);
                try {
                    // Add a delay to control the speed of the animation
                    Thread.sleep(125);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            long endTime = System.currentTimeMillis(); // End time measurement
            long durationAnimation = endTime - startTime; // Calculate animation duration

            // Read data from file
            startTime = System.currentTimeMillis(); // Reset start time measurement
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while ((reader.readLine()) != null) {
                    // Read the file line by line without doing anything
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
            endTime = System.currentTimeMillis(); // End time measurement after reading data
            long durationReading = endTime - startTime; // Calculate reading duration

            // Calculate total duration
            long totalDuration = durationAnimation + durationReading;

            // Convert milliseconds to seconds
            double durationSeconds = durationReading / 1000.0;

            // Clear the loading animation
            System.out.print("\rData is Loading ");
            System.out.println();
            System.out.println("#".repeat(25));
            System.out.println("Time taken for animation: " + durationAnimation + " milliseconds");
            System.out.println("Time taken to read data from file: " + durationReading + " milliseconds");
            System.out.println("Total time taken: " + totalDuration + " milliseconds");
            System.out.println("Total time taken in seconds: " + durationSeconds + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
