package br.com.desafio_neki.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// @JsonIdentityInfo(
//     generator = ObjectIdGenerators.PropertyGenerator.class,
//     property = "skillId"
// )
@Entity
@Table(name = "skill")
public class Skill {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer skillId;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image;


    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    
    public String getSkillName() {
        return name;
    }

    public void setSkillName(String name) {
        this.name = name;
    }

    public String getSkillVersion() {
        return version;
    }

    public void setSkillVersion(String version) {
        this.version = version;
    }

    public String getSkillDescription() {
        return description;
    }

    public void setSkillDescription(String description) {
        this.description = description;
    }

    public String getSkillImage() {
        return image;
    }

    public void setSkillImage(String image) {
        this.image = image;
    }
}
