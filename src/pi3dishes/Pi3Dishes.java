package pi3dishes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.util.Random;
import pi3dishes.misc.*;
import pi3dishes.nodes.*;

@Manifest(version = 2.0, authors = {"PI3"}, description = "Makes Pie dishes out of Soft clay at Burthorpe! (Money Making/Crafting)", name = "Pi3 Dishes")
public class Pi3Dishes extends ActiveScript implements PaintListener {
    
    public static final Paint paint = new Paint();
    private static final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;
    
    public synchronized final void provide(final Node... jobs) {
	for (final Node job : jobs) {
	    if (!jobsCollection.contains(job)) {
		jobsCollection.add(job);
	    }
	}
	jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }
    
    public static void stopScript() {
	Game.logout(true);
	jobsCollection.clear();
    }
    
    @Override
    public void onStart() {
	provide(new MakeItem(CraftItem.PIE_DISH_U),
		new MakeItem(CraftItem.PIE_DISH),
		new BankDishes());
    }
    
    @Override
    public int loop() {
	Task.sleep(150, 250);
	if (jobContainer != null) {
	    final Node job = jobContainer.state();
	    if (job != null) {
		jobContainer.set(job);
		getContainer().submit(job);
		job.join();
	    }
	}
	return jobsCollection.isEmpty() ? -1 : 50;
    }
    
    @Override
    public void onRepaint(Graphics g1) {
	paint.paint(g1);
    }
}