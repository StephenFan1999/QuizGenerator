	package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/*
 * Responsible for reading, parsing and passing question data (correct/incorrect answers and other
 * metadata)from JSON files into the Question class. Holds master list of questions available to be
 * made in ArrayList. Must also output JSON files containing all questions in the question class
 */
@SuppressWarnings(value = { "unchecked" })
public class DataManager implements DataManagerADT {


    ArrayList<String> topics = new ArrayList<String>(); // holds current topics available
    ArrayList<Question> allQuestions = new ArrayList<Question>(); // holds all available questions


    /**
     * For importing questions to master list via a JSON file
     * 
     * @param filePath: file to read from
     */
    @Override
    public void addJSONQuestions(String filePath) throws FileNotFoundException, ParseException, IOException{
        // TODO Auto-generated method stub
        Object fileParser = new JSONParser().parse(new FileReader(filePath));
        
        JSONObject jsonFile = (JSONObject) fileParser;
        
        ArrayList<Question> questions = new ArrayList<Question>();
        JSONArray questionArray = (JSONArray) jsonFile.get("questionArray");
        
        Iterator itr1 = questionArray.iterator();
        Iterator<Map.Entry> itr2;
        
        while(itr1.hasNext())   //iterates through each question in the questionArray
        {
            itr2 = ((Map) itr1.next()).entrySet().iterator();
            String questionText = new String("");
            String topic = new String("");
            String imageName = new String("");
            int index = 0;
            ObservableList<String> answers = FXCollections.observableArrayList();
            while(itr2.hasNext())   //iterates through the fields in each question
            {
                Map.Entry pair = itr2.next();
                if(pair.getKey().equals("questionText"))    //checks if this item is the question text
                {
                    questionText = (String) pair.getValue();
                }
                
                if(pair.getKey().equals("topic"))   //checks if this item is the topic
                {
                    topic = (String) pair.getValue();
                }
                
                if(pair.getKey().equals("image")) //places image name into imageName variable if it is there
                {
                    if(!pair.getValue().equals("none")) imageName = (String) pair.getValue();   
                }
                
                if(pair.getKey().equals("choiceArray"))
                {
                    JSONArray currentAnswers = (JSONArray) pair.getValue();
                    Iterator itr3 = currentAnswers.iterator();
                    int counter = 0;
                    while(itr3.hasNext())
                    {
                        Iterator itr4 = ((Map) itr3.next()).entrySet().iterator();
                        while(itr4.hasNext())
                        {
                            Map.Entry currAnswer = (Entry) itr4.next();
                            if(currAnswer.getKey().equals("isCorrect"))
                            {
                                if(currAnswer.getValue().equals("T")) index = counter;
                                counter++;
                            }
                            
                            if(currAnswer.getKey().equals("choice")) 
                            {
                                String value = (String) currAnswer.getValue();
                                answers.add(value);
                            }

                        }
                    }
                    
                }
                
                
                
            }
            
            allQuestions.add(new Question(questionText, answers, index, topic, imageName));
            if(!this.topics.contains(topic)) {
                topics.add(topic);
            }
        }
        
    }

    /**
     * For users to add question to question list based on current topics to the master list of
     * questions
     * 
     * @param question: question statement
     * @param Topic: topic of question
     * @param correctAnswer: correct answer of question
     * @param incorrect: first incorrect answer
     * @param incorrect2: second incorrect answer
     * @param incorrect3: third incorrect answer
     * @param incorrect4: fourth incorrect answer
     */
    @Override
    public void addQuestionUser(String question, String topic, String correctAnswer,
        String incorrect, String incorrect2, String incorrect3, String incorrect4, String image) {
        ObservableList<String> answers = FXCollections.observableArrayList(); // list for answers
        answers.add(correctAnswer); // add correct and incorrect answers
        answers.add(incorrect);
        answers.add(incorrect2);
        answers.add(incorrect3);
        answers.add(incorrect4);

        Question toAdd = new Question(question, answers, 1, topic, image); // new question object
        allQuestions.add(toAdd); // add question to master ArrayList

    }

    /**
     * For exporting JSON file of existing questions to external location given by filePath
     * parameter.
     * 
     * @param filepath: file to send question list to
     * @throws Exception 
     */
    @Override
    public void exportQuestion(String filepath) throws Exception {
        JSONObject jo = new JSONObject(); // main question list object
        JSONArray questionArray = new JSONArray(); // main array of questions
        LinkedHashMap<String, String> n; // for holding answers

        for (Question question : this.allQuestions) { // iterate through all questions
            JSONObject questionObject = new JSONObject();
            questionObject.put("meta-data", "unused"); // initial parameters in JSON from question
            questionObject.put("questionText", question.questionText);
            questionObject.put("topic", question.topic);
            questionObject.put("image", question.image);

            JSONArray answerArray = new JSONArray(); // for choices to question
            int correctIndex = question.correctIndex; // to mark correct choice True

            for (int i = 0; i < question.answers.size(); ++i) { // iterate through question answers
                n = new LinkedHashMap<String, String>();

                if (i == correctIndex) {                //document correct answer in JSON file
                    n.put("isCorrect", "T");
                    n.put("choice", question.answers.get(i));
                } else {
                    n.put("isCorrect", "F");
                    n.put("choice", question.answers.get(i));
                }

                answerArray.add(n); // add each individual choice to answerArray
            }
            questionObject.put("choiceArray", answerArray); //add answer array in question object
            questionArray.add(questionObject); // add question object into main question array
        }

        jo.put("questionArray", questionArray); // add question array into main JSON object

        PrintWriter print;
        try {
            print = new PrintWriter(filepath); // new printwriter to designated filepath
            print.write(jo.toJSONString()); // write to file
        }

        catch (Exception e) {
            return;
        }

        print.flush(); // flush and close PrintWriter
        print.close();


    }

    /**
     * Returns number questions available
     * @return, number of questions
     */
    @Override
    public int getNumOfQuestions() {
        
        return allQuestions.size();
    }

    /**
     * Return a reference to the ArrayList of all questions
     * @return
     */
    public ArrayList<Question> getAllQuestion() {
        return this.allQuestions;
    }

    /**
     * Return a reference to the ArrayList of all topics
     * @return
     */
    public ArrayList<String> getTopics() {
        return this.topics;
    }
}
