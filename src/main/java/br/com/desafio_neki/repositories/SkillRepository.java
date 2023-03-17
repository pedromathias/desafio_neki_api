package br.com.desafio_neki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio_neki.models.Skill;

public interface SkillRepository extends JpaRepository<Skill,Integer> {
    
}
