package app.isparks.service.impl;

import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.OptionConverter;
import app.isparks.core.pojo.entity.Option;
import app.isparks.core.pojo.enums.*;
import app.isparks.core.pojo.param.OptionParam;
import app.isparks.core.service.IOptionService;
import app.isparks.core.util.*;
import app.isparks.dao.repository.OptionCurd;
import app.isparks.dao.repository.impl.OptionCurdImpl;
import app.isparks.service.base.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author： chenghd
 * @date： 2021/1/13
 */
@Service
public class OptionServiceImpl extends AbstractService<Option> implements IOptionService {

    private Logger log = LoggerFactory.getLogger(OptionServiceImpl.class);

    private OptionConverter optionConverter = ConverterFactory.get(OptionConverter.class);

    private OptionCurd optionCurd;

    public OptionServiceImpl(OptionCurdImpl optionCurd){
        super(optionCurd);
        this.optionCurd = optionCurd;
    }

    @Override
    public void save(OptionParam param) {
        notNull(param,"param can't be null.");

        IEnum.codeToEnum(OptionType.class,param.getType());

        Option option = optionConverter.map(param);

        optionCurd.insert(option);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Map<String, Object> mapOptions) {
        if(CollectionUtils.isEmpty(mapOptions)){
            log.warn("options map is null.");
            return;
        }

        Map<String,Option> oldMap = CollectionUtils.convertToMap(abstractListAll(),Option::getKey);

        List<Option> createOption = new ArrayList<>();
        List<Option> updateOption = new ArrayList<>();

        mapOptions.forEach((key,value)->{
            Option oldValue = oldMap.get(key);
            Option newOption = new Option(key,value.toString());

            if(oldValue == null){
                newOption.setType(OptionType.INTERNAL.getCode());
                createOption.add(newOption);
            }else if(!StringUtils.equals(oldValue.getValue(),value.toString())){
                BeanUtils.updateProperties(newOption,oldValue);
                updateOption.add(oldValue);
            }

        });

        // create options
        abstractInsertBatch(createOption);

        // update options
        abstractUpdateBatch(updateOption);

    }

    @Override
    public <V> V getByPropertyOrDefault(PropertyEnum property, Class<V> valueType) {
        notNull(property,"system property must not be empty.");

        if (property.getValueType() != valueType){
            throw new InvalidValueException("Class<V> valueType 要与 property 的 value 类型一致。");
        }

        Optional<Option> option = getOptionByKey(property.getKey());

        V result;

        if (!option.isPresent()){
            result = ISparksUtils.stringParse(property.getValue(),valueType);
        }else{
            result = ISparksUtils.stringParse(option.get().getValue(),valueType);
        }

        return result;
    }

    @Override
    public Optional<Option> getOptionByKey(String key) {
        notEmpty(key,"option key must not bet empty.");

        Option option = new Option();
        option.setKey(key);

        return abstractListBy(option).stream().findFirst();
    }

    @Override
    public Map<String, Object> listOptions() {

        Map<String,Object> result = new HashMap<>();

        abstractListAll().forEach((option)->{
            result.put(option.getKey(),option.getValue());
        });

        return result;
    }

}
