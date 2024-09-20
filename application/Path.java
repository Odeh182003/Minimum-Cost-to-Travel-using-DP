package application;

public class Path {
private String path;
private int cost;
public Path(String path,int cost) {
	this.path=path;
	this.cost=cost;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public int getCost() {
	return cost;
}
public void setCost(int cost) {
	this.cost = cost;
}

}
