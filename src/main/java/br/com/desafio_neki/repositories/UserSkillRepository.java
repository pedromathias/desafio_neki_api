package br.com.desafio_neki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio_neki.models.UserSkill;

public interface UserSkillRepository extends JpaRepository<UserSkill,Integer> {
    
}
