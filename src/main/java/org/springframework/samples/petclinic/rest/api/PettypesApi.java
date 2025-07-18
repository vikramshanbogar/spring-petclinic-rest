/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.13.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.springframework.samples.petclinic.rest.api;

import org.springframework.samples.petclinic.rest.dto.PetTypeDto;
import org.springframework.samples.petclinic.rest.dto.PetTypeFieldsDto;
import org.springframework.samples.petclinic.rest.dto.ProblemDetailDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-11T23:32:27.608942600+05:30[Asia/Calcutta]", comments = "Generator version: 7.13.0")
@Validated
@Tag(name = "pettypes", description = "Endpoints related to pet types.")
public interface PettypesApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /pettypes : Create a pet type
     * Creates a pet type .
     *
     * @param petTypeFieldsDto The pet type (required)
     * @return Pet type created successfully. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Pet Type not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "addPetType",
        summary = "Create a pet type",
        description = "Creates a pet type .",
        tags = { "pettypes" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Pet type created successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Pet Type not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/pettypes",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<PetTypeDto> addPetType(
        @Parameter(name = "PetTypeFieldsDto", description = "The pet type", required = true) @Valid @RequestBody PetTypeFieldsDto petTypeFieldsDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"cat\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /pettypes/{petTypeId} : Delete a pet type by ID
     * Returns the pet type or a 404 error.
     *
     * @param petTypeId The ID of the pet type. (required)
     * @return Pet type details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Pet type not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "deletePetType",
        summary = "Delete a pet type by ID",
        description = "Returns the pet type or a 404 error.",
        tags = { "pettypes" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Pet type details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Pet type not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/pettypes/{petTypeId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PetTypeDto> deletePetType(
        @Min(0) @Parameter(name = "petTypeId", description = "The ID of the pet type.", required = true, in = ParameterIn.PATH) @PathVariable("petTypeId") Integer petTypeId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"cat\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /pettypes/{petTypeId} : Get a pet type by ID
     * Returns the pet type or a 404 error.
     *
     * @param petTypeId The ID of the pet type. (required)
     * @return Pet type details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Pet Type not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "getPetType",
        summary = "Get a pet type by ID",
        description = "Returns the pet type or a 404 error.",
        tags = { "pettypes" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Pet type details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Pet Type not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/pettypes/{petTypeId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PetTypeDto> getPetType(
        @Min(0) @Parameter(name = "petTypeId", description = "The ID of the pet type.", required = true, in = ParameterIn.PATH) @PathVariable("petTypeId") Integer petTypeId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"cat\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /pettypes : Lists pet types
     * Returns an array of pet types.
     *
     * @return Pet types found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "listPetTypes",
        summary = "Lists pet types",
        description = "Returns an array of pet types.",
        tags = { "pettypes" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Pet types found and returned.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PetTypeDto.class)))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/pettypes",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<PetTypeDto>> listPetTypes(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"name\" : \"cat\", \"id\" : 1 }, { \"name\" : \"cat\", \"id\" : 1 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /pettypes/{petTypeId} : Update a pet type by ID
     * Returns the pet type or a 404 error.
     *
     * @param petTypeId The ID of the pet type. (required)
     * @param petTypeDto The pet type (required)
     * @return Pet type details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Pet Type not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "updatePetType",
        summary = "Update a pet type by ID",
        description = "Returns the pet type or a 404 error.",
        tags = { "pettypes" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Pet type details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Pet Type not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/pettypes/{petTypeId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<PetTypeDto> updatePetType(
        @Min(0) @Parameter(name = "petTypeId", description = "The ID of the pet type.", required = true, in = ParameterIn.PATH) @PathVariable("petTypeId") Integer petTypeId,
        @Parameter(name = "PetTypeDto", description = "The pet type", required = true) @Valid @RequestBody PetTypeDto petTypeDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"cat\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"detail\" : \"No static resource api/owner.\", \"type\" : \"http://localhost:9966/petclinic/api/owner\", \"title\" : \"NoResourceFoundException\", \"status\" : 500, \"timestamp\" : \"2024-11-23T13:59:21.3820407Z\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
