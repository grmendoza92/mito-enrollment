package com.enrollment.service;

import com.enrollment.model.Estudiante;

import java.util.List;

public interface IEstudianteService extends ICRUD<Estudiante, Integer> {

    List<Estudiante> getStudentDescAge() throws Exception;
}
