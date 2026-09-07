import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================HOTEL BOOKING ======================== \n 1. Register \n 2. Login \n 0. Exit \n Choice:");
        int choice = scanner.nextInt();
        switch (choice){
            case 1 : System.out.println("======================== REGISTER ======================== ") ;
            break ;
        }
    }
}