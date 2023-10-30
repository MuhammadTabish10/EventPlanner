package com.EventPlanner.service.impl;

import com.EventPlanner.dto.QuestionsDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.Questions;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.QuestionsRepository;
import com.EventPlanner.service.QuestionsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final AccountRepository accountRepository;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository, AccountRepository accountRepository) {
        this.questionsRepository = questionsRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    @Transactional
    public QuestionsDto save(QuestionsDto questionsDto) {
        Questions questions = toEntity(questionsDto);
        questions.setStatus(true);

        Account account = accountRepository.findById(questions.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", questions.getAccount().getId())));

        questions.setAccount(account);
        Questions createdQuestions = questionsRepository.save(questions);
        return toDto(createdQuestions);
    }

    @Override
    public List<QuestionsDto> getAll() {
        List<Questions> questionList = questionsRepository.findAllInDesOrderByIdAndStatus();
        List<QuestionsDto> questionsDtoList = new ArrayList<>();

        for (Questions questions : questionList) {
            QuestionsDto questionsDto = toDto(questions);
            questionsDtoList.add(questionsDto);
        }
        return questionsDtoList;
    }

    @Override
    public QuestionsDto findById(Long id) {
        Questions questions = questionsRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Questions not found for id => %d", id)));
        return toDto(questions);
    }

    @Override
    public QuestionsDto findByQuestion(String question) {
        Questions questions = questionsRepository.findByQuestion(question)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Questions not found for name => %s", question)));
        return toDto(questions);
    }

    @Override
    public List<QuestionsDto> searchByQuestion(String question) {
        List<Questions> questionList = questionsRepository.findQuestionsByQuestion(question);
        List<QuestionsDto> questionsDtoList = new ArrayList<>();

        for (Questions questions : questionList) {
            QuestionsDto questionsDto = toDto(questions);
            questionsDtoList.add(questionsDto);
        }
        return questionsDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Questions questions = questionsRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Questions not found for id => %d", id)));
        questionsRepository.setStatusInactive(questions.getId());
    }

    @Override
    @Transactional
    public QuestionsDto update(Long id, QuestionsDto questionsDto) {
        Questions existingQuestions = questionsRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Questions not found for id => %d", id)));

        existingQuestions.setQuestion(questionsDto.getQuestion());
        existingQuestions.setDescription(questionsDto.getDescription());
        existingQuestions.setType(questionsDto.getType());
        existingQuestions.setAppliesToOurAccount(questionsDto.getAppliesToOurAccount());
        existingQuestions.setIsSystem(questionsDto.getIsSystem());
        existingQuestions.setStatus(questionsDto.getStatus());

        existingQuestions.setAccount(accountRepository.findById(questionsDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", questionsDto.getAccount().getId()))));

        Questions updatedQuestions = questionsRepository.save(existingQuestions);
        return toDto(updatedQuestions);
    }

    public QuestionsDto toDto(Questions questions) {
        return QuestionsDto.builder()
                .id(questions.getId())
                .question(questions.getQuestion())
                .description(questions.getDescription())
                .type(questions.getType())
                .appliesToOurAccount(questions.getAppliesToOurAccount())
                .isSystem(questions.getIsSystem())
                .status(questions.getStatus())
                .account(questions.getAccount())
                .build();
    }

    public Questions toEntity(QuestionsDto questionsDto) {
        return Questions.builder()
                .id(questionsDto.getId())
                .question(questionsDto.getQuestion())
                .description(questionsDto.getDescription())
                .type(questionsDto.getType())
                .appliesToOurAccount(questionsDto.getAppliesToOurAccount())
                .isSystem(questionsDto.getIsSystem())
                .status(questionsDto.getStatus())
                .account(questionsDto.getAccount())
                .build();
    }

}
