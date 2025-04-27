import mayflower.*;

public class MyWorld extends World {
    
    public MyWorld() 
    {
        setBackground("img/Title_Screen.png");
        Mayflower.showBounds(true);
        
        

        
        
       
    }

    public void act()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_ENTER)){
            Mayflower.setWorld(new HomeScreen());
        }
    }
}