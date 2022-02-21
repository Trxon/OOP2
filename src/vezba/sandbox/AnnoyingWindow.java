package vezba.sandbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnnoyingWindow extends Application {
	

	@Override
	public void start(Stage stage) throws Exception {
		
		Button btnClose = new Button("Click me to Close");
		final double Y1 = 40, Y2 = 160;
		btnClose.setPrefSize(260, 40);
		btnClose.relocate(30, Y1);
		
		// ako korisnik klikne na dugme, zatvoricemo prozor
		btnClose.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1)
				stage.close();
		});
		
		// sakrivamo dekoracije prozora, ukljucujuci i dugmice minimize, maximize i close
		stage.initStyle(StageStyle.UNDECORATED);
		
		// ako korisnik pokusa da zatvori prozor (Alt+F4), ignorisacemo ga
		stage.setOnCloseRequest(e -> e.consume());
		
		// ako kursor misa udje u dugme sa gornje strane dugme pomeramo gore i obrnuto
		btnClose.setOnMouseMoved(e -> {
			boolean gore = Math.abs(btnClose.getLayoutY() - Y1) < 0.001;
			double newY = gore ? Y2 : Y1;
			btnClose.setLayoutY(newY);
		});
		
		Pane root = new Pane(btnClose);
		stage.setScene(new Scene(root, 320, 240));
		stage.setTitle("Annoying Window");
		stage.setResizable(false);
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
