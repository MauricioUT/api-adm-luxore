package mx.luxore.service;

import mx.luxore.dto.ImgDto;
import mx.luxore.dto.ImgReqDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ImgService {

    ResponseEntity<?> updateImg(int id, List<ImgReqDto> img) throws IOException;

    ResponseEntity<?> deleteImg(int id, ImgDto img);
}
