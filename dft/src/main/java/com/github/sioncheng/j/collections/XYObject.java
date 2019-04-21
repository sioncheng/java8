package com.github.sioncheng.j.collections;

public class XYObject {

    public static class Builder {
        public static XYObject build(int x, int y) {
            XYObject xyObject = new XYObject();
            xyObject.x = x;
            xyObject.y = y;

            return xyObject;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x * y);
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("XYObject#equals");
        if (obj == null) {
            return false;
        }

        if (obj instanceof XYObject) {
            XYObject that = (XYObject) obj;
            return that.x == this.x && that.y == this.y;
        } else {
            return false;
        }
    }

    private int x;
    private int y;
}
