package application;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.ArrayList;

import application.windows;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			ObservableList<String> options = FXCollections.observableArrayList("Baby don't hurt me", "Shrek",
					"profound attraction for another person");
			String questionText = new String("What is love?");
			String image = new String("temp_img.jpg");
			int correctIndex = 0;
			String topic = new String("Life Essentials");
			Question question = new Question(questionText, options, correctIndex, topic, image);
			ArrayList<Question> tempQList = new ArrayList<Question>();
			tempQList.add(question);
			Quiz tempQuiz = new Quiz(10);

			Scene main_menu = windows.main_menu(primaryStage);
			// Scene question_window = windows.question(primaryStage, tempQuiz);
			Scene export_question = windows.exportQuestion(primaryStage);
			/*
			 * Scene topic_selection = windows.topic_selection(primaryStage); Scene
			 * import_question = windows.import_question(primaryStage); Scene add_question =
			 * windows.add_question(primaryStage);
			 */

			primaryStage.setScene(main_menu); // change this line to show specific screen

			primaryStage.show();
			primaryStage.setTitle("CS400 Quiz Generator");

			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				primaryStage.setScene(export_question);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
