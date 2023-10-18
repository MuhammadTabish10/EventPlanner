package com.EventPlanner.service;

import com.EventPlanner.dto.SubAccountDto;
import com.EventPlanner.model.SubAccountPK;

import java.util.List;

public interface SubAccountService {
    SubAccountDto save(SubAccountDto subAccountDto);
    List<SubAccountDto> getAll();
    SubAccountDto findById(SubAccountPK id);
    SubAccountDto findByName(String name);
    List<SubAccountDto> searchByName(String name);
    void deleteById(SubAccountPK id);
    SubAccountDto update(SubAccountPK id, SubAccountDto subAccountDto);
}
