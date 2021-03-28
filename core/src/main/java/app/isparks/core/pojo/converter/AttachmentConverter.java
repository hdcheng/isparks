package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.AttachmentDTO;
import app.isparks.core.pojo.entity.Attachment;
import app.isparks.core.pojo.param.AttachmentParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface AttachmentConverter {

    Attachment map(AttachmentParam source);

    AttachmentDTO map(Attachment source);

    List<AttachmentDTO> maps(List<Attachment> sources);

}
