package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void createRegister(@RequestBody @Valid DoctorData data) {
        repository.save(new Doctor(data));
    }

    @GetMapping
    public Page<ListDoctorData> listRegisters(@PageableDefault(page = 0, size = 20, sort = {"name"}) Pageable pageable) {
        return repository.findAllByActiveTrue(pageable).map(ListDoctorData::new);
    }

    @PutMapping
    @Transactional
    public void updateRegister(@RequestBody @Valid UpdateDoctorData data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.updateInformation(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.disable();
    }


}
