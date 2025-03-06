package com.codegym.controller;

import com.codegym.dto.TypeDTO;
import com.codegym.model.Type;
import com.codegym.model.Computer;
import com.codegym.service.ITypeService;
import com.codegym.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/types")
public class TypeRestController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IComputerService computerService;

    @GetMapping("")
    public ResponseEntity<Iterable<Type>> listTypes() {
        Iterable<Type> types = typeService.findAll();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (!typeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Type not found");
        }
        return ResponseEntity.ok(typeOptional.get());
    }

    @PostMapping("")
    public ResponseEntity<Type> createType(@RequestBody @Valid TypeDTO typeDTO) {
        Type type = new Type();
        type.setName(typeDTO.getName());
        typeService.save(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateType(@PathVariable Long id,
                                        @RequestBody @Valid TypeDTO typeDTO) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (!typeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Type not found");
        }
        Type type = typeOptional.get();
        type.setName(typeDTO.getName());
        typeService.save(type);
        return ResponseEntity.ok(type);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteType(@PathVariable Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (!typeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Type not found");
        }
        typeService.remove(id);
        return ResponseEntity.noContent().build();
    }

    // Thêm endpoint tùy chọn: Lấy danh sách máy tính theo Type
    @GetMapping("/{id}/computers")
    public ResponseEntity<?> getComputersByType(@PathVariable Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (!typeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Type not found");
        }
        Iterable<Computer> computers = computerService.findAllByType(typeOptional.get());
        return ResponseEntity.ok(computers);
    }
}
