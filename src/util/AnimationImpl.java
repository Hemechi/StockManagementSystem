package util;


import static java.lang.System.out;

public class AnimationImpl implements Animation{
    @Override
    public void loadingAnimation(boolean b) {
        try {
            // Check if b is false, then stop the program
            if (!b) {
                System.exit(0);
            }

            // Define the animation characters
            String[] animationChars = { ".", "..", "...", "...." };
            String[] animationDots = { "|", "/", "-", "\\" };
            int timesToRepeat = 10;
            out.println("#".repeat(25));

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
            // Clear the loading animation
            out.print("\rData is Loading ");
            out.println();
            out.println("#".repeat(25));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
