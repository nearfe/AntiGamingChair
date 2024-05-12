package club.mineman.antigamingchair.check.impl.autoclicker;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.check.checks.PacketCheck;
import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.event.player.PlayerAlertEvent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.Player;

import java.util.Deque;

public class AutoClickerH extends PacketCheck {
    private boolean failed;
    private boolean sent;
    private Deque<Integer> recentCounts;
    private int flyingCount;
    private boolean release;

    public AutoClickerH(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
    }

    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && !this.playerData.isDigging() && !this.playerData.isPlacing() && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 220L && this.playerData.getLastMovePacket() != null && System.currentTimeMillis() - this.playerData.getLastMovePacket().getTimestamp() < 110L && !this.playerData.isDigging()) {
            if (this.flyingCount < 10) {
                if (this.release) {
                    this.release = false;
                    this.flyingCount = 0;
                    return;
                }
                this.recentCounts.add(this.flyingCount);
                if (this.recentCounts.size() == 100) {
                    double average = 0.0;
                    for (final int i : this.recentCounts) {
                        average += i;
                    }
                    average /= this.recentCounts.size();
                    double stdDev = 0.0;
                    for (final int j : this.recentCounts) {
                        stdDev += Math.pow(j - average, 2.0);
                    }
                    stdDev /= this.recentCounts.size();
                    stdDev = Math.sqrt(stdDev);
                    double vl = this.playerData.getCheckVl(this);
                    this.alert(PlayerAlertEvent.AlertType.EXPERIMENTAL, player, "failed Auto Clicker Check H (Experimental).");
                    this.failed = true;
                }
            } else {
                this.sent = true;
            }
        } else if (packet instanceof PacketPlayInFlying) {
            ++this.flyingCount;
        } else if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
            this.release = true;
        }
    }
}
