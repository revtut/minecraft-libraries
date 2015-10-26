package net.revtut.libraries.minecraft.appearance;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Name Tag Library.
 *
 * <P>A library with methods name tag related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class NameTag implements Listener {

    /**
     * Hide name tag of a player.
     *
     * @param p player to hide the NameTag
     */
    public static void hideNametag(final Player p) {
        final LivingEntity entidade = p.getWorld().spawn(p.getLocation(), Squid.class);
        entidade.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        p.setPassenger(entidade);
    }

    /**
     * Unhide the name tag of a player.
     *
     * @param p player to Unhide the NameTag
     */
    public static void unHideNametag(final Player p) {
        final LivingEntity entidade = (LivingEntity) p.getPassenger();
        if(entidade == null)
            return;

        p.eject();
        entidade.remove();
    }

    /**
     * Check if a player has name tag hidden.
     *
     * @param p player to see if has nametag visible
     * @return true he has it hidden
     */
    public static boolean isNameTagHidden(final Player p) {
        return p.getPassenger() != null && p.getPassenger().getType() == EntityType.SQUID && ((LivingEntity) p.getPassenger()).hasPotionEffect(PotionEffectType.INVISIBILITY);
    }

    /**
     * Change the nametag of a player.
     *
     * @param tagId id of the name tag
     * @param prefix prefix to add
     * @param suffix suffix to add
     * @param board scoreBoard of the Teams
     * @param player   player to show the NameTag
     * @param perPlayerScoreBoard if multiple ScoreBoards
     */
    public static void setNameTag(final String tagId, final String prefix, final String suffix, final Scoreboard board, final Player player, final boolean perPlayerScoreBoard) {
        if(!perPlayerScoreBoard) {
            setNameTag(tagId, prefix, suffix, board, player); // Only one ScoreBoard is in use
            return;
        }

        Scoreboard targetBoard;
        for (final Player target : Bukkit.getOnlinePlayers()) {
            targetBoard = target.getScoreboard();
            if (targetBoard != null)
                setNameTag(tagId, prefix, suffix, targetBoard, player); // Add "Player" to target Board
            setNameTag(tagId, prefix, suffix, board, target); // Add "Target" to player board
        }
    }

    /**
     * Change the name tag of a player.
     *
     * @param tagId id of the name tag
     * @param prefix prefix to add
     * @param suffix suffix to add
     * @param board scoreBoard of the Teams
     * @param player player to set the name tag
     */
    private static void setNameTag(final String tagId, final String prefix, final String suffix, final Scoreboard board, final Player player) {
        Team team = board.getTeam(tagId);
        if (team == null) {
            team = board.registerNewTeam(tagId);
            team.setPrefix(prefix);
            team.setSuffix(suffix);
            team.setAllowFriendlyFire(true);
            team.setCanSeeFriendlyInvisibles(false);
        }
        team.addEntry(player.getName());

        // Display name
        final String name = prefix + player.getName() + suffix;
        if (!player.getDisplayName().equals(name))
            player.setDisplayName(name);
    }
}
