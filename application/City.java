package application;

//import java.util.ArrayList;
//import java.util.List;

public class City {
private String name;
private LinkedList<Adjacents> adjacents;
public City(String name) {
	this.name=name;
	this.adjacents=new LinkedList<>();
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public LinkedList<Adjacents> getAdjacents() {
	return adjacents;
}
public void setAdjacents(LinkedList<Adjacents> adjacents) {
	this.adjacents = adjacents;
}
public void addAdjacent(Adjacents adjacentCity) {
    adjacents.addLast(adjacentCity);
}

public Adjacents getAdjacent(String cityName) {
    for (Adjacents adjacent : adjacents) {
        if (adjacent.getName().equals(cityName)) {
            return adjacent;
        }
    }
    return null;
}
@Override
public String toString() {
	return "City{" +
            "name='" + name + '\''
            ;
}

}
