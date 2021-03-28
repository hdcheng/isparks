package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.entity.FFile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class FileConverterImpl implements FileConverter {

    @Override
    public FileDTO map(FFile source) {
        if ( source == null ) {
            return null;
        }

        FileDTO fileDTO = new FileDTO();

        fileDTO.setId( source.getId() );
        fileDTO.setModifyTime( source.getModifyTime() );
        fileDTO.setCreateTime( source.getCreateTime() );
        fileDTO.setName( source.getName() );
        fileDTO.setOrigin( source.getOrigin() );
        fileDTO.setMediaType( source.getMediaType() );
        fileDTO.setFileType( source.getFileType() );
        fileDTO.setLocation( source.getLocation() );

        return fileDTO;
    }

    @Override
    public List<FileDTO> maps(List<FFile> sources) {
        if ( sources == null ) {
            return null;
        }

        List<FileDTO> list = new ArrayList<FileDTO>( sources.size() );
        for ( FFile fFile : sources ) {
            list.add( map( fFile ) );
        }

        return list;
    }
}
