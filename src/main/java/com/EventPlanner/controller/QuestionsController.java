package com.EventPlanner.controller;

import com.EventPlanner.dto.ProvinceDto;
import com.EventPlanner.dto.QuestionsDto;
import com.EventPlanner.service.QuestionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionsController {
    private final QuestionsService questionsService;

    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @PostMapping("/questions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestionsDto> createQuestions(@Valid @RequestBody QuestionsDto questionsDto) {
        return ResponseEntity.ok(questionsService.save(questionsDto));
    }

    @GetMapping("/questions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestionsDto>> getAllQuestions() {
        List<QuestionsDto> questionsDtoList = questionsService.getAll();
        return ResponseEntity.ok(questionsDtoList);
    }

    @GetMapping("/questions/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestionsDto> getQuestionsById(@PathVariable Long id) {
        QuestionsDto questionsDto = questionsService.findById(id);
        return ResponseEntity.ok(questionsDto);
    }

    @GetMapping("/questions/{question}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestionsDto> getQuestionsByQuestion(@PathVariable String question) {
        QuestionsDto questionsDto = questionsService.findByQuestion(question);
        return ResponseEntity.ok(questionsDto);
    }

    @GetMapping("/questions/questions/{question}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestionsDto>> getAllQuestionsByQuestion(@PathVariable String question) {
        List<QuestionsDto> questionsDtoList = questionsService.searchByQuestion(question);
        return ResponseEntity.ok(questionsDtoList);
    }

    @DeleteMapping("/questions/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteQuestions(@PathVariable Long id) {
        questionsService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/questions/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestionsDto> updateQuestions(@PathVariable Long id, @Valid @RequestBody QuestionsDto questionsDto) {
        QuestionsDto updatedQuestionsDto = questionsService.update(id, questionsDto);
        return ResponseEntity.ok(updatedQuestionsDto);
    }
}
