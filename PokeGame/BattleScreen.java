import mayflower.*;
import java.util.*;

public class BattleScreen extends World {
    private Deck playerDeck;
    private GameBoard playerBoard;
    private GameBoard opponentBoard;
    private boolean isPlayerTurn;
    private List<Card> playerHand;
    private List<Card> opponentHand;
    private int playerPrizes;
    private int opponentPrizes;
    private Label playerActiveText;
    private Label opponentActiveText;
    private Label playerBenchText;
    private Label playerHandText;
    private Label prizeText;
    private Label actionText;
    private Label turnText;
    private List<CardActor> handActors;
    private CardActor activePokemonActor;
    private List<CardActor> benchActors;
    private CardActor opponentActiveActor;
    private List<CardActor> prizeActors;
    private Label currentPrompt;
    private int timer;
    
    public BattleScreen(HomeScreen homeScreen) {
        try {
            this.playerDeck = homeScreen.getPlayerDeck();
            this.playerBoard = new GameBoard();
            this.opponentBoard = new GameBoard();
            this.isPlayerTurn = true;
            this.playerHand = new ArrayList<>();
            this.opponentHand = new ArrayList<>();
            this.playerPrizes = 6;
            this.opponentPrizes = 6;
            this.handActors = new ArrayList<>();
            this.benchActors = new ArrayList<>();
            this.prizeActors = new ArrayList<>();
            this.timer = 0;
            
            // Initialize game
            setupGame();
            setBackground("img/backgrounds/battle_background.jpg");
            
            // Initialize UI elements
            setupUI();
        } catch (Exception e) {
            System.out.println("Error initializing BattleScreen: " + e.getMessage());
        }
    }
    
    private void setupUI() {
        try {
            // Player's active Pokemon
            playerActiveText = new Label("Active: None");
            playerActiveText.setColor(mayflower.Color.WHITE);
            addObject(playerActiveText, 100, 400);
            
            // Opponent's active Pokemon
            opponentActiveText = new Label("Opponent Active: None");
            opponentActiveText.setColor(mayflower.Color.WHITE);
            addObject(opponentActiveText, 100, 100);
            
            // Player's bench
            playerBenchText = new Label("Bench: None");
            playerBenchText.setColor(mayflower.Color.WHITE);
            addObject(playerBenchText, 100, 450);
            
            // Player's hand
            playerHandText = new Label("Hand: None");
            playerHandText.setColor(mayflower.Color.WHITE);
            addObject(playerHandText, 100, 500);
            
            // Prize cards
            prizeText = new Label("Your Prizes: 6 | Opponent Prizes: 6");
            prizeText.setColor(mayflower.Color.WHITE);
            addObject(prizeText, 300, 50);
            
            // Available actions
            actionText = new Label("Actions: P-Play Pokemon | E-Attach Energy | A-Attack | R-Retreat | T-End Turn");
            actionText.setColor(mayflower.Color.WHITE);
            addObject(actionText, 100, 550);
            
            // Turn indicator
            turnText = new Label("Your Turn");
            turnText.setColor(mayflower.Color.YELLOW);
            addObject(turnText, 50, 50);
        } catch (Exception e) {
            System.out.println("Error setting up UI: " + e.getMessage());
        }
    }
    
