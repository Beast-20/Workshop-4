package com.example;

import java.util.ArrayList;
import java.util.List;

class Participant {
    String name;
    int score = 0;
    List<String> correct_questions;
    List<String> incorrect_questions;
    List<String> not_attempted_questions;
    public Participant(String name){
        this.name = name;
        this.correct_questions = new ArrayList<>();
        this.incorrect_questions = new ArrayList<>();
        this.not_attempted_questions = new ArrayList<>();
    }
 
    public String get_name(){
        return name;
    }

    public void add_to_correct(String question){
        correct_questions.add(question);
    }

    public void add_to_incorrect(String question){
        incorrect_questions.add(question);
    }
    public void add_to_not_attempted(String question){
        not_attempted_questions.add(question);
    }
    
    public void set_score(int score){
        this.score = score;
    }
    
    public int get_score(){
        return score;
    }

    public void get_result(){
        System.out.println("Score of "+name+" is:-"+score);
        System.out.println("Correctly answered questions are:-");
        for(int i = 0;i<correct_questions.size();i++){
            System.out.println(correct_questions.get(i));
        }
        System.out.println("----------------------------");
        System.out.println("Incorrectly answered questions are:- ");
        for(int i = 0;i<incorrect_questions.size();i++){
            System.out.println(incorrect_questions.get(i));
        }
        System.out.println("----------------------------");
        System.out.println("Not attempted questions are:- ");
        for(int i = 0;i<not_attempted_questions.size();i++){
            System.out.println(not_attempted_questions.get(i));
        }
    }

    public List<String> get_correct_questions(){
        return correct_questions;
    }

    public List<String> get_incorrect_questions(){
        return incorrect_questions;
    }
}
