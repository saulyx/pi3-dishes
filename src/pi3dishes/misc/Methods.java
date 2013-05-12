package pi3dishes.misc;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;

public class Methods {

    /// Loop walks aren't good, I know.
    /// I don't use Walking.TilePath#traverse because it doesn't work when the ending/target Tile is null (not loaded)
    public static void walkPath(Tile[] tiles) {
	TilePath path = Walking.newTilePath(tiles);
	Tile lastTile = null;
	while (path.getEnd().distanceTo() > 5) {
	    if (!path.getNext().equals(lastTile) || !Players.getLocal().isMoving()) {
		lastTile = path.getNext();
		lastTile.randomize(1, 1).clickOnMap();
		Task.sleep(750, 1000);
	    }
	    Task.sleep(100, 200);
	}
    }
}
