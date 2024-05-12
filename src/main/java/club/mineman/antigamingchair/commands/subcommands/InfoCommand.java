package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.commands.subcommands.SubCommand;
import club.mineman.antigamingchair.data.PlayerData;
import java.beans.ConstructorProperties;

import club.mineman.antigamingchair.util.CC;
import club.mineman.antigamingchair.util.StringUtil;
import org.bukkit.entity.Player;

public class InfoCommand implements SubCommand {
   private final AntiGamingChair plugin;

   public void execute(Player player, Player target, String[] args) {
      if(target == null) {
         player.sendMessage(String.format(StringUtil.PLAYER_NOT_FOUND, new Object[]{args[1]}));
      } else {
         PlayerData targetData = this.plugin.getPlayerDataManager().getPlayerData(target);
         StringBuilder builder = new StringBuilder("\u00a78 \u00a78 \u00a71 \u00a73 \u00a73 \u00a77 \u00a78 \u00a7r\n");
         builder.append(CC.L_PURPLE + CC.BOLD).append(" AGC lookup for " + target.getName() + "all time:\n");
         builder.append("");
         builder.append(CC.YELLOW + CC.BOLD).append(" Player Information:").append(CC.YELLOW).append(targetData.getClient().getName()).append("\n");
         builder.append(CC.L_PURPLE + CC.I ).append("Client: ").append(CC.YELLOW).append(targetData.getClient().getName()).append("\n");
         builder.append("");
         builder.append(CC.YELLOW + CC.BOLD).append(" Statistics:").append(CC.YELLOW).append(targetData.getClient().getName()).append("\n");;
         builder.append(CC.L_PURPLE + CC.I).append("Total Logs:  ").append(CC.YELLOW).append(targetData.getClient().getName()).append("\n");
         builder.append("");
         builder.append(CC.L_PURPLE + CC.I).append("Avarage CPS: ").append(CC.YELLOW).append(targetData.getCps()).append("\n");
         builder.append(CC.L_PURPLE + CC.I).append("Avarage Ping: ").append(CC.YELLOW).append(targetData.getPing()).append("\n");

         builder.append("\u00a78 \u00a78 \u00a71 \u00a73 \u00a73 \u00a77 \u00a78 \u00a7r\n");
         player.sendMessage(builder.toString());
      }

   }

   @ConstructorProperties({"plugin"})
   public InfoCommand(AntiGamingChair plugin) {
      this.plugin = plugin;
   }
}
