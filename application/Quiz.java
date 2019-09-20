package application;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Generates quiz using questions in the currently selected topics and prepares
 * questions to be viewed by the user in the form of an ArrayList.
 * This class will also keep track of the user’s quiz score.
 */
public class Quiz {

	private int score; // User's total score.
	private int numberOfQuestions;
	private int currentQuestion;
	private ArrayList<Question> questionList; // ArrayList of questions related to the topics selected.

	public Quiz(int totalQuestions) {
		this.currentQuestion = 0;
		this.score = 0;
		this.numberOfQuestions = totalQuestions;
	}

	/*
	 * Generates the questionList based off of the topics selected and the number of
	 * questions needed
	 */
	public void generateQuestionList(DataManager dm, ObservableList<String> topicList, int userNumQuestions) {
		// parse the comboBox list for what topics have been selected

		ArrayList<String> selectedTopics = new ArrayList<String>();
		ArrayList<Question> filteredQuestions = new ArrayList<Question>();

		topicList.forEach(topic -> {
			selectedTopics.add(topic);
		});

		int addedQuestions = 0;
		ArrayList<Question> allQuestions = dm.getAllQuestion();
		// iterate through total questions
		for (int t = 0; t < selectedTopics.size(); t++) {
			for (int i = 0; i < allQuestions.size(); i++) {
				// if a question matches a selected topics
				if (selectedTopics.get(t).equals(allQuestions.get(i).topic)) {
					// add it to this list
					filteredQuestions.add(allQuestions.get(i));
					addedQuestions++;
				}
			}
		}

		this.numberOfQuestions = userNumQuestions;
		this.questionList = filteredQuestions;
	}

	/*
	 * Increments the score if a correct answers was selected
	 */
	void answer(Question currQ, String selectedAnswer) {
		if (currQ.answers.get(currQ.correctIndex).contentEquals(selectedAnswer)) {
			score++;
		}
	}

	public int getScore() {
		return this.score;
	}

	public int getCurrQuestion() {
		return this.currentQuestion;
	}

	public void incrementQuestion() {
		this.currentQuestion++;
	}

	public int getNumUserQuestions() {
		return this.numberOfQuestions;
	}

	public void setNumUserQuestions(int num) {
		this.numberOfQuestions = num;
	}

	public ArrayList<Question> getQuestionList() {
		return this.questionList;
	}

}
