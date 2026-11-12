package game;

import game.characters.Knight;
import game.characters.Troll;
import game.weapons.Axe;
import game.weapons.Sword;

public class Main {

    public static void main(String[] args) {

        Knight knight = new Knight();
        Troll troll = new Troll();

        // Test initial weapon behavior
        System.out.println("Knight:");
        knight.fight();

        System.out.println("Troll:");
        troll.fight();

        // Change weapons at runtime
        knight.setWeapon(new Axe());
        troll.setWeapon(new Sword());

        // Test new weapon behavior
        System.out.println("Knight after switching weapons:");
        knight.fight();

        System.out.println("Troll after switching weapons:");
        troll.fight();
    }
}
