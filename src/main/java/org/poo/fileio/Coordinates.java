package org.poo.fileio;

/**
 * The type Coordinates.
 */
public final class Coordinates {
   private int x, y;

    /**
     * Instantiates a new Coordinates.
     */
    public Coordinates() {
   }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
      return x;
   }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(final int x) {
      this.x = x;
   }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
      return y;
   }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(final int y) {
      this.y = y;
   }

   @Override
   public String toString() {
      return "Coordinates{"
              + "x="
              + x
              + ", y="
              + y
              + '}';
   }
}
