import mayflower.*;
import java.awt.Color;

public class BattleWorld extends World {
    private Label playerActiveText;
    private Label opponentActiveText;
    private GameBoard gameBoard;
    private OpponentAI opponent;
    private boolean isPlayerTurn;
    
    public BattleWorld(Deck playerDeck) {
        setBackground("img/backgrounds/battle_background.jpg");
        gameBoard = new GameBoard(playerDeck);
        opponent = new OpponentAI();
        isPlayerTurn = true;
        setupUI();
        setupGame();
    }
    
    private void setupUI() {
        try {
            // Player's active Pokemon
            playerActiveText = new Label("Active: None");
            playerActiveText.setColor(Color.WHITE);
            addObject(playerActiveText, 100, 550);  // Bottom of screen

            // Opponent's active Pokemon
            opponentActiveText = new Label("Active: None");
            opponentActiveText.setColor(Color.WHITE);
            addObject(opponentActiveText, 100, 50);  // Top of screen
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupGame() {
        // Add game board
        addObject(gameBoard, 294, 444);  // Center of screen
        
        // Deal initial hands
        gameBoard.dealInitialHands();
        opponent.setupInitialBoard();
        
        updateUI();
    }
    
    public void act() {
        if (isPlayerTurn) {
            handlePlayerTurn();
        } else {
            handleOpponentTurn();
        }
        
        // Check for game end conditions
        if (checkGameEnd()) {
            endGame();
        }
        
        updateUI();
    }
    
    private void handlePlayerTurn() {
        // Handle player input
        if (Mayflower.isKeyDown(Keyboard.KEY_SPACE)) {
            endPlayerTurn();
        }
        
        gameBoard.update();  // Handle card interactions
    }
    
    private void handleOpponentTurn() {
        opponent.takeTurn();
        endOpponentTurn();
    }
    
    private void endPlayerTurn() {
        isPlayerTurn = false;
    }
    
    private void endOpponentTurn() {
        isPlayerTurn = true;
    }
    
    private boolean checkGameEnd() {
        // Check if either player has no more Pokemon or cards
        return gameBoard.isGameOver() || opponent.isDefeated();
    }
    
    private void endGame() {
        if (gameBoard.isGameOver()) {
            showGameOver("Opponent");
        } else {
            showGameOver("Player");
        }
    }
    
    private void showGameOver(String winner) {
        Label gameOverLabel = new Label(winner + " Wins!");
        gameOverLabel.setColor(Color.WHITE);
        addObject(gameOverLabel, 294, 444);
    }
    
    private void updateUI() {
        // Update active Pokemon displays
        PokemonCard playerActive = gameBoard.getActivePokemon();
        PokemonCard opponentActive = opponent.getActivePokemon();
        
        playerActiveText.setText("Active: " + (playerActive != null ? playerActive.getName() : "None"));
        opponentActiveText.setText("Active: " + (opponentActive != null ? opponentActive.getName() : "None"));
    }
} 