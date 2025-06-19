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
import org.springframework.samples.petclinic.mapper.PetTypeMapper;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.rest.api.PettypesApi;
import org.springframework.samples.petclinic.rest.dto.PetTypeDto;
import org.springframework.samples.petclinic.rest.dto.PetTypeFieldsDto;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api")
public class PetTypeRestController implements PettypesApi {

    private static final Logger logger = LoggerFactory.getLogger(PetTypeRestController.class);

    private final ClinicService clinicService;
    private final PetTypeMapper petTypeMapper;


    public PetTypeRestController(ClinicService clinicService, PetTypeMapper petTypeMapper) {
        this.clinicService = clinicService;
        this.petTypeMapper = petTypeMapper;
    }

    @PreAuthorize("hasAnyRole(@roles.OWNER_ADMIN, @roles.VET_ADMIN)")
    @Override
    public ResponseEntity<List<PetTypeDto>> listPetTypes() {
        logger.debug("API request: list all pet types");
        List<PetType> petTypes = new ArrayList<>(this.clinicService.findAllPetTypes());
        if (petTypes.isEmpty()) {
            logger.warn("No pet types found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Returning {} pet types", petTypes.size());
        return new ResponseEntity<>(petTypeMapper.toPetTypeDtos(petTypes), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole(@roles.OWNER_ADMIN, @roles.VET_ADMIN)")
    @Override
    public ResponseEntity<PetTypeDto> getPetType(Integer petTypeId) {
        logger.debug("API request: get pet type with ID={}", petTypeId);
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            logger.warn("Pet type with ID={} not found", petTypeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Pet type with ID={} found successfully", petTypeId);
        return new ResponseEntity<>(petTypeMapper.toPetTypeDto(petType), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Override
    public ResponseEntity<PetTypeDto> addPetType(PetTypeFieldsDto petTypeFieldsDto) {
        logger.debug("API request: add new pet type");
        HttpHeaders headers = new HttpHeaders();
        final PetType type = petTypeMapper.toPetType(petTypeFieldsDto);
        this.clinicService.savePetType(type);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/pettypes/{id}").buildAndExpand(type.getId()).toUri());
        logger.info("Pet type created successfully with ID={}", type.getId());
        return new ResponseEntity<>(petTypeMapper.toPetTypeDto(type), headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Override
    public ResponseEntity<PetTypeDto> updatePetType(Integer petTypeId, PetTypeDto petTypeDto) {
        logger.debug("API request: update pet type with ID={}", petTypeId);
        PetType currentPetType = this.clinicService.findPetTypeById(petTypeId);
        if (currentPetType == null) {
            logger.warn("Pet type with ID={} not found for update", petTypeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentPetType.setName(petTypeDto.getName());
        this.clinicService.savePetType(currentPetType);
        logger.info("Pet type with ID={} updated successfully", petTypeId);
        return new ResponseEntity<>(petTypeMapper.toPetTypeDto(currentPetType), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Transactional
    @Override
    public ResponseEntity<PetTypeDto> deletePetType(Integer petTypeId) {
        logger.debug("API request: delete pet type with ID={}", petTypeId);
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            logger.warn("Pet type with ID={} not found for deletion", petTypeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deletePetType(petType);
        logger.info("Pet type with ID={} deleted successfully", petTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
