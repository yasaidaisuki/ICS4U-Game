import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Tile {

    BufferedImage image;
    private boolean collision = false;
    private Rectangle hitbox;
    private int tileN;

    public Tile() {

    }

    public Tile(BufferedImage image, Rectangle hitbox, int tileN, boolean collision) {
        this.image = image;
        this.hitbox = hitbox;
        this.tileN = tileN;
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public int getTileN() {
        return tileN;
    }

    public void setTileN(int tileN) {
        this.tileN = tileN;
    }

}
