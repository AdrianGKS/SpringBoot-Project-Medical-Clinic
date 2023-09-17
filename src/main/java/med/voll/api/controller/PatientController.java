package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid PatientData data) {
        repository.save(new Patient(data));
    }

    @GetMapping
    public Page<ListPatientData> list(@PageableDefault(page = 0, size = 10, sort = {"name"}) Pageable pageable) {
        return repository.findByActiveTrue(pageable).map(ListPatientData::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid UpdatePatientData data) {
        var paciente = repository.getReferenceById(data.id());
        paciente.updateInformation(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void remover(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.disable();
    }
}
