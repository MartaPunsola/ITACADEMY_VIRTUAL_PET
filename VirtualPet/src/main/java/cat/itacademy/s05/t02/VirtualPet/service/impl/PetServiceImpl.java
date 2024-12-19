package cat.itacademy.s05.t02.VirtualPet.service.impl;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.PetNotFoundException;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import cat.itacademy.s05.t02.VirtualPet.repository.PetRepository;
import cat.itacademy.s05.t02.VirtualPet.repository.UserRepository;
import cat.itacademy.s05.t02.VirtualPet.service.PetService;
import cat.itacademy.s05.t02.VirtualPet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    //create
    /*Permet als usuaris crear noves mascotes virtuals per cuidar i mimar. Poden triar entre una varietat de criatures, des de dracs fins a licorns, i fins i tot extraterrestres. Després, poden personalitzar el color, el nom i les característiques úniques de la seva mascota.*/

    @Override
    public PetDTO createPet(CreatePetRequest request) {
        //provar aquesta versió quan tingui el front
        /*String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findUserByEmail(currentUserEmail);*/

        User currentUser = userService.findUserById(request.getUser_id()); //si al final deixo això, fer un mètode per buscar usuaris per id a UserService
        //comprovar que no existeixi un pet amb aquest nom
        if(petRepository.findByUserIdAndName(currentUser.getId(), request.getName()).isPresent()) {
            throw new NameAlreadyExistsException("This name is not available.");
        }

        Pet newPet = buildPet(request, currentUser);
        currentUser.getPets().add(newPet); //cal vincular l'id del user al pet
        userRepository.save(currentUser);
        //newPet.map(this::buildPetDTO) i guardar en una variable PetDTO, que seria la que es retorna

        return buildPetDTO(newPet);
        //o DTO!!! el JSON object no em retorna l'id, diu que és null
    }

    private Pet buildPet(CreatePetRequest request, User user) {
        Pet newPet = Pet.builder()
                .name(request.getName())
                .type(request.getType())
                .color(request.getColor())
                .happiness(40)
                .energy_level(60)
                .hunger(20)
                .accessories(new HashSet<>())
                .location(PetLocation.HOME)
                .user(user)
                .build();
        //newPet.setAccessoryPreferences(generateAccessoryPreferences());
        //newPet.setLocationPreferences(generateLocationPreferences());
        return newPet;
    }

    //readOne (NO OBLIGATORI SEGONS L'ENUNCIAT)
    //buscar per id i mostrar totes les dades

    //readAll
    /*Mostra totes les mascotes virtuals existents en un entorn virtual vibrants i colorit. Els usuaris poden interactuar amb les seves mascotes, veure el seu estat d'ànim, nivell d'energia i necessitats.*/

    public List<Pet> getAllPetsForUser() {
        //chat gpt
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        //fer opció sense security

        //return petRepository.findAllByOwner(currentUser);
        return currentUser.getPets();
        //exception si no té pets creats
    }

    //tots els pets de tots els users
    //cridar al mètode del userservice per retornar tots els users i, a partir d'aquí, rdcuperar les dades dels pets

    @Override
    public List<PetDTO> getAllPets() {
        List<User> users = userService.allUsers();
        return users.stream()
                .flatMap(user -> user.getPets().stream())
                .map(this::buildPetDTO)
                .collect(Collectors.toList());
        //exception si no hi ha mascotes??
    }

    private PetDTO buildPetDTO(Pet pet) {
        return PetDTO.builder()
                .username(pet.getUser().getUsername())
                .name(pet.getName())
                .color(pet.getColor())
                .type(pet.getType())
                .happiness(pet.getHappiness())
                .energy_level(pet.getEnergy_level())
                .hunger(pet.getHunger())
                .isAsleep(pet.isAsleep())
                .location(pet.getLocation())
                .accessories(pet.getAccessories())
                .build();
    }

    //update
    /*Permet als usuaris cuidar i personalitzar les seves mascotes virtuals. Poden alimentar-les, jugar amb elles, comprar accessoris divertits i canviar el seu entorn virtual per mantenir-les felices i saludables.
     */
    //chatgpt
    /*public Pet updatePet(Long petId, UpdatePetRequest request) {
    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Pet pet = petRepository.findByIdAndOwner(petId, currentUser)
            .orElseThrow(() -> new IllegalArgumentException("Pet not found or you don't have permission"));

    pet.setName(request.getName());
    pet.setColor(request.getColor());
    pet.setType(request.getType());

    return petRepository.save(pet);
}*/

    @Override
    public void deletePet(FindPetRequest findPetRequest) { //o per name?
        User currentUser = userService.findUserById(findPetRequest.getUser_id());
        Pet foundPet = petRepository.findByUserIdAndName(currentUser.getId(), findPetRequest.getName())
                .orElseThrow(() -> new PetNotFoundException("You have no pets under this name."));
        //es pot fer un mètode a part només per buscar
        petRepository.delete(foundPet);
    }
    //chatgpt user logejat
    /*
    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User currentUser = userRepository.findByEmail(currentUserEmail) //implementar mètode del userService
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}*/



}
