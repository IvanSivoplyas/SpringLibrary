package ru.silvan.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.silvan.springcourse.dao.BookDAO;
import ru.silvan.springcourse.dao.PersonDAO;
import ru.silvan.springcourse.models.Book;
import ru.silvan.springcourse.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
     private final PersonDAO personDAO;
     private final BookDAO bookDAO;

    public BookController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id,
                       Model model,
                       @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(book_id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(book_id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id") int book_id){
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("book_id") int book_id){
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id){
        bookDAO.delete(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String release(@PathVariable("book_id") int book_id){
        bookDAO.release(book_id);
        return "redirect:/books/{book_id}";
    }

    @PatchMapping("/{book_id}/assign")
    public String assign(@PathVariable("book_id") int book_id,
                         @ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(book_id, selectedPerson);
        return "redirect:/books/{book_id}";
    }
}
