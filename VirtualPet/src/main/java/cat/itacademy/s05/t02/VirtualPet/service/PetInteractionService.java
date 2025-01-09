package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetAccessory;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetInteraction;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetInteractionService {


    public void changeAccessories(Pet pet, PetAccessory accessory) {
        Set<PetAccessory> accessories = pet.getAccessories();

        if (accessories.contains(accessory)) {
            accessories.remove(accessory);
            pet.setHappiness(decreaseState(pet.getHappiness()));
            log.info("Accessory {} removed from pet {}. Happiness decreased to {}", accessory, pet.getName(), pet.getHappiness());
        } else {
            accessories.add(accessory);
            pet.setHappiness(increaseState(pet.getHappiness()));
            log.info("Accessory {} added to pet {}. Happiness increased to {}", accessory, pet.getName(), pet.getHappiness());
        }

        wakeUp(pet);
    }

    public void changeLocation(Pet pet, PetLocation newLocation) {
        pet.setLocation(newLocation);
        wakeUp(pet);
        log.info("Pet '{}' location updated to '{}'.", pet.getName(), pet.getLocation());
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
        log.info("Pet '{}' fed. Updated hunger: {}, happiness: {}, energy level: {}",
                pet.getName(), pet.getHunger(), pet.getHappiness(), pet.getEnergyLevel());
    }

    private void sleep(Pet pet) {
        if(!pet.isAsleep()) {
            pet.setAsleep(true);
            pet.setHappiness(increaseState(pet.getHappiness()));
            pet.setEnergyLevel(increaseState(pet.getEnergyLevel()));
            pet.setLocation(PetLocation.HOME);
            log.info("Pet '{}' is now asleep. Updated happiness: {}, energy level: {}, location: {}",
                    pet.getName(), pet.getHappiness(), pet.getEnergyLevel(), pet.getLocation());
        }
    }

    private void play(Pet pet) {
        wakeUp(pet);
        pet.setHappiness(increaseState(pet.getHappiness()));
        pet.setEnergyLevel(decreaseState(pet.getEnergyLevel()));
        pet.setHunger(increaseState(pet.getHunger()));
        log.info("Pet '{}' finished playing. Updated happiness: {}, energy level: {}, hunger: {}",
                pet.getName(), pet.getHappiness(), pet.getEnergyLevel(), pet.getHunger());
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

