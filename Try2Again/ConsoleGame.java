 

import java.util.Scanner;

/**
 * Main class for running the Pokémon TCG game in console mode
 */
public class ConsoleGame {
    private Scanner scanner;
    
    public ConsoleGame() {
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("=========================================");
        System.out.println("  Welcome to Simplified Pokémon TCG");
        System.out.println("=========================================");
        
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Play Game");
            System.out.println("2. View Sample Cards");
            System.out.println("3. Exit");
            
            int choice = getIntInput("Enter your choice (1-3): ", 1, 3);
            
            switch (choice) {
                case 1:
                    playGame();
                    break;
                case 2:
                    viewSampleCards();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Thank you for playing! Goodbye.");
                    break;
            }
        }
    }
    
    private void playGame() {
        System.out.println("\n=== NEW GAME ===");
        System.out.println("Starting a new game against the computer player...");
        
        // Create players
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        Player humanPlayer = new Player(playerName);
        ComputerPlayer computerPlayer = new ComputerPlayer("Computer");
        
        // Create and shuffle decks
        Deck playerDeck = DeckBuilder.createStarterDeck();
        Deck computerDeck = DeckBuilder.createStarterDeck();
        
        // Create game
        Game game = new Game(humanPlayer, computerPlayer);
        game.setupGame(playerDeck, computerDeck);
        
        // Play game
        System.out.println("\nGame setup complete! Let's begin!");
        game.playGame();
    }
    
    private void viewSampleCards() {
        System.out.println("\n=== SAMPLE CARDS ===");
        
        PokemonCard LardiasLardios = DeckBuilder.createLardiasLardios();
        PokemonCard Nigamath = DeckBuilder.createNigamath();
        PokemonCard SuhasStroll = DeckBuilder.createSuhasStroll();
        PokemonCard Crusader = DeckBuilder.createCrusader();
        PokemonCard Vedanth = DeckBuilder.createVedanth();
        
        System.out.println("1. " + LardiasLardios.getName());
        System.out.println("2. " + Nigamath.getName());
        System.out.println("3. " + SuhasStroll.getName());
        System.out.println("4. " + Crusader.getName());
        System.out.println("5. " + Vedanth.getName());
        
        int choice = getIntInput("Select a card to view details (1-5): ", 1, 5);
        
        PokemonCard selected = null;
        switch (choice) {
            case 1:
                selected = LardiasLardios;
                break;
            case 2:
                selected = Nigamath;
                break;
            case 3:
                selected = SuhasStroll;
                break;
            case 4:
                selected = Crusader;
                break;
            case 5:
                selected = Vedanth;
                break;
        }
        
        if (selected != null) {
            System.out.println("\n" + selected.getDetails());
        }
        
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
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
    
    public static void main(String[] args) {
        ConsoleGame game = new ConsoleGame();
        game.start();
    }
}
