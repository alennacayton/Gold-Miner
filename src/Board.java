public class Board {

    private Spot [][] boxes;
    private int size;

    public Board(int s)
    {
        this.size = s;
        this.boxes = new Spot [s][s];

        for(int i = 0; i < s; i++)
        {
            for(int j = 0; j < s; j++)
            {

                boxes[i][j] = new Spot (i,j);
                boxes[i][j].setState('*');



            }
        }

        for(int a = 0; a < s; a++)
        {
            for(int b = 0; b < s; b++)
            {
                System.out.print(boxes[a][b].getState() + " ");

                if (b == s - 1)
                    System.out.println();



            }
        }





    }


    public Spot getBox (int x, int y)
    {
        return boxes [x][y];
    }

    public void displayBoard ()
    {
        for(int a = 0; a < this.size; a++)
        {
            for(int b = 0; b < this.size; b++)
            {
                System.out.print(boxes[a][b].getState() + " ");

                if (b == this.size - 1)
                    System.out.println();



            }
        }


    }



}


