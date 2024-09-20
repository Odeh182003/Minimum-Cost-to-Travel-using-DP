package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Info {
	private LinkedList<City> cities;
	
	private int numCities ;
	//private City[] cities2 = new City[numCities];
	private String startCity;
	private String endCity;
	private int[][] dp;
	private int[][] path;
	TextArea alternTable = new TextArea();
	Button browserbtn = new Button("Browser");
	TextField costtxt = new TextField();
	TextArea dpTable = new TextArea();
	TextField filename = new TextField();
	//TextArea pathTable = new TextArea();
    TextField pathtxt = new TextField();
    Button travelbtn = new Button("Travel");
    public Info() {
    	cities = new LinkedList<>();
    	
    }
    public void handleBrowserbtnAction() {
    	FileChooser choose = new FileChooser();
    	choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    	File file = choose.showOpenDialog(filename.getScene().getWindow());
    	if(file != null) {
    		String filePath = file.getAbsolutePath();
    		filename.setText(filePath);
    	}
    }
    public void handleTravelbtnAction() {
    	String filepath = filename.getText();
    	if (!filepath.isEmpty()) {
    		cities.clear();
    		readCitiesFromFile(filepath);
    		calculateoptimalCost();
    		displayResults();
    	}
    }
    public void readCitiesFromFile(String filepath) {
    	cities = new LinkedList<>();
    	
    	try(BufferedReader br = new BufferedReader(new FileReader(filepath))){
    		numCities = Integer.parseInt(br.readLine());
    		String[] startEnd = br.readLine().split(", ");
    		startCity = startEnd[0];
    		endCity = startEnd[1];
    		String line;
    		while((line = br.readLine()) != null) {
    			String[] parts = line.split(", ");
    			String cityname = parts[0];
    			City city = null;
    			for(City existing : cities) {
    				 if (existing.getName().equals(cityname)) {
    					city = existing;
    					break;
    				}
    			}
    			if(city == null) {
    				city = new City(cityname);
    				cities.addLast(city);
    				//cities2[cityIndex] = city;
    			//	cityIndex++;
    			}
    			for(int i = 1;i < parts.length;i++) {
    				String[] adjacentParts = parts[i].substring(1,parts[i].length() - 1).split(",");
    				String adjecentName = adjacentParts[0];
    				int petrolcost = Integer.parseInt(adjacentParts[1]);
    				int hotelcost = Integer.parseInt(adjacentParts[2]);
    				Adjacents adjacentCities = new Adjacents(adjecentName,petrolcost,hotelcost);
    				city.addAdjacent(adjacentCities);
    			}
    		}
    		//add destination city
    		cities.addLast(new City(endCity));
    		
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
  
    public void calculateoptimalCost() {
    	dp = new int[numCities + 1][numCities + 1];
    	path = new int[numCities + 1][numCities + 1];
    	for(int i = 0;i < dp.length; i++) {
    		for(int j=0;j < dp.length; j++) {
    			if(i == j || j == 0 || i == 0) {
    				dp[i][j] = 0;
    			}else if(i > j) {
    				dp[i][j] = Integer.MAX_VALUE;
    			}else if(i < j) {
    				String secondCity = cities.get(j - 1).getName();
    				City firstcity = cities.get(i - 1);
    				
    				Adjacents adjacentCity = firstcity.getAdjacent(secondCity);
    				if(adjacentCity != null) {
    					int distance = adjacentCity.getPetrolcost()+adjacentCity.getHotalcost();
    					dp[i][j] = distance;
    				}else {
    					dp[i][j] = Integer.MAX_VALUE;
    				}
    			}
    		}
    	}
   //these two for loops iterates over all possible inbetween cities to update the shortest path between pairs of cities
    	for(int z = 2; z < dp.length; z++) {
    		for(int i = 1,j = z;i < dp.length && j < dp.length; i++,j++) {
    			if(j > i+ 1) {
    				//iterates between all possible inbetween i and j cities to update the shortest path between them
    				for(int k = i + 1;k < j;k++) {
    					if(dp[i][k] == Integer.MAX_VALUE || dp[k][j] == Integer.MAX_VALUE)
    						continue;
    					else {
    	//if the distance through city k is shorter than the current shortest distance update path with the new shortest distance
    						if(dp[i][k] + dp[k][j] < dp[i][j]) {
    							path[i][j] = k;
    						}
    						dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
    					}
    				}
    			}
    		}
    	}
    }
    //sorting paths according to cost
    private void sortPathsUsingCosts(LinkedList<Path> path) {
    	for (int i =0; i < path.size() - 1;i++) {
    		for(int j = i+1 ;j < path.size();j++ ) {
    			if(path.get(i).getCost() > path.get(j).getCost()) {
    				Path temp = path.get(i);
    				path.set(i, path.get(j));// --> path.get(i) = path.get(j);
    				path.set(j, temp); // --> path.get(j) = temp
    			}
    		}
    	}
    }
    //to display the results inside the javaFx components
    private void displayResults() {
    	LinkedList<Path> allPaths = getAllPathsWithCost(startCity, new LinkedList<>());
    	//sorting the alternative path based on their cost using sortPathsUsingCosts sort method
    	sortPathsUsingCosts(allPaths);
    	StringBuilder sb = new StringBuilder();
    	for(Path path : allPaths) {
    		sb.append(path.getPath()).append(" (Cost: ").append(path.getCost()).append(")").append("\n");
    	}
    	costtxt.setText(String.valueOf(dp[1][numCities]));
    	alternTable.setText(sb.toString());
    	dpTable.setText(generateDpTable(dp));
    	//pathTable.setText(generateDpTable(path));
    	alternTable.setVisible(true);
    	dpTable.setVisible(true);
    	pathtxt.setDisable(false);
    	pathtxt.setPrefColumnCount(20);
    //getPath methods starts from the start city 1 and ends with the end city in the city linked list
    	pathtxt.setText(getPath(path, 1, numCities, cities));
    }
    private String getPath(int[][] path, int start,int end,LinkedList<City> cities) {
//        starts with the start city and recursively follows the path until
//        it reaches the end city, concatenating the city names along the way.
    	if (start == end){
    		return cities.get(start - 1).getName();
    	}else {
    		int inbetween = path[start][end];
    		if(inbetween == 0 && start != end) {
    			return cities.get(start - 1).getName()+" --> "+cities.get(end - 1).getName();
    		}else {
    			//recursively getting the path from the start city to inberween 
    			//then from inbetween to end city 
    			//to cover all the cities
    			String path1 = getPath(path,start, inbetween, cities);
    			String path2 = getPath(path,inbetween, end, cities);
    			if(path1.endsWith(cities.get(inbetween - 1).getName())) {
    				// Remove the duplicate city from path1
    				path1 = path1.substring(0,path1.lastIndexOf(cities.get(inbetween - 1).getName()));
    			}
    			return path1 + path2;
    		}
    	}
    }
    public String generateDpTable(int[][] table) {
        StringBuilder sb = new StringBuilder();

        // Print the city names on the first row
        sb.append("\t");
        for (int i = 0; i < numCities; i++) {
            sb.append(cities.get(i).getName()).append("\t");
        }
        sb.append("\n");

        // Print the table
        for (int i = 1; i < table.length; i++) {
            if (i > 0) {
                sb.append(""+cities.get(i - 1).getName()).append("\t"); // Print the city name on the leftmost column
            }
            for (int j = 1; j < table[i].length; j++) {
                if (table[i][j] == Integer.MAX_VALUE) {
                    sb.append("NF").append("\t");
                } else {
                    sb.append(table[i][j]).append("\t");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    private LinkedList<Path> getAllPathsWithCost(String current,LinkedList<String> path){
    	path.addLast(current);
    	if(current.equals(endCity)) {
    		int cost = calculatePathCost(path);
    		String pathstr = "";
    		for(int i = 0;i < path.size();i++) {
    			pathstr += path.get(i);
    			if(i != path.size() - 1)
    				pathstr += " --> ";
    		}
    		LinkedList<Path> result = new LinkedList<>();
    		result.addLast(new Path(pathstr,cost));
    		return result;
    	}
    	LinkedList<Path> allPaths = new LinkedList<>();

        for (City city : cities) {
            if (city.getName().equals(current)) {
                for (Adjacents adjacentCity : city.getAdjacents()) {
                    String adjacentName = adjacentCity.getName();

                    // Avoiding revisiting cities in the current path to prevent cycles
                    if (!path.contains(adjacentName)) {
                        LinkedList<Path> paths = getAllPathsWithCost(adjacentName, new LinkedList<>(path));
                        for (Path route : paths) {
                            allPaths.addLast(route);
                        }
                    }
                }
                break;
            }
        }
    	return allPaths;
    }
    private int calculatePathCost(LinkedList<String> path) {
 // the total cost = petrol cost + hotel cost (for each adjacent cities)
    	int cost = 0;
    	for(int i = 0;i < path.size() - 1;i++) {
    		String cityName = path.get(i);
    		String nextCity = path.get(i + 1);
    		for(City city : cities) {
    			if(city.getName().equals(cityName)) {
    				Adjacents adjacentCity = city.getAdjacent(nextCity);
    				cost += adjacentCity.getPetrolcost() + adjacentCity.getHotalcost();
    				break;
    			}
    		}
    	}
    	return cost;
    }
	public LinkedList<City> getCities() {
		return cities;
	}
	public void setCities(LinkedList<City> cities) {
		this.cities = cities;
	}
	public int getNumCities() {
		return numCities;
	}
	public void setNumCities(int numCities) {
		this.numCities = numCities;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	public int[][] getDp() {
		return dp;
	}
	public void setDp(int[][] dp) {
		this.dp = dp;
	}
	public int[][] getPath() {
		return path;
	}
	public void setPath(int[][] path) {
		this.path = path;
	}
    
}
