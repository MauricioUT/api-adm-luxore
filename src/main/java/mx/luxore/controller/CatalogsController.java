package mx.luxore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mx.luxore.dto.CatalogDto;
import mx.luxore.dto.CityDto;
import mx.luxore.dto.ColonyDto;
import mx.luxore.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogsController {

    @Autowired
    private CatalogsService catalogsService;

    @Operation(summary = "get Catalogs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @GetMapping(value = "/{catalog}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCatalog(@PathVariable String catalog) {
        return catalogsService.getCatalogs(catalog);
    }

    @Operation(summary = "get Amenities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/amenities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAmenitiesPageable(@RequestBody CatalogDto pageable) {
        return catalogsService.getAmenityPageable(pageable);
    }

    @Operation(summary = "get city by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/cities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCitiesByState(@RequestBody CityDto state) {
        return catalogsService.getCityPageable(state);
    }

    @Operation(summary = "get colonies by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/colonies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getColonyPageable(@RequestBody ColonyDto city) {
        return catalogsService.getColonyPageable(city);
    }

    @Operation(summary = "add amenity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/addAmenity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAmenity(@RequestBody CatalogDto amenity) {
        return catalogsService.addAmenity(amenity);
    }

    @Operation(summary = "add property type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/addPropertyType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPropertyType(@RequestBody CatalogDto property) {
        return catalogsService.addPropertyType(property);
    }

    @Operation(summary = "add city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/addCity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto) {
        return catalogsService.addCity(cityDto);
    }

    @Operation(summary = "add colony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/addColony", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addColony(@RequestBody ColonyDto colonyDto) {
        return catalogsService.addColony(colonyDto);
    }

    @Operation(summary = "update amenity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/updateAmenity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAmenity(@RequestBody CatalogDto amenity) {
        return catalogsService.updateAmenity(amenity);
    }

    @Operation(summary = "update property type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/updatePropertyType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePropertyType(@RequestBody CatalogDto property) {
        return catalogsService.updatePropertyType(property);
    }

    @Operation(summary = "update city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/updateCity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCity(@RequestBody CityDto cityDto) {
        return catalogsService.updateCity(cityDto);
    }

    @Operation(summary = "update colony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")  })
    @PostMapping(value = "/updateColony", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateColony(@RequestBody ColonyDto colonyDto) {
        return catalogsService.updateColony(colonyDto);
    }
}
