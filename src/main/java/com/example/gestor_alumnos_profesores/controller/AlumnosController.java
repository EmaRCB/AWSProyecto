package com.example.gestor_alumnos_profesores.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestor_alumnos_profesores.model.Alumno;
import com.example.gestor_alumnos_profesores.services.AlumnoService;
import com.example.gestor_alumnos_profesores.services.S3Service;
import com.example.gestor_alumnos_profesores.services.SimpleNotificationService;

import jakarta.validation.Valid;
import software.amazon.awssdk.services.s3.S3Client;

@RestController
@RequestMapping("/alumnos")
@Validated
public class AlumnosController {

   @Autowired
   private AlumnoService alumnoService;

   @Autowired
   private S3Service s3Service;

   @Autowired
   private SimpleNotificationService simpleNotificationService;

   @Autowired
   public void AlumnoController(AlumnoService alumnoService, SimpleNotificationService simpleNotificationService) {
      this.alumnoService = alumnoService;
      this.simpleNotificationService = simpleNotificationService;
   }

   private final List<Alumno> alumnos = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Alumno>> getAlumnos() {
        return ResponseEntity.ok(alumnoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable int id) {
        return alumnoService.findById(id).map(ResponseEntity::ok)
              .orElse(ResponseEntity
                    .status(HttpStatus.NOT_FOUND).build());
     }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@RequestBody @Valid Alumno alumno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.save(alumno));
     }

    @PutMapping("/{id}")
     public ResponseEntity<Alumno> updateAlumno(@PathVariable int id, @RequestBody @Valid Alumno alumno) {
        return alumnoService.update(id, alumno).map(ResponseEntity::ok)
              .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
  

    @PostMapping("/{id}/fotoPerfil")
   public ResponseEntity<Map<String, Object>> uploadFotoPerfil(@PathVariable int id,
         @RequestParam("foto") MultipartFile file) {

      Alumno alumno = alumnoService.findById(id).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

      try {
         // Crear archivo temporal
         Path tempFile = Files.createTempFile("fotoPerfil", file.getOriginalFilename());
         file.transferTo(tempFile);

         String fotoPerfilUrl = s3Service.uploadFile(tempFile, "fotoPerfilUrl/" + file.getOriginalFilename());
         alumno.setFotoPerfilUrl(fotoPerfilUrl);
         alumnoService.save(alumno);

         Map<String, Object> response = new HashMap<>();
         response.put("message", "Foto de perfil subida con éxito");
         response.put("fotoPerfilUrl", fotoPerfilUrl);
         response.put("success", true);

         return ResponseEntity.ok(response);
      } catch (IOException e) {
         Map<String, Object> response = new HashMap<>();
         response.put("message", "Error al procesar el archivo");
         response.put("success", false);

         return ResponseEntity.status(500).body(response);
      }
   }

   @PostMapping("/{id}/email")
   public ResponseEntity<Map<String, Object>> sendEmailNotification(@PathVariable int id) {
      Alumno alumno = alumnoService.findById(id).orElse(null);

      if (alumno == null) {
         Map<String, Object> response = new HashMap<>();
         response.put("message", "Alumno no encontrado");
         response.put("success", false);
         return ResponseEntity.status(404).body(response);
      }

      String subject = "Calificaciones de " + alumno.getNombres() + " " + alumno.getApellidos();
      String message = "Nombre: " + alumno.getNombres() + " " + alumno.getApellidos() +
            "\nMatrícula: " + alumno.getMatricula() +
            "\nPromedio: " + alumno.getPromedio();

      String messageId = simpleNotificationService.sendNotification(subject, message);

      Map<String, Object> response = new HashMap<>();
      response.put("message", "Notificación enviada");
      response.put("data", messageId);
      response.put("success", true);

      return ResponseEntity.ok(response);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Alumno> deleteAlumno(@PathVariable int id) {
      return alumnoService.deleteById(id)
            ? ResponseEntity.ok().build()
            : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
   }
   

    private Integer generateId() {
        return alumnos.size() + 1; // Simple ID generation, replace with better logic if needed
    }
}
