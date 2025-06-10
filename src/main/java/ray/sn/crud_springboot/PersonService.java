package ray.sn.crud_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonEntiy createPerson(PersonEntiy person) {
        return personRepository.save(person);
    }

    public List<PersonEntiy> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<PersonEntiy> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public PersonEntiy updatePerson(Long id, PersonEntiy personDetails) {
    Optional<PersonEntiy> optionalPerson = personRepository.findById(id);

    if (optionalPerson.isPresent()) {
        PersonEntiy person = optionalPerson.get();

        if (personDetails.getName() != null) {
            person.setName(personDetails.getName());
        }
        if (personDetails.getCity() != null) {
            person.setCity(personDetails.getCity());
        }
        if (personDetails.getPhoneNumber() != null) {
            person.setPhoneNumber(personDetails.getPhoneNumber());
        }

        return personRepository.save(person);
        } else {
            throw new RuntimeException("Personne non trouvée avec l'id : " + id);
        }
    }


    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
