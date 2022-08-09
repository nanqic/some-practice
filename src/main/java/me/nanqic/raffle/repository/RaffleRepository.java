package me.nanqic.raffle.repository;

import me.nanqic.raffle.model.Raffle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaffleRepository extends JpaRepository<Raffle, Integer> {
    Page<Raffle> findByBiliNameLike(String biliName, Pageable pageable);
}