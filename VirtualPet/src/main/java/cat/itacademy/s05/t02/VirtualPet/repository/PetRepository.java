package cat.itacademy.s05.t02.VirtualPet.repository;

import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
