package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.QuestionsDto;

import java.util.List;

public interface QuestionsService {
    QuestionsDto save(QuestionsDto questionsDto);
    List<QuestionsDto> getAll();
    PaginationResponse getAllPaginatedQuestion(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByQuestion(String question, Integer pageNumber, Integer pageSize);
    QuestionsDto findById(Long id);
    QuestionsDto findByQuestion(String question);
    void deleteById(Long id);
    QuestionsDto update(Long id, QuestionsDto questionsDto);
}
