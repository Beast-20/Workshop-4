package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Quiz {
    String quiz_name;
    LinkedHashMap<String,String> questions;
    LinkedHashMap<String, String> questionCategories;
    String instructorName;
    LinkedHashMap<String, List<String>> mcqQuestions;

    public Quiz(String quiz_name,String instructorName){
        this.quiz_name = quiz_name;
        this.instructorName = instructorName;

        this.questions = new LinkedHashMap<>();
        this.questionCategories = new LinkedHashMap<>();
        this.mcqQuestions = new LinkedHashMap<>();
    }

    public String get_name(){
        return quiz_name;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void add_question(String question, String answer,String category){
        questions.put(question,answer);
        questionCategories.put(question, category); 
        System.out.println("Following question added in "+quiz_name+":- "+question);
    }

    public void edit_question(String question, String updated_answer){
        if(questions.containsKey(question)){
            questions.put(question, updated_answer);
            System.out.println("Following question edited in "+quiz_name+":- "+question);
        }
        else{
            System.out.println("No such question found");
        }
    }

    public void delete_question(String question){
        if(questions.containsKey(question)){
            questions.remove(question);
            System.out.println("Following question deleted in "+quiz_name+":- "+question);
        }
        else{
            System.out.println("No such question found in "+quiz_name);
        }
    }

    public void editMCQQuestion(String question, List<String> updatedOptions, int updatedCorrectOptionIndex) {
        if (mcqQuestions.containsKey(question)) {
            List<String> mutableOptions = new ArrayList<>(updatedOptions);  // Create a mutable copy
            mcqQuestions.put(question, mutableOptions);
            mcqQuestions.get(question).add(Integer.toString(updatedCorrectOptionIndex));
            System.out.println("Following MCQ question edited in " + quiz_name + ":- " + question);
        } else {
            System.out.println("No such MCQ question found");
        }
    }
    

    public void deleteMCQQuestion(String question) {
        if (mcqQuestions.containsKey(question)) {
            mcqQuestions.remove(question);
            System.out.println("Following MCQ question deleted in " + quiz_name + ":- " + question);
        } else {
            System.out.println("No such MCQ question found in " + quiz_name);
        }
    }

    public void randomizeQuestionsOrder() {
        List<String> questionList = new ArrayList<>(questions.keySet());
        Collections.shuffle(questionList);
        LinkedHashMap<String, String> randomizedQuestions = new LinkedHashMap<>();
        for (String question : questionList) {
            randomizedQuestions.put(question, questions.get(question));
        }

        questions = randomizedQuestions;
    }

    public LinkedHashMap<String, String> get_questions(){
        return questions;
    }
    public LinkedHashMap<String, String> get_questionCategories() {
        return questionCategories;
    }

    public void addMCQQuestion(String question, List<String> options, int correctOptionIndex) {
        if (correctOptionIndex >= 0 && correctOptionIndex < options.size()) {
            List<String> mcqOptions = new ArrayList<>(options); // Create a new ArrayList based on existing options
            mcqOptions.add(Integer.toString(correctOptionIndex));
            mcqQuestions.put(question, mcqOptions);
            System.out.println("Following MCQ question added in " + quiz_name + ":- " + question);
        } else {
            System.out.println("Error: Invalid correct option index.");
        }
    }
    

    public LinkedHashMap<String, List<String>> getMCQQuestions() {
        return mcqQuestions;
    }

} 
