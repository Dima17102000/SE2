package game.characters;

import game.weapons.WeaponBehavior;

public abstract class GameCharacter {

    private WeaponBehavior weaponBehavior;

    public GameCharacter(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }

    public void setWeapon(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }

    public void fight() {
        weaponBehavior.useWeapon();
    }
}
