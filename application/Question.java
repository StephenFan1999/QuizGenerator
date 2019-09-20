package application;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * This class will be used to create objects that represent the questions that the user would be asking for and answering.
 * This class will need a variable to hold the question itself, one to represent the topic of question,
 * an array of the answers and a variable that represents the index of the correct answer in the array.
 */
public class Question {

	public String questionText; // the text of the question
	public String topic; // topic of the question
	public ObservableList<String> answers; // Array of strings to store the answer choices
	public int correctIndex; // Int that represents the index of the correct answer in the Array answers
	public String image; // the string of the image file name

	/**
	 * Constructor for Question class
	 * 
	 * @param text         is the question text
	 * @param answers      is the array of answer choices
	 * @param correctIndex is the index of the correct answer
	 * @param topic        is the topic of the question
	 * @param image        is the string of the image file name
	 */
	public Question(String text, ObservableList<String> answers, int correctIndex, String topic, String image) {
		this.questionText = text;
		this.topic = topic;
		this.answers = answers;
		this.correctIndex = correctIndex;
		this.image = image;
	}

	/**
	 * Alternate Constructor for the question class
	 * 
	 * @param text         is the question text
	 * @param answers      is the array of answer choices
	 * @param correctIndex is the index of the correct answer
	 * @param topic        is the topic of the question
	 */
	public Question(String text, ObservableList<String> answers, int correctIndex, String topic) {
		this.questionText = text;
		this.topic = topic;
		this.answers = answers;
		this.correctIndex = correctIndex;
	}

	/**
	 * Scrambles the order of the Array of answers. Called when quiz is generated
	 * 
	 * @return a scrambled array of the answer choices
	 */
	public ObservableList<String> scramble() {
		// create variables
		Random rand = new Random();
		ObservableList<String> scrambledAnswers = FXCollections.observableArrayList();

		// make a string value of the correct answer so it can be its index can be
		// updated later
		String correctAnswer = this.answers.get(this.correctIndex);

		// fill scrambledAnswers array with null values
		for (int j = 0; j < this.answers.size(); j++) {
			scrambledAnswers.add(null);
		}

		for (int i = 0; i < answers.size(); i++) {
			// generate a random integer within the array's size
			int nextIndex = rand.nextInt(answers.size());

			// check if the index in the scrambled answers array is already filled
			// get a new random index if it is already filled
			while (scrambledAnswers.get(nextIndex) != null) {
				nextIndex = rand.nextInt(answers.size());
			}

			// remove the current value at nextIndex
			scrambledAnswers.remove(nextIndex);

			// add each answer choice to the random index in the scrambled answers array
			scrambledAnswers.add(nextIndex, answers.get(i));
		}

		// set answers to the new scrambled list of answers
		this.answers = scrambledAnswers;

		// make sure correctIndex still refers to the correct answer
		for (int q = 0; q < this.answers.size(); q++) {
			if (this.answers.get(q).equals(correctAnswer)) {
				correctIndex = q;
			}
		}

		return scrambledAnswers;
	}
}
