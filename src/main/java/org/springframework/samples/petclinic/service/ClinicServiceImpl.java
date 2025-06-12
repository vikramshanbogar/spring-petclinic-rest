/*
 * Copyright 2002-2017 the original author or authors.
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
package org.springframework.samples.petclinic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@Service
public class ClinicServiceImpl implements ClinicService {

    private static final Logger logger = LoggerFactory.getLogger(ClinicServiceImpl.class);

    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final SpecialtyRepository specialtyRepository;
    private final PetTypeRepository petTypeRepository;

    @Autowired
    public ClinicServiceImpl(
        PetRepository petRepository,
        VetRepository vetRepository,
        OwnerRepository ownerRepository,
        VisitRepository visitRepository,
        SpecialtyRepository specialtyRepository,
        PetTypeRepository petTypeRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.specialtyRepository = specialtyRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pet> findAllPets() throws DataAccessException {
        logger.debug("Finding all pets");
        return petRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePet(Pet pet) throws DataAccessException {
        logger.debug("Deleting pet: {}", pet.getId());
        petRepository.delete(pet);
        logger.info("Pet with ID {} successfully deleted", pet.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Visit findVisitById(int visitId) throws DataAccessException {
        logger.debug("Finding visit with ID: {}", visitId);
        Visit visit = findEntityById(() -> visitRepository.findById(visitId));
        if (visit == null) {
            logger.warn("Visit with ID {} not found", visitId);
        }
        return visit;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Visit> findAllVisits() throws DataAccessException {
        logger.debug("Finding all visits");
        return visitRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteVisit(Visit visit) throws DataAccessException {
        logger.debug("Deleting visit: {}", visit.getId());
        visitRepository.delete(visit);
        logger.info("Visit with ID {} successfully deleted", visit.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Vet findVetById(int id) throws DataAccessException {
        logger.debug("Finding vet with ID: {}", id);
        Vet vet = findEntityById(() -> vetRepository.findById(id));
        if (vet == null) {
            logger.warn("Vet with ID {} not found", id);
        }
        return vet;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Vet> findAllVets() throws DataAccessException {
        logger.debug("Finding all vets");
        return vetRepository.findAll();
    }

    @Override
    @Transactional
    public void saveVet(Vet vet) throws DataAccessException {
        logger.debug("Saving vet: {}", vet.getId() == null ? "new" : vet.getId());
        vetRepository.save(vet);
        logger.info("Vet {} successfully saved", vet.getId());
    }

    @Override
    @Transactional
    public void deleteVet(Vet vet) throws DataAccessException {
        logger.debug("Deleting vet: {}", vet.getId());
        vetRepository.delete(vet);
        logger.info("Vet with ID {} successfully deleted", vet.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findAllOwners() throws DataAccessException {
        logger.debug("Finding all owners");
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOwner(Owner owner) throws DataAccessException {
        logger.debug("Deleting owner: {}", owner.getId());
        ownerRepository.delete(owner);
        logger.info("Owner with ID {} successfully deleted", owner.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public PetType findPetTypeById(int petTypeId) {
        logger.debug("Finding pet type with ID: {}", petTypeId);
        PetType petType = findEntityById(() -> petTypeRepository.findById(petTypeId));
        if (petType == null) {
            logger.warn("Pet type with ID {} not found", petTypeId);
        }
        return petType;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findAllPetTypes() throws DataAccessException {
        logger.debug("Finding all pet types");
        return petTypeRepository.findAll();
    }

    @Override
    @Transactional
    public void savePetType(PetType petType) throws DataAccessException {
        logger.debug("Saving pet type: {}", petType.getId() == null ? "new" : petType.getId());
        petTypeRepository.save(petType);
        logger.info("Pet type {} successfully saved", petType.getId());
    }

    @Override
    @Transactional
    public void deletePetType(PetType petType) throws DataAccessException {
        logger.debug("Deleting pet type: {}", petType.getId());
        petTypeRepository.delete(petType);
        logger.info("Pet type with ID {} successfully deleted", petType.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Specialty findSpecialtyById(int specialtyId) {
        logger.debug("Finding specialty with ID: {}", specialtyId);
        Specialty specialty = findEntityById(() -> specialtyRepository.findById(specialtyId));
        if (specialty == null) {
            logger.warn("Specialty with ID {} not found", specialtyId);
        }
        return specialty;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Specialty> findAllSpecialties() throws DataAccessException {
        logger.debug("Finding all specialties");
        return specialtyRepository.findAll();
    }

    @Override
    @Transactional
    public void saveSpecialty(Specialty specialty) throws DataAccessException {
        logger.debug("Saving specialty: {}", specialty.getId() == null ? "new" : specialty.getId());
        specialtyRepository.save(specialty);
        logger.info("Specialty {} successfully saved", specialty.getId());
    }

    @Override
    @Transactional
    public void deleteSpecialty(Specialty specialty) throws DataAccessException {
        logger.debug("Deleting specialty: {}", specialty.getId());
        specialtyRepository.delete(specialty);
        logger.info("Specialty with ID {} successfully deleted", specialty.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        logger.debug("Finding pet types");
        return petRepository.findPetTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        logger.debug("Finding owner with ID: {}", id);
        Owner owner = findEntityById(() -> ownerRepository.findById(id));
        if (owner == null) {
            logger.warn("Owner with ID {} not found", id);
        }
        return owner;
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        logger.debug("Finding pet with ID: {}", id);
        Pet pet = findEntityById(() -> petRepository.findById(id));
        if (pet == null) {
            logger.warn("Pet with ID {} not found", id);
        }
        return pet;
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        logger.debug("Saving pet: {}", pet.getId() == null ? "new" : pet.getId());
        pet.setType(findPetTypeById(pet.getType().getId()));
        petRepository.save(pet);
        logger.info("Pet {} successfully saved", pet.getId());
    }

    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        logger.debug("Saving visit: {}", visit.getId() == null ? "new" : visit.getId());
        visitRepository.save(visit);
        logger.info("Visit {} successfully saved", visit.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Vet> findVets() throws DataAccessException {
        logger.debug("Finding all vets");
        return vetRepository.findAll();
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        logger.debug("Saving owner: {}", owner.getId() == null ? "new" : owner.getId());
        ownerRepository.save(owner);
        logger.info("Owner {} successfully saved", owner.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        logger.debug("Finding owners by last name: {}", lastName);
        Collection<Owner> owners = ownerRepository.findByLastName(lastName);
        if (owners.isEmpty()) {
            logger.warn("No owners found with last name: {}", lastName);
        } else {
            logger.debug("Found {} owners with last name: {}", owners.size(), lastName);
        }
        return owners;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Visit> findVisitsByPetId(int petId) {
        logger.debug("Finding visits for pet ID: {}", petId);
        return visitRepository.findByPetId(petId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialty> findSpecialtiesByNameIn(Set<String> names) {
        logger.debug("Finding specialties by names: {}", names);
        return findEntityById(() -> specialtyRepository.findSpecialtiesByNameIn(names));
    }

    private <T> T findEntityById(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            logger.error("Entity retrieval failed", e);
            // Just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
    }
}