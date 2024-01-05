package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom ;
	private Date datenaiss;
	private String email;
	private String password;
	private int telephone;

	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles ;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
	    name = "user_formation",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "formation_id")
	)
	private List<Formation> formations = new ArrayList<>();

    public User(String nom, String prenom, String datenaiss, Date email, String encode, int telephone) {
    }


    public List<Formation> getFormations() {
		return formations;
	}
	public void setFormations(List<Formation> formations) {
		this.formations = formations;
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
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDatenaiss() {
		return datenaiss;
	}
	public void setDatenaiss(Date datenaiss) {
		this.datenaiss = datenaiss;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTelephone() {
		return telephone;
	}
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	

	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", datenaiss=" + datenaiss + ", email="
				+ email + ", password=" + password + ", telephone=" + telephone + ", roles=" + roles + "]";
	}
	/*public User(Long id, String nom, String prenom, Date datenaiss, String email, String password, int telephone,
			String photo) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.datenaiss = datenaiss;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.photo = photo;
	}*/

	public User(String nom, String prenom,  Date datenaiss, String email, String password, int telephone
			) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.datenaiss = datenaiss;
		this.email = email;
		this.password = password;
		this.telephone = telephone;

	}
	public User() {
		super();
	}



}
