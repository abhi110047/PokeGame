import java.util.*;

public class PokemonCard extends Card
{
   private int health;
   private int atk;
   
   public PokemonCard(String name, String description, int health, int attack){
       super(name, description);
       this.health = health;
       this.atk = atk;
   }
   
   public int getHealth(){
       return health;
   }
   
   public int getAttackPower(){
       return atk;
   }
}
