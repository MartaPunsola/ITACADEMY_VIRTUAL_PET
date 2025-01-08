package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetAccessory;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetInteraction;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PetInteractionService {

    //calen atributs??


    public void changeAccessories(Pet pet, PetAccessory accessory) {
        Set<PetAccessory> accessories = pet.getAccessories();

        if (accessories.contains(accessory)) {
            accessories.remove(accessory);
            pet.setHappiness(decreaseState(pet.getHappiness()));
        } else {
            accessories.add(accessory);
            pet.setHappiness(increaseState(pet.getHappiness()));
        }

        /*Optional.of(accessory)
                .filter(a -> !accessories.contains(a))  // Afegir si no existeix
                .ifPresentOrElse(a -> {
                    accessories.add(a);
                    pet.setHappiness(increaseState(pet.getHappiness()));
                   // log.info("Accessory {} added to pet {}. Happiness increased to {}", a, pet.getName(), pet.getHappiness());
                }, () -> {
                    accessories.remove(accessory);
                    pet.setHappiness(decreaseState(pet.getHappiness()));
                    //log.info("Accessory {} removed from pet {}. Happiness decreased to {}", accessory, pet.getName(), pet.getHappiness());
                });*/


        wakeUp(pet);

        // Afegir accessoris nous
        /*for (PetAccessory accessory : requestedAccessories) {
            if (!currentAccessories.contains(accessory)) {
                currentAccessories.add(accessory);
                increaseState(pet, pet.getHappiness());
                //+ happiness
            } else {
                throw new IllegalStateException("The pet already has " + accessory.name());
            }
        }*/

        // Eliminar accessoris no inclosos a la nova llista
        //currentAccessories.removeIf(accessory -> !requestedAccessories.contains(accessory));
        //- happiness

        //pet.setAccessories(currentAccessories);  // Assegura que s'actualitza el set


        // Opcional: registrar l'acció
        //log.info("Updated accessories for pet {}: {}", pet.getName(), pet.getAccessories());
    }

    //addAccessory
    public void addAccessory(Pet pet) {
        //comprovar que no el té i afegir
        pet.setHappiness(increaseState(pet.getHappiness())); //augmentar 20%
        //return petService.buildPetDTO(pet);
    }

    //removeAccessory
    public void removeAccessory(Pet pet) {

        //decreaseState(pet, pet.getHappiness()); //disminuir 20% happiness
        //return petService.buildPetDTO(pet);
    }

    //changeLocation
    public void changeLocation(Pet pet, PetLocation newLocation) {
        pet.setLocation(newLocation);
        //depèn de la location, modificar els estats
        //cal que retorni un Pet o pot tornar void??
        wakeUp(pet);
    }
    
    public void interact(Pet pet, PetInteraction interaction) {
        switch(interaction) {
            case FEED -> feed(pet);
            case SLEEP -> sleep(pet);
            case PLAY -> play(pet);
        }
    }

    private void wakeUp(Pet pet) {
        if(pet.isAsleep()) {
            pet.setAsleep(false);
        }
    }

    private void feed(Pet pet) {
        wakeUp(pet);
        pet.setHunger(decreaseState(pet.getHunger()));
        pet.setHappiness(increaseState(pet.getHappiness()));
        pet.setEnergyLevel(increaseState(pet.getEnergyLevel()));
    }

    private void sleep(Pet pet) {
        if(!pet.isAsleep()) {
            pet.setAsleep(true);
            pet.setHappiness(increaseState(pet.getHappiness()));
            pet.setEnergyLevel(increaseState(pet.getEnergyLevel()));
            pet.setLocation(PetLocation.HOME);
        }
    }

    private void play(Pet pet) {
        wakeUp(pet);
        pet.setHappiness(increaseState(pet.getHappiness()));
        pet.setEnergyLevel(decreaseState(pet.getEnergyLevel()));
        pet.setHunger(increaseState(pet.getHunger()));
    }

    private int increaseState(int state) {
        int increment = (int) (state * 0.1);
        int newState = state + increment;
        return Math.min(newState, 100);
    }

    private int decreaseState(int state) {
        int decrement = (int) (state * 0.1);
        int newState = state - decrement;
        return Math.max(newState, 0);
    }

}

