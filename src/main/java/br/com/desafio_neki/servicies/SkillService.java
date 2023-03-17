package br.com.desafio_neki.servicies;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.desafio_neki.dto.imgbb.ImgBBDTO;
import br.com.desafio_neki.models.Skill;
import br.com.desafio_neki.repositories.SkillRepository;

@Service
public class SkillService {
    @Autowired
	SkillRepository skillRepository;

	@Value("${imgbb.host.url}")
	private String imgBBHostUrl;
	
	@Value("${imgbb.host.key}")
    private String imgBBHostKey;

    public List<Skill> getAll(){
		return skillRepository.findAll();
	}

    public Skill getById(Integer id) {
		return skillRepository.findById(id).orElse(null) ;
	}

    // public Skill saveSkill(Skill skill) {
	// 	return skillRepository.save(skill);
	// }

	public Skill saveSkill(
		String skill,
		MultipartFile file
		) throws IOException {
			RestTemplate restTemplate = new RestTemplate();
			String serverUrl = imgBBHostUrl + imgBBHostKey;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
			
			ContentDisposition contentDisposition = ContentDisposition
					.builder("form-data")
					.name("image")
					.filename(file.getOriginalFilename())
					.build();
			
			fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
			
			HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
			
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("image", fileEntity);
			
			HttpEntity<MultiValueMap<String, Object>> requestEntity =
					new HttpEntity<>(body, headers);
			
			ResponseEntity<ImgBBDTO> response = null;
			ImgBBDTO imgDTO = new ImgBBDTO();
			// Editora novaEditora = new Editora();
			Skill novaSkill = new Skill(); 
			try {
				response = restTemplate.exchange(
						serverUrl,
						HttpMethod.POST,
						requestEntity,
						ImgBBDTO.class);
				
				imgDTO = response.getBody();
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
			}
			
			if(null != imgDTO) {
				
				// Skill skillFromJson = new Skill();
				Skill skillFromJson = convertSkillFromStringJson(skill);
				
				skillFromJson.setSkillImage(imgDTO.getData().getUrl());
				novaSkill = skillRepository.save(skillFromJson);
				
			}
			return novaSkill;
	}

	private Skill convertSkillFromStringJson(String skillJson) {
		Skill novoSkill = new Skill();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			objectMapper.registerModule(new JavaTimeModule());
			novoSkill = objectMapper.readValue(skillJson, Skill.class);
		} catch (IOException err) {
			System.out.printf("Ocorreu um erro ao tentar converter a string json para um instância do DTO Editora", err.toString());
		}
		
		return novoSkill;
	}

    public Skill updateSkill(Integer id, Skill skill) {
		Skill skillAtualizado = skillRepository.findById(id).orElse(null);
		if(skillAtualizado != null) {
            skillAtualizado.setSkillName(skill.getSkillName());
            skillAtualizado.setSkillVersion(skill.getSkillVersion());
            skillAtualizado.setSkillDescription(skill.getSkillDescription());
            skillAtualizado.setSkillImage(skill.getSkillImage());
			return skillRepository.save(skillAtualizado);
		}else {
			return null;
		}
	}

    public Boolean deleteSkill(Integer id) {
		Skill skill = skillRepository.findById(id).orElse(null);
		if(skill != null) {
			skillRepository.delete(skill);
			return true;
		}else {
			return false;
		}
	}
}
