package cat.itacademy.s05.t02.VirtualPet.service.impl;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.UpdatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoPetsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.PetNotFoundException;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetInteraction;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import cat.itacademy.s05.t02.VirtualPet.repository.PetRepository;
import cat.itacademy.s05.t02.VirtualPet.repository.UserRepository;
import cat.itacademy.s05.t02.VirtualPet.service.PetInteractionService;
import cat.itacademy.s05.t02.VirtualPet.service.PetService;
import cat.itacademy.s05.t02.VirtualPet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PetInteractionService petInteractionService;

    //chatgpt user logejat: opció per buscar el user que està logejat ara mateix
    //provar quan tingui el front; si no, es queda iqual
    /*
    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User currentUser = userService.findUserByEmail(currentUserEmail);
}*/

    //create
    /*Permet als usuaris crear noves mascotes virtuals per cuidar i mimar. Poden triar entre una varietat de criatures, des de dracs fins a licorns, i fins i tot extraterrestres. Després, poden personalitzar el color, el nom i les característiques úniques de la seva mascota.*/

    @Override
    public PetDTO createPet(CreatePetRequest request) {
        //User currentUser = userService.findUserById(request.getUserId());
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findUserByEmail(currentUserEmail);

        if(petRepository.findByUserIdAndName(currentUser.getId(), request.getName()).isPresent()) {
            throw new NameAlreadyExistsException("This name is not available.");
        }

        Pet newPet = buildPet(request, currentUser);
        //si guardo el per al mètode buildPet, cal que el posi al user o es fa directaemnt??
        currentUser.getPets().add(newPet);
        userRepository.save(currentUser); //es pot fer un mètode al userService per no haver de posar el repo aquí: val la pena??

        return buildPetDTO(newPet);
    }

    private Pet buildPet(CreatePetRequest request, User user) {
        return Pet.builder()
                .name(request.getName())
                .type(request.getType())
                .color(request.getColor())
                .happiness(40)
                .energyLevel(60)
                .hunger(20)
                .accessories(new HashSet<>())
                .location(PetLocation.HOME)
                .user(user)
                .build();
        //newPet.setAccessoryPreferences(generateAccessoryPreferences());
        //newPet.setLocationPreferences(generateLocationPreferences());
        //return newPet;
    }

    //readOne (NO OBLIGATORI SEGONS L'ENUNCIAT)
    //buscar per id i mostrar totes les dades
    @Override
    public PetDTO getOnePetForUser(FindPetRequest findPetRequest) {
        Pet foundPet = findPet(findPetRequest.getUserId(), findPetRequest.getName());
        return buildPetDTO(foundPet);
    }

    //readAllForUser
    @Override
    public List<PetDTO> getAllPetsForUser(Long userId) {
        User currentUser = userService.findUserById(userId);
        if (currentUser.getPets() == null || currentUser.getPets().isEmpty()) {
            throw new NoPetsException("There are no pets to show.");
        }

        return currentUser.getPets()
                .stream()
                .map(this::buildPetDTO)
                .collect(Collectors.toList());
    }

    //tots els pets de tots els users
    @Override
    public List<PetDTO> getAllPets() {
        List<User> users = userService.allUsers();
        List<PetDTO> pets = users.stream()
                .flatMap(user -> user.getPets().stream())
                .map(this::buildPetDTO)
                .collect(Collectors.toList());

        if (pets.isEmpty()) {
            throw new NoPetsException("No pets found in the system.");
        }

        return pets;
    }

    @Override //crec que el puc tornar a fer private i treure'l de la interface
    public PetDTO buildPetDTO(Pet pet) {
        return PetDTO.builder()
                .username(pet.getUser().getUsername())
                .name(pet.getName())
                .color(pet.getColor())
                .type(pet.getType())
                .happiness(pet.getHappiness())
                .energyLevel(pet.getEnergyLevel())
                .hunger(pet.getHunger())
                .isAsleep(pet.isAsleep())
                .location(pet.getLocation())
                .accessories(pet.getAccessories())
                .build();
        //el JSON object no em retorna l'id, diu que és null
    }

    @Override
    @Transactional //??
    public PetDTO updatePet(UpdatePetRequest request) {
        Pet foundPet = findPet(request.getUserId(), request.getName());

        if(foundPet.getLocation() != request.getLocation()) {
            petInteractionService.changeLocation(foundPet, request.getLocation());
        }
        if(!foundPet.getAccessories().contains(request.getAccessory())) { //cal?? redundant
           petInteractionService.changeAccessories(foundPet, request.getAccessory());
        }
        if(request.getPetInteraction() != PetInteraction.NONE) { //?? cal none?? puc fer que el camp sigui null?
            petInteractionService.interact(foundPet, request.getPetInteraction());
        }

        //guardar els canvis al repositori
        petRepository.save(foundPet);
        return buildPetDTO(foundPet);
    }

    @Override
    public void deletePet(FindPetRequest findPetRequest) { //o per name?
        Pet foundPet = findPet(findPetRequest.getUserId(), findPetRequest.getName());
        petRepository.delete(foundPet);
    }

    private Pet findPet(Long userId, String petName) {
        User currentUser = userService.findUserById(userId);
        return petRepository.findByUserIdAndName(currentUser.getId(), petName)
                .orElseThrow(() -> new PetNotFoundException("You have no pets under this name."));
    }


}
