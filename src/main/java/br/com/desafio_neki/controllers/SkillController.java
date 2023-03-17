package br.com.desafio_neki.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafio_neki.models.Skill;
import br.com.desafio_neki.servicies.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {
    @Autowired
	SkillService skillService;

    @GetMapping
	public ResponseEntity<List<Skill>> getAll(){
		List<Skill> skills = skillService.getAll();
		if(!skills.isEmpty())
			return new ResponseEntity<>(skills, HttpStatus.OK);
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

    @GetMapping("/{id}")
	public ResponseEntity<Skill> getById(@PathVariable Integer id) {
		Skill skill = skillService.getById(id);
		if(skill != null)
			return new ResponseEntity<>(skill, HttpStatus.OK); 
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);		
	}

    // @PostMapping
	// public ResponseEntity<Skill> saveSkill(@RequestBody Skill skill) {
	// 	return new ResponseEntity<>(skillService.saveSkill(skill), HttpStatus.CREATED);
	// }

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Skill> saveSkill(@RequestPart ("skill") String skill,
	@RequestPart ("source") MultipartFile file) throws IOException{
		Skill novoSkill = skillService.saveSkill(skill, file);
		if(null == novoSkill)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(novoSkill, HttpStatus.CREATED);
	}


    @PutMapping("/{id}")
	public ResponseEntity<Skill> updateSkill(@PathVariable Integer id, @RequestBody Skill skill) {
		Skill skillAtualizada = skillService.updateSkill(id, skill);
		if(skillAtualizada != null)
			return new ResponseEntity<>(skillAtualizada, HttpStatus.OK); 
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

    @DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteSkill(@PathVariable Integer id) {
		if(skillService.deleteSkill(id))
			return new ResponseEntity<>(true, HttpStatus.OK);
		else 
			return new ResponseEntity<>(false, HttpStatus.OK);
	}
}
