package com.enrollment.service.impl;

import com.enrollment.model.Curso;
import com.enrollment.repo.ICursoRepo;
import com.enrollment.repo.IGenericRepo;
import com.enrollment.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoServiceImpl extends CRUDImpl<Curso, Integer> implements ICursoService {

    @Autowired
    private ICursoRepo repo;

    @Override
    protected IGenericRepo<Curso, Integer> getRepo() {
        return repo;
    }
}
