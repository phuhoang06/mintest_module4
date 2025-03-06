package com.codegym.controller;

import com.codegym.dto.ComputerDTO;
import com.codegym.model.Computer;
import com.codegym.model.Type;
import com.codegym.service.IComputerService;
import com.codegym.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/computers")
public class ComputerRestController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ITypeService typeService;

    @GetMapping("")
    public ResponseEntity<Page<Computer>> listComputers(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Computer> computers = computerService.findAll(pageable);
        return ResponseEntity.ok(computers);
    }

    @PostMapping("")
    public ResponseEntity<?> createComputer(@RequestBody @Valid ComputerDTO computerDTO) {
        Optional<Type> typeOptional = typeService.findById(computerDTO.getTypeId());
        if (!typeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid typeId provided");
        }
        Computer computer = new Computer();
        computer.setCode(computerDTO.getCode());
        computer.setName(computerDTO.getName());
        computer.setProducer(computerDTO.getProducer());
        computer.setComputerType(typeOptional.get());
        computerService.save(computer);
        return ResponseEntity.status(HttpStatus.CREATED).body(computer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComputer(@PathVariable Long id,
                                            @RequestBody @Valid ComputerDTO computerDTO) {
        Optional<Computer> computerOptional = computerService.findById(id);
        if (!computerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Computer not found");
        }
        Optional<Type> typeOptional = typeService.findById(computerDTO.getTypeId());
        if (!typeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid typeId provided");
        }
        Computer computer = computerOptional.get();
        computer.setCode(computerDTO.getCode());
        computer.setName(computerDTO.getName());
        computer.setProducer(computerDTO.getProducer());
        computer.setComputerType(typeOptional.get());
        computerService.save(computer);
        return ResponseEntity.ok(computer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComputer(@PathVariable Long id) {
        Optional<Computer> computerOptional = computerService.findById(id);
        if (!computerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Computer not found");
        }
        computerService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
