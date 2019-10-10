package t1708e.assigment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t1708e.assigment.entity.AptechClass;
import t1708e.assigment.entity.Student;
import t1708e.assigment.repository.AptechClassRepository;
import t1708e.assigment.repository.StudentRepository;

import java.util.List;

@Controller
@RequestMapping(value = "/class")
public class AptechClassController {

    @Autowired
    AptechClassRepository aptechClassRepository;

    @Autowired
    StudentRepository studentRepository;

    private final AptechClassRepository classRepository;

    public AptechClassController(AptechClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        if(classRepository.findAll().size() <= 0){
            classRepository.save(new AptechClass("T1708E"));
            classRepository.save(new AptechClass("T1707A"));
            classRepository.save(new AptechClass("AB1707M"));
            classRepository.save(new AptechClass("T1805A"));
        }
    }

    @GetMapping()
    public String list(Model model){
        List<AptechClass> aptechClass = aptechClassRepository.findAll();
        model.addAttribute("aptechClass", aptechClass);
        return "AptechClass/list";
    }

    @GetMapping(value = "/{id}")
    public String list(@PathVariable int id, Model model){
        AptechClass aptechClass = aptechClassRepository.findById(id).orElse(null);
        List<Student> students = studentRepository.findAll();
        if(aptechClass == null){
            return "error/404";
        }
        model.addAttribute("aptechClass", aptechClass);
        model.addAttribute("students", students);
        return "AptechClass/detail";
    }

    public String update(@PathVariable int id, @RequestParam(value = "student") int[] student){
        AptechClass aptechClass = aptechClassRepository.findById(id).orElse(null);
        for (int sid: student
        ) {
            Student student1 = studentRepository.findById(sid).orElse(null);
            aptechClass.addStudent(student1);
        }
        aptechClassRepository.save(aptechClass);
        return "redirect:/class";
    }
}
