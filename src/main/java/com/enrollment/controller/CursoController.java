package com.enrollment.controller;

import com.enrollment.dto.CursoDto;
import com.enrollment.exception.ModelNotFoundException;
import com.enrollment.model.Curso;
import com.enrollment.service.ICursoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private ICursoService service;

    @Autowired
    @Qualifier("cursoMapper")
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CursoDto>> readAll() throws Exception {
        List<CursoDto> list = service.readAll()
                .stream().map(e -> modelMapper.map(e, CursoDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDto> readById(@PathVariable("id") Integer id) throws Exception {
        CursoDto cur = modelMapper.map(service.readById(id), CursoDto.class);
        if (cur == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + id);
        }
        return new ResponseEntity<>(cur, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CursoDto> create(@RequestBody CursoDto dto) throws Exception {
        Curso cur = service.create(modelMapper.map(dto, Curso.class));
        return new ResponseEntity<>(modelMapper.map(cur, CursoDto.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CursoDto> update(@RequestBody CursoDto dto) throws Exception {
        Curso cur = service.readById(dto.getIdCurso());
        if (cur == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + dto.getIdCurso());
        }
        Curso curUpdate = service.update(modelMapper.map(dto, Curso.class));
        CursoDto dtoResponse = modelMapper.map(curUpdate, CursoDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        Curso cur = service.readById(id);
        if (cur == null) {
            throw new ModelNotFoundException("ID NOT FOUND:" + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
