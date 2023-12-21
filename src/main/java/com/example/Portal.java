package com.example;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class Portal {
    Quiz quiz;

    public Portal(Quiz quiz){
        this.quiz = quiz;
    }

    public void take_quiz(Participant participant, List<String> openEndedAnswers, List<String> mcqAnswers) throws IncompleteQuizException {
        LinkedHashMap<String, String> openEndedQuestions = quiz.get_questions();
        LinkedHashMap<String, List<String>> mcqQuestions = quiz.getMCQQuestions();

        int score = 0;
        int openEndedIndex = 0;
        int mcqIndex = 0;
        int open_q_size = openEndedQuestions.size();
        int mcq_q_size = mcqQuestions.size();
        int open_ans_size = openEndedAnswers.size();
        int mcq_ans_size = mcqAnswers.size();

 List<String> orderedOpenEndedQuestions = new ArrayList<>(openEndedQuestions.keySet());
List<String> orderedMCQQuestions = new ArrayList<>(mcqQuestions.keySet());

for (int i = 0; i < orderedOpenEndedQuestions.size(); i++) {
    String question = orderedOpenEndedQuestions.get(i);
    String answer = openEndedQuestions.get(question);

    if (i < open_ans_size) {
        try {
            if (answer.equals(openEndedAnswers.get(i))) {
                score++;
                participant.add_to_correct(question);
            } else {
                participant.add_to_incorrect(question);
                throw new IncorrectanswerException("Wrong answer for question: " + question);
            }
        } catch (IncorrectanswerException e) {
            System.out.println(e.getMessage());
        }
    } else {
        participant.add_to_not_attempted(question);
    }
}

for (int i = 0; i < orderedMCQQuestions.size(); i++) {
    String question = orderedMCQQuestions.get(i);
    List<String> options = mcqQuestions.get(question);

    if (i < mcq_ans_size) {
        try {
            int correctOptionIndex = Integer.parseInt(options.get(options.size() - 1));
            if (correctOptionIndex==Integer.parseInt(mcqAnswers.get(i))) {
                score++;
                participant.add_to_correct(question);
            } else {
                participant.add_to_incorrect(question);
                throw new IncorrectanswerException("Wrong answer for question: " + question);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException | IncorrectanswerException e) {
            System.out.println(e.getMessage());
        }
    } else {
        participant.add_to_not_attempted(question);
    }
}

participant.set_score(score);

if (open_ans_size < open_q_size || mcq_ans_size < mcq_q_size) {
    throw new IncompleteQuizException("Incomplete quiz attempted.");
}

System.out.println("Thanks " + participant.get_name() + " for attempting quiz " + quiz.get_name());
    }
    
} 
