package pl.scoottrack.repair;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.scoottrack.repair.model.Repair;
import pl.scoottrack.repair.model.dto.AddRepairRequest;
import pl.scoottrack.repair.model.dto.EditRepairRequest;
import pl.scoottrack.repair.repository.RepairRepository;
import pl.scoottrack.repair.service.impl.RepairServiceImpl;
import pl.scoottrack.scooter.model.Scooter;
import pl.scoottrack.scooter.repository.ScooterRepository;
import pl.scoottrack.user.model.User;

import java.math.BigDecimal;
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
class RepairServiceImplTest {

    @Mock
    private RepairRepository repairRepository;

    @Mock
    private ScooterRepository scooterRepository;

    @InjectMocks
    private RepairServiceImpl repairService;

    private User otherUser;
    private Scooter testScooter;
    private Repair testRepair;

    @BeforeEach
    void setUp() {
        User testUser = new User();
        testUser.setUuid(UUID.randomUUID());
        testUser.setId(1L);

        otherUser = new User();
        otherUser.setUuid(UUID.randomUUID());

        testScooter = new Scooter();
        testScooter.setUuid(UUID.randomUUID());
        testScooter.setUser(testUser);

        testRepair = new Repair();
        testRepair.setUuid(UUID.randomUUID());
        testRepair.setScooter(testScooter);
        testRepair.setCreatedBy(testUser.getId());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(testUser, null, List.of());
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAddRepairByOwner() {
        AddRepairRequest request = new AddRepairRequest(testScooter.getUuid(), "New Repair", "Description", BigDecimal.valueOf(100));

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        repairService.addRepair(request);

        verify(repairRepository).save(any(Repair.class));
        verify(repairRepository, times(1)).save(any());
    }

    @Test
    void shouldNotAllowAddRepairByNonOwner() {
        setLoggedUser(otherUser);

        AddRepairRequest request = new AddRepairRequest(testScooter.getUuid(), "New Repair", "Description", BigDecimal.valueOf(100));

        when(scooterRepository.findByUuid(testScooter.getUuid())).thenReturn(Optional.of(testScooter));

        assertThrows(RuntimeException.class, () -> repairService.addRepair(request), "Nie jesteś upoważniony do dodania naprawy do tej hulajnogi");
    }

    @Test
    void shouldEditRepairByOwner() {
        EditRepairRequest request = new EditRepairRequest(testRepair.getUuid(), "Updated Repair", "Updated Description", BigDecimal.valueOf(150));

        when(repairRepository.findByUuid(testRepair.getUuid())).thenReturn(Optional.of(testRepair));

        repairService.editRepair(request);

        verify(repairRepository).save(testRepair);
        assertEquals("Updated Repair", testRepair.getTitle());
        assertEquals("Updated Description", testRepair.getDescription());
        assertEquals(BigDecimal.valueOf(150), testRepair.getPrice());
    }

    @Test
    void shouldNotAllowEditRepairByNonOwner() {
        setLoggedUser(otherUser);

        EditRepairRequest request = new EditRepairRequest(testRepair.getUuid(), "Updated Repair", "Updated Description", BigDecimal.valueOf(150));

        when(repairRepository.findByUuid(testRepair.getUuid())).thenReturn(Optional.of(testRepair));

        assertThrows(RuntimeException.class, () -> repairService.editRepair(request), "Nie jesteś upoważniony do edycji tej naprawy");
    }

    @Test
    void shouldDeleteRepairByOwner() {
        when(repairRepository.findByUuid(testRepair.getUuid())).thenReturn(Optional.of(testRepair));

        repairService.deleteRepair(testRepair.getUuid());

        verify(repairRepository).delete(testRepair);
    }

    @Test
    void shouldNotAllowDeleteRepairByNonOwner() {
        setLoggedUser(otherUser);

        when(repairRepository.findByUuid(testRepair.getUuid())).thenReturn(Optional.of(testRepair));

        assertThrows(RuntimeException.class, () -> repairService.deleteRepair(testRepair.getUuid()), "Nie jesteś upoważniony do usunięcia tej naprawy");
    }

    private void setLoggedUser(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
    }
}