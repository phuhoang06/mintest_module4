package com.codegym.service.computer;

import com.codegym.model.Computer;
import com.codegym.model.Type;
import com.codegym.repository.IComputerRepository;
import com.codegym.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComputerService implements IComputerService {

    @Autowired
    private IComputerRepository computerRepository;

    @Override
    public Iterable<Computer> findAll() {
        return computerRepository.findAll();
    }
    @Override
    public void save(Computer computer) {
        computerRepository.save(computer);
    }
    @Override
    public Optional<Computer> findById(Long id) {
        return computerRepository.findById(id);
    }
    @Override
    public void remove(Long id) {
        computerRepository.deleteById(id);
    }
    @Override
    public Iterable<Computer> findAllByType(Type type) {
        return computerRepository.findAllByComputerType(type);
    }
    @Override
    public Page<Computer> findAll(Pageable pageable) {
        return computerRepository.findAll(pageable);
    }
    @Override
    public Page<Computer> findAllByNameContaining(Pageable pageable, String name) {
        return computerRepository.findAllByNameContaining(name, pageable);
    }
    @Override
    public Page<Computer> findAllByName(Pageable pageable, String name) {
        // Có thể triển khai theo yêu cầu. Ở đây dùng cùng hàm tìm kiếm
        return computerRepository.findAllByNameContaining(name, pageable);
    }
}
