package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.JournalDTO;
import app.isparks.core.pojo.entity.Journal;
import app.isparks.core.pojo.param.JournalParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:00+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class JournalConverterImpl implements JournalConverter {

    @Override
    public Journal map(JournalParam source) {
        if ( source == null ) {
            return null;
        }

        Journal journal = new Journal();

        journal.setContent( source.getContent() );

        return journal;
    }

    @Override
    public JournalDTO map(Journal source) {
        if ( source == null ) {
            return null;
        }

        JournalDTO journalDTO = new JournalDTO();

        journalDTO.setId( source.getId() );
        journalDTO.setModifyTime( source.getModifyTime() );
        journalDTO.setCreateTime( source.getCreateTime() );
        journalDTO.setContent( source.getContent() );

        return journalDTO;
    }

    @Override
    public List<JournalDTO> maps(List<Journal> source) {
        if ( source == null ) {
            return null;
        }

        List<JournalDTO> list = new ArrayList<JournalDTO>( source.size() );
        for ( Journal journal : source ) {
            list.add( map( journal ) );
        }

        return list;
    }
}
