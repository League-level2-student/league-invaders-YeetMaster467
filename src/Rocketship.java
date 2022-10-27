
import java.awt.Color;
import java.awt.Graphics;

public class Rocketship extends GameObject {

	boolean up = false;
	boolean down = false;
	boolean left = false;
	boolean right = false;

	Rocketship(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 10;
	}

	void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}

	void move() {
		if (up && y - speed > 0) {
			y -= speed;
		}
		if (down && y + speed < LeagueInvaders.HEIGHT - height) {
			y += speed;
		}
		if (left && x - speed > 0) {
			x -= speed;
		}
		if (right && x + speed < LeagueInvaders.WIDTH - width) {
			x += speed;
		}
	}
}
