package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.ParseException;

/*
 * This class serves as a “plug” for the DataManager class for requisite methods needed to import
 * JSON files, parse them, and create questions and topics in their respective classes based off the
 * JSON file.
 */

public interface DataManagerADT {

	/**
	 * For importing questions to master list via a JSON file
	 * 
	 * @param filePath: file to read from
	 */
	void addJSONQuestions(String filePath) throws FileNotFoundException, ParseException, IOException;

	/**
	 * For users to add question to question list based on current topics to the
	 * master list of questions
	 * 
	 * @param question:      question statement
	 * @param Topic:         topic of question
	 * @param correctAnswer: correct answer of question
	 * @param incorrect:     first incorrect answer
	 * @param incorrect2:    second incorrect answer
	 * @param incorrect3:    third incorrect answer
	 * @param incorrect4:    fourth incorrect answer
	 */
	void addQuestionUser(String question, String Topic, String correctAnswer, String incorrect, String incorrect2,
			String incorrect3, String incorrect4, String image);

	/**
	 * For exporting JSON file of existing questions to external location given by
	 * filePath parameter.
	 * 
	 * @param filepath: file to send question list to
	 * @throws Exception
	 */
	void exportQuestion(String filepath) throws Exception;

	/**
	 * Returns number questions @return, number of questions
	 */
	int getNumOfQuestions();

}
