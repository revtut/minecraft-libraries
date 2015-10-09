package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import org.bukkit.inventory.ItemStack;

/**
 * Player Friendly Fire Event
 */
public class PlayerFriendlyFireEvent extends PlayerEvent {

    /**
     * Damager of the target
     */
    private final PlayerData damager;

    /**
     * Item used when attacked the player
     */
    private final ItemStack itemUsed;

    /**
     * Damage caused by the attack
     */
    private double damage;

    /**
     * Constructor of PlayerFriendlyFireEvent
     * @param player player that was damaged in the arena
     * @param damager player that damaged the target
     * @param itemUsed item used when attacked the player
     * @param damage damage caused by the attack
     * @param arena arena where the event occurred
     */
    public PlayerFriendlyFireEvent(final PlayerData player, final PlayerData damager, final ItemStack itemUsed, final double damage, final Arena arena) {
        super(player, arena);
        this.damager = damager;
        this.itemUsed = itemUsed;
        this.damage = damage;
    }

    /**
     * Get the damager of the target
     * @return damager of the target
     */
    public PlayerData getDamager() {
        return damager;
    }

    /**
     * Get the item used when attacked the player
     * @return item used when attacked the player
     */
    public ItemStack getItemUsed() {
        return itemUsed;
    }

    /**
     * Get the damage caused by the attack
     * @return damage caused by the attack
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Set the damage of the attack
     * @param damage new value of attack damage
     */
    public void setDamage(final double damage) {
        this.damage = damage;
    }

}
