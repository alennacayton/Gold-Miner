import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class GamePanel extends JPanel implements ActionListener{

    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 600;
    static int n;

    static int mod;



    static int UNIT_SIZE;

    static int DELAY = 600;


    int x; // position of player in the board
    int y;



    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    int movesnum = 0;
    int rotates = 0;
    int scans = 0;

   ArrayList<Integer> pitX = new ArrayList<Integer>();
    ArrayList<Integer> pitY = new ArrayList<Integer>();


    int goldX;
    int goldY;

    ArrayList<Integer> beaconX = new ArrayList<Integer>();
    ArrayList<Integer> beaconY= new ArrayList<Integer>();



    static Board board;

    GamePanel(){

        importFiles();


        System.out.println("UNIT SIZE --" + UNIT_SIZE);
        System.out.println(n);

        if (mod != 0){
            SCREEN_WIDTH = 600 - mod;
            SCREEN_HEIGHT = 600 - mod;
        }



      //  board.displayBoard();

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public class Move{

        public int row;
        public int col;
        public char state;

        public Move(int a, int b, char m)
        {
            row = a;
            col = b;
            state = m;
        }

        public int getRow()
        {
            return row;
        }


        public int getCol()
        {
            return col;
        }

        public char getState()
        {
            return state;
        }




    }

    public void importFiles(){




        /*
        TEXT FILE FORMAT

        <GRID_SIZE><newline>
        <GOLD_X>,<GOLD_Y><newline>
        <space>
        <BEACON_X_n1>,<BEACON_Y_n1><newline>
        .
        .
        .
       <BEACON_X_n>,<BEACON_Y_n><newline>
       <space>
       <PIT_X_n1>,<PIT_Y_n1><newline>
        .
        .
        .
       <PIT_X_n>,<PIT_Y_n>
       <eof>
         */





















        File inputFile;

        try {
            inputFile = new File("input.txt");
            Scanner reader = new Scanner(inputFile);
            while (reader.hasNext()) {

                String size = reader.next();
                n = Integer.parseInt(size);


                mod = 600 % n;
                UNIT_SIZE = (600 - mod)/n;
                board = new Board(n, UNIT_SIZE);



                reader.nextLine();

                String[] goldXY = reader.nextLine().split(",");


                goldX = board.getBox(Integer.parseInt(goldXY[0]), Integer.parseInt(goldXY[1])).getX();
                goldY = board.getBox(Integer.parseInt(goldXY[0]), Integer.parseInt(goldXY[1])).getY();

                System.out.println(goldX + "  GOOLD  " + goldY);
                board.getBox(Integer.parseInt(goldXY[0]), Integer.parseInt(goldXY[1])).setState('G');


                    reader.nextLine();

                    String line;

                    // reading beacon coordinates

                    while (reader.hasNext() && (line = reader.nextLine()).length() != 0)
                    {

                        String[] temp = line.split(",");

                        System.out.println(temp[0] + " " + temp[1]);

                        beaconX.add(Integer.parseInt(temp[0]));
                        beaconY.add(Integer.parseInt(temp[1]));

                        board.getBox(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).setState('B');


                    }

        // reading pit coordinates
                String line2;
                System.out.println("yyeeet");
                while (reader.hasNext() && (line2 = reader.nextLine()).length() != 0)
                {


                    String[] temp = line2.split(",");

                    System.out.println(temp[0] + " " + temp[1]);

                    pitX.add(Integer.parseInt(temp[0]));
                    pitY.add(Integer.parseInt(temp[1]));

                    board.getBox(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).setState('P');
                }






            }
            reader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist! ");
            System.out.println(ex.getMessage());
            //System.exit(1);

        }






    }


    public void startGame() {

      //  addPits();
       // addBeacons();
        //addGold();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) {

        if(running) {

			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i* UNIT_SIZE, 0, i* UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i* UNIT_SIZE, SCREEN_WIDTH, i* UNIT_SIZE);

			}


    // drawing pits

            for(int p = 0; p < pitX.size(); p++)
            {

                g.setColor(Color.white);
                g.fillOval(pitY.get(p) * UNIT_SIZE, pitX.get(p) * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            //     System.out.println("PITS X = " + pitX.get(p) + " Y = " + pitY.get(p));

            }

    // drawing gold
            g.setColor(Color.yellow);
            g.fillOval(goldX, goldY, UNIT_SIZE, UNIT_SIZE);

    // drawing beacons

            for(int b = 0; b < beaconX.size(); b++)
            {

                g.setColor(Color.blue);
                g.fillOval(beaconY.get(b) * UNIT_SIZE, beaconX.get(b) * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

            //    System.out.println("BEACONS X = " + beaconX.get(b) + " Y = " + beaconY.get(b));

            }

    // miner
            g.setColor(Color.green);
            g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);



        }
        else {
            gameOver(g);
        }

    }
/*
    public void addBeacons(){

        beaconX = board.getBox(0,1).getX();
        beaconY = board.getBox(0,1).getY();
        board.getBox(0,1).setState('P');
        System.out.println("BEACON X = " + beaconX + "BEACON Y = "  + beaconY);
        System.out.println("BACON STATE = " + board.getBox(0  ,1).getState());

    }


    public void addPits(){


        pitX = board.getBox(0,1).getX();
        pitY = board.getBox(0,1).getY();
        board.getBox(0,1).setState('P');
        System.out.println("PIT X = " + pitX + "PIT Y = "  + pitY);
        System.out.println("PIT STATE = " + board.getBox(0  ,1).getState());

    }
*/

    public void addGold(){


        goldX = board.getBox(2,2).getX();
        goldY = board.getBox(2,2).getY();
        board.getBox(2,2).setState('G');
        System.out.println("GOLD X = " + goldX + "GOLD Y = "  + goldY);
        System.out.println("GOLD STATE = " + board.getBox(2  ,2).getState());

    }




    public void move(){

        int col = x/ UNIT_SIZE;
        int row = y/ UNIT_SIZE;
        System.out.println("x == " + x + " y == " + y);

        board.getBox(row,col).setVisited(true);
        System.out.println("SET VISITED TO TRUE FOR ROW AND COL : " + row + " " + col);

        switch(direction) {
            case 'U':
                y = y - UNIT_SIZE;
                break;
            case 'D':
                y = y + UNIT_SIZE;
                break;
            case 'L':
                x  = x  - UNIT_SIZE;
                break;
            case 'R':
                x  = x  + UNIT_SIZE;
                break;
        }

        System.out.println("MINER MOVED TO  X = " + x + " Y = " + y);

        movesnum += 1;


    }


    public int beacon(int rowPos, int colPos)
    {
        int row = 0;
        int col = 0;

    // check all four directions for gold except for position //

        // check above

        while(row != -1 )
        {
            row = rowPos - 1;
            col = colPos;


            if(board.getBox(row,col).getState() == 'G')
            {

            }



        }






        return 0;
    }


    public void rotate(){

        if (direction == 'R')
            direction = 'D';
        else if (direction == 'D')
            direction = 'L';
        else if (direction == 'L')
            direction = 'U';
        else if (direction == 'U')
            direction = 'R';

        rotates += 1;


    }

// convert x and y to coordinates
    public char scan(int row, int col){

       // System.out.println("Scanning.. row = " + row + " col = "+ col);
        scans += 1;

     //   return board.getBox(row,col).getState();


        if (direction == 'R')
        {
          //  System.out.println(board.getBox(row , col + 1).getX() + " " + board.getBox(row , col + 1).getY());
          //  System.out.println(board.getBox(row , col + 1).getState());
            return board.getBox(row , col + 1).getState();
        }


        else if(direction == 'D')
        {
          //  System.out.println(board.getBox(row + 1, col ).getX() + " " + board.getBox(row + 1, col ).getY());
         //   System.out.println(board.getBox(row + 1, col).getState());
            return board.getBox(row + 1, col).getState();
        }


        else if (direction == 'L')
        {
          //  System.out.println(board.getBox(row , col - 1).getX() + " " + board.getBox(row , col - 1).getY());
          //  System.out.println(board.getBox(row, col - 1).getState());
            return board.getBox(row, col - 1).getState();
        }


        else if (direction  == 'U')
        {
          //  System.out.println(board.getBox(row - 1, col ).getX() + " " + board.getBox(row - 1, col ).getY());
         //   System.out.println(board.getBox(row - 1, col).getState());
            return board.getBox(row - 1, col).getState();
        }


        return 'X';



    }

    // this method returns the row and col of the ideal move

    public Move selectMove(ArrayList<Move> moves)
    {
       // check for the gold
        // if theres gold return move
        // check for a beacon
        // if theres a beacon, return move

        //check for pits
        // if theres a pit return the nearest coordinates of

        for(int j = 0; j < moves.size(); j++){
            System.out.println("    SELECTION MOVES ROW = " + moves.get(j).getRow() + " MOVE COL = " + moves.get(j).getCol() + " MOVE STATE = " + moves.get(j).getState());
        }



        //declaring a temporary array for visited moves already

        ArrayList<Move> temp = new ArrayList<Move>();
        Move e,r;
        int firstEmpty = -1; // index of the first empty tile//



        System.out.println("    WITHOUT CONDITIONAL STATEMENTS");
        for(int i = 0; i < moves.size(); i++)
        {
            System.out.println("INDEX " + i );
            if(board.getBox( moves.get(i).getRow(),  moves.get(i).getCol()).getVisited())
            System.out.println("    ROW = " + moves.get(i).getRow() + " COL = " + moves.get(i).getCol() + " VISITED");

            else
                System.out.println("    ROW = " + moves.get(i).getRow() + " COL = " + moves.get(i).getCol() + " NOT VISITED");
        }


        // this loop separates the visited tiles from the unvisited ones
        System.out.println("CONTENT OF ARRAY MOVE BEFORE SPLITTING");
        System.out.println("SIZE OF MOVE: " + moves.size());
        for (int i = 0; i < moves.size(); i++)
        {
            System.out.println(" ROW : " + moves.get(i).getRow() + " COLUMN : "+ moves.get(i).getCol());


            if(board.getBox(moves.get(i).getRow(),moves.get(i).getCol()).getVisited())
            {


                e = new Move(moves.get(i).getRow(),moves.get(i).getCol(),moves.get(i).getState());
                temp.add(e);
             //   System.out.println(" INDEX i : " + i);
            //    System.out.println("  REMOVED " + moves.get(i).getRow() + "  " + moves.get(i).getCol());


             //   moves.remove(i);

            }

            else{
                System.out.println(" INDEX i : " + i);
                continue;

            }



        }

        for (int i = 0; i < moves.size(); i++)
        {
            for( int j = 0; j < temp.size(); j++)
            {
                if(moves.get(i).getRow() == temp.get(j).getRow() && moves.get(i).getCol() == temp.get(j).getCol())
                    moves.remove(i);

            }
        }






        System.out.println("FIRST ARRAY BEING SEARCHED/ UNVISITED TILES");

        for(int a = 0; a < moves.size(); a++){

            System.out.print(moves.get(a).getRow() + " " + moves.get(a).getCol());
            System.out.println(" VISITED?? " + board.getBox(moves.get(a).getRow(),moves.get(a).getCol()).getVisited());
        }


        System.out.println("SECOND ARRAY BEING SEARCHED/ VISITED");

        for(int b = 0; b < temp.size(); b++){

            System.out.println(temp.get(b).getRow() + " " + temp.get(b).getCol());
            System.out.println("VISITED?? " + board.getBox(temp.get(b).getRow(),temp.get(b).getCol()).getVisited());
        }


        for(int j = 0; j < moves.size(); j++)
        {
            if(moves.get(j).getState() == 'G')
            {
                return moves.get(j);
            }

            if(moves.get(j).getState() == 'B')
            {
                return moves.get(j);
            }

            if(moves.get(j).getState() == 'P')
            {
                continue;
            }

            if (moves.get(j).getState() == '*')
            {
                if(firstEmpty == -1)
                    firstEmpty = j;
                else
                    continue;

            }
        }


        if(firstEmpty != -1)
        return moves.get(firstEmpty);

        else{

            for(int x = 0; x < temp.size(); x++)
            {
                if(temp.get(x).getState() == 'G')
                {
                    return temp.get(x);
                }

                if(temp.get(x).getState() == 'B')
                {
                    return temp.get(x);
                }

                if(temp.get(x).getState() == 'P')
                {
                    continue;
                }

                if (temp.get(x).getState() == '*')
                {
                    return temp.get(x);

                }

            }


        }



        return temp.get(0);




    }





// x and y here should be position in the board not row and col

    public boolean checkCollision(int x, int y) {

        //check if touches left border
        if(x < 0) {

            return true;
        }
        //check if touches right border
        if(x >= SCREEN_WIDTH) {

            return true;
        }
        //check if touches top border
        if(y < 0) {

            return true;
        }
        //check if touches bottom border
        if(y >= SCREEN_HEIGHT) {

            return true;
        }

        if(!running) {
            timer.stop();
        }

        return false;


    }

    public void gameOver(Graphics g) {

        //Game Over display
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", ((int)SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, (int)SCREEN_HEIGHT/2);
    }
/*
    public void generateRandom()
    {
        int p = random.nextInt();

        if (moves == 1)
            direction = 'U';
        else if (moves == 2)
            direction = 'R';
        else if (moves == 3)
            direction = 'L';
        else if (moves == 4)
            direction = 'D';


    }

*/


    public boolean checkFront(int row, int col)
    {
        if (direction == 'R')
        {
            if (y/UNIT_SIZE == row && x/ UNIT_SIZE + 1 == col)
                return true;

        }
        else if (direction == 'D')
        {
            if(y/UNIT_SIZE + 1 == row && x/UNIT_SIZE == col)
                return true;
        }
        else if (direction == 'L')
        {
            if (y/UNIT_SIZE == row && x/ UNIT_SIZE - 1 == col)
                return true;

        }
        else if(direction == 'U')
        {
            if (y/UNIT_SIZE - 1 == row && x/ UNIT_SIZE  == col)
                return true;

        }
        return false;
    }



    @Override
    public void actionPerformed(ActionEvent e) {




        if(running) {

                // x and  y position of miner
                // x2 and y2 position being checked


            ArrayList<Move> moves = new ArrayList<Move>();
            Move a,b;
            int x2 = 0, y2 = 0;
            System.out.println("OUTSIDE LOOP");
        for(int i = 0; i < 4; i++)
        {
            // the following if statements are for assigning x2 and y2 to the row and col position of the tiles around the miner

            if (direction == 'R')
            {
                   x2 = x + UNIT_SIZE;
                   y2 = y;
            }

            else if(direction == 'D')
            {
                x2 = x;
                y2 = y + UNIT_SIZE;

            }

            else if (direction == 'L')
            {
                x2 = x - UNIT_SIZE;
                y2 = y;


            }

            else if (direction  == 'U')
            {

                x2 = x;
                y2 = y - UNIT_SIZE;

            }

            //System.out.println("X2 VAL = " + x2 + "Y2 VAL = " + y2);
            if (!checkCollision(x2,y2))
            {
                // getting the row and col equivalent of front being checked
                int col = x2/ UNIT_SIZE;
                int row = y2/ UNIT_SIZE;

               // System.out.println("ROW PASSED  = " + row + "COL PASSED  = " + col);
              //  if(!board.getBox(row,col).getScanned())
            //    {

                    char state = board.getBox(row,col).getState(); /// this is scan //
                    System.out.println("SCANNED AREA :   ROW  = " + row + "COL = " + col + "STATE = " + state);

                    board.getBox(row,col).setScanned(true);  // marking it as scanned;

                    a = new Move(row,col,state);


                    moves.add(a);

             //   }


            }

            rotate();



        }

            b =  selectMove(moves); // b - coordinates of where miner will go
            //configure current position to coordinates of the move

            System.out.println("    SELECTED MOVE: ROW = " + b.getRow() + " COL = " + b.getCol() + " STATE = " + b.getState());
            System.out.println("CURRENT DIRECTION: " + direction);

            while(!checkFront(b.getRow(), b.getCol()))
            {
                rotate();
                System.out.println("rotated to : " + direction);
            }



            move();

            if (b.getState( ) == 'G' || b.getState() == 'P')
            {

                running = false;
                timer.stop();

            }

/*
            System.out.println("FINAL VAL OF X AND Y = " + x + "   " + y);
            System.out.println("ROTATES = " + rotates);
            System.out.println("SCANS = " + scans);
            System.out.println("MOVES = " + movesnum );

*/



        }





        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}