package me.nanqic.raffle.service;

import me.nanqic.raffle.model.Raffle;
import me.nanqic.raffle.model.vo.Result;
import me.nanqic.raffle.repository.RaffleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaffleService {
    @Autowired
    private RaffleRepository repository;

    public Result getPage(int index, int size){
        Page<Raffle> page = repository.findAll(PageRequest.of(index,size));
        Long count = page.getTotalElements();

        return Result.success(page.getContent(), count);
    }

    public Result getSearchPage(int index, int size, String biliName){
        Page<Raffle> page = repository
                .findByBiliNameLike('%'+biliName+'%',PageRequest.of(index,size));
        Long count = page.getTotalElements();

        return Result.success(page.getContent(), count);
    }

    public String save(Raffle raffle){
        repository.save(raffle);
        return "ok";

    }

    public String delete(int id){
        repository.deleteById(id);
        return "ok";
     }
}
