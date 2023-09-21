package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.doctor.DetailDoctorData;
import med.voll.api.domain.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createRegister(@RequestBody @Valid DoctorData data, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailDoctorData(doctor));
    }

    @GetMapping
    public ResponseEntity <Page<ListDoctorData>> listRegisters(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {


        var page = repository.findAllByActiveTrue(pageable).map(ListDoctorData::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateRegister(@RequestBody @Valid UpdateDoctorData data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.updateInformation(data);

        return ResponseEntity.ok(new DetailDoctorData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.disable();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailDoctorData(doctor));
    }

}
