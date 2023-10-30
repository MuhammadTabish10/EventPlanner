package com.EventPlanner.service;

import com.EventPlanner.dto.QuestionsDto;

import java.util.List;

public interface QuestionsService {
    QuestionsDto save(QuestionsDto questionsDto);
    List<QuestionsDto> getAll();
    QuestionsDto findById(Long id);
    QuestionsDto findByQuestion(String question);
    List<QuestionsDto> searchByQuestion(String question);
    void deleteById(Long id);
    QuestionsDto update(Long id, QuestionsDto questionsDto);
}
