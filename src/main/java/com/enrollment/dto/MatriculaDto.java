package com.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDto {

    private Integer idMatricula;
    private LocalDateTime fechaMatricula;

    @JsonIncludeProperties(value= {"idEstudiante"})
    private EstudianteDto estudiante;

    private List<DetalleMatriculaDto> detalleMatricula;
    private boolean estado;
}
