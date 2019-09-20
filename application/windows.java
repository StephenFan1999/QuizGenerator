package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.Checkbox;
import java.io.FileNotFoundException;
import org.controlsfx.control.CheckComboBox;
import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This class holds all of the scenes of the program and their respective
 * functions with event handling. These individual methods are able to be called
 * by the main method for main functionality of the program.
 * 
 * @author A-Team 31
 *
 */
@SuppressWarnings("unchecked")
public class windows {

	private static DataManager dataManager = new DataManager();

	/**
	 * This method is used by all methods to set initial parameters for each scene.
	 * 
	 * @return Scene: return initial scene created
	 */
	public static Scene setup_screen() {
		BorderPane root = new BorderPane(); // use borderpane for each scene
		root.setStyle("-fx-background-color: grey"); // universal style

		Scene scene = new Scene(root, 960, 540); // create new scene object
		String css = windows.class.getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		return scene; // return standardized scene

	}

	/**
	 * Prompts the user in the beginning. Gives user option to import questions,
	 * create questions, and generate a quiz.
	 * 
	 * @param: main_stage: stage for scene to be placed on
	 */
	public static Scene main_menu(Stage main_stage) {
		Scene scene = setup_screen(); // set up scene
		BorderPane pane = (BorderPane) scene.getRoot(); // get pane from scene object

		// START JAVAFX element creation
		Label greetingLabel = new Label("Welcome to Quiz Generator");
		greetingLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Button importButton = new Button("Import Question");

		// display # questions
		Label numberQuestions = new Label("Available questions: " + dataManager.getNumOfQuestions());
		numberQuestions.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));

		// buttons for initial functionality
		Button createQuestionButton = new Button("Create Question");
		Button selectTopicsButton = new Button("Make Quiz");

		// add main nodes to vertical box
		VBox vBox = new VBox();
		vBox.getChildren().addAll(greetingLabel, importButton, createQuestionButton, selectTopicsButton);
		vBox.setSpacing(20.0);
		vBox.setAlignment(Pos.CENTER);

		// event handling for topic selection
		selectTopicsButton
				.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
					@Override
					public void handle(javafx.event.ActionEvent event) {
						main_stage.setScene(topic_selection(main_stage));
					}
				});
		// event handling for import questions button
		importButton.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				main_stage.setScene(import_question(main_stage));
			}

		});
		// event handling for creation of quiz button
		createQuestionButton
				.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
					@Override
					public void handle(javafx.event.ActionEvent event) {
						main_stage.setScene(add_question(main_stage));
					}
				});

		// ADD elements to pane
		pane.setCenter(vBox);
		pane.setBottom(numberQuestions);

		return scene; // return scene for main class to use
	}

	/**
	 * This method sets up the scene and event handling for the quiz setup page.
	 * 
	 * @param main_stage: stage for scene to be placed on
	 */
	public static Scene topic_selection(Stage main_stage) {
		Scene scene = setup_screen(); // set up scene basics
		BorderPane pane = (BorderPane) scene.getRoot(); // get pane from scene object

		// START JAVAFX element creation
		Button returnToMain = new Button("Back");
		Label greetingLabel = new Label("Quiz Setup");
		greetingLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		Label instructionLabel = new Label("Select topic:");
		instructionLabel.setFont(Font.font(20));

		// adding available topics to observable list
		ObservableList<String> options = FXCollections.observableArrayList();

		for (int i = 0; i < dataManager.getTopics().size(); i++) {
			options.add(dataManager.getTopics().get(i));
		}

		// combo box for topic options
		final CheckComboBox<String> checkComboBox = new CheckComboBox<String>(options);

		// hBox for label and topic options
		HBox topicSelection = new HBox();
		topicSelection.getChildren().addAll(instructionLabel, checkComboBox);
		topicSelection.setSpacing(75.0);
		topicSelection.setAlignment(Pos.CENTER);

		// prompt and textfield box for entering number of questions
		Label numberQuestions = new Label("Enter number of questions:");
		numberQuestions.setFont(Font.font(20));
		TextField quizQuestions = new TextField();
		quizQuestions.setPromptText("Number of Questions");
		quizQuestions.setMaxWidth(95);

		// add nodes to hBox
		HBox questionSetup = new HBox();
		questionSetup.getChildren().addAll(numberQuestions, quizQuestions);
		questionSetup.setSpacing(20);
		questionSetup.setAlignment(Pos.CENTER);

		// display number of available questions in corner
		Label availableQuestions = new Label("Available questions: " + dataManager.getNumOfQuestions());
		availableQuestions.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));

		// main generate quiz button
		Button button = new Button("Generate Quiz");

		// put all nodes in Vbox
		VBox allNodes = new VBox();
		allNodes.getChildren().addAll(greetingLabel, topicSelection, questionSetup, button);
		allNodes.setSpacing(40.0);
		allNodes.setAlignment(Pos.CENTER);
		// END JAVAFX element creation

		// ADD elements to pane
		pane.setTop(returnToMain);
		pane.setCenter(allNodes);
		pane.setBottom(availableQuestions);

		// event handling for "back" button
		returnToMain.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				main_stage.setScene(main_menu(main_stage));
			}

		});

		// event handling for "generate quiz" button
		button.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				if (quizQuestions.getText().isEmpty() || options.isEmpty()) {
					Alert alert = // alert displayed
							new Alert(AlertType.WARNING, "Fill out all fields above");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				} else {
					// check that there are more questions than number requested
					
					int requestedQuestions = Integer.parseInt(quizQuestions.getText());
					// int numQuestions = dataManager.getNumOfQuestions();
					Quiz userQuiz = new Quiz(requestedQuestions);
					userQuiz.generateQuestionList(dataManager, options, requestedQuestions);
					int numQuestions = userQuiz.getNumUserQuestions();
					if (requestedQuestions > numQuestions) {
						Alert alert = // alert displayed
								new Alert(AlertType.WARNING, "Not enough questions available!");
						alert.showAndWait().filter(response -> response == ButtonType.OK);
					} else { // valid number and quiz can be created
						userQuiz.generateQuestionList(dataManager, options, requestedQuestions);
						main_stage.setScene(question(main_stage, userQuiz));
					}
				}
			}

		});

		// event handling for "back" to home button
		returnToMain.setOnAction((event) -> {
			main_stage.setScene(main_menu(main_stage));
		});

		return scene; // return quiz setup scene
	}

	/*
	 * Ethan Handles importing JSON files from the user computer to be parsed as a
	 * list of questions.
	 */
	public static Scene import_question(Stage main_stage) { //
		Scene scene = setup_screen();
		BorderPane pane = (BorderPane) scene.getRoot();

		// START JAVAFX element creation
		Button returnToMainButton = new Button("Back");
		Label importQuestionHeader = new Label("Import Questions");
		importQuestionHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Label fileNameLabel = new Label("Enter name of the JSON file you wish to import");
		TextField fileNameTextField = new TextField();
		fileNameTextField.setMaxWidth(125);
		HBox info = new HBox(fileNameLabel, fileNameTextField);
		info.setSpacing(20.0);
		Button submitButton = new Button("Submit");
		VBox tripleLayer = new VBox(importQuestionHeader, info, submitButton);
		tripleLayer.setSpacing(20.0);
		tripleLayer.setAlignment(Pos.CENTER);
		info.setAlignment(Pos.CENTER);
		importQuestionHeader.setAlignment(Pos.TOP_CENTER);
		// END JAVAFX element creation

		// ADD elements to pane
		pane.setCenter(tripleLayer);
		pane.setTop(returnToMainButton);

		returnToMainButton
				.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
					@Override
					public void handle(javafx.event.ActionEvent event) {
						main_stage.setScene(main_menu(main_stage));
					}
				});

		submitButton.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {

				try {
					dataManager.addJSONQuestions(fileNameTextField.getText());
					Alert alert = new Alert(AlertType.INFORMATION, "File Uploaded Successfully!");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
					main_stage.setScene(main_menu(main_stage));
				} catch (FileNotFoundException e) {
					Alert alert = new Alert(AlertType.WARNING, "File was not found");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING, "Oops! Something went wrong!");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				}
			}
		});

		return scene;
	}

	/*
	 * Ethan Prompts the user to create questions from scratch. This window needs to
	 * obtain the question, correct/incorrect answers, and any files (pictures)
	 * needed for the question.
	 */
	public static Scene add_question(Stage main_stage) {
		Scene scene = setup_screen();
		BorderPane pane = (BorderPane) scene.getRoot();

		// START JAVAFX element creation
		GridPane grid = new GridPane();
		Button submitNewQuestionButton = new Button("Submit Question");
		Button returnToMainButton = new Button("Back");
		submitNewQuestionButton.setAlignment(Pos.BOTTOM_CENTER);
		Label addQuestionLabel = new Label("Add Question: ");
		addQuestionLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		grid.add(addQuestionLabel, 0, 0, 2, 1);
		grid.add(submitNewQuestionButton, 0, 6);
		Label topicLabel = new Label("Topic: ");
		topicLabel.setFont(Font.font(20));
		grid.add(topicLabel, 0, 1);

		TextField topicTextField = new TextField();
		topicTextField.setPromptText("Enter topic here");
		grid.add(topicTextField, 1, 1);
		Label questionLabel = new Label("Question: ");
		questionLabel.setFont(Font.font(20));
		grid.add(questionLabel, 0, 2);

		TextField questionTextField = new TextField();
		questionTextField.setPromptText("Enter Question here:");
		questionTextField.setPrefWidth(300);
		grid.add(questionTextField, 1, 2);

		Label imageLabel = new Label("File Name of Image(Optional): ");
		imageLabel.setFont(Font.font(20));
		grid.add(imageLabel, 0, 3);

		TextField imageTextField = new TextField();
		imageTextField.setPromptText("Enter Image File Name here:");
		grid.add(imageTextField, 1, 3);

		Label correctAnswerLabel = new Label("Correct Answer: ");
		correctAnswerLabel.setFont(Font.font(20));
		grid.add(correctAnswerLabel, 0, 4);

		TextField correctAnswerTextField = new TextField();
		correctAnswerTextField.setPromptText("Enter Correct Answer here:");
		grid.add(correctAnswerTextField, 1, 4);

		Label incorrectAnswersLabel = new Label("Incorrect Answers: ");
		incorrectAnswersLabel.setFont(Font.font(20));
		grid.add(incorrectAnswersLabel, 0, 5);

		TextField incorrectAnswer1TextField = new TextField();
		incorrectAnswer1TextField.setPromptText("Enter Incorrect Answer here:");
		grid.add(incorrectAnswer1TextField, 1, 5);
		TextField incorrectAnswer2TextField = new TextField();
		incorrectAnswer2TextField.setPromptText("Enter Incorrect Answer here:");
		grid.add(incorrectAnswer2TextField, 1, 6);
		TextField incorrectAnswer3TextField = new TextField();
		incorrectAnswer3TextField.setPromptText("Enter Incorrect Answer here:");
		grid.add(incorrectAnswer3TextField, 1, 7);
		TextField incorrectAnswer4TextField = new TextField();
		incorrectAnswer4TextField.setPromptText("Enter Incorrect Answer here:");
		grid.add(incorrectAnswer4TextField, 1, 8);

		grid.setAlignment(Pos.TOP_CENTER);
		grid.setVgap(20);
		grid.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		grid.getColumnConstraints().add(column1);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(30);
		grid.getRowConstraints().add(row1);
		// END JAVAFX element creation

		returnToMainButton
				.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
					@Override
					public void handle(javafx.event.ActionEvent event) {

						main_stage.setScene(main_menu(main_stage));
					}

				});

		submitNewQuestionButton
				.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
					@Override
					public void handle(javafx.event.ActionEvent event) {
						boolean qTF = questionTextField.getText().isEmpty();
						boolean tTF = topicTextField.getText().isEmpty();
						boolean iTF = imageTextField.getText().isEmpty();
						boolean cATF = correctAnswerTextField.getText().isEmpty();
						boolean iCATF1 = incorrectAnswer1TextField.getText().isEmpty();
						boolean iCATF2 = incorrectAnswer2TextField.getText().isEmpty();
						boolean iCATF3 = incorrectAnswer3TextField.getText().isEmpty();
						boolean iCATF4 = incorrectAnswer4TextField.getText().isEmpty();

						if (qTF || tTF || cATF || iCATF1 || iCATF2 || iCATF3 || iCATF4) // Shows alert
																						// if any of the
																						// non-optional
																						// fields have
																						// no text
						{
							Alert alert = new Alert(AlertType.WARNING, "Fields must be filled unless marked optional");
							alert.showAndWait().filter(response -> response == ButtonType.OK);
						} else {
							String imageString = new String("");
							if (!dataManager.getTopics().contains(topicTextField.getText())) {
								dataManager.getTopics().add(topicTextField.getText());
							}

							if (!iTF)
								imageString = imageTextField.getText().trim();

							dataManager.addQuestionUser(questionTextField.getText().trim(),
									topicTextField.getText().trim(), correctAnswerTextField.getText().trim(),
									incorrectAnswer1TextField.getText().trim(),
									incorrectAnswer2TextField.getText().trim(),
									incorrectAnswer3TextField.getText().trim(),
									incorrectAnswer4TextField.getText().trim(), imageString);
							Alert completedAdd = new Alert(AlertType.INFORMATION, "Question was added successfully!");
							completedAdd.showAndWait().filter(response -> response == ButtonType.OK);
							main_stage.setScene(main_menu(main_stage));
						}

					}

				});

		// ADD elements to pane
		pane.setCenter(grid);
		pane.setTop(returnToMainButton);
		return scene;
	}

	/*
	 * Displays the question and gives the user options to selects. Also displays an
	 * image if available. I'm working on this method
	 */
	public static Scene question(Stage main_stage, Quiz quiz) { // Steve "Stevo" Fan
		Scene scene = setup_screen();
		BorderPane pane = (BorderPane) scene.getRoot();
		Question q = quiz.getQuestionList().get(quiz.getCurrQuestion());

		q.scramble();
		// START JAVAFX element creation
		// build ComboBox
		final ComboBox comboBox = new ComboBox(q.answers);

		// build button
		Button button = new Button("Submit");
		button.setAlignment(Pos.BOTTOM_CENTER);

		// build label
		Label questionLabel = new Label(q.questionText);
		questionLabel.setWrapText(true);
		questionLabel.setPrefWidth(700);

		// set font for label for multiple choice question
		questionLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		// create vertical box to add items to
		VBox vBox = new VBox();
		if (!q.image.equals("")) { // if there is no image for this question
			// build ImageView
			ImageView imageview = new ImageView(q.image);
			vBox.getChildren().addAll(questionLabel, comboBox, imageview, button);
		} else {
			vBox.getChildren().addAll(questionLabel, comboBox, button);
		}

		vBox.setSpacing(20.0);
		vBox.setAlignment(Pos.CENTER);

		// END JAVAFX element creation

		// ADD elements to pane
		pane.setCenter(vBox);

		// event handling for submit button
		button.setOnAction((EventHandler<javafx.event.ActionEvent>) new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				String selected = (String) comboBox.getValue();
				if (selected == null) {
					Alert alert = new Alert(AlertType.WARNING, "Pick an answer!");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				} else {
					// check to see if the answer is right
					if (quiz.getCurrQuestion() >= quiz.getNumUserQuestions() - 1) {
						selected = (String) comboBox.getValue();
						quiz.answer(q, selected);
						quiz.incrementQuestion();
						main_stage.setScene(quiz_end(main_stage, quiz));
					} else {
						selected = (String) comboBox.getValue();
						quiz.answer(q, selected);
						quiz.incrementQuestion();
						main_stage.setScene(question(main_stage, quiz));
					}
				}

			}

		});

		return scene;
	}

	/**
	 * Scene that appears upon exit of program to save existing questions.
	 * 
	 * @param main_stage
	 * @return scene
	 */
	public static Scene exportQuestion(Stage main_stage) { // Shelby
		Scene scene = setup_screen(); // set up default screen parameters
		BorderPane pane = (BorderPane) scene.getRoot(); // attain root from scene setup

		// START JAVAFX element creation
		Label exportLabel = // main label
				new Label("Would you like to export your questions to designated file?");
		exportLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		// build buttons
		Button yes = new Button("Yes");
		Button no = new Button("No");

		HBox hbox = new HBox(); // add buttons to Hbox
		hbox.getChildren().addAll(yes, no);
		hbox.setSpacing(15.0);

		TextField fileName = new TextField(); // field for filename
		fileName.setPromptText("Enter filename");
		fileName.setMaxWidth(150.0);

		VBox vbox = new VBox(); // Vbox for all nodes
		vbox.getChildren().addAll(exportLabel, hbox, fileName);
		vbox.setSpacing(15.0);

		hbox.setAlignment(Pos.CENTER); // align box nodes
		vbox.setAlignment(Pos.CENTER);

		// ADD elements to pane
		pane.setCenter(vbox);

		boolean noFile = false; // variables for keeping track if valid fileName is present
		boolean JSONFile = false;

		yes.setOnAction((event) -> { // event handling for saving a file
			boolean writable = true; // variable to determine if file can be written to
			try {
				if (fileName.getText().isEmpty()) { // check if box is empty: can't write
					Alert alert = new Alert(AlertType.WARNING, "No file entered");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
					writable = false;
				} else { // text exists in box: can maybe write
					int nameLength = fileName.getText().length();
					if (nameLength <= 6) { // name won't have json extension
						Alert alert = new Alert(AlertType.WARNING, "File must have .json extension");
						alert.showAndWait().filter(response -> response == ButtonType.OK);
						writable = false;
					} else { // check for JSON extension
						String fileExtension = fileName.getText().substring(nameLength - 5);
						if (!fileExtension.equalsIgnoreCase(".json")) {
							writable = false;
							Alert alert = new Alert(AlertType.WARNING, "File must have .json extension");
							alert.showAndWait().filter(response -> response == ButtonType.OK);

						} else {
							writable = true; // if no conditions fail: can write to filename
						}
					}
				}
				if (writable) { // writing to JSON file if able
					String fileDest = fileName.getText();
					dataManager.exportQuestion(fileDest); // method in dataManager to write to JSON

					Alert completedAdd = // success message
							new Alert(AlertType.INFORMATION, "File was exported sucessfully!");
					completedAdd.showAndWait().filter(response -> response == ButtonType.OK);
					main_stage.close(); // close stage
				}

			} catch (Exception e) { // catches errors that arise when writing to file

				Alert alert = new Alert(AlertType.WARNING, "Oops! Something went wrong!");
				alert.showAndWait().filter(response -> response == ButtonType.OK); // fail message

			}

		});

		no.setOnAction((event) -> { // event handling for "no" button
			main_stage.close(); // simply close stage
		});

		return scene; // return this particular scene
	}

	/**
	 * This screen shows up after the user has taken the quiz and displays the score
	 * that user received.
	 * 
	 * @param main_stage
	 * @param quiz
	 * @return
	 */
	public static Scene quiz_end(Stage main_stage, Quiz quiz) {
		Scene scene = setup_screen(); // set up scene
		BorderPane pane = (BorderPane) scene.getRoot(); // get pane from scene object

		// START JAVAFX element creation
		Label top_text = new Label("Your Score:");
		float scorePercent = ((float) quiz.getScore() / (float) quiz.getNumUserQuestions()) * 100;
		Label percent = new Label(scorePercent + "%");
		Label fraction = new Label(quiz.getScore() + "/" + quiz.getNumUserQuestions());

		top_text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		percent.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
		fraction.setFont(Font.font("Verdana", FontWeight.BOLD, 40));

		// add main nodes to vertical box
		VBox vBox = new VBox();
		vBox.getChildren().addAll(top_text, percent, fraction);
		vBox.setSpacing(20.0);
		vBox.setAlignment(Pos.CENTER);

		// ADD elements to pane
		pane.setCenter(vBox);

		return scene; // return scene for main class to use
	}

}
