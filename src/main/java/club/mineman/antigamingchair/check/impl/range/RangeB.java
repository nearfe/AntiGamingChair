package club.mineman.antigamingchair.check.impl.range;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.check.AbstractCheck;
import club.mineman.antigamingchair.check.checks.PacketCheck;
import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.location.CustomLocation;
import club.mineman.antigamingchair.util.MathUtil;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class RangeB extends PacketCheck {
    private final Deque<Double> distances;

    private boolean sameTick;

    public RangeB(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
        this.distances = new LinkedList<Double>();
    }

    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity && !player.getGameMode().equals((Object) GameMode.CREATIVE) && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 220L && this.playerData.getLastMovePacket() != null && System.currentTimeMillis() - this.playerData.getLastMovePacket().getTimestamp() < 110L && !this.sameTick) {
            final PacketPlayInUseEntity useEntity = (PacketPlayInUseEntity) packet;
            if (useEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                final Entity targetEntity = useEntity.a(((CraftPlayer) player).getHandle().getWorld());
                if (targetEntity instanceof EntityPlayer) {
                    final Player target = (Player) targetEntity.getBukkitEntity();
                    final CustomLocation targetLocation = this.playerData.getLastPlayerPacket(target.getUniqueId(), MathUtil.pingFormula(this.playerData.getPing()));
                    if (targetLocation == null) {
                        return;
                    }
                    final long diff = System.currentTimeMillis() - targetLocation.getTimestamp();
                    final long estimate = MathUtil.pingFormula(this.playerData.getPing()) * 50L;
                    final long diffEstimate = diff - estimate;
                    if (diffEstimate >= 500L) {
                        return;
                    }
                    final CustomLocation playerLocation = this.playerData.getLastMovePacket();
                    final PlayerData targetData = this.getPlugin().getPlayerDataManager().getPlayerData(target);
                    if (targetData == null) {
                        return;
                    }
                    final double range = Math.hypot(playerLocation.getX() - targetLocation.getX(), playerLocation.getZ() - targetLocation.getZ());
                    if (range > 6.5) {
                        return;
                    }
                    double threshold = 3.2;
                    if (!targetData.isSprinting() || MathUtil.getDistanceBetweenAngles(playerLocation.getYaw(), targetLocation.getYaw()) <= 90.0) {
                        threshold = 3.5;
                    }
                    double vl = this.playerData.getCheckVl(this);
                    if (range > threshold) {
                        this.plugin.getAlertsManager().forceAlert(String.format("failed Range Check B (Development). %.3f. %.2f. VL %.2f.", range, -0.2, vl), player);
                    } else {
                        vl -= 0.3;
                    }
                    this.playerData.setCheckVl(vl, this);
                    this.distances.clear();
                }
            }
        }
    }
}