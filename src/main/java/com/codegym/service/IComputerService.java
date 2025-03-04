package com.codegym.service;

import com.codegym.model.Computer;
import com.codegym.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComputerService extends IGenerateService<Computer> {
    Iterable<Computer> findAllByType(Type type);
    Page<Computer> findAll(Pageable pageable);
    Page<Computer> findAllByNameContaining(Pageable pageable, String name);
    Page<Computer> findAllByName(Pageable pageable, String name);
}
