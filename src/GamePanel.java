import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 600;
    static int n = 5;

    static int mod = 600 % n;



    static int UNIT_SIZE = (600 - mod)/n;

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

    int pitX;
    int pitY;

    int goldX;
    int goldY;

    static Board board = new Board(n, UNIT_SIZE);

    GamePanel(){

        System.out.println("UNIT SIZE --" + UNIT_SIZE);

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


    public void startGame() {

        addPits();
        addGold();
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



            g.setColor(Color.white);
            g.fillOval(pitX, pitY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.yellow);
            g.fillOval(goldX, goldY, UNIT_SIZE, UNIT_SIZE);



            g.setColor(Color.green);
            g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);



        }
        else {
            gameOver(g);
        }

    }

  /*
    public void newApple(){
    //    appleX = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
      //    appleY = random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

        appleX = board.getBox(2,3).getX();
        appleY = board.getBox(2,3).getY();

    }


    */


    public void addPits(){


        pitX = board.getBox(0,1).getX();
        pitY = board.getBox(0,1).getY();
        board.getBox(0,1).setState('P');
        System.out.println("PIT X = " + pitX + "PIT Y = "  + pitY);
        System.out.println("PIT STATE = " + board.getBox(0  ,1).getState());

    }


    public void addGold(){


        goldX = board.getBox(2,2).getX();
        goldY = board.getBox(2,2).getY();
        board.getBox(2,2).setState('G');
        System.out.println("GOLD X = " + goldX + "GOLD Y = "  + goldY);
        System.out.println("GOLD STATE = " + board.getBox(2  ,2).getState());

    }




    public void move(){


        System.out.println("x == " + x + " y == " + y);


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




        for (int i = 0; i < moves.size(); i++)
        {
            if(moves.get(i).getState() == 'G')
            {
                return moves.get(i);
            }

            if(moves.get(i).getState() == 'B')
            {
                return moves.get(i);
            }

            if(moves.get(i).getState() == 'P')
            {
                continue;
            }

            if(moves.get(i).getState() == '*')
            {
                continue;
            }



        }

        for (int i = 0; i < moves.size(); i++)
        {
            if(moves.get(i).getState() == '*')
                return moves.get(i);

        }




        return moves.get(0);



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

                int col = x2/ UNIT_SIZE;
                int row = y2/ UNIT_SIZE;

               // System.out.println("ROW PASSED  = " + row + "COL PASSED  = " + col);
                if(!board.getBox(row,col).getScanned())
                {


                    char state = board.getBox(row,col).getState(); /// this is scan //
                    System.out.println("SCANNED AREA :   ROW  = " + row + "COL = " + col + "STATE = " + state);

                    board.getBox(row,col).setScanned(true);  // marking it as scanned;

                    a = new Move(row,col,state);


                    moves.add(a);

                }


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