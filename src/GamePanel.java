
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class GamePanel extends JPanel implements ActionListener{



    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 600;
    static int n;
    static int mod;

    static int UNIT_SIZE;
    static int DELAY = 700;


    static int agent = -1;

    static int x  = 0; // position of player in the board
    static int y = 0;


    static char direction = 'R';
    static boolean running = false;
    Timer timer;
    Random random;

    static int movesnum = 0;
    static int rotates = 0;
    static int scans = 0;


    static int goldX;
    static int goldY;


    ArrayList<Integer> beaconX = new ArrayList<Integer>();
    ArrayList<Integer> beaconY= new ArrayList<Integer>();


    ArrayList<Integer> pitX = new ArrayList<Integer>();
    ArrayList<Integer> pitY = new ArrayList<Integer>();



    static Board board;

    GamePanel(int a) {
        // smart agent = 1
        // random agent = 0

        agent = a;

        importFiles();



        if (mod != 0){
            SCREEN_WIDTH = 600 - mod;
            SCREEN_HEIGHT = 600 - mod;
        }


        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
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
            // reading gold coordinates

                String[] goldXY = reader.nextLine().split(",");


                goldX = board.getBox(Integer.parseInt(goldXY[0]), Integer.parseInt(goldXY[1])).getX();
                goldY = board.getBox(Integer.parseInt(goldXY[0]), Integer.parseInt(goldXY[1])).getY();

                board.getBox(Integer.parseInt(goldXY[0]), Integer.parseInt(goldXY[1])).setState('G');


                    reader.nextLine();

                    String line;

                    // reading beacon coordinates

                    while (reader.hasNext() && (line = reader.nextLine()).length() != 0)
                    {

                        String[] temp = line.split(",");

                        if(Integer.parseInt(temp[0]) != -1 && Integer.parseInt(temp[1]) != -1)
                        {

                            beaconX.add(Integer.parseInt(temp[0]));
                            beaconY.add(Integer.parseInt(temp[1]));

                            board.getBox(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).setState('B');

                        }



                    }

        // reading pit coordinates
                String line2;

                while (reader.hasNext() && (line2 = reader.nextLine()).length() != 0)
                {


                    String[] temp = line2.split(",");

                    if(Integer.parseInt(temp[0]) != -1 && Integer.parseInt(temp[1]) != -1)
                    {
                        pitX.add(Integer.parseInt(temp[0]));
                        pitY.add(Integer.parseInt(temp[1]));

                        board.getBox(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).setState('P');
                    }

                }




            }
            reader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist! ");
            System.out.println(ex.getMessage());


        }



    }


    public void startGame() {


        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
            draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     function retrieved from:
     https://stackoverflow.com/questions/22426040/error-sun-awt-image-toolkitimage-cannot-be-cast-to-java-awt-image-bufferedimage

     */
    public static BufferedImage convertToARGB(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }



    public void draw(Graphics g) throws IOException {


       BufferedImage toPaintMiner = convertToARGB(ImageIO.read(ClassLoader.getSystemResource("miner.png")));

        if(running) {

            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i* UNIT_SIZE, 0, i* UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i* UNIT_SIZE, SCREEN_WIDTH, i* UNIT_SIZE);

			}

     // drawing pits
            for(int p = 0; p < pitX.size(); p++)
            {
                g.drawImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("hole.png")), pitY.get(p) * UNIT_SIZE ,pitX.get(p) * UNIT_SIZE,UNIT_SIZE ,UNIT_SIZE , null);
            }

     // drawing gold
            g.drawImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("treasure.png")), goldX ,goldY,UNIT_SIZE  ,UNIT_SIZE  , null);

     // drawing beacons

            for(int b = 0; b < beaconX.size(); b++)
            {
                g.drawImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("lighthouse.png")), beaconY.get(b) * UNIT_SIZE,beaconX.get(b) * UNIT_SIZE ,UNIT_SIZE ,UNIT_SIZE , null);
            }

     // drawing miner
            g.drawImage(toPaintMiner, x,y,UNIT_SIZE,UNIT_SIZE, null);


        }
        else {
            goldFound(g);
        }

    }




    public void move(){

        int col = x/ UNIT_SIZE;
        int row = y/ UNIT_SIZE;

        board.getBox(row,col).setVisited(true);
        board.displayBoard(x,y, UNIT_SIZE);


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

        System.out.println("MINER MOVED TO  ROW = " + y/UNIT_SIZE + " COL = " + x/UNIT_SIZE);


        movesnum += 1;




    }

    // returns the number of squares to gold, 0 if not in the direction

    public int beacon(int row, int col)
    {
        int x2 = 0;
        int y2 = 0;

        int check = 0;

        switch(direction){

            case 'R': check = 1; break;
            case 'D': check = 2; break;
            case 'L': check = 3; break;
            case 'U': check = 4; break;
            default: break;
        }

        for(int x = 0; x < 4; x++)
        {

            int count = 0;
            if (check == 1)
            {
                x2 = x + UNIT_SIZE;
                y2 = y;
            }

            else if(check  == 2)
            {
                x2 = x;
                y2 = y + UNIT_SIZE;

            }

            else if (check == 3)
            {
                x2 = x - UNIT_SIZE;
                y2 = y;


            }

            else if (check  == 4)
            {

                x2 = x;
                y2 = y - UNIT_SIZE;

            }

            if(!checkCollision(x2,y2))
            {
                // check all four directions for first occurrence of any state aside from a blank tile
                if (check == 1)
                {

                    for(int i = col + 1; i < n; i++)
                    {
                        count += 1;

                        if(board.getBox(row,i).getState() == 'G')
                            return count;
                        if(board.getBox(row,i).getState() == 'P')
                            return 0;

                    }

                }

                else if(check == 2)
                {
                    for(int i = row + 1; i < n; i++)
                    {
                        count += 1;
                        if(board.getBox(i,col).getState() == 'G')
                            return count;
                        if(board.getBox(i,col).getState() == 'P')
                            return 0;

                    }


                }

                else if (check == 3)
                {

                    for(int i = col - 1; i > n; i--)
                    {
                        count += 1;

                        if(board.getBox(row,i).getState() == 'G')
                            return count;
                        if(board.getBox(row,i).getState() == 'P')
                            return 0;

                    }

                }

                else if (check  == 4)
                {
                    for(int i = row - 1; i > n; i--)
                    {
                        count += 1;
                        if(board.getBox(i,col).getState() == 'G')
                            return count;
                        if(board.getBox(i,col).getState() == 'P')
                            return 0;

                    }


                }

            }


            check++;

        }


       return 0;


    }


    public void rotate()
    {

        if (direction == 'R')
            direction = 'D';
        else if (direction == 'D')
            direction = 'L';
        else if (direction == 'L')
            direction = 'U';
        else if (direction == 'U')
            direction = 'R';

        rotates += 1;

      //  System.out.println("MINER ROTATED TO = " + direction);


    }


    public char scan(int row, int col)
    {

        scans += 1;
       return board.getBox(row,col).getState();


    }

    // this method returns the row and col of the ideal move

    public Move selectSmartMove(ArrayList<Move> moves)
    {

        ArrayList<Move> temp = new ArrayList<Move>();
        Move e;
        int firstEmpty = -1; // index of the first empty tile//


        for (int i = 0; i < moves.size(); i++)
        {

            if(board.getBox(moves.get(i).getRow(),moves.get(i).getCol()).getVisited())
            {
                e = new Move(moves.get(i).getRow(),moves.get(i).getCol(),moves.get(i).getState());
                temp.add(e);
            }

            else
                continue;


        }


        for (int i = 0; i < moves.size(); i++)
        {
            for( int j = 0; j < temp.size(); j++)
            {
                if(moves.get(i).getRow() == temp.get(j).getRow() && moves.get(i).getCol() == temp.get(j).getCol())
                    moves.remove(i);

            }
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

            if (moves.get(j).getState() == '-')
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

                if (temp.get(x).getState() == '-')
                {
                    return temp.get(x);

                }

            }


        }


        return temp.get(0);



    }


    public Move selectRandomMove(ArrayList<Move> moves)
    {
        int min = 0;
        int max = moves.size() - 1;
        int num = 0;



        do{
            num = random.nextInt(max - min + 1) + min;

        } while(moves.get(num).getState() == 'P');

        return moves.get(num);



    }




    // x and y here are position in the board

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

    public void goldFound(Graphics g) {

        //Game Over display
        g.setColor(Color.yellow);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Gold Found!", ((int)SCREEN_WIDTH - metrics2.stringWidth("Gold Found!"))/2, (int)SCREEN_HEIGHT/2 - 80);



        g.setColor(Color.yellow);
        g.setFont( new Font("Ink Free",Font.BOLD, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());

        g.drawString("TOTAL MOVES: " + movesnum, ((int)SCREEN_WIDTH - metrics3.stringWidth("TOTAL MOVES "))/2 - 10, (int)SCREEN_HEIGHT/2 + 75 - 80);
        g.drawString("TOTAL ROTATES: " + rotates, ((int)SCREEN_WIDTH - metrics3.stringWidth("TOTAL MOVES "))/2 - 40, (int)SCREEN_HEIGHT/2 + 120 - 80);
        g.drawString("TOTAL SCANS: " + scans, ((int)SCREEN_WIDTH - metrics3.stringWidth("TOTAL MOVES "))/2 - 10, (int)SCREEN_HEIGHT/2 + 165 - 80);

    }





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


    // moves back one tile

    public void moveBack()
    {

        if(direction == 'R')
        {
            while(direction != 'L')
                rotate();
        }

        else if(direction == 'D')
        {
            while(direction != 'U')
                rotate();
        }

        else if (direction == 'L')
        {
            while(direction != 'R')
                rotate();
        }

        else if (direction == 'U')
        {
            while(direction != 'D')
                rotate();
        }


        move();

    }


    public void enteredBeacon(Move b)
    {


        int numtiles = beacon(b.getRow(), b.getCol());

        int r = 0, d = 0, l = 0, u = 0;

        // if gold is visible
        if(numtiles != 0)
        {
            for(int j = 0; j < 4; j++)
            {
                int checkRow = 0;
                int checkCol = 0;

                if(direction == 'R')
                {
                    r = 1;
                    checkRow = y/UNIT_SIZE;
                    checkCol = x/UNIT_SIZE + 1;
                }

                if(direction == 'D')
                {
                    d = 1;
                    checkRow = y/UNIT_SIZE + 1;
                    checkCol = x/UNIT_SIZE;
                }

                if (direction == 'L')
                {
                    l = 1;
                    checkRow = y/UNIT_SIZE;
                    checkCol = x/UNIT_SIZE - 1;

                }

                if (direction == 'U')
                {
                    u = 1;
                    checkRow = y/UNIT_SIZE - 1;
                    checkCol = x/UNIT_SIZE;

                }

                if(!checkCollision(checkRow * UNIT_SIZE, checkCol * UNIT_SIZE))
                {
                    for(int i = 0; i < numtiles; i++)
                    {

                        if(!checkCollision(checkRow * UNIT_SIZE, checkCol * UNIT_SIZE))
                        {

                            char state = scan(checkRow, checkCol);
                            if (state == 'P' && i == 0)
                                break;

                            if (state == 'P' && i != 0)
                            {
                                while(x != b.getCol() * UNIT_SIZE && y != b.getRow() * UNIT_SIZE)
                                {
                                    moveBack();
                                }

                                break;
                            }

                            if(state == 'G')
                            {
                               // System.out.println("FOUND GOLD");
                               // board.displayBoard(x,y, UNIT_SIZE);
                                return;
                            }



                            move();


                            if (direction == 'L')
                                checkCol--;
                            else if (direction == 'R')
                                checkCol++;
                            else if (direction == 'U')
                                checkRow--;
                            else if(direction == 'D')
                                checkRow++;


                        }


                    }

                }



                while(x != b.getCol() * UNIT_SIZE || y != b.getRow() * UNIT_SIZE)
                {

                    moveBack();
                    // rotate until opposite direction

                    if (direction == 'L')
                        direction = 'R';
                    else if (direction == 'R')
                        direction = 'L';
                    else if (direction == 'U')
                        direction = 'D';
                    else if(direction == 'D')
                        direction = 'U';



                }

                if (r != 1)
                    while(direction != 'R')
                        rotate();
                else if (d != 1)
                    while(direction != 'D')
                        rotate();
                else if (l != 1)
                    while(direction != 'L')
                        rotate();
                else if (u != 1)
                    while(direction != 'U')
                        rotate();
                // rotate until all sides are covered //



            }



        }



    }


    public ArrayList <Move> addMoves( )
    {

        ArrayList<Move> moves = new ArrayList<Move>();
        Move a;

        int x2 = 0, y2 = 0;
        // x and  y position of miner
        // x2 and y2 position being checked

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

            if (!checkCollision(x2,y2))
            {
                // getting the row and col equivalent of front being checked
                int col = x2/ UNIT_SIZE;
                int row = y2/ UNIT_SIZE;

                char state =  scan(row,col);
                a = new Move(row,col,state);


                moves.add(a);



            }

            rotate();



        }

        return moves;


    }











    @Override
    public void actionPerformed(ActionEvent e) {


        if(running) {
            // b is the selected move
            Move b = null;

            // if smart agent
            if(agent == 1)
            {
                b =  selectSmartMove(addMoves()); // b - coordinates of where miner will go

                while(!checkFront(b.getRow(), b.getCol()))
                {
                    rotate();
                }

                move();

            }

            // if random agent
            else if(agent == 0)
            {
                b =  selectRandomMove(addMoves());

                while(!checkFront(b.getRow(), b.getCol()))
                {
                    rotate();
                }

                move();

            }



            // if miner moved to beacon
            if(b.getState() == 'B')
            {

                enteredBeacon(b);

            }





            if (b.getState( ) == 'G' || b.getState() == 'P')
            {
                board.displayBoard(x,y, UNIT_SIZE);


                System.out.println("\nGOLD FOUND!\n");

                System.out.println("FINAL POSITION ROW = " + y/UNIT_SIZE + " COLUMN = " + x/UNIT_SIZE);
                System.out.println("TOTAL ROTATES = " + rotates);
                System.out.println("TOTAL SCANS = " + scans);
                System.out.println("TOTAL MOVES = " + movesnum );


                running = false;
                timer.stop();

            }



        }

        repaint();

    }

}