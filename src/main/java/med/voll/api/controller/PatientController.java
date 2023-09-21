package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.patient.*;
import med.voll.api.domain.patient.DetailsPatientData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid PatientData data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsPatientData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<ListPatientData>> list(@PageableDefault(page = 0, size = 10, sort = {"name"}) Pageable pageable) {
        var page = repository.findByActiveTrue(pageable).map(ListPatientData::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdatePatientData data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateInformation(data);

        return ResponseEntity.ok(new DetailsPatientData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.disable();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailsPatientData(patient));
    }

}
