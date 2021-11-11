import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	private Controller controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller= new Controller();
		
		Scene escena = new Scene(controller.getView());
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("Enviar email");
		primaryStage.getIcons().addAll(new Image("/fxml/icon.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