    private void updateUI() {
        try {
            // Update active Pokemon
            if (playerBoard.getActivePokemon() != null) {
                if (activePokemonActor == null || activePokemonActor.getCard() != playerBoard.getActivePokemon()) {
                    if (activePokemonActor != null) {
                        removeObject(activePokemonActor);
                    }
                    activePokemonActor = new CardActor(playerBoard.getActivePokemon());
                    addObject(activePokemonActor, 300, 400);
                }
            } else if (activePokemonActor != null) {
                removeObject(activePokemonActor);
                activePokemonActor = null;
            }
            
            // Update opponent's active Pokemon
            if (opponentBoard.getActivePokemon() != null) {
                if (opponentActiveActor == null || opponentActiveActor.getCard() != opponentBoard.getActivePokemon()) {
                    if (opponentActiveActor != null) {
                        removeObject(opponentActiveActor);
                    }
                    opponentActiveActor = new CardActor(opponentBoard.getActivePokemon());
                    addObject(opponentActiveActor, 300, 100);
                }
            } else if (opponentActiveActor != null) {
                removeObject(opponentActiveActor);
                opponentActiveActor = null;
            }
            
            // Update bench
            List<PokemonCard> bench = playerBoard.getBench();
            if (bench != null) {
                while (benchActors.size() > bench.size()) {
                    removeObject(benchActors.remove(benchActors.size() - 1));
                }
                
                for (int i = 0; i < bench.size(); i++) {
                    if (i >= benchActors.size() || benchActors.get(i).getCard() != bench.get(i)) {
                        if (i < benchActors.size()) {
                            removeObject(benchActors.get(i));
                        }
                        CardActor actor = new CardActor(bench.get(i));
                        benchActors.add(i, actor);
                        addObject(actor, 150 + i * 100, 450);
                    }
                }
            }
            
            // Update hand
            if (playerHand != null) {
                while (handActors.size() > playerHand.size()) {
                    removeObject(handActors.remove(handActors.size() - 1));
                }
                
                for (int i = 0; i < playerHand.size(); i++) {
                    if (i >= handActors.size() || handActors.get(i).getCard() != playerHand.get(i)) {
                        if (i < handActors.size()) {
                            removeObject(handActors.get(i));
                        }
                        CardActor actor = new CardActor(playerHand.get(i));
                        handActors.add(i, actor);
                        addObject(actor, 150 + i * 100, 500);
                    }
                }
            }
            
            // Update prize cards
            if (playerBoard.getPrizeCards() != null) {
                while (prizeActors.size() > playerBoard.getRemainingPrizeCards()) {
                    removeObject(prizeActors.remove(prizeActors.size() - 1));
                }
                
                for (int i = 0; i < playerBoard.getRemainingPrizeCards(); i++) {
                    if (i >= prizeActors.size()) {
                        Card prizeCard = playerBoard.getPrizeCards().get(i);
                        if (prizeCard != null) {
                            CardActor actor = new CardActor(prizeCard);
                            actor.setFaceUp(false); // Prize cards are face down
                            prizeActors.add(actor);
                            addObject(actor, 400 + i * 50, 50);
                        }
                    }
                }
            }
            
            // Update text displays
            if (playerActiveText != null) {
                playerActiveText.setText("Active: " + (playerBoard.getActivePokemon() != null ? 
                    playerBoard.getActivePokemon().getName() : "None"));
            }
            
            if (opponentActiveText != null) {
                opponentActiveText.setText("Opponent Active: " + (opponentBoard.getActivePokemon() != null ? 
                    opponentBoard.getActivePokemon().getName() : "None"));
            }
            
            if (prizeText != null) {
                prizeText.setText("Your Prizes: " + playerBoard.getRemainingPrizeCards() + 
                    " | Opponent Prizes: " + opponentBoard.getRemainingPrizeCards());
            }
            
            if (turnText != null) {
                turnText.setText(isPlayerTurn ? "Your Turn" : "Opponent's Turn");
                turnText.setColor(isPlayerTurn ? mayflower.Color.YELLOW : mayflower.Color.RED);
            }
        } catch (Exception e) {
            System.out.println("Error updating UI: " + e.getMessage());
        }
    }
    
