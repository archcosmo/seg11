package core;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GUI extends Application {
	
	private static final String WINDOW_NAME = "Runway Re-Declaration Application";
	private static final int INIT_WINDOW_HEIGHT = 600;
	private static final int INIT_WINDOW_WIDTH = 400;
	
	Stage stage;
   

	/* SAMPLE CODE
     * Creates window, pane and button
     * Adds handler to button
     */
    @Override
    public void start(Stage mainWindow) {
        mainWindow.setTitle(WINDOW_NAME);
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        mainWindow.setScene(new Scene(root, INIT_WINDOW_HEIGHT, INIT_WINDOW_WIDTH));
        mainWindow.show();
    }
}