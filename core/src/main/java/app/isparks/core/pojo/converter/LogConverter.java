package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.LogDTO;
import app.isparks.core.pojo.entity.Log;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface LogConverter {

    LogDTO map(Log source);

    List<LogDTO> maps(List<Log> source);
}
