import java.util.Scanner;

public class OnlineExamination {

    static Scanner sc = new Scanner(System.in);

    static String username = "student";
    static String password = "1234";

    static int score = 0;

    public static void main(String[] args) {

        System.out.println("===== Online Examination System =====");

        // Login
        System.out.print("Enter Username: ");
        String user = sc.next();

        System.out.print("Enter Password: ");
        String pass = sc.next();


        if(user.equals(username) && pass.equals(password)) {

            System.out.println("\nLogin Successful!");

            int choice;

            do {
                System.out.println("\n1. Update Profile");
                System.out.println("2. Start Exam");
                System.out.println("3. Logout");

                System.out.print("Enter Choice: ");
                choice = sc.nextInt();

                switch(choice) {

                    case 1:
                        updateProfile();
                        break;

                    case 2:
                        startExam();
                        break;

                    case 3:
                        System.out.println("Logged Out Successfully");
                        break;

                    default:
                        System.out.println("Invalid Choice");
                }

            }while(choice != 3);

        }
        else {
            System.out.println("Invalid Login Details!");
        }
    }


    static void updateProfile() {

        System.out.print("Enter New Username: ");
        username = sc.next();

        System.out.print("Enter New Password: ");
        password = sc.next();

        System.out.println("Profile Updated Successfully!");
    }


    static void startExam() {

        score = 0;

        System.out.println("\nExam Started");
        System.out.println("You have 3 Questions");


        System.out.println("\nQ1. Java is a ____ language");
        System.out.println("1. Programming");
        System.out.println("2. Markup");

        int ans1 = sc.nextInt();

        if(ans1 == 1)
            score++;


        System.out.println("\nQ2. Which keyword creates class?");
        System.out.println("1. function");
        System.out.println("2. class");

        int ans2 = sc.nextInt();

        if(ans2 == 2)
            score++;


        System.out.println("\nQ3. Extension of Java file?");
        System.out.println("1. .java");
        System.out.println("2. .txt");

        int ans3 = sc.nextInt();

        if(ans3 == 1)
            score++;


        System.out.println("\nExam Auto Submitted");
        System.out.println("Your Score: " + score + "/3");
    }
}