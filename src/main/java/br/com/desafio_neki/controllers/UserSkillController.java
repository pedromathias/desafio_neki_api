package br.com.desafio_neki.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio_neki.models.UserSkill;
import br.com.desafio_neki.servicies.UserSkillService;

@RestController
@RequestMapping("/user_skill")
public class UserSkillController {
    @Autowired
	UserSkillService userSkillService;

    @GetMapping
	public ResponseEntity<List<UserSkill>> getAll(){
		List<UserSkill> userSkills = userSkillService.getAll();
		if(!userSkills.isEmpty())
			return new ResponseEntity<>(userSkills, HttpStatus.OK);
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

    @GetMapping("/{id}")
	public ResponseEntity<UserSkill> getById(@PathVariable Integer id) {
		UserSkill userSkill = userSkillService.getById(id);
		if(userSkill != null)
			return new ResponseEntity<>(userSkill, HttpStatus.OK); 
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);		
	}

    @PostMapping
	public ResponseEntity<UserSkill> saveUserSkill(@RequestBody UserSkill userSkill) {
		return new ResponseEntity<>(userSkillService.saveUserSkill(userSkill), HttpStatus.CREATED);
	}

    @PutMapping("/{id}")
	public ResponseEntity<UserSkill> updateUserSkill(@PathVariable Integer id, @RequestBody UserSkill userSkill) {
		UserSkill userSkillAtualizada = userSkillService.updateUserSkill(id, userSkill);
		if(userSkillAtualizada != null)
			return new ResponseEntity<>(userSkillAtualizada, HttpStatus.OK); 
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

    @DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUserSkill(@PathVariable Integer id) {
		if(userSkillService.deleteUserSkill(id))
			return new ResponseEntity<>(true, HttpStatus.OK);
		else 
			return new ResponseEntity<>(false, HttpStatus.OK);
	}
}
