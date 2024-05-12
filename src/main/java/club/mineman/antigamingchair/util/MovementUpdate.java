/*
 * Class file decompiled by SakuraFlower
 */

package club.mineman.antigamingchair.util;


import club.mineman.antigamingchair.location.CustomLocation;

public final class MovementUpdate {
    public final CustomLocation fromFrom;
    public final CustomLocation from;
    public final CustomLocation to;
    private final boolean ground;

    public CustomLocation getFromFrom() {
        return this.fromFrom;
    }

    public CustomLocation getFrom() {
        return this.from;
    }

    public CustomLocation getTo() {
        return this.to;
    }

    public boolean isGround() {
        return this.ground;
    }

    public MovementUpdate(CustomLocation fromFrom, CustomLocation from, CustomLocation to, boolean ground) {
        this.fromFrom = fromFrom;
        this.from = from;
        this.to = to;
        this.ground = ground;
    }
}