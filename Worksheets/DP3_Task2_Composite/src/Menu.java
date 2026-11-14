import java.util.ArrayList;
import java.util.List;

public class Menu extends MenuComponent {

    private final String name;
    private final String description;

    private final List<MenuComponent> components = new ArrayList<>();

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent component) {
        components.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        components.remove(component);
    }

    @Override
    protected void print(String indent) {
        System.out.println("\n" + indent + name);
        System.out.println(indent + description);

        for (MenuComponent component : components) {
            component.print(indent + "  ");
        }
    }
}