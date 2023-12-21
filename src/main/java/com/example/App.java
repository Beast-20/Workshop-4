package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import java.util.concurrent.atomic.AtomicBoolean;

public class App {
    private static String userInput;

    private static void getUserInput(Scanner scanner) {
        userInput = scanner.nextLine();
    }

    static class InputTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Time's up! No user input received within 20 seconds.");
            // You can add additional handling here if needed
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final Manager manager = new Manager();

        while (true) {
            System.out.println("Are you an instructor or a participant?");
            System.out.print("Enter 'instructor' or 'participant' (or 'exit' to quit): ");
            String userType = scanner.nextLine().toLowerCase();

            if (userType.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            if (userType.equals("instructor")) {
                boolean flag = true;
                while (flag) {
                    System.out.println("\n1. Add Quiz");
                    System.out.println("2. Edit Quiz");
                    System.out.println("3. Delete Quiz");
                    System.out.println("4. List Quizzes");
                    System.out.println("5. Add Quizzes to CSV File");
                    System.out.println("6. View Leader Board");
                    System.out.println("7. Randomize the quiz");
                    System.out.println("8. View the quiz with all questions in it");
                    System.out.println("9. Exit");

                    System.out.print("Select an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    switch (choice) {
                        case 1:
                            System.out.print("Enter instructor name: ");
                            String instructorName = scanner.nextLine();
                            System.out.print("Enter quiz name: ");
                            String quizName = scanner.nextLine();
                            Quiz newQuiz = new Quiz(quizName, instructorName);

                            // Adding open-ended questions to the quiz
                            boolean addMoreQuestions = true;
                            while (addMoreQuestions) {
                                System.out.print("Enter open-ended question: ");
                                String question = scanner.nextLine();
                                System.out.print("Enter answer: ");
                                String answer = scanner.nextLine();
                                System.out.print("Enter category: ");
                                String category = scanner.nextLine();

                                newQuiz.add_question(question, answer, category); // Include category

                                System.out.print("Do you want to add more open-ended questions? (y/n): ");
                                String moreQuestions = scanner.nextLine().toLowerCase();
                                addMoreQuestions = moreQuestions.equals("y");
                            }
                            System.out.println("Do you want to add mcq type question? (y/n):");
                            String addmcq = scanner.nextLine().toLowerCase();

                            // Adding MCQ questions to the quiz
                            boolean addMoreMCQ = addmcq.equals("y");
                            while (addMoreMCQ) {
                                System.out.print("Enter MCQ question: ");
                                String question = scanner.nextLine();
                                System.out.print("Enter options (comma-separated): ");
                                String[] optionsArray = scanner.nextLine().split(",");
                                List<String> options = Arrays.asList(optionsArray);
                                System.out.print("Enter correct option index: ");
                                int correctOptionIndex = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character

                                newQuiz.addMCQQuestion(question, options, correctOptionIndex);

                                System.out.print("Do you want to add more MCQ questions? (y/n): ");
                                String moreMCQ = scanner.nextLine().toLowerCase();
                                addMoreMCQ = moreMCQ.equals("y");
                            }

                            manager.addQuiz(newQuiz);
                            break;

                            case 2:
                            manager.listQuizzes();
                            System.out.print("Enter quiz name to edit: ");
                            String quizToEditName = scanner.nextLine();
                            Quiz quizToEdit = manager.getQuizByName(quizToEditName);
                        
                            if (quizToEdit != null) {
                                while (true) {
                                    System.out.println("\n1. Add Open-Ended Question");
                                    System.out.println("2. Edit Open-Ended Question");
                                    System.out.println("3. Delete Open-Ended Question");
                                    System.out.println("4. Add MCQ Question");
                                    System.out.println("5. Edit MCQ Question");
                                    System.out.println("6. Delete MCQ Question");
                                    System.out.println("7. Exit");
                        
                                    System.out.print("Select an option: ");
                                    int editOption = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character
                        
                                    switch (editOption) {
                                        case 1:
                                            manager.addQuestionToQuiz(quizToEdit);
                                            break;
                                        case 2:
                                            manager.editQuestionInQuiz(quizToEdit);
                                            break;
                                        case 3:
                                            manager.deleteQuestionInQuiz(quizToEdit);
                                            break;
                                        case 4:
                                            manager.addMCQQuestionToQuiz(quizToEdit);
                                            break;
                                        case 5:
                                            manager.editMCQQuestionInQuiz(quizToEdit);
                                            break;
                                        case 6:
                                            manager.deleteMCQQuestionInQuiz(quizToEdit);
                                            break;
                                        case 7:
                                            System.out.println("Exiting quiz editing...");
                                            break;
                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                    }
                        
                                    if (editOption == 7) {
                                        break; // Exit the quiz editing loop
                                    }
                                }
                            } else {
                                System.out.println("Error: Quiz '" + quizToEditName + "' not found in the system.");
                            }
                            break;
                        

                        case 3:
                            manager.listQuizzes();
                            System.out.print("Enter quiz name to delete: ");
                            String quizToDeleteName = scanner.nextLine();
                            Quiz quizToDelete = manager.getQuizByName(quizToDeleteName);
                            if (quizToDelete != null) {
                                manager.deleteQuiz(quizToDelete);
                            } else {
                                System.out.println("Error: Quiz " + quizToDeleteName + " not found in the system.");
                            }
                            break;
                        case 4:
                            manager.listQuizzes();
                            break;
                        case 5:
                            manager.addQuizzestoCSV();
                        case 9:
                            System.out.println("Exiting to main menu...");
                            flag = false;
                            break;
                        case 6:
                            manager.listQuizzes();
                            System.out.print("Enter quiz name of which you want to view Leaderboard: ");
                            String quizname = scanner.nextLine();

                            manager.displayLeaderboard(quizname);
                            break;
                        case 7:
                            manager.listQuizzes();
                            System.out.print("Enter quiz name to randomize: ");
                            String quizToRandomName = scanner.nextLine();

                            manager.randomizeQuestionsOrder(quizToRandomName);
                            break;
                        case 8:
                            manager.listQuizzes();
                            System.out.print("Enter quiz name to view: ");
                            String quizToPrint = scanner.nextLine();

                            manager.displayQuestionsWithCategories(quizToPrint);
                            manager.displayMCQQuestions(quizToPrint); // Add this line to display MCQ questions
                            break;

                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            } else if (userType.equals("participant")) {
                System.out.print("Enter your name: ");
                String participantName = scanner.nextLine();
                final Participant participant = new Participant(participantName);

                manager.addParticipant(participant);

                manager.listQuizzes();

                System.out.print("Enter the quiz name you want to take: ");
                String quizName = scanner.nextLine();
                final Quiz selectedQuiz = manager.getQuizByName(quizName);
                if (selectedQuiz != null) {

                    final List<String> openEndedanswers = new ArrayList<>();
                    final List<String> mcqAnswers = new ArrayList<>();

                    System.out.println("Quiz: " + selectedQuiz.get_name());
                    Map<String, String> openEndedQuestions = selectedQuiz.get_questions();
                    Map<String, List<String>> mcqQuestions = selectedQuiz.getMCQQuestions();
                    openEndedanswers.clear();
                    mcqAnswers.clear();
                    Timer timer = new Timer();
                    // Taking open-ended questions
                    for (Map.Entry<String, String> entry : openEndedQuestions.entrySet()) {
                        System.out.println("Question: " + entry.getKey());
                        String answer;
                        System.out.println("Your answer (You have 20 seconds...)");
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println("Time is up! Submitting partial attempted quiz");
                                try {
                                    manager.takeQuiz(participant, selectedQuiz, openEndedanswers, mcqAnswers);
                                    participant.get_result();
                                } catch (ParticipantNotFound e) {
                                    System.out.println(e.getMessage());
                                } catch (QuizNotFoundException e) {
                                    System.out.println(e.getMessage());
                                } catch (IncompleteQuizException e) {
                                    System.out.println(e.getMessage());
                                }
                                participant.get_result();
                                System.exit(0);
                            }
                        };

                        timer.schedule(task, 20 * 1000);
                        answer = scanner.nextLine();
                        openEndedanswers.add(answer);
                    }
                    timer.cancel();
                    Timer timer1 = new Timer();
                    // Taking multiple-choice questions
                    for (Map.Entry<String, List<String>> mcqEntry : mcqQuestions.entrySet()) {
                        String question = mcqEntry.getKey();
                        List<String> options_including_answer = mcqEntry.getValue();

                        System.out.println("Question: " + question);
                        List<String> options = new ArrayList<>(options_including_answer.subList(0, options_including_answer.size() - 1)) ;
                        System.out.println("Options: " + String.join(", ", options));
                        String answer;
                        System.out.println("Your answer (You have 20 seconds...)");
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println("Time is up! Submitting partial attempted quiz");
                                try {
                                    manager.takeQuiz(participant, selectedQuiz, openEndedanswers, mcqAnswers);
                                    participant.get_result();
                                } catch (ParticipantNotFound e) {
                                    System.out.println(e.getMessage());
                                } catch (QuizNotFoundException e) {
                                    System.out.println(e.getMessage());
                                } catch (IncompleteQuizException e) {
                                    System.out.println(e.getMessage());
                                }
                                participant.get_result();
                                System.exit(0);
                            }
                        };

                        timer1.schedule(task, 20 * 1000);
                        answer = scanner.nextLine();
                        mcqAnswers.add(answer);
                    }
                    
                    System.out.println("Open ended sols:- "+ openEndedanswers);
                    System.out.println("Open ended sols:- "+ mcqAnswers);
                    timer1.cancel();

                    try {
                        System.out.println(
                                "Welcome " + participant.get_name() + " in " + selectedQuiz.get_name() + " quiz");
                        manager.takeQuiz(participant, selectedQuiz, openEndedanswers, mcqAnswers);
                        participant.get_result();
                    } catch (ParticipantNotFound e) {
                        System.out.println(e.getMessage());
                    } catch (QuizNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (IncompleteQuizException e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("Error: Quiz '" + quizName + "' not found in the system.");
                }
            }

        }
    }
}
