package com.enrollment.controller;

import com.enrollment.dto.EstudianteDto;
import com.enrollment.exception.ModelNotFoundException;
import com.enrollment.model.Curso;
import com.enrollment.model.Estudiante;
import com.enrollment.service.IEstudianteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private IEstudianteService service;

    @Autowired
    @Qualifier("estudianteMapper")
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EstudianteDto>> readAll() throws Exception {
        List<EstudianteDto> list = service.readAll()
                .stream().map(e -> modelMapper.map(e, EstudianteDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDto> readById(@PathVariable("id") Integer id) throws Exception {
        EstudianteDto est = modelMapper.map(service.readById(id), EstudianteDto.class);
        if (est == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + id);
        }
        return new ResponseEntity<>(est, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EstudianteDto> create(@RequestBody EstudianteDto dto) throws Exception {
        Estudiante est = service.create(modelMapper.map(dto, Estudiante.class));
        return new ResponseEntity<>(modelMapper.map(est, EstudianteDto.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EstudianteDto> update(@RequestBody EstudianteDto dto) throws Exception {
        Estudiante est = service.readById(dto.getIdEstudiante());
        if (est == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + dto.getIdEstudiante());
        }
        Estudiante estUpdate = service.update(modelMapper.map(dto, Estudiante.class));
        EstudianteDto dtoResponse = modelMapper.map(estUpdate, EstudianteDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        Estudiante est = service.readById(id);
        if (est == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Listar estudiantes ordenados de forma descendente por edad usando programación funcional.
    @GetMapping("/getStudentDescAge")
    public ResponseEntity<List<EstudianteDto>> getStudentDescAge() throws Exception {
        List<EstudianteDto> list = service.getStudentDescAge()
                .stream().map(e -> modelMapper.map(e, EstudianteDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
