package pi3dishes.misc;

public enum CraftItem {

    PIE_DISH_U("Pie dish (unfired)", Variables.SOFT_CLAY, Variables.WHEEL, "Form", 4),
    PIE_DISH("Pie dish", Variables.PIE_DISH_U, Variables.OVEN, "Use", 8);
    private String name, action;
    private int objectID, reagent, index;

    private CraftItem(String name, int reagent, int objectID, String action, int index) {
	this.name = name;
	this.action = action;
	this.objectID = objectID;
	this.reagent = reagent;
	this.index = index;
    }

    public int getID() {
	return objectID;
    }

    public int getReagent() {
	return reagent;
    }

    public int getIndex() {
	return index;
    }

    public String getName() {
	return name;
    }

    public String getAction() {
	return action;
    }
}
