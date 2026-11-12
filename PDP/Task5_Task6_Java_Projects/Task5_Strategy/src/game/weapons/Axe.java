package game.weapons;

public class Axe implements WeaponBehavior {

    @Override
    public void useWeapon() {
        System.out.println("Attacking with an axe!");
    }
}
