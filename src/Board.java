public class Board {

    private Tile [][] boxes;
    private int size;

    public Board(int s, int unitSize)
    {
        this.size = s;
        this.boxes = new Tile [s][s];


        int x = 0;
        int y = 0;

        for(int i = 0; i < s; i++)
        {
                for(int j = 0; j < s; j++)
                {

                        boxes[i][j] = new Tile (x,y);
                        boxes[i][j].setState('-');


                    x += unitSize;

                }

            y += unitSize;
            x = 0;


        }







    }


    public Tile getBox (int row, int col)
    {
        return boxes [row][col];
    }


    public void displayBoard (int x, int y, int u)
    {
        for(int a = 0; a < this.size; a++)
        {
            for(int b = 0; b < this.size; b++)
            {

                if(a == y/u && b == x/u)
                    System.out.print("M" + "\t");
                else
                System.out.print(boxes[a][b].getState() + "\t");

                if (b == this.size - 1)
                    System.out.println( );

            }
        }


/*

    int count = 0;
        for(int a = 0; a < this.size; a++)
        {
            for(int b = 0; b < this.size; b++)
            {
                    count++;

                System.out.println(a + "    " + b);

                System.out.println(" X pos = " + boxes[a][b].getX() + " Y pos = " + boxes[a][b].getY());



            }
        }

        System.out.println("COUNT " + count);
*/

    }



}


