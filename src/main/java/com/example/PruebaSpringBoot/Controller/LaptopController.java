package com.example.PruebaSpringBoot.Controller;

import com.example.PruebaSpringBoot.Entity.Laptop;
import com.example.PruebaSpringBoot.Repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private LaptopRepository laptopRepository;

    // ESTE LOGER ES UNA FORMA DE VER ERRORES EN LA CONSOLA OBTENIENDO MAS INFORMACION QUE CON EL SOUT
    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    // METODOS GET - PARA OBTENER INFORMACION

    @GetMapping("/laptops")
    public List<Laptop> findAll() {

        return laptopRepository.findAll();
    }

    //aca devuelvo una response entity como deberia ser.
    @GetMapping("/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable("id") Long id) {
        Optional<Laptop> laptopOpt = laptopRepository.findById(id);

        if (laptopOpt.isPresent())
            return ResponseEntity.ok(laptopOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

        //METODOS POST PARA GUARDAR INFORMACION

    // puedo poner la misma url porque una eta en get y la otra en post.
    @PostMapping("/laptops/create")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop) {
        if (laptop.getId() != null) {
            log.warn("triying to create a laptop with id");
            return ResponseEntity.badRequest().build();
        }
        else {
            Laptop lap = laptopRepository.save(laptop);
            return ResponseEntity.ok(lap);
        }
    }

    // para borrar un recurso    @DeleteMapping
    // para actualizar el recurso entero
    @PutMapping("/laptops/update")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if(laptop.getId() == null){
            log.error("trying to update a laptop whitout id");
            return ResponseEntity.badRequest().build();
        }
        if (!laptopRepository.existsById(laptop.getId())) {
            log.error("trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }
        Laptop laptop1 = laptopRepository.save(laptop);
        return ResponseEntity.ok(laptop1);
    }

    @DeleteMapping("/laptops/delete/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){

        //ACA TENGO QUE VALORAR LOS POSIBLES ERROR CON TRY CATCH,
        //MUCHAS DE ESTAS GESTIONES DEBERIAN DEBERIAN DE PASAR POR OTRA CLASE SERVICIO.
        //Y GESTIONAR TODAS LAS EXCEPCIONES Y TRABAJAR CON REPOSITORY DESDE AHI

        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/laptops/deleteall")
    public ResponseEntity<Laptop> deleteAll(){
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }



    // para actualizar solo una parte    @PatchMapping
}
