package game.weapons;

public class Sword implements WeaponBehavior {

    @Override
    public void useWeapon() {
        System.out.println("Attacking with a sword!");
    }
}
