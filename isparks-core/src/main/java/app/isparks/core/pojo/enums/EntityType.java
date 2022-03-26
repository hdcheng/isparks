package app.isparks.core.pojo.enums;


import app.isparks.core.pojo.entity.FFile;

public enum EntityType {
    POST(app.isparks.core.pojo.entity.Post.class),
    JOURNAL(app.isparks.core.pojo.entity.Journal.class),
    ATTACHMENT(app.isparks.core.pojo.entity.Attachment.class),
    CATEGORY(app.isparks.core.pojo.entity.Category.class),
    COMMENT(app.isparks.core.pojo.entity.Comment.class),
    FFILE(FFile.class),
    LINK(app.isparks.core.pojo.entity.Link.class),
    LOG(app.isparks.core.pojo.entity.Log.class),
    OPTION(app.isparks.core.pojo.entity.Option.class),
    TAG(app.isparks.core.pojo.entity.Tag.class),
    USER(app.isparks.core.pojo.entity.User.class);

    EntityType(Class<?> aClass){
        this.aClass = aClass;
    }

    private Class<?> aClass;

    public Class<?> typeClass(){
        return aClass;
    }

}
