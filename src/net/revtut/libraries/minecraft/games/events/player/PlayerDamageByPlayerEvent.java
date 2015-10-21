package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import org.bukkit.inventory.ItemStack;

/**
 * Player Damage By Player Event
 */
public class PlayerDamageByPlayerEvent extends PlayerEvent {

    /**
     * Damager of the target
     */
    private final GamePlayer damager;

    /**
     * Item used when attacked the player
     */
    private final ItemStack itemUsed;

    /**
     * Damage caused by the attack
     */
    private double damage;

    /**
     * Constructor of PlayerDamageByPlayerEvent
     * @param player player that was damaged in the arena
     * @param damager player that damaged the target
     * @param itemUsed item used when attacked the player
     * @param damage damage caused by the attack
     * @param arena arena where the event occurred
     */
    public PlayerDamageByPlayerEvent(final GamePlayer player, final GamePlayer damager, final ItemStack itemUsed, final double damage, final Arena arena) {
        super(player, arena);
        this.damager = damager;
        this.itemUsed = itemUsed;
        this.damage = damage;
    }

    /**
     * Get the damager of the target
     * @return damager of the target
     */
    public GamePlayer getDamager() {
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
