import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing the game logic
 */
public class Game {
    private Player player1;
    private Player player2;
    private int currentPlayerIndex;
    private Scanner scanner;
    private boolean gameOver;
    
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayerIndex = 0;  // Player 1 goes first
        this.scanner = new Scanner(System.in);
        this.gameOver = false;
    }
    
    public void setupGame(Deck deck1, Deck deck2) {
        // Shuffle decks
        deck1.shuffle();
        deck2.shuffle();
        
        // Draw 7 cards for each player
        for (int i = 0; i < 7; i++) {
            player1.addCardToHand(deck1.drawCard());
            player2.addCardToHand(deck2.drawCard());
        }
    }
    
    public void playGame() {
        while (!gameOver) {
            Player currentPlayer = (currentPlayerIndex == 0) ? player1 : player2;
            Player opponent = (currentPlayerIndex == 0) ? player2 : player1;
            
            System.out.println("=== " + currentPlayer.getName() + "'s Turn ===");
            displayGameState();
            
            // Draw a card at the beginning of each turn (except the first turn)
            if (currentPlayer.getHand().size() < 7) {
                System.out.println(currentPlayer.getName() + " draws a card.");
                // For simplicity, we'll just add a basic energy card instead of drawing from the deck
                currentPlayer.addCardToHand(new EnergyCard("Basic", "Colorless", 1));
            }
            
            // Play a Pokemon if no active Pokemon
            if (!currentPlayer.hasActivePokemon()) {
                playPokemonFromHand(currentPlayer);
            }
            
            // Display options
            System.out.println("\nChoose an action:");
            System.out.println("1. Play a Pokémon to the bench");
            System.out.println("2. Attach an energy");
            System.out.println("3. Attack");
            System.out.println("4. End turn");
            
            int choice = getIntInput("Enter your choice (1-4): ", 1, 4);
            
            switch (choice) {
                case 1:
                    playPokemonFromHand(currentPlayer);
                    break;
                case 2:
                    attachEnergyFromHand(currentPlayer);
                    break;
                case 3:
                    if (currentPlayer.canAttack()) {
                        System.out.println("Choose an attack:");
                        System.out.println("1. " + currentPlayer.getActivePokemon().get(0).getAttack1().getName());
                        
                        if (currentPlayer.getActivePokemon().get(0).getAttack2() != null) {
                            System.out.println("2. " + currentPlayer.getActivePokemon().get(0).getAttack2().getName());
                            choice = getIntInput("Enter your choice (1-2): ", 1, 2);
                        } else {
                            choice = 1;
                        }
                        
                        currentPlayer.performAttack(opponent, choice);
                        
                        // Check if game is over
                        if (!opponent.hasActivePokemon() && opponent.getBenchPokemon().isEmpty()) {
                            System.out.println("\n=== GAME OVER ===");
                            System.out.println(currentPlayer.getName() + " wins!");
                            gameOver = true;
                        }
                        
                        // End turn after attack
                        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
                    } else {
                        System.out.println("You don't have enough energy to attack.");
                    }
                    break;
                case 4:
                    currentPlayerIndex = (currentPlayerIndex + 1) % 2;
                    break;
            }
        }
    }
    
    private void playPokemonFromHand(Player player) {
        List<PokemonCard> pokemonInHand = new ArrayList<>();
        
        for (Card card : player.getHand()) {
            if (card instanceof PokemonCard) {
                pokemonInHand.add((PokemonCard) card);
            }
        }
        
        if (pokemonInHand.isEmpty()) {
            System.out.println("No Pokémon cards in hand!");
            return;
        }
        
        System.out.println("\nPokémon in hand:");
        for (int i = 0; i < pokemonInHand.size(); i++) {
            System.out.println((i + 1) + ". " + pokemonInHand.get(i));
        }
        
        int choice = getIntInput("Choose a Pokémon to play (1-" + pokemonInHand.size() + "), or 0 to cancel: ", 0, pokemonInHand.size());
        
        if (choice == 0) {
            return;
        }
        
        PokemonCard selected = pokemonInHand.get(choice - 1);
        
        if (!player.hasActivePokemon()) {
            player.setActivePokemon(selected);
            System.out.println(selected.getName() + " is now your active Pokémon!");
        } else if (player.getBenchPokemon().size() < 5) {
            player.addPokemonToBench(selected);
            System.out.println(selected.getName() + " is now on your bench!");
        } else {
            System.out.println("Your bench is full!");
        }
    }
    
    private void attachEnergyFromHand(Player player) {
        if (!player.hasActivePokemon()) {
            System.out.println("You need an active Pokémon to attach energy!");
            return;
        }
        
        List<EnergyCard> energyInHand = new ArrayList<>();
        
        for (Card card : player.getHand()) {
            if (card instanceof EnergyCard) {
                energyInHand.add((EnergyCard) card);
            }
        }
        
        if (energyInHand.isEmpty()) {
            System.out.println("No energy cards in hand!");
            return;
        }
        
        System.out.println("\nEnergy cards in hand:");
        for (int i = 0; i < energyInHand.size(); i++) {
            System.out.println((i + 1) + ". " + energyInHand.get(i));
        }
        
        int choice = getIntInput("Choose an energy to attach (1-" + energyInHand.size() + "), or 0 to cancel: ", 0, energyInHand.size());
        
        if (choice == 0) {
            return;
        }
        
        EnergyCard selected = energyInHand.get(choice - 1);
        player.attachEnergy(selected);
        System.out.println("Attached " + selected.getName() + " energy to " + player.getActivePokemon().get(0).getName() + "!");
    }
    
    private void displayGameState() {
        Player currentPlayer = (currentPlayerIndex == 0) ? player1 : player2;
        Player opponent = (currentPlayerIndex == 0) ? player2 : player1;
        
        System.out.println("\n=== GAME STATE ===");
        
        // Display opponent's field
        System.out.println("\n" + opponent.getName() + "'s field:");
        System.out.print("Active Pokémon: ");
        if (opponent.hasActivePokemon()) {
            PokemonCard active = opponent.getActivePokemon().get(0);
            System.out.println(active.getName() + " (HP: " + active.getHp() + ")");
        } else {
            System.out.println("None");
        }
        
        System.out.print("Bench: ");
        if (opponent.getBenchPokemon().isEmpty()) {
            System.out.println("Empty");
        } else {
            for (int i = 0; i < opponent.getBenchPokemon().size(); i++) {
                PokemonCard bench = opponent.getBenchPokemon().get(i);
                System.out.print((i + 1) + ". " + bench.getName() + " (HP: " + bench.getHp() + ") ");
            }
            System.out.println();
        }
        
        // Display current player's field
        System.out.println("\n" + currentPlayer.getName() + "'s field:");
        System.out.print("Active Pokémon: ");
        if (currentPlayer.hasActivePokemon()) {
            PokemonCard active = currentPlayer.getActivePokemon().get(0);
            System.out.println(active.getName() + " (HP: " + active.getHp() + ")");
            System.out.println("Attached energy: " + currentPlayer.getTotalEnergy() + "/" + active.getEnergyRequired() + " required for attack");
        } else {
            System.out.println("None");
        }
        
        System.out.print("Bench: ");
        if (currentPlayer.getBenchPokemon().isEmpty()) {
            System.out.println("Empty");
        } else {
            for (int i = 0; i < currentPlayer.getBenchPokemon().size(); i++) {
                PokemonCard bench = currentPlayer.getBenchPokemon().get(i);
                System.out.print((i + 1) + ". " + bench.getName() + " (HP: " + bench.getHp() + ") ");
            }
            System.out.println();
        }
        
        // Display current player's hand
        System.out.println("\nYour hand:");
        List<Card> hand = currentPlayer.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i));
        }
    }
    
    private int getIntInput(String prompt, int min, int max) {
        int input = -1;
        do {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                input = -1;
            }
        } while (input < min || input > max);
        
        return input;
    }
}