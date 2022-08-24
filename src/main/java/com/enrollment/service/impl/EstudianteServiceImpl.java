package com.enrollment.service.impl;

import com.enrollment.model.Estudiante;
import com.enrollment.repo.IEstudianteRepo;
import com.enrollment.repo.IGenericRepo;
import com.enrollment.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, Integer> implements IEstudianteService {

    @Autowired
    private IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Estudiante> getStudentDescAge() throws Exception {
        return getRepo().findAll()
                .stream().sorted(Comparator.comparing(Estudiante::getEdad).reversed())
                .collect(Collectors.toList());
    }
}
