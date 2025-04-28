import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponent;
    private int turnNumber;
    private boolean gameOver;
    private String gamePhase; // Draw, Play, Attack, End
    private Map<Player, Integer> prizeCards;
    private ArrayList<Card> discardPile;
    private boolean canPlaySupporter;
    private boolean canPlayStadium;
    private String activeStadium;

    public GameState(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.opponent = player2;
        this.turnNumber = 1;
        this.gameOver = false;
        this.gamePhase = "Draw";
        this.prizeCards = new HashMap<>();
        this.prizeCards.put(player1, 6);
        this.prizeCards.put(player2, 6);
        this.discardPile = new ArrayList<>();
        this.canPlaySupporter = true;
        this.canPlayStadium = true;
        this.activeStadium = null;
    }

    public void startTurn() {
        gamePhase = "Draw";
        currentPlayer.drawCard();
        canPlaySupporter = true;
        canPlayStadium = true;
    }

    public void endTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
            opponent = player1;
        } else {
            currentPlayer = player1;
            opponent = player2;
        }
        currentPlayer.resetTurn();
        gamePhase = "Draw";
        turnNumber++;
    }

    public void playCard(Card card) {
        if (card == null) return;

        if (card instanceof PokemonCard) {
            PokemonCard pokemon = (PokemonCard)card;
            if (currentPlayer.getActivePokemon() == null) {
                currentPlayer.setActivePokemon(pokemon);
                pokemon.setActive(true);
            } else if (!currentPlayer.isBenchFull()) {
                currentPlayer.getBench().add(pokemon);
                pokemon.setBenched(true);
            }
        } else if (card instanceof EnergyCard) {
            EnergyCard energy = (EnergyCard)card;
            if (currentPlayer.getActivePokemon() != null) {
                currentPlayer.attachEnergy(energy, currentPlayer.getActivePokemon());
            }
        } else if (card instanceof TrainerCard) {
            TrainerCard trainer = (TrainerCard)card;
            if (trainer.getTrainerType().equals("Supporter") && !currentPlayer.canPlaySupporter()) {
                return;
            }
            if (trainer.getTrainerType().equals("Stadium") && !currentPlayer.canPlayStadium()) {
                return;
            }
            currentPlayer.applyTrainerEffect(trainer);
            if (trainer.getTrainerType().equals("Supporter")) {
                currentPlayer.setHasPlayedSupporter(true);
            } else if (trainer.getTrainerType().equals("Stadium")) {
                currentPlayer.setHasPlayedStadium(true);
            }
        }
        discardPile.add(card);
    }

    public void takePrizeCard(Player player) {
        int currentPrize = prizeCards.get(player);
        if (currentPrize > 0) {
            prizeCards.put(player, currentPrize - 1);
            player.drawCard();
        }
    }

    public void checkGameEnd() {
        // Check if a player has no Pokemon in play
        if (player1.getActivePokemon() == null && player1.getBench().isEmpty()) {
            gameOver = true;
            return;
        }
        if (player2.getActivePokemon() == null && player2.getBench().isEmpty()) {
            gameOver = true;
            return;
        }

        // Check if a player has taken all prize cards
        if (prizeCards.get(player1) == 0 || prizeCards.get(player2) == 0) {
            gameOver = true;
            return;
        }

        // Check if a player has no cards in deck
        if (player1.getDeck().isEmpty() || player2.getDeck().isEmpty()) {
            gameOver = true;
        }
    }

    // Getters
    public Player getCurrentPlayer() { return currentPlayer; }
    public Player getOpponent() { return opponent; }
    public int getTurnNumber() { return turnNumber; }
    public boolean isGameOver() { return gameOver; }
    public String getGamePhase() { return gamePhase; }
    public Map<Player, Integer> getPrizeCards() { return prizeCards; }
    public ArrayList<Card> getDiscardPile() { return discardPile; }
    public boolean canPlaySupporter() { return canPlaySupporter; }
    public boolean canPlayStadium() { return canPlayStadium; }
    public String getActiveStadium() { return activeStadium; }

    // Setters
    public void setGamePhase(String phase) { gamePhase = phase; }
    public void setCanPlaySupporter(boolean canPlay) { canPlaySupporter = canPlay; }
    public void setCanPlayStadium(boolean canPlay) { canPlayStadium = canPlay; }
    public void setActiveStadium(String stadium) { activeStadium = stadium; }
} 