package com.example.demo.service;

import com.example.demo.entity.Apprenant;
import com.example.demo.repository.ApprenantRepository;

import java.util.List;
import java.util.Optional;

public interface ApprenantService {
    public List<Apprenant> getAllApprenant();
    public Apprenant findApprenantById (Long id);
    public Apprenant createApprenant( Apprenant apprenant);
    public Apprenant updateApprenant( Apprenant apprenant);
    public void deleteApprenant( long id);


    Apprenant getProfilApprenantById(String id);
}
