package org.palladiosimulator.simulizar.ui.measuringview.data;

public class TreeContentItem {
	protected String name;
	protected TreeContentItem[] children;
	protected TreeContentItem parent;
	
	protected TreeItemStatus status;
	
    public boolean hasChildren() {
		return children.length > 0;
	}

    public TreeContentItem getParent() {
		return parent;
	}

    public TreeContentItem[] getChildren() {
		return children;
	}
    
    public String toString() {
		return name;
	}
    
    public TreeItemStatus getStatus() {
		return status;
	}
	
	public void setParent (TreeContentItem parent) {
		this.parent = parent;
	}
}
