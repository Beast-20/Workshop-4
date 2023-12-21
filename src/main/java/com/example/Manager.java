package com.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class Manager {
    private List<Quiz> quizzes;
    private List<Participant> participants;

    public Manager() {
        this.quizzes = new ArrayList<>();
        this.participants = new ArrayList<>();

        try (CSVWriter writer1 = new CSVWriter(new FileWriter("Participants File", false))) {
            String[] data1 = new String[3];
            data1[0] = "Participant";
            data1[1] = "Quiz";
            data1[2] = "Score";
            writer1.writeNext(data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        System.out.println("Quiz added to the system: " + quiz.get_name());

    }

    public void addQuestionToQuiz(Quiz quiz) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new question: ");
        String newQuestion = scanner.nextLine();
        System.out.print("Enter answer: ");
        String newAnswer = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        quiz.add_question(newQuestion, newAnswer, category); // Include category
        System.out.println("Question added to the quiz.");
    }

    public void addQuizzestoCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("QuizzFile", false))) {
            String[] header_data = new String[4];
            header_data[0] = "Instructor Name";
            header_data[1] = "Quiz Name";
            header_data[2] = "Question";
            header_data[3] = "Answer";
            writer.writeNext(header_data);

            for (Quiz quiz : quizzes) {
                String instructorName = quiz.getInstructorName();
                String quizName = quiz.get_name();

                // Open-ended questions
                for (Map.Entry<String, String> entry : quiz.get_questions().entrySet()) {
                    String[] data = new String[4];
                    data[0] = instructorName;
                    data[1] = quizName;
                    data[2] = entry.getKey();
                    data[3] = entry.getValue();
                    writer.writeNext(data);
                }

                // MCQ questions
                for (Map.Entry<String, List<String>> entry : quiz.getMCQQuestions().entrySet()) {
                    String question = entry.getKey();
                    List<String> options = entry.getValue();

                    String[] data = new String[4];
                    data[0] = instructorName;
                    data[1] = quizName;
                    data[2] = question;
                    int correct_option = Integer.parseInt(options.get(options.size() - 1));
                    data[3] = options.get(correct_option);

                    writer.writeNext(data);
                }
            }

            System.out.println("Quizzes added to CSV file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editQuestionInQuiz(Quiz quiz) {
        System.out.println("Current questions in the quiz:");
        for (Map.Entry<String, String> entry : quiz.get_questions().entrySet()) {
            System.out.println("- " + entry.getKey());
        }

        System.out.print("Enter question to edit: ");
        Scanner scanner = new Scanner(System.in);
        String questionToEdit = scanner.nextLine();
        if (quiz.get_questions().containsKey(questionToEdit)) {
            System.out.print("Enter updated answer: ");
            String updatedAnswer = scanner.nextLine();
            quiz.edit_question(questionToEdit, updatedAnswer);
            System.out.println("Question updated in the quiz.");
        } else {
            System.out.println("Error: Question '" + questionToEdit + "' not found in the quiz.");
        }
    }

    public void deleteQuestionInQuiz(Quiz quiz) {
        System.out.println("Current questions in the quiz:");
        for (Map.Entry<String, String> entry : quiz.get_questions().entrySet()) {
            System.out.println("- " + entry.getKey());
        }

        System.out.print("Enter question to delete: ");
        Scanner scanner = new Scanner(System.in);
        String questionToDelete = scanner.nextLine();
        if (quiz.get_questions().containsKey(questionToDelete)) {
            quiz.delete_question(questionToDelete);
            System.out.println("Question deleted from the quiz.");
        } else {
            System.out.println("Error: Question '" + questionToDelete + "' not found in the quiz.");
        }
    }

    public void addMCQQuestionToQuiz(Quiz quiz) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new MCQ question: ");
        String newMCQQuestion = scanner.nextLine();

        System.out.print("Enter options (comma-separated): ");
        List<String> options = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Enter correct option index: ");
        int correctOptionIndex = Integer.parseInt(scanner.nextLine());

        quiz.addMCQQuestion(newMCQQuestion, options, correctOptionIndex);
        System.out.println("MCQ question added to the quiz.");
    }

    public void editMCQQuestionInQuiz(Quiz quiz) {
        System.out.println("Current MCQ questions in the quiz:");
        for (Map.Entry<String, List<String>> entry : quiz.getMCQQuestions().entrySet()) {
            System.out.println("- " + entry.getKey());
        }

        System.out.print("Enter MCQ question to edit: ");
        Scanner scanner = new Scanner(System.in);
        String mcqQuestionToEdit = scanner.nextLine();
        if (quiz.getMCQQuestions().containsKey(mcqQuestionToEdit)) {
            System.out.print("Enter updated options (comma-separated): ");
            List<String> updatedOptions = Arrays.asList(scanner.nextLine().split(","));

            System.out.print("Enter updated correct option index: ");
            int updatedCorrectOptionIndex = Integer.parseInt(scanner.nextLine());

            quiz.editMCQQuestion(mcqQuestionToEdit, updatedOptions, updatedCorrectOptionIndex);
            System.out.println("MCQ question updated in the quiz.");
        } else {
            System.out.println("Error: MCQ question '" + mcqQuestionToEdit + "' not found in the quiz.");
        }
    }

    public void deleteMCQQuestionInQuiz(Quiz quiz) {
        System.out.println("Current MCQ questions in the quiz:");
        for (Map.Entry<String, List<String>> entry : quiz.getMCQQuestions().entrySet()) {
            System.out.println("- " + entry.getKey());
        }

        System.out.print("Enter MCQ question to delete: ");
        Scanner scanner = new Scanner(System.in);
        String mcqQuestionToDelete = scanner.nextLine();
        if (quiz.getMCQQuestions().containsKey(mcqQuestionToDelete)) {
            quiz.deleteMCQQuestion(mcqQuestionToDelete);
            System.out.println("MCQ question deleted from the quiz.");
        } else {
            System.out.println("Error: MCQ question '" + mcqQuestionToDelete + "' not found in the quiz.");
        }
    }

    public void deleteQuiz(Quiz quizName) {

        quizzes.remove(quizName);

    }

    public Quiz getQuizByName(String quizName) {
        for (Quiz quiz : quizzes) {
            if (quiz.get_name().equalsIgnoreCase(quizName)) {
                return quiz;
            }
        }
        return null;
    }

    public void listQuizzes() {
        System.out.println("Available Quizzes:");
        for (Quiz quiz : quizzes) {
            System.out.println("- " + quiz.get_name());
        }
    }

    public void displayQuestionsWithCategories(String quizName) {
        Quiz quiz = getQuizByName(quizName);

        if (quiz != null) {
            System.out.println("Open-ended Questions for Quiz: " + quizName);
            Map<String, String> questions = quiz.get_questions();
            Map<String, String> questionCategories = quiz.get_questionCategories();

            for (Map.Entry<String, String> entry : questions.entrySet()) {
                String question = entry.getKey();
                String answer = entry.getValue();
                String category = questionCategories.get(question);

                System.out.println("- Question: " + question);
                System.out.println("  Answer: " + answer);
                System.out.println("  Category: " + category);
            }
        } else {
            System.out.println("Error: Quiz '" + quizName + "' not found in the system.");
        }
    }

    public void displayMCQQuestions(String quizName) {
        Quiz quiz = getQuizByName(quizName);

        if (quiz != null) {
            System.out.println("MCQ Questions for Quiz: " + quizName);
            Map<String, List<String>> mcqQuestions = quiz.getMCQQuestions();

            for (Map.Entry<String, List<String>> entry : mcqQuestions.entrySet()) {
                String question = entry.getKey();
                List<String> options = entry.getValue();

                System.out.println("- MCQ Question: " + question);
                System.out.println("  Options: " + String.join(", ", options.subList(0, options.size() - 1)));
                System.out.println("  Correct Option Index: " + options.get(options.size() - 1));
            }
        } else {
            System.out.println("Error: Quiz '" + quizName + "' not found in the system.");
        }
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
        System.out.println("Participant added to the system: " + participant.get_name());
    }

    public void listParticipants() {
        System.out.println("Registered Participants:");
        for (Participant participant : participants) {
            System.out.println("- " + participant.get_name());
        }
    }

    public boolean checkParticipant(Participant participant) {
        return participants.contains(participant);
    }

    public boolean checkQuiz(Quiz quiz) {
        return quizzes.contains(quiz);
    }

    public void takeQuiz(Participant participant, Quiz quiz, List<String> openEndedAnswers, List<String> mcqAnswers)
            throws ParticipantNotFound, IncompleteQuizException, QuizNotFoundException {
        if (!participants.contains(participant)) {
            throw new ParticipantNotFound("Participant not found in the system.");
        } else if (!quizzes.contains(quiz)) {
            throw new QuizNotFoundException("Quiz not found in system.");
        }

        Portal portal = new Portal(quiz);
        portal.take_quiz(participant, openEndedAnswers, mcqAnswers);

        try (CSVWriter writer = new CSVWriter(new FileWriter("Participants File", true))) {
            String[] data = new String[3];
            data[0] = participant.get_name();
            data[1] = quiz.get_name();
            data[2] = participant.get_score() + "";
            writer.writeNext(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayLeaderboard(String quizName) {
        try (CSVReader reader = new CSVReader(new FileReader("Participants File"))) {
            String[] nextRecord;
            Map<String, Integer> participantScores = new HashMap<>();
            reader.readNext();
            while ((nextRecord = reader.readNext()) != null) {
                String participantName = nextRecord[0];
                String currentQuiz = nextRecord[1];
                int score = Integer.parseInt(nextRecord[2]);

                if (currentQuiz.equalsIgnoreCase(quizName)) {
                    // Update or add the participant's score
                    participantScores.put(participantName, score);
                }
            }

            System.out.println("Leaderboard for Quiz: " + quizName);
            System.out.println("Participant Name    Score");
            System.out.println("----------------------------");

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(participantScores.entrySet());
            Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                    return entry2.getValue().compareTo(entry1.getValue());
                }
            });

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                System.out.printf("%-20s %d%n", entry.getKey(), entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void randomizeQuestionsOrder(String quizName) {
        Quiz quiz = getQuizByName(quizName);
        if (quiz != null) {
            quiz.randomizeQuestionsOrder();
            System.out.println("Questions order randomized for quiz: " + quizName);
        } else {
            System.out.println("Error: Quiz '" + quizName + "' not found in the system.");
        }
    }

}
