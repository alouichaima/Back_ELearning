package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Formation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String Description;
	@Temporal(TemporalType.DATE)
	private Date datecreation;
	private String image;
	
	


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "formation")
	@JsonManagedReference
	private List<User> user;
	
	
	

	
	public Formation(Long id, String nom, String description, Date datecreation, String image, List<User> user) {
		super();
		this.id = id;
		this.nom = nom;
		this.Description = description;
		this.datecreation = datecreation;
		this.image = image;
		this.user = user;
	}
	
	
	public Formation() {
		super();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Date getDatecreation() {
		return datecreation;
	}
	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Formation [id=" + id + ", nom=" + nom + ", Description=" + Description + ", datecreation="
				+ datecreation + ", image=" + image + "]";
	}
	
	

}
