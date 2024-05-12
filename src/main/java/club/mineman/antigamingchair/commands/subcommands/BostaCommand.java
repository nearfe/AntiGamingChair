package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.AntiGamingChair;
import java.beans.ConstructorProperties;
import club.mineman.antigamingchair.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BostaCommand implements SubCommand {
    private final AntiGamingChair plugin;

    public void execute(Player player, Player target, String[] args) {
        if(args.length == 0) {

            player.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------------");
            player.sendMessage(CC.YELLOW + "/alerts - Regular alerts");
            player.sendMessage(CC.YELLOW + "/agcban - Force AGC Ban someone");
            player.sendMessage(CC.YELLOW + "/watch - Watch someone");
            player.sendMessage(CC.YELLOW + "/rangevl - Set range volume");
            player.sendMessage(CC.YELLOW + "/toggle - Toggle a check");
            player.sendMessage(CC.YELLOW + "/banwave - Ban Wave executable");
            player.sendMessage(CC.YELLOW + "/info - Display player information");
            player.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------------");
        }
    }

    @ConstructorProperties({"plugin"})
    public BostaCommand(AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
