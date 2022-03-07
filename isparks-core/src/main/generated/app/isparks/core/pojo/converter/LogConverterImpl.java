package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.LogDTO;
import app.isparks.core.pojo.entity.Log;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class LogConverterImpl implements LogConverter {

    @Override
    public LogDTO map(Log source) {
        if ( source == null ) {
            return null;
        }

        LogDTO logDTO = new LogDTO();

        logDTO.setId( source.getId() );
        logDTO.setModifyTime( source.getModifyTime() );
        logDTO.setCreateTime( source.getCreateTime() );
        logDTO.setType( source.getType() );
        logDTO.setContent( source.getContent() );
        logDTO.setIp( source.getIp() );
        logDTO.setDate( source.getDate() );

        return logDTO;
    }

    @Override
    public List<LogDTO> maps(List<Log> source) {
        if ( source == null ) {
            return null;
        }

        List<LogDTO> list = new ArrayList<LogDTO>( source.size() );
        for ( Log log : source ) {
            list.add( map( log ) );
        }

        return list;
    }
}
