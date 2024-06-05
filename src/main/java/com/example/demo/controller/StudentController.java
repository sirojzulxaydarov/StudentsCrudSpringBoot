package com.example.demo.controller;

import com.example.demo.dto.StudentReq;
import com.example.demo.entity.Student;
import com.example.demo.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/student")
@Controller
public class StudentController {

    private final StudentRepo studentRepo;

    @GetMapping
    public String student(Model model) {
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "student";
    }

    @GetMapping("/create")
    public String studentCreatePage() {
        return "create";
    }

    @GetMapping("/update")
    public String studentUpdatePage(@RequestParam Integer id, Model model) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        Student student = studentOptional.get();
        model.addAttribute("student", student);
        return "create";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@ModelAttribute StudentReq studentReq, @PathVariable Integer id) {
        Student student = Student.builder()
                .firstName(studentReq.firstName())
                .lastName(studentReq.lastName())
                .age(studentReq.age())
                .id(id)
                .build();
        studentRepo.save(student);
        return "redirect:/student";
    }


    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        studentRepo.deleteById(id);
        return "redirect:/student";

    }

    @PostMapping
    public String save(@ModelAttribute StudentReq studentReq) {
        Student student = Student.builder()
                .firstName(studentReq.firstName())
                .lastName(studentReq.lastName())
                .age(studentReq.age())
                .build();
        studentRepo.save(student);
        return "redirect:/student";
    }


}
