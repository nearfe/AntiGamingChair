package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.event.BanWaveEvent;
import club.mineman.antigamingchair.event.player.PlayerBanWaveEvent;
import club.mineman.antigamingchair.util.StringUtil;
import java.beans.ConstructorProperties;
import java.util.Iterator;
import club.mineman.antigamingchair.util.CC;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BanWaveCommand implements SubCommand {
    private final AntiGamingChair plugin;

    public void execute(final Player player, Player target, String[] args) {
        if (args.length >= 2) {
            String var4 = args[1].toLowerCase();
            byte var5 = -1;
            switch(var4.hashCode()) {
                case 96417:
                    if (var4.equals("add")) {
                        var5 = 2;
                    }
                    break;
                case 3322014:
                    if (var4.equals("list")) {
                        var5 = 3;
                    }
                    break;
                case 3540994:
                    if (var4.equals("stop")) {
                        var5 = 1;
                    }
                    break;
                case 109757538:
                    if (var4.equals("start")) {
                        var5 = 0;
                    }
            }

            switch(var5) {
                case 0:
                    BanWaveEvent banWaveEvent = new BanWaveEvent(player.getName());
                    this.plugin.getServer().getPluginManager().callEvent(banWaveEvent);
                    break;
                case 1:
                    this.plugin.getBanWaveManager().setBanWaveStarted(false);
                    break;
                case 2:
                    if (args.length < 3) {
                        return;
                    }

                    Player player1 = this.plugin.getServer().getPlayer(args[2]);
                    if (player1 == null) {
                        player.sendMessage(String.format(StringUtil.PLAYER_NOT_FOUND, args[2]));
                        return;
                    }

                    PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player1);
                    if (!playerData.isBanWave()) {
                        playerData.setBanWave(true);
                        PlayerBanWaveEvent banEvent = new PlayerBanWaveEvent(player1, "Manual");
                        this.plugin.getServer().getPluginManager().callEvent(banEvent);
                        player.sendMessage(CC.L_PURPLE + "Added " + CC.D_PURPLE + player1.getName() + CC.L_PURPLE + " to the ban wave.");
                    }
                    break;
                        }
            }

        }

    @ConstructorProperties({"plugin"})
    public BanWaveCommand(AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}