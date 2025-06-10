package ray.sn.crud_springboot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntiy, Long> {

}
