package com.nghj.controller;

import com.nghj.entity.Book;
import com.nghj.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookHandler {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/find/{page}/{size}")
    public Page<Book> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return bookRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Book book){
        Book result = bookRepository.save(book);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }
    @GetMapping("/findById/{id}")
    public Book findById(@PathVariable("id") Integer id){
        Book book = bookRepository.findById(id).get();
        return book;
    }
    @PutMapping("/update")
    public String update(@RequestBody Book book){
        Book result = bookRepository.save(book);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }
    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable("id") Integer id){
        bookRepository.deleteById(id);
    }
}
