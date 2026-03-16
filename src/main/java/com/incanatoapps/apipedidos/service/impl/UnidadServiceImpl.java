package com.incanatoapps.apipedidos.service.impl;

import com.incanatoapps.apipedidos.entity.Unidad;
import com.incanatoapps.apipedidos.repository.UnidadRepository;
import com.incanatoapps.apipedidos.service.UnidadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UnidadServiceImpl implements UnidadService {
    private final UnidadRepository repository;

    public UnidadServiceImpl(UnidadRepository repository){
        this.repository=repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Unidad> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Unidad findById(String id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Unidad create(Unidad obj) {
        return repository.save(obj);
    }

    @Override
    public Unidad update(String id, Unidad obj) {
        if(repository.existsById(id)){
            obj.setId(id);
            return repository.save(obj);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
