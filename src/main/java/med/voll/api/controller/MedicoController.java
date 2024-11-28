package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2, page = 1, sort = "nombre") Pageable pageable) {
        /*return medicoRepository.findAll(pageable).map(DatosListadoMedico::new);*/
        return medicoRepository.findByActivoTrue(pageable).map(DatosListadoMedico::new);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registraMedico(@Valid @RequestBody DatosRegistroMedico datosRegistroMedico) {
        // Verificar si el médico ya está registrado
        if (medicoRepository.existsByEmailAndDocumento(datosRegistroMedico.email(), datosRegistroMedico.documento())) {
            throw new IllegalStateException("ERR_DUPLICATE_RECORD"); // Lanza la excepción para manejarla en el global
        }

        // Guardar el médico
        medicoRepository.save(new Medico(datosRegistroMedico));

        // Construir la respuesta de éxito
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("code", "CREATED");
        successResponse.put("message", "Médico registrado exitosamente.");

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Map<String, String>> actualizarMedico(@Valid @RequestBody DatosActualizaMedico datosActualizaMedico) {
        Medico medico = medicoRepository.findById(datosActualizaMedico.id())
                .orElseThrow(() -> new EntityNotFoundException("ERR_MEDICO_NOT_FOUND"));

        Medico medicoActualizado = medico.actualizarDatos(
                datosActualizaMedico.nombre(),
                datosActualizaMedico.documento(),
                datosActualizaMedico.direccion()
        );

        medicoRepository.save(medicoActualizado);

        // Respuesta de éxito
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("code", "UPDATED");
        successResponse.put("message", "Médico actualizado exitosamente.");
        successResponse.put("id", String.valueOf(medicoActualizado.getId()));

        return ResponseEntity.ok(successResponse);
    }


    // DELETE Logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_MEDICO_NOT_FOUND")); // Manejado en el global

        Medico medicoActualizado = medico.desactivarMedico(); // Generar nueva instancia con activo = false
        medicoRepository.save(medicoActualizado); // Guardar el cambio en la base de datos

        // Respuesta de éxito
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("code", "DELETED");
        successResponse.put("message", "Médico desactivado exitosamente.");
        successResponse.put("id", String.valueOf(id));

        return ResponseEntity.ok(successResponse);
    }

    /*@DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_MEDICO_NOT_FOUND")); // Manejado en el global

        medicoRepository.delete(medico);

        // Respuesta de éxito
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("code", "DELETED");
        successResponse.put("message", "Médico eliminado exitosamente.");
        successResponse.put("id", String.valueOf(id));

        return ResponseEntity.ok(successResponse);
    }*/
}