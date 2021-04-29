package pl.csanecki.animalshelter.webservice.web;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.command.Result;
import pl.csanecki.animalshelter.domain.model.AnimalKind;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/shelter/animals")
public class ShelterController {

    private final ShelterService shelterService;

    @Autowired
    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity<Void> acceptIntoShelter(@Valid @RequestBody AddAnimalRequest addAnimalRequest) {
        long animalId = shelterService.acceptIntoShelter(new AddAnimalCommand(
                addAnimalRequest.getName(),
                AnimalKind.findAnimalKind(addAnimalRequest.getKind()),
                addAnimalRequest.getAge()
        ));

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(animalId)
                        .toUri()
        ).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable long id) {
        AnimalDetails animalDetails = shelterService.getAnimalDetails(id);

        return ResponseEntity.ok(animalDetails);
    }

    @GetMapping
    public ResponseEntity<List<AnimalShortInfo>> getAnimals() {
        List<AnimalShortInfo> animals = shelterService.getAnimalsInfo();

        return ResponseEntity.ok(animals);
    }

    @PostMapping("/{id}/adopt")
    public ResponseEntity<Void> adoptAnimal(@PathVariable long id) {
        Try<Result> result = shelterService.adoptAnimal(id);

        return result
                .map(success -> ResponseEntity.ok().<Void>build())
                .getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}

class AddAnimalRequest {

    String name;
    String kind;
    int age;

    AddAnimalRequest(
            @NotEmpty @Size(max = 250) final String name,
            @ValueOfAnimalKind(enumClass = AnimalKind.class) final String kind,
            @PositiveOrZero final int age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }

    String getName() {
        return name;
    }

    String getKind() {
        return kind;
    }

    int getAge() {
        return age;
    }
}