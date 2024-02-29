package util;

import model.Product;
import service.ServiceImpl;

import java.util.List;

public class AnimationImpl implements Animation{
    @Override
    public void loadData(String fileName) {
        try {
            // Check if b is false, then stop the program
            if (!b) {
                System.exit(0);
            }

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
                    out.println(e.getMessage());
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
            out.print("\rData is Loading ");
            out.println();
            out.println("#".repeat(25));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
