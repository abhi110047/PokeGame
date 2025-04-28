import mayflower.*;

public class PokemonTCGWorld extends World {
    private GameState gameState;
    private Label turnLabel;
    private Label phaseLabel;
    private Label prizeLabel;
    private Label messageLabel;

    public PokemonTCGWorld(String player1Name, String player2Name) {
        super();
        // Create a solid green background
        try {
            setBackground("images/table_background.png");
        } catch (Exception e) {
            // If background image fails to load, use a default color
            MayflowerImage background = new MayflowerImage("images/blank.png");
            background.scale(800, 600);
            setBackground(background);
        }
        
        // Initialize game state
        gameState = new GameState(new Player(player1Name), new Player(player2Name));
        
        // Initialize UI elements
        turnLabel = new Label("Current Turn: " + gameState.getCurrentPlayer().getName(), 20, new Color(255, 255, 255));
        addObject(turnLabel, 10, 10);
        
        phaseLabel = new Label("Phase: " + gameState.getGamePhase(), 16, new Color(255, 255, 255));
        addObject(phaseLabel, 10, 40);
        
        prizeLabel = new Label("Prize Cards: " + gameState.getPrizeCards().get(gameState.getCurrentPlayer()), 16, new Color(255, 255, 255));
        addObject(prizeLabel, 10, 70);
        
        messageLabel = new Label("", 16, new Color(255, 255, 255));
        addObject(messageLabel, 10, 100);
        
        // Initialize players' decks and draw initial hands
        initializeGame();
    }

    private void initializeGame() {
        gameState.getCurrentPlayer().initializeDeck();
        gameState.getOpponent().initializeDeck();
        gameState.getCurrentPlayer().drawInitialHand();
        gameState.getOpponent().drawInitialHand();
        updateDisplay();
    }

    @Override
    public void act() {
        if (!gameState.isGameOver()) {
            handleInput();
            updateDisplay();
        } else {
            showGameOver();
        }
    }

    private void handleInput() {
        if (Mayflower.isKeyDown(Keyboard.KEY_SPACE)) {
            endTurn();
        }
    }

    private void endTurn() {
        gameState.endTurn();
        updateDisplay();
    }

    private void updateDisplay() {
        turnLabel.setText("Current Turn: " + gameState.getCurrentPlayer().getName());
        phaseLabel.setText("Phase: " + gameState.getGamePhase());
        prizeLabel.setText("Prize Cards: " + gameState.getPrizeCards().get(gameState.getCurrentPlayer()));
    }

    private void showGameOver() {
        messageLabel.setText("Game Over! Winner: " + determineWinner());
    }

    private String determineWinner() {
        if (gameState.getPrizeCards().get(gameState.getCurrentPlayer()) == 0) {
            return gameState.getCurrentPlayer().getName();
        } else if (gameState.getPrizeCards().get(gameState.getOpponent()) == 0) {
            return gameState.getOpponent().getName();
        }
        return "Draw";
    }
} 