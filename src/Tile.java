public class Tile {

    private int x;
    private int y;
    private char state;
    private boolean scanned;
    private boolean visited;

    /*
    state
    P - Pit
    G - Gold
    B - Beacon
    N - Normal/Empty box
    */


    public Tile(int a , int b)
    {
        this.x = a;
        this.y = b;
        this.visited = false;
        this.scanned = false;
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

    public void setScanned (boolean a)
    {
        this.scanned = a;
    }

    public void setVisited(boolean v){ this.visited = v;}

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

    public boolean getScanned ()
    {
        return this.scanned;
    }

    public boolean getVisited(){return this.visited;}


}