    private void setupGame() {
        try {
            // Shuffle decks
            if (playerDeck != null) {
                playerDeck.shuffle();
                
                // Draw initial hands
                for (int i = 0; i < 7; i++) {
                    Card card = playerDeck.drawCard();
                    if (card != null) {
                        playerHand.add(card);
                    }
                }
                
                // Set up prize cards
                List<Card> playerPrizes = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    Card card = playerDeck.drawCard();
                    if (card != null) {
                        playerPrizes.add(card);
                    }
                }
                playerBoard.setPrizeCards(playerPrizes);
            }
        } catch (Exception e) {
            System.out.println("Error setting up game: " + e.getMessage());
        }
    }
    
    public void act() {
        try {
            timer++;
            updateUI();
            if (isPlayerTurn) {
                handlePlayerInput();
            } else {
                // Opponent's turn logic would go here
                if (timer % 60 == 0) {
                    endTurn();
                }
            }
        } catch (Exception e) {
            System.out.println("Error in act method: " + e.getMessage());
        }
    }
    
    private void handlePlayerInput() {
        try {
            if (Mayflower.isKeyDown(Keyboard.KEY_P)) {
                playPokemon();
            } else if (Mayflower.isKeyDown(Keyboard.KEY_E)) {
                attachEnergy();
            } else if (Mayflower.isKeyDown(Keyboard.KEY_A)) {
                attack();
            } else if (Mayflower.isKeyDown(Keyboard.KEY_R)) {
                retreat();
            } else if (Mayflower.isKeyDown(Keyboard.KEY_T)) {
                endTurn();
            } else if (Mayflower.isKeyDown(Keyboard.KEY_ESCAPE)) {
                Mayflower.setWorld(new HomeScreen());
            }
        } catch (Exception e) {
            System.out.println("Error handling player input: " + e.getMessage());
        }
    }
    
    private void playPokemon() {
        try {
            if (currentPrompt != null) {
                removeObject(currentPrompt);
            }
            
            currentPrompt = new Label("Click on a Pokemon card to play it");
            addObject(currentPrompt, 100, 600);
            
            for (CardActor actor : handActors) {
                if (actor != null && Mayflower.mouseClicked(actor) && actor.getCard() instanceof PokemonCard) {
                    PokemonCard pokemon = (PokemonCard) actor.getCard();
                    if (playerBoard.getActivePokemon() == null) {
                        playerBoard.setActivePokemon(pokemon);
                    } else if (playerBoard.getBench().size() < 5) {
                        playerBoard.addToBench(pokemon);
                    }
                    playerHand.remove(actor.getCard());
                    removeObject(currentPrompt);
                    currentPrompt = null;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error playing Pokemon: " + e.getMessage());
            if (currentPrompt != null) {
                removeObject(currentPrompt);
                currentPrompt = null;
            }
        }
    }
    
    private void attachEnergy() {
        try {
            if (currentPrompt != null) {
                removeObject(currentPrompt);
            }
            
            if (playerBoard.getActivePokemon() != null || !playerBoard.getBench().isEmpty()) {
                currentPrompt = new Label("Click on a Pokemon, then an Energy card");
                addObject(currentPrompt, 100, 600);
                
                // Wait for Pokemon selection
                PokemonCard selectedPokemon = null;
                if (activePokemonActor != null && Mayflower.mouseClicked(activePokemonActor)) {
                    selectedPokemon = (PokemonCard) activePokemonActor.getCard();
                } else {
                    for (CardActor actor : benchActors) {
                        if (actor != null && Mayflower.mouseClicked(actor)) {
                            selectedPokemon = (PokemonCard) actor.getCard();
                            break;
                        }
                    }
                }
                
                if (selectedPokemon != null) {
                    // Wait for Energy selection
                    for (CardActor actor : handActors) {
                        if (actor != null && Mayflower.mouseClicked(actor) && actor.getCard() instanceof EnergyCard) {
                            playerBoard.attachEnergy(selectedPokemon, (EnergyCard) actor.getCard());
                            playerHand.remove(actor.getCard());
                            removeObject(currentPrompt);
                            currentPrompt = null;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error attaching energy: " + e.getMessage());
            if (currentPrompt != null) {
                removeObject(currentPrompt);
                currentPrompt = null;
            }
        }
    }
    
    private void attack() {
        try {
            if (currentPrompt != null) {
                removeObject(currentPrompt);
            }
            
            if (playerBoard.getActivePokemon() != null && opponentBoard.getActivePokemon() != null) {
                PokemonCard attacker = playerBoard.getActivePokemon();
                PokemonCard defender = opponentBoard.getActivePokemon();
                
                List<EnergyCard> attachedEnergy = playerBoard.getAttachedEnergy(attacker);
                List<PokemonCard.Attack> availableAttacks = new ArrayList<>();
                
                // Find available attacks
                for (PokemonCard.Attack attack : attacker.getAttacks()) {
                    if (hasEnoughEnergy(attachedEnergy, attack.getEnergyCost())) {
                        availableAttacks.add(attack);
                    }
                }
                
                if (!availableAttacks.isEmpty()) {
                    currentPrompt = new Label("Click on an attack to use it");
                    addObject(currentPrompt, 100, 600);
                    
                    // Show attack options
                    List<Label> attackLabels = new ArrayList<>();
                    for (int i = 0; i < availableAttacks.size(); i++) {
                        PokemonCard.Attack attack = availableAttacks.get(i);
                        Label attackLabel = new Label((i + 1) + ". " + attack.getName() + " (" + attack.getDamage() + " damage)");
                        addObject(attackLabel, 100, 620 + i * 20);
                        attackLabels.add(attackLabel);
                    }
                    
                    // Check for clicks on attack labels
                    for (int i = 0; i < attackLabels.size(); i++) {
                        Label label = attackLabels.get(i);
                        if (Mayflower.mouseClicked(label)) {
                            PokemonCard.Attack selectedAttack = availableAttacks.get(i);
                            if (defender.getHealth() <= selectedAttack.getDamage()) {
                                handleKnockout(defender, true);
                            }
                            removeObject(currentPrompt);
                            currentPrompt = null;
                            for (Label l : attackLabels) {
                                removeObject(l);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in attack: " + e.getMessage());
            if (currentPrompt != null) {
                removeObject(currentPrompt);
                currentPrompt = null;
            }
        }
    }
    
    private void retreat() {
        try {
            if (currentPrompt != null) {
                removeObject(currentPrompt);
            }
            
            if (playerBoard.getActivePokemon() != null) {
                PokemonCard active = playerBoard.getActivePokemon();
                if (playerBoard.canRetreat(active)) {
                    playerBoard.retreatPokemon(active);
                    // Allow player to select a new active Pokemon from bench
                    if (!playerBoard.getBench().isEmpty()) {
                        currentPrompt = new Label("Click on a bench Pokemon to make it active");
                        addObject(currentPrompt, 100, 600);
                        
                        for (CardActor actor : benchActors) {
                            if (actor != null && Mayflower.mouseClicked(actor)) {
                                playerBoard.setActivePokemon((PokemonCard) actor.getCard());
                                playerBoard.getBench().remove(actor.getCard());
                                removeObject(currentPrompt);
                                currentPrompt = null;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in retreat: " + e.getMessage());
            if (currentPrompt != null) {
                removeObject(currentPrompt);
                currentPrompt = null;
            }
        }
    }
    
    private void endTurn() {
        try {
            isPlayerTurn = false;
            // Draw a card
            if (playerDeck != null && !playerDeck.isEmpty()) {
                Card card = playerDeck.drawCard();
                if (card != null) {
                    playerHand.add(card);
                }
            }
            // Opponent's turn logic would go here
            isPlayerTurn = true;
        } catch (Exception e) {
            System.out.println("Error ending turn: " + e.getMessage());
        }
    }
    
    private boolean hasEnoughEnergy(List<EnergyCard> attachedEnergy, List<String> requiredEnergy) {
        try {
            return attachedEnergy != null && requiredEnergy != null && 
                   attachedEnergy.size() >= requiredEnergy.size();
        } catch (Exception e) {
            System.out.println("Error checking energy: " + e.getMessage());
            return false;
        }
    }
    
    private void handleKnockout(PokemonCard defeatedPokemon, boolean isOpponent) {
        try {
            if (defeatedPokemon != null) {
                if (isOpponent) {
                    opponentBoard.discardCard(defeatedPokemon);
                    Card prize = playerBoard.takePrizeCard();
                    if (prize != null) {
                        playerHand.add(prize);
                    }
                    playerPrizes--;
                } else {
                    playerBoard.discardCard(defeatedPokemon);
                    opponentPrizes--;
                }
                
                if (playerPrizes == 0 || opponentPrizes == 0) {
                    endGame(playerPrizes == 0);
                }
            }
        } catch (Exception e) {
            System.out.println("Error handling knockout: " + e.getMessage());
        }
    }
    
    private void endGame(boolean playerWon) {
        try {
            Label gameOverText = new Label(playerWon ? "Congratulations! You won the game!" : "Game Over! You lost!");
            gameOverText.setColor(playerWon ? mayflower.Color.GREEN : mayflower.Color.RED);
            addObject(gameOverText, 300, 300);
            
            // Return to home screen after delay
            if (timer % 120 == 0) {
                Mayflower.setWorld(new HomeScreen());
            }
        } catch (Exception e) {
            System.out.println("Error ending game: " + e.getMessage());
        }
    }
}