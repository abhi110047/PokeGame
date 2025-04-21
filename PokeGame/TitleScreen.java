import mayflower.*;
import mayflower.Label;
import java.util.*;

public class TitleScreen extends World {
    private Label titleLabel;
    private Label pressEnterLabel;

    public TitleScreen() {
        setBackground("img/title_background.png");
    }

    public void act() {
        if (Mayflower.isKeyDown(Keyboard.KEY_ENTER)) {
            Mayflower.setWorld(new HomeScreen());
        }
    }
} 