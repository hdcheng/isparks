package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.entity.Option;
import app.isparks.core.pojo.param.OptionParam;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class OptionConverterImpl implements OptionConverter {

    @Override
    public Option map(OptionParam param) {
        if ( param == null ) {
            return null;
        }

        Option option = new Option();

        option.setKey( param.getKey() );
        option.setValue( param.getValue() );
        option.setType( param.getType() );

        return option;
    }
}
