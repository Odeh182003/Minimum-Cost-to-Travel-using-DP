package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	Button btn = new Button("Start Your Journey");
	Info info = new Info(); 
	//Button nextbtn = new Button("next");
	//Button previousbtn = new Button("previous");
	 Scene  pathsc ;
	 Label filenameLabel = new Label("File Path:");
     TextField filenameTextField = new TextField();
     Button browseButton = new Button("Browse");
     Button travelButton = new Button("Travel");
     TextArea alternateTableTextArea = new TextArea();
     TextField costtext = new TextField();
     TextArea dpTableTextArea = new TextArea();
     TextField path = new TextField();
     Stage stage = new Stage();
	 //Image backgroundImage ;
	 //ImageView backgroundImageView ;
	 TabPane tpane = new TabPane();
     Tab t1 = new Tab("Cost & Path");
     Tab t2 = new Tab("DP Table");
	 @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 800, 500); 
            
            
            root.setBottom(getMain());
            btn.setOnAction(e->{
            	 BorderPane pane2 = new BorderPane();
                 Scene sc2 = new Scene(pane2,800,500);
                 pane2.setCenter(getLayout());
                 primaryStage.setScene(sc2);
            });
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Minimum Cost");
            primaryStage.show();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public StackPane getMain() {
		ImageView img = new ImageView(new Image("C:\\Users\\odehl\\OneDrive\\Desktop\\Leen java Code\\MinimumCostDP\\src\\application\\travel.jpg"))	;
		StackPane pane = new StackPane();
		img.setFitWidth(800);
		img.setFitHeight(500);
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(img,vbox);
		btn.setPrefSize(200, 50);
		
		vbox.getChildren().addAll(btn);
		return pane;
	}
    public BorderPane getLayout() {
    	BorderPane pane = new BorderPane();
        dpTableTextArea.setEditable(false);
        dpTableTextArea.setPrefColumnCount(50);
        // Set the controls from the Info class
        info.alternTable = alternateTableTextArea;
        info.browserbtn = browseButton;
        info.costtxt = costtext;
        info.dpTable = dpTableTextArea;
        info.filename = filenameTextField;
        info.pathtxt = path;
        info.travelbtn = travelButton;
        HBox filehbox = new HBox(20);
        filenameTextField.setPrefWidth(400);
        filehbox.getChildren().addAll(filenameLabel,filenameTextField);
        filehbox.setAlignment(Pos.CENTER);
        HBox btnhbox = new HBox(20);
        btnhbox.getChildren().addAll(browseButton,travelButton);
        btnhbox.setAlignment(Pos.CENTER);
        browseButton.setPrefSize(200, 50);
        travelButton.setPrefSize(200, 50);
      //  BorderPane bpane = new BorderPane();
        VBox layout = new VBox(40);
        layout.getChildren().addAll(filehbox,btnhbox);
        layout.setAlignment(Pos.CENTER);
        pane.setCenter(layout);
        
        //layout.setBackground(Background.fill(Color.BLUEVIOLET));
        browseButton.setBorder(Border.stroke(Color.BLUEVIOLET));
        travelButton.setBorder(Border.stroke(Color.BLUEVIOLET));
        browseButton.setOnAction(event -> info.handleBrowserbtnAction());
        travelButton.setOnAction(e->{
        	tpane.getTabs().addAll(t1,t2);
            
        	
        	HBox costhbox = new HBox(20);
        	VBox mainvb = new VBox(10);
        	Label lb = new Label("Alternative Paths with their costs");
        	lb.setFont(Font.font(40));
        	VBox vbalt = new VBox(10);
        	HBox altTablehbox = new HBox();
        	altTablehbox.setAlignment(Pos.CENTER);
        	altTablehbox.getChildren().add(alternateTableTextArea);
        	vbalt.getChildren().addAll(lb,altTablehbox);
        	vbalt.setAlignment(Pos.CENTER);
        	Label minlb = new Label("Minimum Cost:");
        	//HBox minhbox = new HBox(20);
        	Label pathlb = new Label("Path");
        	pathlb.setFont(Font.font(20));
        	HBox pathhbox = new HBox(15);
        	minlb.setFont(Font.font(20));
        	path.setPrefWidth(280);
        	pathhbox.getChildren().addAll(pathlb,path);
        	costhbox.getChildren().addAll(minlb,costtext);
        	HBox tophbox = new HBox(150);
        	tophbox.getChildren().addAll(costhbox,pathhbox);
        	tophbox.setAlignment(Pos.CENTER);
        	HBox nextpane = new HBox();
        	nextpane.setAlignment(Pos.CENTER);
        	//nextpane.getChildren().add(nextbtn);
        	mainvb.getChildren().addAll(tophbox,vbalt,nextpane);
        	info.handleTravelbtnAction();
         	StackPane root = new StackPane();
         	
         	VBox vb = new VBox(20);
         	Label lb2 = new Label("DP Table:");
         	lb2.setFont(Font.font(20));
         	HBox dphbox = new HBox();
         	dphbox.setAlignment(Pos.CENTER);
         	dphbox.getChildren().add(dpTableTextArea);
         	vb.getChildren().addAll(lb2,dphbox);
         	vb.setAlignment(Pos.CENTER);
         	 
         	t2.setContent(vb);
         	t1.setContent(mainvb);
        	root.getChildren().addAll(tpane);
        	 pathsc = new Scene(root, 800, 500);
        	stage.setScene(pathsc);
        	stage.show();
        });
        
return pane;
    }
   
    public static void main(String[] args) {
        launch(args);
    }
}
