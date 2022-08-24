package com.enrollment.controller;

import com.enrollment.dto.MatriculaDto;
import com.enrollment.dto.ResponseRelationCourseDto;
import com.enrollment.exception.ModelNotFoundException;
import com.enrollment.model.Estudiante;
import com.enrollment.model.Matricula;
import com.enrollment.service.IMatriculaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    @Autowired
    private IMatriculaService service;

    @Autowired
    @Qualifier("matriculaMapper")
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<MatriculaDto>> readAll() throws Exception {
        List<MatriculaDto> list = service.readAll()
                .stream().map(e -> modelMapper.map(e, MatriculaDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDto> readById(@PathVariable("id") Integer id) throws Exception {
        MatriculaDto mat = modelMapper.map(service.readById(id), MatriculaDto.class);
        if (mat == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + id);
        }
        return new ResponseEntity<>(mat, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MatriculaDto> create(@RequestBody MatriculaDto dto) throws Exception {
        dto.setFechaMatricula(LocalDateTime.now());
        Matricula mat = modelMapper.map(dto, Matricula.class);
        Matricula matricula = service.saveTransactional(mat, mat.getDetalleMatricula());
        MatriculaDto response = modelMapper.map(matricula, MatriculaDto.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MatriculaDto> update(@RequestBody MatriculaDto dto) throws Exception {
        Matricula mat = service.readById(dto.getIdMatricula());
        if (mat == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + dto.getIdMatricula());
        }
        Matricula matUpdate = service.update(modelMapper.map(dto, Matricula.class));
        MatriculaDto dtoResponse = modelMapper.map(matUpdate, MatriculaDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        Matricula mat = service.readById(id);
        if (mat == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Mostrar la relación de cursos matriculados y sus estudiantes correspondientes
    // usando programación funcional (sugerencia, usar un Map<K,V>).
    @GetMapping("/getRelationCourseStudent")
    public ResponseEntity<Map<String, Long>> getRelationCourseStudent() throws Exception {
        Map<String, Long> map = service.getRelationCourseStudent();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
