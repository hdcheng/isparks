package app.isparks.service.impl;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.LinkConverter;
import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.entity.Link;
import app.isparks.core.pojo.enums.IEnum;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.pojo.param.LinkParam;
import app.isparks.core.service.ILinkService;
import app.isparks.core.util.StringUtils;
import app.isparks.dao.repository.LinkCurd;
import app.isparks.dao.repository.impl.LinkCurdImpl;
import app.isparks.service.base.AbstractService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/13
 */
@Service
public class LinkServiceImpl extends AbstractService<Link> implements ILinkService {

    private LinkConverter CONVERTER = ConverterFactory.get(LinkConverter.class);

    private LinkCurd linkCurd;

    public LinkServiceImpl(LinkCurdImpl linkCurd){
        super(linkCurd);
        this.linkCurd = linkCurd;
    }

    @Override
    public Optional<LinkDTO> save(LinkParam param, LinkType type) {
        notNull(param,"link param must not be null.");

        if(type == null){
            type = IEnum.codeToEnum(LinkType.class,param.getType());
        }
        notNull(type,"link type must not be null.");

        if(StringUtils.hasEmpty(param.getUrl(),param.getLogo(),param.getName())){
            return Optional.empty();
        }

        Link link = CONVERTER.map(param);
        link.setType(type.getCode());

        verityUrl(link);

        return saveIfUrlAbsent(link);
    }

    @Override
    public Optional<LinkDTO> update(LinkParam param) {
        notNull(param,"link param must not be null.");
        notEmpty(param.getId(),"link id must not be empty.");

        Link link = CONVERTER.map(param);

        verityUrl(link);

        abstractUpdate(link);

        return Optional.ofNullable(converter(link));
    }

    @Override
    public Optional<LinkDTO> delete(String id) {
        notEmpty(id,"link id must not be null");
        Optional<Link> link = abstractDelete(id);
        return converter(link);
    }

    @Override
    public List<LinkDTO> listByType(LinkType type) {
        notNull(type,"link type must not be null.");

        Link link = new Link();
        link.setType(type.getCode());

        List<Link> links = abstractListBy(link);

        return converters(links);

    }

    @Override
    public PageData<LinkDTO> pageByType(int page,int size,LinkType type) {
        notNull(type,"link type must not be null.");

        Link link = new Link();
        link.setType(type.getCode());

        PageData<Link> linkPageData = abstractPageValidStatusBy(new PageInfo(page,size),link);
        PageData<LinkDTO> dto = linkPageData.convertData(l -> converter(l));

        return dto;
    }

    @Override
    public List<LinkDTO> listMenuType() {
        // TODO:缓存
        return listByType(LinkType.MENU_LINK);

    }

    @Override
    public List<LinkDTO> listPluginType() {
        // todo:缓存
        return listByType(LinkType.PLUGIN);

    }


    @Override
    public Optional<LinkDTO> saveIfUrlAbsent(final Link link) {
        notNull(link,"link must not be null.");

        Link l = linkCurd.selectByUrl(link.getUrl());

        if(l != null){
            return Optional.ofNullable(converter(l));
        }

        abstractInsert(link);

        return Optional.ofNullable(converter(link));
    }

    /**
     * dao to dto
     */
    private LinkDTO converter(Link link){
        return CONVERTER.map(link);
    }

    /**
     * dao to dto
     */
    private Optional<LinkDTO> converter(Optional<Link> link){
        return Optional.ofNullable(converter(link.get()));
    }

    /**
     * daos to dtos
     */
    private List<LinkDTO> converters(List<Link> links){
        if(links == null){
            return new ArrayList<>();
        }
        List<LinkDTO> dtos = new ArrayList<>(links.size());

        links.forEach(link -> {
            dtos.add(converter(link));
        });

        return dtos;
    }

    /**
     * 验证url
     */
    private void verityUrl(final Link link){
        if(link == null || link.getType() == null){
            return;
        }

        LinkType type = IEnum.codeToEnum(LinkType.class,link.getType());

        String url = link.getUrl().trim();
        if(type == LinkType.OFFSITE_LINK && !url.startsWith(ISparksConstant.PROTOCOL_HTTPS) && !url.startsWith(ISparksConstant.PROTOCOL_HTTP)){
            link.setUrl(ISparksConstant.PROTOCOL_HTTPS + url);
        }

    }

}
