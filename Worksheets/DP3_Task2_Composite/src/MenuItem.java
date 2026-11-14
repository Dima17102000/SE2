public class MenuItem extends MenuComponent {

    private final String name;
    private final String description;
    private final double price;
    private final boolean vegetarian;

    public MenuItem(String name, String description,
            double price, boolean vegetarian) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
    }

    @Override
    protected void print(String indent) {
        System.out.println(indent + name + " - " + price + " EUR");
        System.out.println(indent + description);
        System.out.println(indent + "Vegetarian: " + vegetarian);
    }
}