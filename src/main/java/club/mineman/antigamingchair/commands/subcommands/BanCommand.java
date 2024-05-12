package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.AntiGamingChair;
import java.beans.ConstructorProperties;

import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.event.player.PlayerBanEvent;
import club.mineman.antigamingchair.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BanCommand implements SubCommand {
    private final AntiGamingChair plugin;

    public void execute(Player player, Player target, String[] args) {

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /agcban <player>");
            return;
        }

        if (target == null) {
            player.sendMessage(ChatColor.RED + "That player is not online.");
            return;
        }

        player.sendMessage(CC.GRAY + "AGC banning " + target.getName() + "...");

        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(target);
        playerData.setBanning(true);
        final PlayerBanEvent event = new PlayerBanEvent(target, "Client", 1);
        this.plugin.getServer().getPluginManager().callEvent(event);
    }

    @ConstructorProperties({"plugin"})
    public BanCommand(AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
