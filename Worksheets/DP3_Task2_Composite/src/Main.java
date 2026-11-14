public class Main {

    public static void main(String[] args) {

        // Create menus
        Menu mainMenu = new Menu("Main Menu", "Restaurant menu");
        Menu dinnerMenu = new Menu("Dinner Menu", "Dinner dishes");
        Menu pancakeMenu = new Menu("Pancake House Menu", "Pancakes");
        Menu cafeMenu = new Menu("Cafe Menu", "Cafe dishes");
        Menu dessertMenu = new Menu("Dessert Menu", "Desserts");

        // Build Composite structure
        mainMenu.add(dinnerMenu);
        mainMenu.add(pancakeMenu);
        mainMenu.add(cafeMenu);

        // Nested menu
        dinnerMenu.add(dessertMenu);

        // Dinner items
        dinnerMenu.add(new MenuItem(
                "Pasta", "Pasta with tomato sauce", 12.50, true));

        dinnerMenu.add(new MenuItem(
                "Steak", "Beef steak", 22.00, false));

        // Dessert items
        dessertMenu.add(new MenuItem(
                "Ice Cream", "Vanilla ice cream", 5.00, true));

        dessertMenu.add(new MenuItem(
                "Cake", "Chocolate cake", 6.50, true));

        // Pancake items
        pancakeMenu.add(new MenuItem(
                "Pancakes", "Pancakes with maple syrup", 8.50, true));

        // Cafe items
        cafeMenu.add(new MenuItem(
                "Sandwich", "Chicken sandwich", 7.50, false));

        cafeMenu.add(new MenuItem(
                "Salad", "Fresh vegetable salad", 9.00, true));

        // Demonstrate add and remove
        MenuItem temporaryItem = new MenuItem(
                "Temporary", "Test item", 1.00, true);

        cafeMenu.add(temporaryItem);
        cafeMenu.remove(temporaryItem);

        // Print complete menu
        mainMenu.print();
    }

}