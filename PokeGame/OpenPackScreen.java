import mayflower.*;


public class OpenPackScreen extends World
{
   private HomeScreen homeScreen;
   private CardPacks cardPack;
   
   public OpenPackScreen(HomeScreen homeScreen){
       this.homeScreen = homeScreen;
       this.cardPack = Cards;
       setBackground("img/pack_opening_screen.png");
   }
  
  
   public void act(){
       if (Mayflower.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mayflower.setWorld(homeScreen);
       }
       if(Mayflower.isKeyPressed(Keyboard.KEY_G)){
            CardPacks.openPack();
        }
    }
}
