import java.util.Scanner;

public class GoldMiner {




    public static void main (String args[])
    {
        Scanner sc = new Scanner(System.in);

        Miner player = new Miner();
        int nSize, nB, nP;
        System.out.print("Enter size of board: ");
        nSize = sc.nextInt();

        Board board = new Board(nSize); // initializing board
        sc.nextLine();
        System.out.print("Enter coordinates of pot of gold (x,y): ");
        String [] temp = sc.nextLine().split(",", 2);

        board.getBox(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).setState('G');

        System.out.print("Number of BEACONS: ");
        nB = sc.nextInt();

        sc.nextLine();

        System.out.println("Enter coordinates of beacons (x,y): ");

        for(int i = 0; i < nB; i++)
        {
            String [] beacons = sc.nextLine().split(",", 2);
            board.getBox(Integer.parseInt(beacons[0]), Integer.parseInt(beacons[1])).setState('B');

        }

        System.out.print("Number of PITS: ");
        nP = sc.nextInt();

        sc.nextLine();

        System.out.println("Enter coordinates of pits (x,y): ");

        for(int i = 0; i < nP; i++)
        {
            String [] pits = sc.nextLine().split(",", 2);
            board.getBox(Integer.parseInt(pits[0]), Integer.parseInt(pits[1])).setState('P');

        }





        board.displayBoard();




    }
}
