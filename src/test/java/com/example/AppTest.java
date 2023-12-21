package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class AppTest 
{
    //Check Quiz Creation
     @Test
    public void testQuizCreation() {
        Manager manager = new Manager();
        Quiz quiz = new Quiz("TestQuiz","I1");
        manager.addQuiz(quiz);
        assertTrue(manager.checkQuiz(quiz));
    }
    
    //Check Quiz is capable of adding question
    @Test
    public void testAddQuestion() {
        Quiz quiz = new Quiz("TestQuiz","I1");
        quiz.add_question("What is 2 + 2?", "4","Easy");
        Map<String, String> questions = quiz.get_questions();
        assertTrue(questions.containsKey("What is 2 + 2?"));
        assertEquals("4", questions.get("What is 2 + 2?"));
    }

    @Test
    public void testAddMCQquestion(){
        Quiz quiz = new Quiz("TestQuiz","I1");
        String question = "Where is bollywood located?";
        List<String> options = new ArrayList<>();
        options.add("bhopal");
        options.add("mumbai");
        options.add("pune");
        options.add("nagpur");
        quiz.addMCQQuestion(question, options, 1);
        Map<String, List<String>> mcqQuestions = quiz.getMCQQuestions();
        assertTrue(mcqQuestions.containsKey("Where is bollywood located?"));
   }

    //Checking delete question functionality
    @Test
    public void testDeleteQuestion(){
        Quiz quiz = new Quiz("TestQuiz","I1");
        quiz.add_question("What is 2 + 2?", "4","Easy");
        quiz.add_question("What is the capital of France?", "Paris","Easy");
        quiz.delete_question("What is 2 + 2?");
        Map<String, String> questions = quiz.get_questions();
        assertFalse(questions.containsKey("What is 2 + 2?"));

    }

    //Checking edit question functionality
    @Test
    public void testEditQuestion(){
        Quiz quiz = new Quiz("TestQuiz","I1");
        quiz.add_question("What is 2 + 2?", "4","Easy");
        quiz.add_question("What is the capital of France?", "Paris","Easy");
        quiz.edit_question("What is 2 + 2?","5");
        Map<String, String> questions = quiz.get_questions();
        assertEquals("5", questions.get("What is 2 + 2?"));
    }
    
    //Check Participant is there in system
    @Test
    public void testParticipantRegistration() {
        Manager manager = new Manager();
        Participant participant = new Participant("Shrikant");
        manager.addParticipant(participant);
        assertTrue(manager.checkParticipant(participant));
    }

    //Check quiz attemption with unregistered candidate
    @Test
    public void testConductQuizWithParticipantNotRegistered() {
        Manager manager = new Manager();
        Quiz quiz = new Quiz("TestQuiz","I1");
        
        quiz.add_question("What is 2 + 2?", "4","Easy");
        quiz.add_question("What is the capital of France?", "Paris","Easy");
        String question = "Where is bollywood located?";
        List<String> options = new ArrayList<>();
        options.add("bhopal");
        options.add("mumbai");
        options.add("pune");
        options.add("nagpur");
        quiz.addMCQQuestion(question, options, 1);
        manager.addQuiz(quiz);

        Participant participant = new Participant("Himanshu");
        List<String> participantAnswers = new ArrayList<>();
        participantAnswers.add("4");
        participantAnswers.add("Berlin");
        List<String> mcqAnswers = new ArrayList<>();
        mcqAnswers.add("1");
        try{
        manager.takeQuiz(participant, quiz,participantAnswers,mcqAnswers);
        fail("Quiz should not be taken");
        }
        catch(ParticipantNotFound e){
            System.out.println(e.getMessage());
            assertEquals("Participant not found in the system.", e.getMessage());
        }
        catch(QuizNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IncompleteQuizException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    //Check accuracy of portal conducting quiz
    @Test
    public void testQuizAttemptAndScoring() {
        Manager manager = new Manager();
        Quiz quiz = new Quiz("TestQuiz","I1");
        quiz.add_question("What is 2 + 2?", "4","Easy");
        quiz.add_question("What is the capital of France?", "Paris","Easy");
        String question = "Where is bollywood located?";
        List<String> options = new ArrayList<>();
        options.add("bhopal");
        options.add("mumbai");
        options.add("pune");
        options.add("nagpur");
        quiz.addMCQQuestion(question, options, 1);
        manager.addQuiz(quiz);

        Participant participant = new Participant("Himanshu");
        manager.addParticipant(participant);

        List<String> participantAnswers = new ArrayList<>();
        participantAnswers.add("4");
        participantAnswers.add("Berlin");
        List<String> mcqAnswers = new ArrayList<>();
        mcqAnswers.add("1");
        try{
        manager.takeQuiz(participant, quiz,participantAnswers,mcqAnswers);
        }
        catch(ParticipantNotFound e){
            fail("Exception not expexted here");
        }
        catch(QuizNotFoundException e){
            fail("Exception not expexted here");
        }
        catch(IncompleteQuizException e){
            fail("Exception not expexted here");
        }

        // Check if the participant's score is correctly evaluated
        assertEquals(2, participant.get_score()); // Participant got one question correct

        // Check if correct and incorrect questions are recorded
        assertTrue(participant.get_correct_questions().contains("What is 2 + 2?"));
        assertTrue(participant.get_incorrect_questions().contains("What is the capital of France?"));
    }
    
}
