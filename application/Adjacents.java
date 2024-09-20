package application;

public class Adjacents {
private String name;
private int petrolcost;
private int hotalcost;
private int distance;
public Adjacents(String name,int petrolcost,int hotalcost) {
	this.name=name;
	this.petrolcost=petrolcost;
	this.hotalcost= hotalcost;
	this.distance=getDistance();
}
public Adjacents(String name,int distance) {
	this.name=name;
	this.distance=distance;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getPetrolcost() {
	return petrolcost;
}
public void setPetrolcost(int petrolcost) {
	this.petrolcost = petrolcost;
}
public int getHotalcost() {
	return hotalcost;
}
public void setHotalcost(int hotalcost) {
	this.hotalcost = hotalcost;
}
public int getDistance() {
	return distance;
}
public void setDistance(int distance) {
	this.distance = distance;
}
public int getDistanceTo(Adjacents destcity) {
	return this.distance + destcity.getDistance();
}
}
