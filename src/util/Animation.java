package util;

import static java.lang.StringTemplate.STR;

public class Animation {
public static void loadData() {
    try {
        // Define the animation characters
        String[] animationChars = { ".", "..", "...", "...." };
        String[] animationDots = { "|", "/", "-", "\\" };
        int timesToRepeat = 10;
        System.out.println("#".repeat(25));

        // Print the loading animation
        for (int i = 0; i < timesToRepeat; i++) {
            System.out.print(STR."\rData is Loading \{animationDots[i % animationDots.length]}\{animationChars[i % animationChars.length]}");
            try {
                // Add a delay to control the speed of the animation
                Thread.sleep(125);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        // Clear the loading animation
        System.out.print("\rData is Loading ");
        System.out.println();
        System.out.println("#".repeat(25));
        System.out.println("Completed! (1s)");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
