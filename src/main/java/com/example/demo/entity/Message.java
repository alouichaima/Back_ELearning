package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idM")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idM;
	private String objet;
	private String contenu;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	@JsonManagedReference
	private Apprenant apprenant;


	public Message() {
		super();
	}


	public Message(Long idM, String objet, String contenu, Apprenant apprenant) {
		super();
		this.idM = idM;
		this.objet = objet;
		this.contenu = contenu;
		this.apprenant = apprenant;
	}


	public Long getIdM() {
		return idM;
	}


	public void setIdM(Long idM) {
		this.idM = idM;
	}


	public String getObjet() {
		return objet;
	}


	public void setObjet(String objet) {
		this.objet = objet;
	}


	public String getContenu() {
		return contenu;
	}


	public void setContenu(String contenu) {
		this.contenu = contenu;
	}


	public Apprenant getApprenant() {
		return apprenant;
	}


	public void setApprenant(Apprenant apprenant) {
		this.apprenant = apprenant;
	}


	@Override
	public String toString() {
		return "Message [idM=" + idM + ", objet=" + objet + ", contenu=" + contenu + ", apprenant=" + apprenant + "]";
	}
	
	


	
	

}
