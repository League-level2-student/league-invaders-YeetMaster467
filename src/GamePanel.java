
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	int currentState = MENU;
	Font titleFont = new Font("Arial", Font.PLAIN, 48);
	Font normalFont = new Font("Arial", Font.PLAIN, 26);
	Timer frameDraw;
	Rocketship rocket = new Rocketship(250, 700, 50, 50);
	// x = 250, y = 700, width & height = 50
	ObjectManager manager = new ObjectManager(rocket);
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	Timer alienSpawn;

	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}

	}

	GamePanel() {
		frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();
		if (needImage) {
		    loadImage ("space.png");
		}
	}

	void updateMenuState() {

	}

	void updateGameState() {
		rocket.move();
		manager.update();
		if(rocket.isActive == false) {
			currentState = END;
		}
	}

	void updateEndState() {

	}

	void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("LEAGUE INVADERS", 25, 100);
		g.setFont(normalFont);
		g.drawString("Press ENTER to start", 100, 400);
		g.drawString("Press SPACE for instructions", 75, 500);

	}

	void drawGameState(Graphics g) {
		if (gotImage) {
			g.drawImage(image, 0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		}
		manager.draw(g);
	}

	void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString("Game Over", 100, 100);
		g.setFont(normalFont);
		g.drawString("You killed " + manager.getScore() + " enemies", 100, 400);
		g.drawString("Press ENTER to restart", 100, 500);
	}
	
	void startGame() {
		alienSpawn = new Timer(1000, manager);
		alienSpawn.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
		}
		repaint();
	}
	
	void loadImage(String imageFile) {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
				rocket = new Rocketship(250, 700, 50, 50);
				manager = new ObjectManager(rocket);
			} else if (currentState == MENU) {
				currentState++;
				startGame();
			} else {
				currentState++;
				alienSpawn.stop();
			}
		}
		
		if(currentState == MENU) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				JOptionPane.showMessageDialog(null, "Use arrow keys to move and space to shoot.\nKill as many aliens as you can and try not to die.");
			}
		}

		if (currentState == GAME) {

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				rocket.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				rocket.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				rocket.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rocket.right = true;
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				manager.addProjectile(rocket.getProjectile());
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			rocket.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			rocket.down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			rocket.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rocket.right = false;
		}
	}
}
