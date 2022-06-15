import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Image;

public class Tile {

    public Image image;
    public boolean collision = false;
    public Rectangle blockHitBox;
    public int num;

    public Tile(Image image, Rectangle blockHitBox, boolean collision, int num) {
        this.image = image;
        this.blockHitBox = blockHitBox;
        this.collision = collision;
        this.num = num;
    }

}
