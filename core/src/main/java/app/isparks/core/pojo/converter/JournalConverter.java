package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.JournalDTO;
import app.isparks.core.pojo.entity.Journal;
import app.isparks.core.pojo.param.JournalParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface JournalConverter {

    Journal map(JournalParam source);

    JournalDTO map(Journal source);

    List<JournalDTO> maps(List<Journal> source);

}
