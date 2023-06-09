import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Table {
    // private int height, width;
    public final static int height = 500;
    public final static int width = 800;
    public final static int playHeight = 500;
    public final static int playWidth = 800;
    public static final Double friction = 0.9;
    private static final Color[] ballColors = {Color.red, Color.orange, Color.yellow, Color.green,
            Color.blue, Color.pink, Color.blue, Color.cyan, Color.BLACK, Color.gray, Color.darkGray,
    Color.red, Color.orange, Color.yellow, Color.pink};
    private ArrayList<Ball> balls;
    private Ball b;
    private Pocket[] pockets;
    private Pocket pocket;
    private Image img;
    private PoolGame game;

    public Table(Ball b, PoolGame game) {
        this.b = b;
        //this.balls = balls;
        //this.img = new ImageIcon("resources/table.png").getImage();
        //pocket = new Pocket(-10, 18);
        int x = -10;
        int y = 29 - 10;
        pockets = new Pocket[6];
        for(int i = 0; i < 5; i+=2) {
            pockets[i] = new Pocket(x, y, this);
            pockets[i+1] = new Pocket(x, 500 - 30, this);
            x+= playWidth/2 - 10;
        }

        // Create balls
        x = 600;
        y = 500 / 2 + 12;
        balls = new ArrayList<Ball>();
        int num = 0;
        for (int row = 0; row < 5; row++) {
            x+= 12 * 2;
            for (int col: setUpY(row)) {
                balls.add(new Ball(x, col + y, 12, ballColors[num % 15], game));
                num++;
            }
        }
    }

    // From: https://stackoverflow.com/questions/59465398/pool-game-in-java-how-to-put-15-balls
    public int[] setUpY(int row) {
        int r = b.getRadius();
        switch (row) {
            case 0: return new int[] {0};
            case 1: return new int[] {-r, r};
            case 2: return new int[] {-2*r, 0, 2*r};
            case 3: return new int[] {-3*r, -r, r, 3*r};
            case 4: return new int[] {-4*r, -2*r, 0, 2*r, 4*r};
            default: throw new IllegalArgumentException("no more than 5 rows");
        }
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Ball getB() {
        return b;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void checkBallCollisions() {
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).isTouching(b)) {
                balls.get(i).bounceBall(b);
            }
            for (int j = 0; j < balls.size(); j++) {
                if (i != j && balls.get(i).isTouching(balls.get(j))) {
                    balls.get(i).bounceBall(balls.get(j));
                }
            }
//            if (balls.get(i).isTouching(balls.get(i+1))) {
//                balls.get(i).bounceBall(balls.get(i+1));
//            }
//            for (int j = i + 1; i < balls.size() - 1; j++) {
//                if (balls.get(i).isTouching(balls.get(j))) {
//                    balls.get(i).bounceBall(balls.get(j));
//                }
//            }
        }
    }

    public void checkWallCollisions() {
        for (Ball ball : balls) {
            ball.bounceWall();
        }
        b.bounceWall();
    }

    public void checkPockets() {
        for (Pocket p: pockets) {
            for (Ball ball : balls) {
                p.inPocket(ball);
            }
            p.inPocket(b);
        }
    }

    public void setTriangle() {

    }

    public void draw(Graphics g, PoolView window) {

        // g.drawImage(img, 0, 18, width, height - 18, window);
        g.setColor(new Color(50, 168, 82));
        g.fillRect(0, 0, width, height);
        //pocket.draw(g);
        for(Pocket p: pockets) {
            p.draw(g);
        }
        g.setColor(Color.yellow);
        //g.fillRect(200, 300, playWidth, playHeight);
    }
}
