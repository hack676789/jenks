package ray.sn.crud_springboot;
import ray.sn.crud_springboot.PersonEntiy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTests {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private PersonEntiy person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new PersonEntiy();
        person.setId(1L);
        person.setName("Jack");
        person.setCity("Dakar");
        person.setPhoneNumber("123456789");
    }

    @Test
    void testCreatePerson() {
        // Arrange
        when(personRepository.save(person)).thenReturn(person);

        // Act
        PersonEntiy savedPerson = personService.createPerson(person);

        // Assert
        assertNotNull(savedPerson, "La personne sauvegardée ne devrait pas être null");
        assertEquals("John", savedPerson.getName(), "le nom ne correspond pas !");
        assertEquals("Dakar", savedPerson.getCity(), "la ville ne correspond pas !");
        assertEquals("123456789", savedPerson.getPhoneNumber(), "Veuillez remplir le numero de telephone s'il vous plait ");
        
        // Verify
        verify(personRepository, times(1)).save(person);
        verify(personRepository, never()).delete(any());
    }

    /*@Test
    void testCreatePersonWithNullValue() {
        PersonEntiy nullPerson = new PersonEntiy();
        assertThrows(IllegalArgumentException.class, () -> {
            personService.createPerson(nullPerson);
        });
    }*/

   @Test
    void testGetAllPersons() {
        List<PersonEntiy> Persons = Arrays.asList(
            new PersonEntiy(1L, "John", "Dakar", "123456789"),
            new PersonEntiy(2L, "Alice", "Paris", "987654321")
        );
        
        when(personRepository.findAll()).thenReturn(Persons);

        List<PersonEntiy> result = personService.getAllPersons();

        // Assert - Vérifications
        assertNotNull(result, "La liste retournée ne devrait pas être null");
        assertEquals(2, result.size(), "La taille de la liste ne correspond pas");
        assertEquals("John", result.get(0).getName(), "Le premier nom ne correspond pas");
        assertEquals("Paris", result.get(1).getCity(), "La ville du second ne correspond pas");
        
        // Verify - Vérification des interactions avec le mock
        verify(personRepository, times(1)).findAll();
        verify(personRepository, never()).delete(any());
    }


    /*@Test
        void testUpdatePerson_Success() {
            // Arrange
            Long personId = 1L;
            PersonEntiy existingPerson = new PersonEntiy();
            existingPerson.setId(personId);
            existingPerson.setName("Old Name");
            
            PersonEntiy updatedData = new PersonEntiy();
            updatedData.setName("New Name");
            updatedData.setCity("New City");

            when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
            when(personRepository.save(any(PersonEntiy.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            PersonEntiy result = personService.updatePerson(personId, updatedData);

            // Assert
            assertNotNull(result);
            assertEquals("New Name", result.getName());
            assertEquals("New City", result.getCity());
            assertEquals(personId, result.getId());
            
            verify(personRepository, times(1)).findById(personId);
            verify(personRepository, times(1)).save(existingPerson);
        }*/

        /*@Test
        void testUpdatePerson_WhenNotFound() {
            // Arrange
            Long personId = 999L;
            PersonEntiy updatedData = new PersonEntiy();
            when(personRepository.findById(personId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundException.class, () -> {
                personService.updatePerson(personId, updatedData);
            });
        }*/

        @Test
        void testUpdatePerson_WithPartialData() {
            // Arrange
            Long personId = 1L;
            PersonEntiy existingPerson = new PersonEntiy();
            existingPerson.setId(personId);
            existingPerson.setName("John");
            existingPerson.setCity("Dakar");
            existingPerson.setPhoneNumber("123456789");

            PersonEntiy updatedData = new PersonEntiy();
            updatedData.setName("Jack"); 

            when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
            when(personRepository.save(any(PersonEntiy.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            PersonEntiy result = personService.updatePerson(personId, updatedData);

            // Assert
            assertEquals("Jack", result.getName());
            assertEquals("Dakar", result.getCity()); 
            assertEquals("123456789", result.getPhoneNumber()); 
        }

}