/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.mapper.OwnerMapper;
import org.springframework.samples.petclinic.mapper.PetMapper;
import org.springframework.samples.petclinic.mapper.VisitMapper;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.rest.api.OwnersApi;
import org.springframework.samples.petclinic.rest.dto.*;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Vitaliy Fedoriv
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class OwnerRestController implements OwnersApi {

    private static final Logger logger = LoggerFactory.getLogger(OwnerRestController.class);

    private final ClinicService clinicService;

    private final OwnerMapper ownerMapper;

    private final PetMapper petMapper;

    private final VisitMapper visitMapper;

    public OwnerRestController(ClinicService clinicService,
                               OwnerMapper ownerMapper,
                               PetMapper petMapper,
                               VisitMapper visitMapper) {
        this.clinicService = clinicService;
        this.ownerMapper = ownerMapper;
        this.petMapper = petMapper;
        this.visitMapper = visitMapper;
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<List<OwnerDto>> listOwners(String lastName) {
        logger.debug("API request: list owners with lastName={}", lastName);
        Collection<Owner> owners;
        if (lastName != null) {
            owners = this.clinicService.findOwnerByLastName(lastName);
        } else {
            owners = this.clinicService.findAllOwners();
        }
        if (owners.isEmpty()) {
            logger.warn("No owners found for lastName={}", lastName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Returning {} owners", owners.size());
        return new ResponseEntity<>(ownerMapper.toOwnerDtoCollection(owners), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<OwnerDto> getOwner(Integer ownerId) {
        logger.debug("API request: get owner with ID={}", ownerId);
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner == null) {
            logger.warn("Owner with ID={} not found", ownerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Owner with ID={} found successfully", ownerId);
        return new ResponseEntity<>(ownerMapper.toOwnerDto(owner), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<OwnerDto> addOwner(OwnerFieldsDto ownerFieldsDto) {
        logger.debug("API request: add new owner");
        HttpHeaders headers = new HttpHeaders();
        Owner owner = ownerMapper.toOwner(ownerFieldsDto);
        this.clinicService.saveOwner(owner);
        OwnerDto ownerDto = ownerMapper.toOwnerDto(owner);
        headers.setLocation(UriComponentsBuilder.newInstance()
            .path("/api/owners/{id}").buildAndExpand(owner.getId()).toUri());
        logger.info("Owner created successfully with ID={}", owner.getId());
        return new ResponseEntity<>(ownerDto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<OwnerDto> updateOwner(Integer ownerId, OwnerFieldsDto ownerFieldsDto) {
        logger.debug("API request: update owner with ID={}", ownerId);
        Owner currentOwner = this.clinicService.findOwnerById(ownerId);
        if (currentOwner == null) {
            logger.warn("Owner with ID={} not found for update", ownerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentOwner.setAddress(ownerFieldsDto.getAddress());
        currentOwner.setCity(ownerFieldsDto.getCity());
        currentOwner.setFirstName(ownerFieldsDto.getFirstName());
        currentOwner.setLastName(ownerFieldsDto.getLastName());
        currentOwner.setTelephone(ownerFieldsDto.getTelephone());
        this.clinicService.saveOwner(currentOwner);
        logger.info("Owner with ID={} updated successfully", ownerId);
        return new ResponseEntity<>(ownerMapper.toOwnerDto(currentOwner), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Transactional
    @Override
    public ResponseEntity<OwnerDto> deleteOwner(Integer ownerId) {
        logger.debug("API request: delete owner with ID={}", ownerId);
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner == null) {
            logger.warn("Owner with ID={} not found for deletion", ownerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteOwner(owner);
        logger.info("Owner with ID={} deleted successfully", ownerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<PetDto> addPetToOwner(Integer ownerId, PetFieldsDto petFieldsDto) {
        logger.debug("API request: add pet to owner with ID={}", ownerId);
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner == null) {
            logger.warn("Owner with ID={} not found when adding pet", ownerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        HttpHeaders headers = new HttpHeaders();
        Pet pet = petMapper.toPet(petFieldsDto);
        owner.setId(ownerId);
        pet.setOwner(owner);
        pet.getType().setName(null);
        this.clinicService.savePet(pet);
        PetDto petDto = petMapper.toPetDto(pet);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/pets/{id}")
            .buildAndExpand(pet.getId()).toUri());
        logger.info("Pet with ID={} added to owner with ID={}", pet.getId(), ownerId);
        return new ResponseEntity<>(petDto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<Void> updateOwnersPet(Integer ownerId, Integer petId, PetFieldsDto petFieldsDto) {
        logger.debug("API request: update pet with ID={} for owner with ID={}", petId, ownerId);
        Owner currentOwner = this.clinicService.findOwnerById(ownerId);
        if (currentOwner != null) {
            Pet currentPet = this.clinicService.findPetById(petId);
            if (currentPet != null) {
                currentPet.setBirthDate(petFieldsDto.getBirthDate());
                currentPet.setName(petFieldsDto.getName());
                currentPet.setType(petMapper.toPetType(petFieldsDto.getType()));
                this.clinicService.savePet(currentPet);
                logger.info("Pet with ID={} updated successfully for owner with ID={}", petId, ownerId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.warn("Pet with ID={} not found for owner with ID={}", petId, ownerId);
            }
        } else {
            logger.warn("Owner with ID={} not found when updating pet", ownerId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<VisitDto> addVisitToOwner(Integer ownerId, Integer petId, VisitFieldsDto visitFieldsDto) {
        logger.debug("API request: add visit for pet with ID={} and owner with ID={}", petId, ownerId);
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner == null) {
            logger.warn("Owner with ID={} not found when adding visit", ownerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Pet pet = this.clinicService.findPetById(petId);
        if (pet == null) {
            logger.warn("Pet with ID={} not found when adding visit", petId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        HttpHeaders headers = new HttpHeaders();
        Visit visit = visitMapper.toVisit(visitFieldsDto);
        pet.setId(petId);
        visit.setPet(pet);
        this.clinicService.saveVisit(visit);
        VisitDto visitDto = visitMapper.toVisitDto(visit);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/visits/{id}")
            .buildAndExpand(visit.getId()).toUri());
        logger.info("Visit with ID={} added for pet with ID={}", visit.getId(), petId);
        return new ResponseEntity<>(visitDto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<PetDto> getOwnersPet(Integer ownerId, Integer petId) {
        logger.debug("API request: get pet with ID={} for owner with ID={}", petId, ownerId);
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner != null) {
            Pet pet = owner.getPet(petId);
            if (pet != null) {
                logger.debug("Pet with ID={} found for owner with ID={}", petId, ownerId);
                return new ResponseEntity<>(petMapper.toPetDto(pet), HttpStatus.OK);
            } else {
                logger.warn("Pet with ID={} not found for owner with ID={}", petId, ownerId);
            }
        } else {
            logger.warn("Owner with ID={} not found when retrieving pet", ownerId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}