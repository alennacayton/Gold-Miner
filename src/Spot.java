public class Spot {

    private int x;
    private int y;
    private char state;
    /*
    state
    P - Pit
    G - Gold
    B - Beacon
    N - Normal/Empty box

    */


    public Spot(int a , int b)
    {

        this.x = a;
        this.y = b;


    }





    public void setX (int a)
    {
        this.x = a;

    }

    public void setY (int b)
    {
        this.y = b;
    }

    public void setState (char c)
    {
        this.state = c;
    }
    public int getX ()
    {
        return this.x;

    }

    public int getY ()
    {
        return this.y;
    }

    public char getState ()
    {
        return this.state;
    }




}
