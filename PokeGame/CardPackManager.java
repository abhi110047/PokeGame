import java.util.List;

public class CardPackManager {
    private Deck playerDeck;
    private CardPacks cardPacks;
    private int basicPackCost = 0;    // Basic pack is free
    private int greatPackCost = 10;   // Great pack costs 10 coins
    private int mightPackCost = 25;   // Might pack costs 25 coins

    public CardPackManager(Deck deck) {
        this.playerDeck = deck;
        this.cardPacks = new CardPacks();
    }

    public boolean openCardPack(HomeScreen homeScreen, String packType) {
        int cost = getPackCost(packType);
        
        if (homeScreen.getCoins() >= cost) {
            homeScreen.addCoins(-cost); // Deduct coins
            List<Card> newCards = cardPacks.openPack(packType);
            for (Card card : newCards) {
                playerDeck.addCard(card);
            }
            System.out.println("You opened a " + packType + " pack and added " + newCards.size() + " cards to your deck!");
            return true;
        } else {
            System.out.println("Not enough coins to open a " + packType + " pack! You need " + cost + " coins.");
            return false;
        }
    }

    private int getPackCost(String packType) {
        switch(packType.toLowerCase()) {
            case "great":
                return greatPackCost;
            case "might":
                return mightPackCost;
            default: // Basic pack
                return basicPackCost;
        }
    }

    public int getBasicPackCost() {
        return basicPackCost;
    }

    public int getGreatPackCost() {
        return greatPackCost;
    }

    public int getMightPackCost() {
        return mightPackCost;
    }
}