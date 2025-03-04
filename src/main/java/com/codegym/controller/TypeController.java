package com.codegym.controller;

import com.codegym.dto.TypeDTO;
import com.codegym.model.Type;
import com.codegym.model.Computer;
import com.codegym.service.ITypeService;
import com.codegym.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private ITypeService typeService;
    @Autowired
    private IComputerService computerService;

    @GetMapping("")
    public ModelAndView listTypes() {
        Iterable<Type> types = typeService.findAll();
        ModelAndView modelAndView = new ModelAndView("/type/list");
        modelAndView.addObject("types", types);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createTypeForm() {
        ModelAndView modelAndView = new ModelAndView("/type/create");
        modelAndView.addObject("typeDTO", new TypeDTO());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createType(@ModelAttribute("typeDTO") TypeDTO typeDTO,
                             RedirectAttributes redirectAttributes) {
        Type type = new Type();
        type.setId(typeDTO.getId());
        type.setName(typeDTO.getName());
        typeService.save(type);
        redirectAttributes.addFlashAttribute("message", "Created type successfully");
        return "redirect:/type";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateTypeForm(@PathVariable Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (typeOptional.isPresent()) {
            Type type = typeOptional.get();
            TypeDTO typeDTO = new TypeDTO();
            typeDTO.setId(type.getId());
            typeDTO.setName(type.getName());
            ModelAndView modelAndView = new ModelAndView("/type/update");
            modelAndView.addObject("typeDTO", typeDTO);
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String updateType(@PathVariable Long id,
                             @ModelAttribute("typeDTO") TypeDTO typeDTO,
                             RedirectAttributes redirectAttributes) {
        Type type = new Type();
        type.setId(id);
        type.setName(typeDTO.getName());
        typeService.save(type);
        redirectAttributes.addFlashAttribute("message", "Updated type successfully");
        return "redirect:/type";
    }

    @GetMapping("/view-type/{id}")
    public ModelAndView viewType(@PathVariable("id") Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (!typeOptional.isPresent()) {
            return new ModelAndView("/error_404");
        }
        Iterable<Computer> computers = computerService.findAllByType(typeOptional.get());
        ModelAndView modelAndView = new ModelAndView("/computer/list");
        modelAndView.addObject("computers", computers);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (typeOptional.isPresent()) {
            typeService.remove(id);
            return "redirect:/type";
        }
        return "redirect:/error_404";
    }
}
