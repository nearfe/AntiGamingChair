package club.mineman.antigamingchair.check.impl.velocity;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.check.checks.PositionCheck;
import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.event.PlayerUpdatePositionEvent;
import club.mineman.antigamingchair.event.player.PlayerAlertEvent;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class VelocityC extends PositionCheck {
    public VelocityC(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
    }

    @Override
    public void handleCheck(final Player player, final PlayerUpdatePositionEvent event) {
        final double offsetY = event.getTo().getY() - event.getFrom().getY();
        final double offsetH = Math.hypot(event.getTo().getX() - event.getFrom().getX(), event.getTo().getZ() - event.getFrom().getZ());
        final double velocityH = Math.hypot(this.playerData.getVelocityX(), this.playerData.getVelocityZ());
        final EntityPlayer entityPlayer = ((CraftPlayer) event.getPlayer()).getHandle();
        if (this.playerData.getVelocityY() > 0.0 && this.playerData.isOnGround() && !this.playerData.isUnderBlock() && !this.playerData.isWasUnderBlock() && !this.playerData.isInLiquid() && !this.playerData.isInWeb() && event.getFrom().getY() % 1.0 == 0.0 && offsetY > 0.0 && offsetY < 0.41999998688697815 && velocityH > 0.45 && !entityPlayer.world.c(entityPlayer.getBoundingBox().grow(1.0, 0.0, 1.0))) {
            final double ratio = offsetH / velocityH;
            double vl = this.playerData.getCheckVl(this);
            if (ratio < 0.62) {
                if (++vl >= 5) {
                    this.alert(PlayerAlertEvent.AlertType.EXPERIMENTAL, player, "failed Velocity Check C (Maintenance so its not banning). D " + ". VL " + vl + ".");
                }
            } else {
                --vl;
            }
            this.playerData.setCheckVl(vl, this);
        }
    }
}


