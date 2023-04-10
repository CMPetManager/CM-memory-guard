package com.catchthemoment.repo;

import com.catchthemoment.entity.User;
import com.catchthemoment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestPropertySource("/application-test.properties")
@ActiveProfiles("shele-test")
public class UserPersitentTests {

    @Autowired
    private UserRepository repository;
    private User savedEntity;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        var saveUser = new User(1L, "Josh", "josh1990@mail.ru", "3274ert");
        savedEntity = repository.save(saveUser);
        assertEquals(saveUser, savedEntity);

    }

    @Test
    @Sql("/create-user.sql")
    public void create() {

        var newEntity = new User(2L, "Dave Vega", "vega_peace@gmail.com", "12345DFgr");
        repository.save(newEntity);

        Optional<User> entity = repository.findById(newEntity.getId());

        assertEquals(newEntity, entity.orElse(new User()));

        assertEquals(2, repository.count());
    }


}
