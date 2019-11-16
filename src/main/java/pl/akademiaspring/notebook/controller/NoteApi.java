package pl.akademiaspring.notebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.akademiaspring.notebook.model.Note;
import pl.akademiaspring.notebook.repo.NoteRepository;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/notes/")
public class NoteApi {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteApi(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("note", new Note());
        return "add-note";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("notes", noteRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addNote(@Valid Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-note";
        }
        note.setDate(getActualDate());
        noteRepository.save(note);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Note note = noteRepository.findById(id);

        model.addAttribute("note", note);
        return "update-note";
    }

    @PostMapping("update/{id}")
    public String updateNote(@PathVariable("id") long id, @Valid Note note, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            note.setId(id);
            return "update-note";
        }

        noteRepository.save(note);
        model.addAttribute("notes", noteRepository.findAll());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Note note = noteRepository.findById(id);
        noteRepository.delete(note);
        model.addAttribute("notes", noteRepository.findAll());
        return "index";
    }

    public String getActualDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}