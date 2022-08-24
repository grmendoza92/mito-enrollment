package com.enrollment.service;

import com.enrollment.model.DetalleMatricula;
import com.enrollment.model.Matricula;

import java.util.List;
import java.util.Map;

public interface IMatriculaService extends ICRUD<Matricula, Integer> {

    Matricula saveTransactional(Matricula sale, List<DetalleMatricula> details);
    Map<String, Long> getRelationCourseStudent() throws Exception;
}
