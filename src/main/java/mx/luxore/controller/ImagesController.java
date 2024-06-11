package mx.luxore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mx.luxore.dto.ImgDto;
import mx.luxore.dto.ImgReqDto;
import mx.luxore.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    @Autowired
    private ImgService service;

    @Operation(summary = "delete property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @DeleteMapping(value = "/{idProperty}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProperty(@PathVariable int idProperty, @RequestBody ImgDto img) {
        return service.deleteImg(idProperty, img);
    }

    @Operation(summary = "update property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping(value = "/{idProperty}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProperty(@PathVariable int idProperty, @RequestBody ImgReqDto img) throws IOException {
        return service.updateImg(idProperty, img);
    }
}
