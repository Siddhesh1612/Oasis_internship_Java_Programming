import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        int rounds = 3;

        System.out.println("===== Number Guessing Game =====");

        for(int r = 1; r <= rounds; r++) {

            int number = random.nextInt(100) + 1;
            int attempts = 5;
            boolean guessed = false;

            System.out.println("\nRound " + r);
            System.out.println("Guess number between 1 to 100");

            while(attempts > 0) {

                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();

                if(guess == number) {
                    System.out.println("Correct Guess!");
                    score += attempts * 10;
                    guessed = true;
                    break;
                }
                else if(guess > number) {
                    System.out.println("Too High!");
                }
                else {
                    System.out.println("Too Low!");
                }

                attempts--;
                System.out.println("Attempts left: " + attempts);
            }

            if(!guessed) {
                System.out.println("Round Over!");
                System.out.println("Correct number was: " + number);
            }
        }

        System.out.println("\nGame Finished");
        System.out.println("Your Score: " + score);

        sc.close();
    }
}