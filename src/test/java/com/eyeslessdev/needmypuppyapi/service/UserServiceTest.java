package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {


    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private static ArrayList<User> userList;


    @BeforeAll()
    static void setUp() {
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setName("myuser");
        firstUser.setPassword("myuser");
        firstUser.setEmail("myuser@test.com");
        firstUser.setRoles(Collections.singleton(Role.USER));

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setName("user100");
        secondUser.setPassword("user100");
        secondUser.setEmail("user100@test.com");
        secondUser.setRoles(Collections.singleton(Role.CREATEDUSER));

        User thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setName("admin");
        thirdUser.setPassword("admin");
        thirdUser.setEmail("admin@test.com");
        thirdUser.setRoles(Collections.singleton(Role.ADMIN));

        userList = new ArrayList<>(Arrays.asList(firstUser, secondUser, thirdUser));
    }

    @Test
    void findAll() {
        when(userRepo.findAll()).thenReturn(userList);

        Optional<List<User>> users = userService.findAll();

        assertEquals(3, userList.size());
        assertTrue(userList.stream().allMatch(User.class::isInstance));
        assertThat(userList).extractingResultOf("hashCode").doesNotHaveDuplicates();
        assertThat(userList).extracting("name").containsExactlyInAnyOrder("myuser","user100","admin");
        assertThat(userList).extracting("name").doesNotContain("vasya");
        Mockito.verify(userRepo, Mockito.times(1)).findAll();
    }

    @Test
    void findById() {
        long arg = 1L;
        int argInt = (int) arg;

        when(userRepo.findById(arg)).thenReturn(Optional.of(userList.get(argInt)));
        Optional<User> testingUser = userService.findById(arg);

        assertNotNull(testingUser);
        assertThat(testingUser.get()).hasSameClassAs(userList.get(argInt));
        assertEquals(testingUser.get().getId(), userList.get(argInt).getId());
    }

    @Test
    void findByIdNotFound() {
        when(userRepo.findById(999L)).thenReturn(Optional.empty());
        Optional<User> testingUser = userService.findById(999L);

        assertNotNull(testingUser);
        assertEquals(testingUser, Optional.empty());
    }


    @Test
    void saveNewUser() {

        User currentUser = new User();
        currentUser.setPassword("myPass");
        currentUser.setEmail("some@some.com");

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepo.save(currentUser)).thenReturn(currentUser);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("verySecretPass");

        User fetchedUser = userService.saveNewUser(currentUser).get();

        assertEquals(fetchedUser, currentUser);
        assertNotNull(fetchedUser.getRegistrationtime());
        assertNotNull(fetchedUser.getLastvisit());
        assertEquals(fetchedUser.getRoles(), Collections.singleton(Role.CREATEDUSER));
        assertThat(fetchedUser.getExternalid()).endsWith("generateinternal");

        assertEquals(fetchedUser.getPassword(), "verySecretPass");

        Mockito.verify(userRepo, Mockito.times(1)).findByEmail("some@some.com");
        Mockito.verify(userRepo, Mockito.times(1)).save(any(User.class));
        Mockito.verify(bCryptPasswordEncoder, Mockito.times(1)).encode(anyString());

    }

    @Test
    void saveNewUserCollision() {

        User currentUser = userList.get(1);

        when(userRepo.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        Optional<User> fetchedUser = userService.saveNewUser(currentUser);
        assertEquals(fetchedUser, Optional.empty());

        Mockito.verify(userRepo, Mockito.times(1)).findByEmail(currentUser.getEmail());
        Mockito.verify(userRepo, Mockito.never()).save(currentUser);
        Mockito.verify(bCryptPasswordEncoder, Mockito.never()).encode(currentUser.getPassword());

    }

    @Test
    void findAllCreated() {

        Set<Role> status = Collections.singleton(Role.CREATEDUSER);

        //this method could check if user contains more than one role, for the single role it could be significant simplifying
        final List<User> persons = userList;
        List<User> filteredPersons =  status.stream()
                .flatMap(n ->
                        persons.stream().filter(p -> p.hasRole(n))                )
                .collect(Collectors.toCollection(ArrayList::new));

        when(userRepo.fetchUserListComparedStatus(status.toString())).thenReturn(Optional.of(filteredPersons));

        List<User> result = userRepo.fetchUserListComparedStatus(status.toString()).get();
        List<Role> roles = result.stream()
                .flatMap(i -> Stream.of(i.getRoles())
                        .flatMap(Collection::stream))
                .collect(Collectors.toList());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(roles).containsOnly(Role.CREATEDUSER);
    }

    @Test
    void setLastVisitTimeToUser() {
    }

    @Test
    void changeStatus() {
    }

}