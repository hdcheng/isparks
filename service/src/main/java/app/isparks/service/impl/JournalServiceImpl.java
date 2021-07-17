package app.isparks.service.impl;

import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.JournalConverter;
import app.isparks.core.pojo.dto.JournalDTO;
import app.isparks.core.pojo.entity.Journal;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.JournalParam;
import app.isparks.core.service.IJournalService;
import app.isparks.dao.repository.AbstractJournalCurd;
import app.isparks.service.base.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JournalServiceImpl extends AbstractService<Journal> implements IJournalService {

    private Logger log = LoggerFactory.getLogger(JournalServiceImpl.class);

    private final JournalConverter CONVERTER = ConverterFactory.get(JournalConverter.class);

    private AbstractJournalCurd journalCurd;

    public JournalServiceImpl(AbstractJournalCurd journalCurd) {
        super(journalCurd);
        this.journalCurd = journalCurd;
    }

    @Override
    public Optional<JournalDTO> create(JournalParam param, DataStatus status) {
        notNull(param,"journal parameter must not be null.");

        Journal journal = CONVERTER.map(param);
        journal.setStatus(status);

        return abstractInsert(journal).isPresent() ? Optional.of(CONVERTER.map(journal)) : Optional.empty();
    }

    @Override
    public Optional<JournalDTO> deleteById(String id) {

        Journal journal = abstractDelete(id).orElse(null);

        return journal == null ? Optional.empty() : Optional.of(CONVERTER.map(journal));
    }

    @Override
    public PageData<JournalDTO> page(int page, int size) {

        PageData<Journal> journalPageData = abstractPageAll(page,size);

        return journalPageData.convertData(journal -> CONVERTER.map(journal));
    }
}
