package club.mineman.antigamingchair.check.impl.timer;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.check.checks.PacketCheck;
import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.event.player.PlayerAlertEvent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.entity.Player;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class TimerA extends PacketCheck {
    private Deque<Long> delays;
    private long lastPacketTime;

    public TimerA(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
        this.delays = new LinkedList<Long>();
    }

    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying && !this.playerData.isAllowTeleport() && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 220L) {
            this.delays.add(System.currentTimeMillis() - this.lastPacketTime);
            if (this.delays.size() == 40) {
                double average = 0.0;
                for (final long l : this.delays) {
                    average += l;
                }
                average /= this.delays.size();
                double vl = this.playerData.getCheckVl(this);
                if (average <= 49.0) {
                    if ((vl += 1.25) > 4.0 && this.alert(PlayerAlertEvent.AlertType.RELEASE, player, String.format("failed Timer Check A. AVG %.3f. VL %.2f.", average, vl)) && vl >= 20.0) {
                        if (average >= 35.714285714285715) {
                            if (!this.playerData.isBanWave()) {
                                this.banWave(player, "Timer Check A");
                            }
                        } else if (!this.playerData.isBanning()) {
                            this.ban(player, "Timer Check A");
                        }
                    }
                } else {
                    vl -= 0.5;
                }
                this.playerData.setCheckVl(vl, this);
                this.delays.clear();
            }
            this.lastPacketTime = System.currentTimeMillis();
        }
    }
}
