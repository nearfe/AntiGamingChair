package club.mineman.antigamingchair.check.impl.motion;

import club.mineman.antigamingchair.AntiGamingChair;
import club.mineman.antigamingchair.check.AbstractCheck;
import club.mineman.antigamingchair.check.checks.PositionCheck;
import club.mineman.antigamingchair.data.PlayerData;
import club.mineman.antigamingchair.event.PlayerUpdatePositionEvent;
import club.mineman.antigamingchair.event.player.PlayerAlertEvent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MotionA extends PositionCheck
{
    private int illegalMovements;
    private int legalMovements;

    public MotionA(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
    }

    @Override
    public void handleCheck(final Player player, final PlayerUpdatePositionEvent event) {
        if (this.playerData.getVelocityH() == 0) {
            final double offsetH = Math.hypot(event.getTo().getX() - event.getFrom().getX(), event.getTo().getZ() - event.getFrom().getZ());
            if (player.hasMetadata("modmode")) {
                return;
            }
            int speed = 0;
            for (final PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals((Object) PotionEffectType.SPEED)) {
                    speed = effect.getAmplifier() + 1;
                    break;
                }
            }
            double threshold;
            if (this.playerData.isOnGround()) {
                threshold = 0.34;
                if (this.playerData.isOnStairs()) {
                    threshold = 0.45;
                } else if (this.playerData.isOnIce()) {
                    if (this.playerData.isUnderBlock()) {
                        threshold = 1.3;
                    } else {
                        threshold = 0.8;
                    }
                } else if (this.playerData.isUnderBlock()) {
                    threshold = 0.7;
                } else {
                    threshold = 0.36;
                    if (this.playerData.isOnStairs()) {
                        threshold = 0.45;
                    } else if (this.playerData.isOnIce()) {
                        if (this.playerData.isUnderBlock()) {
                            threshold = 1.3;
                        } else {
                            threshold = 0.8;
                        }
                    } else if (this.playerData.isUnderBlock()) {
                        threshold = 0.7;
                    }
                    threshold += 0.02 * speed;
                }
                threshold += ((player.getWalkSpeed() > 0.2f) ? (player.getWalkSpeed() * 10.0f * 0.33f) : 0.0f);
                if (offsetH > threshold) {
                    ++this.illegalMovements;
                } else {
                    ++this.legalMovements;
                }
                final int total = this.illegalMovements + this.legalMovements;
                if (total == 20) {
                    final double percentage = this.illegalMovements / 20.0 * 100.0;
                    this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Motion A (Development)");
                        final int violations = this.playerData.getViolations(this, 30000L);
                        if (this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Motion A.")) {
                            if (!this.playerData.isBanning() && violations > 2) {
                                this.ban(player, "Motion Check A");
                            }
                        }
                        final boolean b = false;
                        this.legalMovements = (b ? 1 : 0);
                        this.illegalMovements = (b ? 1 : 0);
                    }
                }
            }
        }
    }
