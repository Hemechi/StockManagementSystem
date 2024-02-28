package util;


import static java.lang.System.out;

public class AnimationImpl implements Animation{
    @Override
    public void loadingAnimation() {
        try {
            // Define the animation characters
            String[] animationChars = { ".", "..", "...", "...." };
            String[] animationDots = { "|", "/", "-", "\\" };
            int timesToRepeat = 10;
            out.println("#".repeat(25));

            long startTime = System.currentTimeMillis(); // Start time measurement

            // Print the loading animation
            for (int i = 0; i < timesToRepeat; i++) {
                out.print("\rData is Loading " + animationDots[i % animationDots.length] + animationChars[i % animationChars.length]);
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
            out.println("Total time taken in seconds: " + durationSeconds + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
