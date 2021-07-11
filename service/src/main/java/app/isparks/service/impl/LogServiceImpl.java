package app.isparks.service.impl;

import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.LogConverter;
import app.isparks.core.pojo.dto.LogDTO;
import app.isparks.core.pojo.entity.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.service.ILogService;
import app.isparks.dao.repository.AbstractLogCurd;
import app.isparks.dao.repository.impl.LogCurdImpl;
import app.isparks.service.base.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenghd
 * @date 2020/7/25
 */
@Service
public class LogServiceImpl extends AbstractService<Log> implements ILogService {

    private Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    LogConverter CONVERTER = ConverterFactory.get(LogConverter.class);

    private AbstractLogCurd logCurd;

    public LogServiceImpl(LogCurdImpl logCurd) {
        super(logCurd);
        this.logCurd = logCurd;
    }

    @Override
    @Async
    public void create(Log log) {
        notNull(log, "log must not be null");
        abstractInsert(log);
    }


    @Override
    public PageData<LogDTO> page(int page, int size) {
        PageInfo pageInfo = new PageInfo(page, size);

        PageData<Log> pageData = logCurd.pageAll(pageInfo);
        List<Log> logs = pageData.getData();

        PageData<LogDTO> result = new PageData<>().withPageInfo(pageData).withData(toDTOs(logs));

        return result;
    }

    @Override
    public PageData<LogDTO> pageByTypes(int page, int size,LogType ... types) {
        if(types.length == 0){
            return page(page,size);
        }

        PageInfo pageInfo = new PageInfo(page, size);

        PageData<Log> pageData = logCurd.pageByType(pageInfo,LogType.types(types));
        List<Log> logs = pageData.getData();
        PageData<LogDTO> result = new PageData<>().withPageInfo(pageData).withData(toDTOs(logs));

        return result;
    }

    @Override
    public Optional<List<LogDTO>> findNumber(int num) {
        if (num <= 0) {
            return Optional.empty();
        }
        PageInfo pageInfo = new PageInfo(1, num);
        List<Log> logs = logCurd.pageAll(pageInfo).getData();
        return Optional.ofNullable(toDTOs(logs));
    }

    @Override
    public Map<String,String> logTypes() {
        Map<String,String> types = new HashMap<>();
        for(LogType type : LogType.values()){
            types.put(type.name(),type.getType());
        }
        return types;
    }

    @Override
    public long countAll() {
        return abstractCount();
    }

    @Override
    public PageData<LogDTO> pageAll(int page, int size) {

        PageData<Log> log = abstractPageAll(page,size);

        PageData<LogDTO> dto = log.convertData((l) -> CONVERTER.map(l));

        return dto;
    }

    private List<LogDTO> toDTOs(List<Log> source) {
        return CONVERTER.maps(source);
    }

}
