
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {

	Rocketship rocket;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	Random random = new Random();
	int score = 0;

	ObjectManager(Rocketship r) {
		this.rocket = r;
	}

	void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	void addAlien() {
		aliens.add(new Alien(random.nextInt(LeagueInvaders.WIDTH), 0, 50, 50));
	}

	void update() {
		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		if (rocket.isActive) {
			checkCollision();
			purgeObjects();
		}
	}

	void draw(Graphics g) {
		rocket.draw(g);

		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).draw(g);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
	}

	void purgeObjects() {
		for (int i = 0; i < aliens.size(); i++) {
			if (aliens.get(i).isActive == false) {
				aliens.remove(i);
				i--;
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isActive == false) {
				projectiles.remove(i);
				i--;
			}
		}
	}

	void checkCollision() {
		for (int i = 0; i < aliens.size(); i++) {

			if (rocket.collisionBox.intersects(aliens.get(i).collisionBox)) {
				aliens.get(i).isActive = false;
				rocket.isActive = false;
			}

			for (int e = 0; e < projectiles.size(); e++) {
				if (projectiles.get(e).collisionBox.intersects(aliens.get(i).collisionBox)) {
					projectiles.get(e).isActive = false;
					aliens.get(i).isActive = false;
					score++;
				}
			}
		}
	}
	
	int getScore() {
		return score;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		addAlien();
	}

	public Projectile getProjectile() {
		return new Projectile(rocket.x + rocket.width / 2, rocket.y, 10, 10);
	}
}
