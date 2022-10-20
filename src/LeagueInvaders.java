
import javax.swing.JFrame;

public class LeagueInvaders {

	JFrame frame;
	GamePanel panel;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 800;

	public static void main(String[] args) {
		LeagueInvaders game = new LeagueInvaders();
		game.setup();
	}

	LeagueInvaders() {
		frame = new JFrame();
	}

	void setup() {
		panel = new GamePanel();
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
