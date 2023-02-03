import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

// Graphics class
public class HangManController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Canvas drawArea;
	private GraphicsContext gc;

	@FXML
	private TextField guess;
	private String CurrentGuess;

	@FXML
	private GridPane grid;
	@FXML
	private Button guessBtn;
	@FXML
	private Button restartBtn;
	@FXML
	private Text textForWord;

	@FXML
	final int SIZE = 26;
	private Logic game = new Logic();
	private Button[] btns = new Button[SIZE];
	private int numOfMistakes = 0;
	private Image image;
	private ImageView imageView;
	private Alert information = new Alert(AlertType.INFORMATION);


	@FXML
	private Label lives;

	@FXML
	// Restart game method
	void restartGame(ActionEvent event) {
		information.close();
		guess.setVisible(true);
		guessBtn.setVisible(true);
		guessBtn.setDisable(false);
		grid.getChildren().clear();
		game.restartGame();
		gc.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
		CurrentGuess = "*";
		setupGame();
		guess.clear();

	}

	@FXML
	// Event handler for inputing a guess
	void userPressed(ActionEvent event) {
		playTurn();
		guess.clear();

	}


	@FXML
	// Initialize game
	void initialize() {
		gc = drawArea.getGraphicsContext2D();
		setupGame();
		instructionForGame();
		information.showAndWait();
	}

	// Game setup method
	public void setupGame() {
		guessBtn.setDisable(true);
		numOfMistakes = 0;
		lives.setText(game.getLives() + "");
		createBtns(btns);
		createHangPlatform();
		textForWord.setText(String.valueOf(game.getAstric()));
	}
	
	// Game Entrance Pop-up maker
	private void instructionForGame() {
		String title = "Welcome";
		String header = "Dog Breed Theme HangMan";
		String content = "You are about to be an expert on Dog Breeds -> Guess the breed \nYou have only 7 guesses to make, do it wisley!. \n \n GOOD LUCK";
		setPopup(title, header, content);
		image = new Image("https://cdn-icons-png.flaticon.com/512/616/616408.png");
		imageView = new ImageView(image);
		imageView.setFitWidth(75);
		imageView.setFitHeight(75);
		((Button)information.getDialogPane().lookupButton(ButtonType.OK)).setText("Let's Play");
		information.setGraphic(imageView);
		
	}

	// Method for creating the hang platform
	private void createHangPlatform() {
		// Create Vertical base Beam
		gc.setStroke(Color.BLACK);
		gc.strokeLine(20, 0, 20, 350);

		// Create Horizontal base Beam
		gc.setStroke(Color.BLACK);
		gc.strokeLine(300, 350, 0, 350);

		// Create top Horizontal Beam
		gc.setStroke(Color.BLACK);
		gc.strokeLine(200, 5, 20, 5);

		// Create hanging rope
		gc.setStroke(Color.BLACK);
		gc.strokeLine(200, 5, 200, 100);
	}
	
	// Create letter buttons method
		private void createBtns(Button[] btns) {
			char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
					's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
			int maxBtnsInRow = 8;
			int maxBtnsInCol = 4;

			btns = new Button[SIZE];
			for (int i = 0; i < btns.length; i++) {
				btns[i] = new Button(alphabet[i] + "");
				btns[i].setPrefSize(grid.getPrefWidth() / maxBtnsInRow, grid.getPrefHeight() / maxBtnsInCol);
				btns[i].setDisable(false);
				grid.add(btns[i], i % maxBtnsInRow, i / maxBtnsInRow);
				btns[i].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

						handleButton(e);

					}
				});
			}
		}
		// Event handler for button's input
		private void handleButton(ActionEvent e) {
			if (guess.getText() == "") {
				guessBtn.setDisable(true);
			} else {
				guess.appendText(((Button) e.getSource()).getText());
				guessBtn.setDisable(false);
			}
			((Button) e.getSource()).setDisable(true);
			CurrentGuess = guess.getText();
			guess.setDisable(true);
		}

	// Play turn method
	public void playTurn() {
		guess.setDisable(false);
		// Check the guess
		textForWord.setText(game.checkGuess(CurrentGuess));
		if (!(game.isCorrectGuess())) {
			// increase mistakes if guess is incorrect
			numOfMistakes += 1;
			// Presents current lives left
			lives.setText(game.getLives() - numOfMistakes + "");
			// draw person's elements
			drawElements(numOfMistakes);
		// if the word is not concealed -> player won!
		} else if (!game.getAstric().contains("*")) {
			grid.getChildren().clear();
			guessBtn.setDisable(true);
			guess.setVisible(false);
			setPopup("Winner!","Great game!","You are probably an expert on dog breeds! \n\n\n THANKS FOR PLAYING!");
			image = new Image("https://styles.redditmedia.com/t5_5vivsz/styles/profileIcon_3qcnddkr9oi81.jpg?width=256&height=256&crop=256:256,smart&s=347ce97478351e8bd8a821e9c0dd3459ba1cfcbf");
			imageView = new ImageView(image);
			imageView.setFitWidth(150);
			imageView.setFitHeight(150);
			information.setGraphic(imageView);
			information.showAndWait();
		}
	}

	// Draw the elements of body ( for each mistake )
	private void drawElements(int num) {
		int getPopup = 0;
		for (int i = 0; i < game.getLives(); i++) {
			if (numOfMistakes == 1) {
				// draw head
				gc.fillRoundRect(175, 50, 50, 50, 100, 100);
			} else if (numOfMistakes == 2) {
				// draw neck
				gc.setStroke(Color.BLACK);
				gc.strokeLine(200, 100, 200, 120);
			} else if (numOfMistakes == 3) {
				// draw right hand
				gc.setStroke(Color.BLACK);
				gc.strokeLine(200, 120, 240, 160);
			} else if (numOfMistakes == 4) {
				// draw left hand
				gc.setStroke(Color.BLACK);
				gc.strokeLine(200, 120, 160, 160);
			} else if (numOfMistakes == 5) {
				// draw Body
				gc.setStroke(Color.BLACK);
				gc.strokeLine(200, 120, 200, 250);
			} else if (numOfMistakes == 6) {
				// draw right leg
				gc.setStroke(Color.BLACK);
				gc.strokeLine(200, 250, 240, 290);
			} else if (numOfMistakes == 7) {
				// draw left leg
				gc.setStroke(Color.BLACK);
				gc.strokeLine(200, 250, 160, 290);
				grid.getChildren().clear();
				guessBtn.setVisible(false);
				//statuMsg.setText("You Lose!\n try again?");
				guess.setVisible(false);
				getPopup = numOfMistakes;
			}
		}
		// END OF THE GAME Pop-up
		if (getPopup == 7) {
			setPopup("END OF THE ROAD","That was so close","better luck next time!  \n\nNext game will be better! \n\n\n THANKS FOR PLAYING!");
			image = new Image("https://pbs.twimg.com/profile_images/622403338470973440/qf1dGvKx_400x400.jpg");
			imageView = new ImageView(image);
			imageView.setFitWidth(150);
			imageView.setFitHeight(150);
			information.setGraphic(imageView);
			information.showAndWait();
		}
	}
	
	// Set Pop-up method
	private void setPopup(String title, String header, String content) {
		information.setTitle(title);
		information.setHeaderText(header);
		information.setContentText(content);
	}
}
