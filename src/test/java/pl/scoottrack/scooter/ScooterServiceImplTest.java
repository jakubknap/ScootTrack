package pl.scoottrack.scooter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.scoottrack.repair.Repair;
import pl.scoottrack.repair.RepairDetailsResponse;
import pl.scoottrack.repair.RepairRepository;
import pl.scoottrack.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScooterServiceImplTest {

    @Mock
    private ScooterRepository scooterRepository;

    @Mock
    private RepairRepository repairRepository;

    @InjectMocks
    private ScooterServiceImpl scooterService;

    private User testUser;
    private User otherUser;
    private Scooter testScooter;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUuid(UUID.randomUUID());
        testUser.setFirstname("TestUser");

        otherUser = new User();
        otherUser.setUuid(UUID.randomUUID());
        otherUser.setFirstname("OtherUser");

        testScooter = new Scooter();
        testScooter.setUuid(UUID.randomUUID());
        testScooter.setUser(testUser);
        testScooter.setName("TestScooter");
        testScooter.setBrand("TestBrand");
        testScooter.setModel("TestModel");
        testScooter.setType("Mountain");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(testUser, null, List.of());
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAddScooterSuccessfully() {
        AddScooterRequest request = new AddScooterRequest("TestScooter", "TestBrand", "TestModel", "Mountain");

        when(scooterRepository.save(any(Scooter.class))).thenReturn(testScooter);

        scooterService.addScooter(request);

        verify(scooterRepository, times(1)).save(any(Scooter.class));
    }

    @Test
    void shouldEditScooterSuccessfully() {
        EditScooterRequest request = new EditScooterRequest(testScooter.getUuid(), "NewName", "NewBrand", "NewModel", "NewType");

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        scooterService.editScooter(request);

        assertEquals("NewName", testScooter.getName());
        assertEquals("NewBrand", testScooter.getBrand());
        assertEquals("NewModel", testScooter.getModel());
        assertEquals("NewType", testScooter.getType());

        verify(scooterRepository, times(1)).save(testScooter);
    }

    @Test
    void shouldThrowExceptionWhenUserUnauthorizedToEditScooter() {
        EditScooterRequest request = new EditScooterRequest(UUID.randomUUID(), "NewName", "NewBrand", "NewModel", "NewType");
        User unauthorizedUser = new User();
        unauthorizedUser.setUuid(UUID.randomUUID());
        testScooter.setUser(unauthorizedUser);

        when(scooterRepository.findByUuid(request.scooterUuid())).thenReturn(Optional.of(testScooter));

        assertThrows(RuntimeException.class, () -> scooterService.editScooter(request));
    }

    @Test
    void shouldDeleteScooterSuccessfully() {
        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        scooterService.deleteScooter(testScooter.getUuid());

        verify(scooterRepository, times(1)).delete(testScooter);
    }

    @Test
    void shouldThrowExceptionWhenUserUnauthorizedToDeleteScooter() {
        User unauthorizedUser = new User();
        unauthorizedUser.setUuid(UUID.randomUUID());
        testScooter.setUser(unauthorizedUser);

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        assertThrows(RuntimeException.class, () -> scooterService.deleteScooter(testScooter.getUuid()));
    }

    @Test
    void shouldReturnScooterDetailsSuccessfully() {
        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        ScooterDetailsResponse response = scooterService.getScooterDetails(testScooter.getUuid());

        assertEquals(testScooter.getName(), response.getName());
        assertEquals(testScooter.getBrand(), response.getBrand());
    }

    @Test
    void shouldReturnScooterDetailsExtendedSuccessfully() {
        Repair repair = new Repair();
        repair.setUuid(UUID.randomUUID());
        repair.setTitle("TestRepair");
        repair.setDescription("Fixing brake");

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));
        when(repairRepository.findAllRepairsByScooterUuid(testScooter.getUuid())).thenReturn(List.of(new RepairDetailsResponse(repair.getTitle(),
                                                                                                                               repair.getDescription(),
                                                                                                                               repair.getPrice(),
                                                                                                                               repair.getCreatedDate(),
                                                                                                                               testScooter.getUuid(),
                                                                                                                               repair.getUuid())));

        ScooterDetailsExtendedResponse response = scooterService.getScooterDetailsExtended(testScooter.getUuid());

        assertEquals(testScooter.getName(), response.getName());
        assertEquals(1,
                     response.getRepairs()
                             .size());
    }

    @Test
    void shouldReturnAllScootersForUser() {
        Pageable pageable = PageRequest.of(0, 5);
        when(scooterRepository.findAllByUserUuid(testUser.getUuid(), pageable)).thenReturn(new PageImpl<>(List.of(new ScooterListResponse(testScooter.getName(),
                                                                                                                                          testScooter.getCreatedDate(),
                                                                                                                                          testScooter.getUuid()))));

        Page<ScooterListResponse> allScooters = scooterService.getAllScooters(pageable);

        assertEquals(1, allScooters.getTotalElements());
    }

    @Test
    void shouldReturnScooterRepairsSuccessfully() {
        Repair repair = new Repair();
        repair.setUuid(UUID.randomUUID());
        repair.setTitle("TestRepair");

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));
        when(repairRepository.findAllRepairsByScooterUuid(testScooter.getUuid())).thenReturn(List.of(new RepairDetailsResponse(repair.getTitle(),
                                                                                                                               repair.getDescription(),
                                                                                                                               repair.getPrice(),
                                                                                                                               repair.getCreatedDate(),
                                                                                                                               testScooter.getUuid(),
                                                                                                                               repair.getUuid())));

        List<RepairDetailsResponse> repairs = scooterService.getScooterRepairs(testScooter.getUuid());

        assertEquals(1, repairs.size());
        assertEquals("TestRepair",
                     repairs.getFirst()
                            .title());
    }

    @Test
    void shouldNotAllowEditScooterByNonOwner() {
        setLoggedUser(otherUser);

        EditScooterRequest request = new EditScooterRequest(testScooter.getUuid(), "NewName", "NewBrand", "NewModel", "NewType");
        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        assertThrows(RuntimeException.class, () -> scooterService.editScooter(request), "Nie jesteś upoważniony do edycji tej hulajnogi");
    }

    @Test
    void shouldNotAllowDeleteScooterByNonOwner() {
        setLoggedUser(otherUser);

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        assertThrows(RuntimeException.class, () -> scooterService.deleteScooter(testScooter.getUuid()), "Nie jesteś upoważniony do usunięcia tej hulajnogi");
    }

    private void setLoggedUser(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
    }
}