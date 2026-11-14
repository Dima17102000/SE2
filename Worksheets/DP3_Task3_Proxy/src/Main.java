public class Main {

    public static void main(String[] args) {

        Image image = new ImageProxy("large_image.jpg");

        System.out.println("Proxy created.");

        // First call: load and display
        image.display();

        // Second call: display without loading
        image.display();

        // Third call: display without loading
        image.display();
    }

}
