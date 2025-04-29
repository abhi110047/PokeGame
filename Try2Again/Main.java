public class Main {
    public static void main(String[] args) {
        System.out.println("Launching Pokemon TCG game via main launcher...");
        
        
        try {
            // Run the real main class from our main package
            ConsoleGame.main(args);
        } catch (Exception e) {
            System.out.println("Error launching game: " + e.getMessage());
            e.printStackTrace();
        }
    }
}