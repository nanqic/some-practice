package me.nanqic.raffle.controller;

import lombok.extern.slf4j.Slf4j;
import me.nanqic.raffle.model.Raffle;
import me.nanqic.raffle.model.vo.Result;
import me.nanqic.raffle.service.RaffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RaffleController {
   @Autowired
   private RaffleService service;
   @GetMapping("/api/list")
   public Result getPage(@RequestParam int page, @RequestParam int limit ,
                         @RequestParam(required = false) String biliName ){
      if(biliName == null || biliName.isEmpty()){
         return service.getPage(--page, limit);
      }
       return  service.getSearchPage(--page, limit,biliName);
   }

   @PostMapping("/api/save")
   public String save(@RequestBody Raffle raffle){ return service.save(raffle);}

   @DeleteMapping("/api/del")
   public String delete(@RequestParam int id){return service.delete(id);}
}
