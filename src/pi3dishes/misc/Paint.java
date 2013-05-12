package pi3dishes.misc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Timer;

public class Paint {

    final RenderingHints AA = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    final Stroke STROKE_1 = new BasicStroke(1);
    final Font FONT_1 = new Font("Arial", 1, 14);
    final Font FONT_2 = new Font("Arial", 1, 16);
    final Timer RUN_TIME = new Timer(0);
    public static int dishesMade = 0;

    public void paint(Graphics g1) {
	Graphics2D g = (Graphics2D) g1;
	g.setRenderingHints(AA);
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, 765, 50);
	g.setColor(Color.WHITE);
	g.setFont(FONT_1);
	g.drawString("" + dishesMade, 300, 34);
	g.drawString((int) ((double) dishesMade / (RUN_TIME.getElapsed() / 1000) * 3600) + "/h", 303, 46);
	g.setFont(FONT_2);
	g.drawString("Pi3 Dishes", 3, 17);
	g.drawString(RUN_TIME.toElapsedString(), 696, 46);
	g.setColor(Color.YELLOW);
	g.drawLine(Mouse.getX(), Mouse.getY() - 5, Mouse.getX(), Mouse.getY() + 5);
	g.drawLine(Mouse.getX() - 5, Mouse.getY(), Mouse.getX() + 5, Mouse.getY());
    }
}
