package t1708e.assigment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import t1708e.assigment.entity.AptechClass;
import t1708e.assigment.entity.Student;
import t1708e.assigment.repository.AptechClassRepository;
import t1708e.assigment.repository.StudentRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AptechClassRepository aptechClassRepository;

    @GetMapping
    public String details(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginUserEmail = authentication.getName();
        Student loggedInUser = studentRepository.findByEmail(currentLoginUserEmail).orElse(null);
        List<AptechClass> aptechClass = aptechClassRepository.findAll();

        model.addAttribute("student", loggedInUser);
        model.addAttribute("aptechClass", aptechClass);
        return "detail";
    }

    @GetMapping(value = "/register")
    public String registerForm(Model model){
        Student newStudent = new Student();
        model.addAttribute("student",newStudent);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "register";
        }
        String oldPass = student.getPassword();
        student.setPassword(passwordEncoder.encode(oldPass));
        studentRepository.save(student);
        return "redirect:/";
    }
}
