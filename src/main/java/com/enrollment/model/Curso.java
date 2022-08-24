package com.enrollment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCurso;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String siglas;

    @Column(nullable = false)
    private boolean estado;


}
