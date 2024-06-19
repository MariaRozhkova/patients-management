package org.hospital.management.patients.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hospital.management.patients.dtos.PaginationTokenDto;
import org.hospital.management.patients.dtos.PaginationResponse;
import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import org.hospital.management.patients.entities.Patient;
import org.hospital.management.patients.exceptions.EntityNotFoundException;
import org.hospital.management.patients.repositories.PatientRepository;
import org.hospital.management.patients.services.PageTokenService;
import org.hospital.management.patients.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final String NOT_FOUND_ERROR_MESSAGE = "Patient with id = '%s' not found";

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final PageTokenService pageTokenService;

    @Override
    public PaginationResponse<PatientDto> findAll(String nextPageToken, int pageSize) {
        var sort = Sort.by(Patient.Fields.createdAt, Patient.Fields.id);
        var pageable = PageRequest.of(0, pageSize, sort);
        List<Patient> patients;
        if (StringUtils.isBlank(nextPageToken)) {
            patients = patientRepository.findAll(pageable).getContent();
        } else {
            var nextPageTokenDto = pageTokenService.decode(nextPageToken);
            patients = patientRepository
                .findAllByCreatedAtGreaterThanEqualAndIdGreaterThan(
                    nextPageTokenDto.date(),
                    nextPageTokenDto.id(),
                    pageable
                );
        }

        return createPaginationResponse(patients, pageSize);
    }

    @Override
    public PatientDto findById(UUID id) {
        return patientRepository.findById(id)
            .map(patient -> modelMapper.map(patient, PatientDto.class))
            .orElseThrow(
                () -> new EntityNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, id))
            );
    }

    @Override
    public PatientDto create(PatientCreateDto patientDto) {
        var patientToSave = modelMapper.map(patientDto, Patient.class);
        var savedPatient = patientRepository.save(patientToSave);

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto update(UUID id, PatientCreateDto patientToUpdate) {
        var savedPatient = patientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(
                NOT_FOUND_ERROR_MESSAGE,
                id
            )));
        savedPatient.setName(patientToUpdate.getName());
        savedPatient.setGender(patientToUpdate.getGender());
        savedPatient.setBirthDate(patientToUpdate.getBirthDate());

        patientRepository.save(savedPatient);

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto deleteById(UUID id) {
        var deletedPatient = findById(id);
        patientRepository.deleteById(id);

        return deletedPatient;
    }

    private PaginationResponse<PatientDto> createPaginationResponse(
        List<Patient> patients,
        int pageSize
    ) {
        var patientDtos = patients.stream()
            .map(patient -> modelMapper.map(patient, PatientDto.class))
            .collect(Collectors.toList());
        var size = patients.size();
        if (size == pageSize && CollectionUtils.isNotEmpty(patients)) {
            var lastPatient = patients.get(size - 1);
            var pageTokenDto = new PaginationTokenDto(
                lastPatient.getId(),
                lastPatient.getCreatedAt()
            );

            var nextPageToken = pageTokenService.encode(pageTokenDto);

            return new PaginationResponse<>(patientDtos, nextPageToken);
        }

        return new PaginationResponse<>(patientDtos, null);
    }
}
