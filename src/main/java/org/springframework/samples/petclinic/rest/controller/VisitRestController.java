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
import org.springframework.samples.petclinic.mapper.VisitMapper;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.rest.api.VisitsApi;
import org.springframework.samples.petclinic.rest.dto.VisitDto;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Fedoriv
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api")
public class VisitRestController implements VisitsApi {

    private static final Logger logger = LoggerFactory.getLogger(VisitRestController.class);

    private final ClinicService clinicService;

    private final VisitMapper visitMapper;

    public VisitRestController(ClinicService clinicService, VisitMapper visitMapper) {
        this.clinicService = clinicService;
        this.visitMapper = visitMapper;
    }


    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<List<VisitDto>> listVisits() {
        logger.debug("API request: list all visits");
        List<Visit> visits = new ArrayList<>(this.clinicService.findAllVisits());
        if (visits.isEmpty()) {
            logger.warn("No visits found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Returning {} visits", visits.size());
        return new ResponseEntity<>(new ArrayList<>(visitMapper.toVisitsDto(visits)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<VisitDto> getVisit( Integer visitId) {
        logger.debug("API request: get visit with ID={}", visitId);
        Visit visit = this.clinicService.findVisitById(visitId);
        if (visit == null) {
            logger.warn("Visit with ID={} not found", visitId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Visit with ID={} found successfully", visitId);
        return new ResponseEntity<>(visitMapper.toVisitDto(visit), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<VisitDto> addVisit(VisitDto visitDto) {
        logger.debug("API request: add new visit");
        HttpHeaders headers = new HttpHeaders();
        Visit visit = visitMapper.toVisit(visitDto);
        this.clinicService.saveVisit(visit);
        visitDto = visitMapper.toVisitDto(visit);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/visits/{id}").buildAndExpand(visit.getId()).toUri());
        logger.info("Visit created successfully with ID={}", visit.getId());
        return new ResponseEntity<>(visitDto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Override
    public ResponseEntity<VisitDto> updateVisit(Integer visitId, VisitDto visitDto) {
        logger.debug("API request: update visit with ID={}", visitId);
        Visit currentVisit = this.clinicService.findVisitById(visitId);
        if (currentVisit == null) {
            logger.warn("Visit with ID={} not found for update", visitId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentVisit.setDate(visitDto.getDate());
        currentVisit.setDescription(visitDto.getDescription());
        this.clinicService.saveVisit(currentVisit);
        logger.info("Visit with ID={} updated successfully", visitId);
        return new ResponseEntity<>(visitMapper.toVisitDto(currentVisit), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @Transactional
    @Override
    public ResponseEntity<VisitDto> deleteVisit(Integer visitId) {
        logger.debug("API request: delete visit with ID={}", visitId);
        Visit visit = this.clinicService.findVisitById(visitId);
        if (visit == null) {
            logger.warn("Visit with ID={} not found for deletion", visitId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteVisit(visit);
        logger.info("Visit with ID={} deleted successfully", visitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
