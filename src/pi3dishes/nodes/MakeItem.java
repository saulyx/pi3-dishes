package pi3dishes.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.widget.Widget;
import pi3dishes.misc.*;

public class MakeItem extends Node {

    CraftItem craft;

    public MakeItem(CraftItem craft) {
	this.craft = craft;
    }

    @Override
    public boolean activate() {
	return Inventory.contains(craft.getReagent());
    }

    @Override
    public void execute() {
	Methods.walkPath(Variables.TO_CRAFT);
	openCraftingScreen(craft);
	selectAndMake(craft);
	waitTillDone(craft.getReagent());
    }

    private void openCraftingScreen(CraftItem craft) {
	Widget screen = Widgets.get(Variables.CRAFTING_SCREEN);
	if (!screen.validate() && !Widgets.get(Variables.WAITING_SCREEN).validate()) {
	    if (!SceneEntities.getNearest(craft.getID()).isOnScreen()) {
		Camera.turnTo(SceneEntities.getNearest(craft.getID()));
	    }
	    SceneEntities.getNearest(craft.getID()).interact(craft.getAction());
	    Task.sleep(150, 300);
	    Timer t1 = new Timer(Random.nextInt(3000, 4000));
	    while (!screen.validate() && t1.isRunning()) {
		Task.sleep(100);
	    }
	    if (screen.validate() && screen.getChild(38).getText().equals("Weave")) {
		screen.getChild(30).interact("Close");
		Task.sleep(250, 500);
	    }
	}
    }

    private void selectAndMake(CraftItem craft) {
	Widget screen = Widgets.get(Variables.CRAFTING_SCREEN);
	if (screen != null && screen.validate()) {
	    if (!screen.getChild(5).getText().contains(craft.getName())) {
		Widgets.get(1371, 44).getChild(craft.getIndex()).interact("Select");
		Timer t = new Timer(Random.nextInt(2600, 3200));
		while (!screen.getChild(56).getText().contains(craft.getName()) && t.isRunning()) {
		    Task.sleep(50, 100);
		}
	    }
	    if (screen.getChild(56).getText().contains(craft.getName())) {
		Mouse.click(Random.nextInt(300, 450), Random.nextInt(360, 375), true);
		Timer t1 = new Timer(Random.nextInt(2500, 3500));
		while (screen.validate() && t1.isRunning()) {
		    Task.sleep(100, 200);
		}
		Task.sleep(250, 500);
		if (Random.nextInt(0, 8) != 4) {
		    Mouse.move(Random.nextInt(30, 730), 0);
		}
	    }
	}
    }

    private void waitTillDone(int itemID) {
	Task.sleep(1000, 2000);
	/// THIS CAN NOT BE AN INFINITE LOOP - THE WIDGET WILL DISAPPEAR ON ITS OWN AFTER A CERTAIN AMOUNT OF TIME
	while (Inventory.contains(itemID) && Widgets.get(Variables.WAITING_SCREEN).validate()) {
	    Task.sleep(1000, 1500);
	}
    }
}
