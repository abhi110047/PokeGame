import mayflower.*;

public class MyMayflower extends Mayflower 
{
    //Constructor
    public MyMayflower()
    {
        super("PokeGame", 588, 888);
    }

    public void init()
    {
        Mayflower.setFullScreen(false);
        World w = new TitleScreen();
        Mayflower.setWorld(w);
    }
}