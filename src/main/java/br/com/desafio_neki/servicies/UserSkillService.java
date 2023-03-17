package br.com.desafio_neki.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio_neki.models.UserSkill;
import br.com.desafio_neki.repositories.UserSkillRepository;

@Service
public class UserSkillService {
    @Autowired
	UserSkillRepository userSkillRepository;

    public List<UserSkill> getAll(){
		return userSkillRepository.findAll();
	}

    public UserSkill getById(Integer id) {
		return userSkillRepository.findById(id).orElse(null) ;
	}

    public UserSkill saveUserSkill(UserSkill userSkill) {
		return userSkillRepository.save(userSkill);
	}

    public UserSkill updateUserSkill(Integer id, UserSkill userSkill) {
		UserSkill userSkillAtualizado = userSkillRepository.findById(id).orElse(null);
		if(userSkillAtualizado != null) {
            userSkillAtualizado.setUser(userSkill.getUser());
            userSkillAtualizado.setSkill(userSkill.getSkill());
            userSkillAtualizado.setKnowledgeLevel(userSkill.getKnowledgeLevel());
            userSkillAtualizado.setUpdatedAt(userSkill.getUpdatedAt());
			return userSkillRepository.save(userSkillAtualizado);
		}else {
			return null;
		}
	}

    public Boolean deleteUserSkill(Integer id) {
		UserSkill userSkill = userSkillRepository.findById(id).orElse(null);
		if(userSkill != null) {
			userSkillRepository.delete(userSkill);
			return true;
		}else {
			return false;
		}
	}
}
