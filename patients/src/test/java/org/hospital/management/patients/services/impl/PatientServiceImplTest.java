package org.hospital.management.patients.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hospital.management.patients.dtos.PaginationResponse;
import org.hospital.management.patients.dtos.PaginationTokenDto;
import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import org.hospital.management.patients.entities.Patient;
import org.hospital.management.patients.enums.Gender;
import org.hospital.management.patients.exceptions.EntityNotFoundException;
import org.hospital.management.patients.repositories.PatientRepository;
import org.hospital.management.patients.services.PageTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "name";
    private static final LocalDateTime BIRTH_DATE = LocalDateTime.now();
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final String UPDATED_NAME = "name";
    private static final int PAGE_SIZE = 2;
    private static final String NEXT_PAGE_TOKEN = "token";

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PageTokenService pageTokenService;

    @Mock
    private Page<Patient> patientsPage;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void shouldFindPaginatedPatientsWhenPageTokenIsNull() {
        var patient = preparePatient(NAME);
        var patientDto = preparePatientDto(NAME);
        var sort = Sort.by(Patient.Fields.createdAt, Patient.Fields.id);
        var pageable = PageRequest.of(0, PAGE_SIZE, sort);

        when(patientRepository.findAll(pageable)).thenReturn(patientsPage);
        when(patientsPage.getContent()).thenReturn(List.of(patient));
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(patientDto);

        var actualResult = patientService.findAll(null, PAGE_SIZE);

        var expectedPaginationResponse = new PaginationResponse<>(List.of(patientDto), null);
        assertEquals(expectedPaginationResponse, actualResult);
        verifyNoInteractions(pageTokenService);
    }

    @Test
    void shouldFindPaginatedPatients() {
        var patient = preparePatient(NAME);
        var patientDto = preparePatientDto(NAME);

        var paginationTokenDto = new PaginationTokenDto(ID, CREATED_AT);
        when(pageTokenService.decode(NEXT_PAGE_TOKEN)).thenReturn(paginationTokenDto);
        when(patientRepository.findAllPageable(CREATED_AT, ID, PAGE_SIZE))
            .thenReturn(List.of(patient, patient));
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(patientDto);
        when(pageTokenService.encode(paginationTokenDto)).thenReturn(NEXT_PAGE_TOKEN);

        var actualResult = patientService.findAll(NEXT_PAGE_TOKEN, PAGE_SIZE);

        var expectedPaginationResponse = new PaginationResponse<>(
            List.of(patientDto, patientDto),
            NEXT_PAGE_TOKEN
        );
        assertEquals(expectedPaginationResponse, actualResult);
    }

    @Test
    void shouldFindPatientById() {
        var patient = preparePatient(NAME);
        var patientDto = preparePatientDto(NAME);
        when(patientRepository.findById(ID)).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(patientDto);

        var actualResult = patientService.findById(ID);

        assertEquals(patientDto, actualResult);
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        when(patientRepository.findById(ID)).thenReturn(Optional.empty());

        var actualResult = assertThrows(
            EntityNotFoundException.class,
            () -> patientService.findById(ID)
        );

        var expectedMessage = String.format("Patient with id = '%s' not found", ID);
        assertEquals(expectedMessage, actualResult.getMessage());
        verifyNoInteractions(modelMapper);
    }

    @Test
    void shouldCreatePatient() {
        var patientCreateDto = preparePatientCreateDto(NAME);
        var patient = preparePatient(NAME);
        var patientDto = preparePatientDto(NAME);
        when(modelMapper.map(patientCreateDto, Patient.class)).thenReturn(patient);
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(patientDto);
        when(patientRepository.save(patient)).thenReturn(patient);

        var actualResult = patientService.create(patientCreateDto);

        assertEquals(patientDto, actualResult);
    }

    @Test
    void shouldUpdatePatient() {
        var patient = preparePatient(NAME);
        var patientToUpdate = preparePatientCreateDto(UPDATED_NAME);
        var updatedPatient = preparePatient(UPDATED_NAME);
        var patientDto = preparePatientDto(UPDATED_NAME);

        when(patientRepository.findById(ID)).thenReturn(Optional.of(patient));
        when(modelMapper.map(updatedPatient, PatientDto.class)).thenReturn(patientDto);

        var actualResult = patientService.update(ID, patientToUpdate);

        assertEquals(patientDto, actualResult);
        verify(patientRepository).save(updatedPatient);
    }

    @Test
    void shouldNotUpdatePatientWhenNotFound() {
        var patientToUpdate = preparePatientCreateDto(UPDATED_NAME);

        when(patientRepository.findById(ID)).thenReturn(Optional.empty());

        var actualResult = assertThrows(
            EntityNotFoundException.class,
            () -> patientService.update(ID, patientToUpdate)
        );

        var expectedMessage = String.format("Patient with id = '%s' not found", ID);
        assertEquals(expectedMessage, actualResult.getMessage());

        verify(patientRepository, never()).save(any(Patient.class));
        verifyNoInteractions(modelMapper);
    }

    @Test
    void shouldDeletePatientById() {
        var patient = preparePatient(NAME);
        var patientDto = preparePatientDto(NAME);
        when(patientRepository.findById(ID)).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(patientDto);

        var actualResult = patientService.deleteById(ID);

        assertEquals(patientDto, actualResult);
        verify(patientRepository).deleteById(ID);
    }

    @Test
    void shouldNotDeleteWhenPatientNotFound() {
        when(patientRepository.findById(ID)).thenReturn(Optional.empty());

        var actualResult = assertThrows(
            EntityNotFoundException.class,
            () -> patientService.deleteById(ID)
        );

        var expectedMessage = String.format("Patient with id = '%s' not found", ID);
        assertEquals(expectedMessage, actualResult.getMessage());
        verifyNoInteractions(modelMapper);
        verify(patientRepository, never()).deleteById(ID);
    }

    private Patient preparePatient(String name) {
        return Patient.builder()
            .id(ID)
            .name(name)
            .birthDate(BIRTH_DATE)
            .gender(Gender.FEMALE)
            .createdAt(CREATED_AT)
            .build();
    }

    private PatientDto preparePatientDto(String name) {
        return PatientDto.builder()
            .id(ID)
            .name(name)
            .birthDate(BIRTH_DATE)
            .gender(Gender.FEMALE)
            .build();
    }

    private PatientCreateDto preparePatientCreateDto(String name) {
        return PatientCreateDto.builder()
            .name(name)
            .birthDate(BIRTH_DATE)
            .gender(Gender.FEMALE)
            .build();
    }
}
