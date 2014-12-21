package com.angelis.tera.game.utils;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class Geom {

    public static short getHeading(final WorldPosition fromWorldPosition, final WorldPosition toWorldPosition) {
        return (short) (Math.atan2(toWorldPosition.getY() - fromWorldPosition.getY(), toWorldPosition.getX() - fromWorldPosition.getX()) * 32768 / Math.PI);
    }

    public static Point3D getNormal(final short heading) {
        final double angle = heading * Math.PI / 32768;
        return new Point3D((float) Math.cos(angle), (float) Math.sin(angle));
    }

    public static short getHeading(final Point3D point3D) {
        return (short) (Math.atan2(point3D.getY(), point3D.getX())*32768/Math.PI);
    }

}
