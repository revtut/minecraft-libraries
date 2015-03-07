package net.revtut.libraries.nametag;

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
public final class NameTagAPI implements Listener {

    /**
     * Hide name tag of a player.
     *
     * @param p player to hide the NameTag
     */
    public static void hideNametag(Player p) {
        LivingEntity entidade = p.getWorld().spawn(p.getLocation(), Squid.class);
        entidade.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        p.setPassenger(entidade);
    }

    /**
     * Unhide the name tag of a player.
     *
     * @param p player to Unhide the NameTag
     */
    public static void unHideNametag(Player p) {
        LivingEntity entidade = (LivingEntity) p.getPassenger();
        p.eject();
        entidade.remove();
    }

    /**
     * Check if a player has name tag hidden.
     *
     * @param p player to see if has nametag visible
     * @return true he has it hidden
     */
    public static boolean isNameTagHidden(Player p) {
        return p.getPassenger() != null && p.getPassenger().getType() == EntityType.SQUID && ((LivingEntity) p.getPassenger()).hasPotionEffect(PotionEffectType.INVISIBILITY);
    }

    /**
     * Change the nametag of a player.
     *
     * @param board scoreBoard of the Teams
     * @param p   player to show the NameTag
     * @param id     id of the group
     * @param prefix prefix of the player
     * @param perPlayerScoreBoard if multiple ScoreBoards
     */
    public static void setNameTag(Scoreboard board, Player p, String id, String prefix, boolean perPlayerScoreBoard) {
        if(!perPlayerScoreBoard) {
            setNameTag(board, p, id, prefix); // Only one ScoreBoard is in use
            return;
        }

        Scoreboard alvoBoard;
        for (Player alvo : Bukkit.getOnlinePlayers()) {
            alvoBoard = alvo.getScoreboard();
            if (alvoBoard != null)
                setNameTag(alvoBoard, p, id, prefix); // Adicionar "Player" a ScoreBoard do Alvo
            setNameTag(board, alvo, id, prefix); // Adicionar "Alvo" a ScoreBoard do Player
        }
    }

    /**
     * Change the nametag of a player.
     *
     * @param board  scoreBoard of the Teams
     * @param p      player to show the NameTag
     * @param id     id of the group
     * @param prefix prefix of the player
     */
    private static void setNameTag(Scoreboard board, Player p, String id, String prefix) {
        if(prefix.length() > 0)
            prefix += " ";
        String sufix = "";

        Team team = board.getTeam(id);
        if (team == null) {
            team = board.registerNewTeam(id);
            team.setPrefix(prefix);
            team.setSuffix(sufix);
            team.setAllowFriendlyFire(true);
            team.setCanSeeFriendlyInvisibles(false);
        }
        team.addPlayer(p);

        // Display name
        String name = prefix + p.getName();
        if (!p.getDisplayName().equals(name))
            p.setDisplayName(name);
    }
}
