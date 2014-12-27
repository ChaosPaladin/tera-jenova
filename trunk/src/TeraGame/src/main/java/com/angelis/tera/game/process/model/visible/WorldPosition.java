package com.angelis.tera.game.process.model.visible;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.utils.Geom;

public final class WorldPosition implements Cloneable {

    private int mapId;
    private final Point3D point3D;
    private short heading;

    public WorldPosition(final Point3D point3D, final int mapId) {
        super();
        this.point3D = point3D;
        this.mapId = mapId;
        this.heading = Geom.getHeading(point3D);
    }

    public WorldPosition(final Point3D point3D) {
        super();
        this.point3D = point3D;
        this.heading = Geom.getHeading(point3D);
    }
    
    public WorldPosition() {
        this(new Point3D());
    }

    public WorldPosition(final int mapId, final float x, final float y, final float z) {
        this(new Point3D(x, y, z));
        this.mapId = mapId;
    }

    public WorldPosition(final int mapId, final float x, final float y, final float z, final short heading) {
        this(mapId, x, y, z);
        this.heading = heading;
    }

    public double distanceTo(final float x, final float y) {
        final double a = x - this.getX();
        final double b = y - this.getY();

        return Math.sqrt(a * a + b * b);
    }

    public double distanceTo(final float x, final float y, final float z) {
        final double a = x - this.getX();
        final double b = y - this.getY();
        final double c = z - this.getZ();

        return Math.sqrt(a * a + b * b + c * c);
    }

    public double distanceTo(final WorldPosition otherWorldPosition) {
        if (otherWorldPosition == null) {
            return Float.MAX_VALUE;
        }

        return this.distanceTo(otherWorldPosition.getX(), otherWorldPosition.getY(), otherWorldPosition.getZ());
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(final int mapId) {
        this.mapId = mapId;
    }

    public Point3D getPoint3D() {
        return point3D;
    }

    public void setPoint3D(final Point3D point3d) {
        this.point3D.setX(point3d.getX());
        this.point3D.setY(point3d.getY());
        this.point3D.setZ(point3d.getZ());
    }

    public float getX() {
        return this.point3D.getX();
    }

    public void setX(final float x) {
        this.point3D.setX(x);
    }

    public void addX(final float x) {
        this.setX(this.getX() + x);
    }

    public float getY() {
        return point3D.getY();
    }

    public void setY(final float y) {
        point3D.setY(y);
    }

    public void addY(final float y) {
        this.setY(this.getY() + y);
    }

    public float getZ() {
        return point3D.getZ();
    }

    public void setZ(final float z) {
        point3D.setZ(z);
    }

    public void addZ(final float z) {
        this.setZ(this.getZ() + z);
    }

    public void setXYZ(final float x, final float y, final float z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public short getHeading() {
        return heading;
    }

    public void setHeading(final short heading) {
        this.heading = heading;
    }

    @Override
    public WorldPosition clone() {
        return new WorldPosition(this.mapId, this.getX(), this.getY(), this.getZ());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + heading;
        result = prime * result + mapId;
        result = prime * result + ((point3D == null) ? 0 : point3D.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WorldPosition)) {
            return false;
        }
        final WorldPosition other = (WorldPosition) obj;
        if (heading != other.heading) {
            return false;
        }
        if (mapId != other.mapId) {
            return false;
        }
        if (point3D == null) {
            if (other.point3D != null) {
                return false;
            }
        }
        else if (!point3D.equals(other.point3D)) {
            return false;
        }
        return true;
    }
}
