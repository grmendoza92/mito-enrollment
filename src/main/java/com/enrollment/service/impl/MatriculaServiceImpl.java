package com.enrollment.service.impl;

import com.enrollment.model.DetalleMatricula;
import com.enrollment.model.Estudiante;
import com.enrollment.model.Matricula;
import com.enrollment.repo.IGenericRepo;
import com.enrollment.repo.IMatriculaRepo;
import com.enrollment.service.IMatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
public class MatriculaServiceImpl extends CRUDImpl<Matricula, Integer> implements IMatriculaService {

    @Autowired
    private IMatriculaRepo repo;

    @Override
    protected IGenericRepo<Matricula, Integer> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public Matricula saveTransactional(Matricula matricula, List<DetalleMatricula> listDetails) {
        listDetails.forEach(d -> d.setMatricula(matricula));
        matricula.setDetalleMatricula(listDetails);
        return repo.save(matricula);
    }

    @Override
    public Map<String, Long> getRelationCourseStudent() throws Exception {

        Stream<List<DetalleMatricula>> stream = repo.findAll().stream().map(Matricula::getDetalleMatricula);
        Stream<DetalleMatricula> streamDetail = stream.flatMap(Collection::stream);

        Map<String, Long> relationCourseStudent = streamDetail
                .collect(groupingBy(r-> r.getCurso().getNombre(), counting()));

        return relationCourseStudent;
    }


}
