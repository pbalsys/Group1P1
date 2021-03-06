package lazytown.source.game.actor;

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;
import lazytown.source.game.UI;
import lazytown.source.game.level.Tile;

/**
 * A class used for the guards, extends MovedActor class.
 */
public class Guard extends MovedActor {

    boolean animation = false;
    boolean facingUp = false;
    boolean facingLeft = false;
    boolean movesHorizontally;

    int framecounter = 0;
    int runningspeed = 14;

    /**
     * Our constructor again calling up to the superclass.
     * @param SVGdata a string which determines vector data for the collision area.
     * @param xLoc x coordinate of the rendered Guard.
     * @param yLoc y coordinate of the rendered Guard.
     * @param spriteCels an array of images for animating the Guard.
     */
    public Guard(String SVGdata, double xLoc, double yLoc, boolean movesHorizontally, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        this.movesHorizontally = movesHorizontally;
    }

    /**
     * An update method for our guard, as the guard object is animated and moving, we are using the update method to
     * allow the game play loop to reflect changes
     */
    @Override
    public void update() {
        checkCollision();
        setXYLocation();
        setImageState();
        moveCharacter();
    }

    // The guard currently moves side to side horizontally.
    private void setXYLocation() {
        if (movesHorizontally) {
            if (facingLeft) {
                iX-=velX;
            } else {
                iX+=velX;
            }
        }
        else {
            if (facingUp) {
                iY-=velY;
            } else {
                iY+=velY;
            }
        }

    }

    private void setImageState() {

        // Moving left
        if (movesHorizontally && facingLeft) {
            spriteFrame.setScaleX(1);
            this.setFlipH(false);
            if (!animation) {
                spriteFrame.setImage(imageStates.get(11));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else {
                spriteFrame.setImage(imageStates.get(9));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }

        // Moving right
        if (movesHorizontally && !facingLeft) {
            spriteFrame.setScaleX(-1);
            this.setFlipH(true);
            if (!animation) {
                spriteFrame.setImage(imageStates.get(9));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(11));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }

        // Moving down
        if (!movesHorizontally && !facingUp) {
            if (!animation) {
                spriteFrame.setImage(imageStates.get(6));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(7));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }

        // Moving up
        if (!movesHorizontally && facingUp) {
            if (!animation) {
                spriteFrame.setImage(imageStates.get(0));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(1));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }
    }

    private void checkCollision() {
        Tile[][] tile = Game.getLevel().getTiles();
        Actor[][] actor = Game.getLevel().getActors();
        if (facingLeft && (tile[(int)(iX/50)][(int)(iY/50)].isCollides() ||
                actor[(int)(iX/50)][(int)(iY/50)] instanceof Tile ||
                actor[(int)(iX/50)][(int)(iY/50)] instanceof InteractiveActor)) facingLeft = false;

        else if (!facingLeft && (tile[(int)(iX/50)+1][(int)(iY/50)].isCollides() ||
                actor[(int)(iX/50)+1][(int)(iY/50)]instanceof Tile ||
                actor[(int)(iX/50)+1][(int)(iY/50)] instanceof InteractiveActor)) facingLeft = true;

        if (facingUp && (tile[(int)(iX/50)][(int)(iY/50)].isCollides() ||
                actor[(int)(iX/50)][(int)(iY/50)] instanceof Tile ||
                actor[(int)(iX/50)][(int)(iY/50)] instanceof InteractiveActor)) facingUp = false;

        else if (!facingUp && (tile[(int)(iX/50)][(int)(iY/50)+1].isCollides() ||
                actor[(int)(iX/50)][(int)(iY/50)+1] instanceof Tile ||
                actor[(int)(iX/50)][(int)(iY/50)+1] instanceof InteractiveActor)) facingUp = true;
    }



    private void moveCharacter() {
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }

}
