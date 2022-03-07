package app.isparks.core.pojo.converter;


import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.entity.FFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FileConverter {


    FileDTO map(FFile source);

    List<FileDTO> maps(List<FFile> sources);

}
