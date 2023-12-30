package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Formation;


public interface FormationService {
	 public List<Formation> getAllFormation();
	    public Formation findFormationById (Long id);
	    public Formation createFormation( Formation formation);
		public Formation updateFormation( Formation formation);
	    public void deleteFormation( long id);
}
