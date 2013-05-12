package pi3dishes.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import pi3dishes.Pi3Dishes;
import pi3dishes.misc.Methods;
import pi3dishes.misc.Paint;
import pi3dishes.misc.Variables;

public class BankDishes extends Node {

    @Override
    public boolean activate() {
	return !Inventory.contains(Variables.PIE_DISH_U) && !Inventory.contains(Variables.PIE_DISH_U);
    }

    @Override
    public void execute() {
	Methods.walkPath(Variables.TO_BANK);
	openBank();
	bank();
    }

    private void openBank() {
	if (!Bank.isOpen()) {
	    Timer t1 = new Timer(Random.nextInt(3000, 4000));
	    while (!Bank.getNearest().isOnScreen() && t1.isRunning()) {
		Camera.setAngle(Camera.getYaw() + Random.nextInt(10, 30));
	    }
	    Bank.open();
	    if (Inventory.getCount() > 0 && Random.nextInt(0, 4) != 2) {
		Mouse.move(Random.nextInt(360, 380), Random.nextInt(353, 365));
	    }
	    Timer t2 = new Timer(Random.nextInt(5000, 6000));
	    while (!Bank.isOpen() && t2.isRunning()) {
		Task.sleep(100, 150);
	    }
	}
    }

    private void bank() {
	if (Bank.isOpen()) {
	    Task.sleep(150, 300);
	    if (Inventory.getCount() > 0) {
		int dishCount = Inventory.getCount(Variables.PIE_DISH);
		depositAll();
		Timer t1 = new Timer(Random.nextInt(2800, 3200));
		while (Inventory.contains(Variables.PIE_DISH) && t1.isRunning()) {
		    Task.sleep(100, 200);
		}
		if (!Inventory.contains(Variables.PIE_DISH)) {
		    Paint.dishesMade += dishCount;
		}
		Task.sleep(150, 300);
	    }
	    if (Bank.getItemCount(Variables.SOFT_CLAY) > 0) {
		Bank.withdraw(Variables.SOFT_CLAY, Bank.Amount.ALL);
		Timer t1 = new Timer(Random.nextInt(3000, 4000));
		while (!Inventory.contains(Variables.SOFT_CLAY) && t1.isRunning()) {
		    Task.sleep(150, 300);
		}
	    } else {
		Bank.close();
		Task.sleep(500, 1000);
		Pi3Dishes.stopScript();
	    }
	}
    }

    private void depositAll() {
	if (!Menu.getActions()[0].contains("Deposit carried items")) {
	    Widgets.get(762, 34).interact("Deposit carried items");
	} else {
	    Mouse.click(true);
	}
    }
}
