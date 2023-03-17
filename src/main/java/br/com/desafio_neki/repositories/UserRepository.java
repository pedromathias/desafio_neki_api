package br.com.desafio_neki.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio_neki.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	Optional<User> findByLogin(String login);
}