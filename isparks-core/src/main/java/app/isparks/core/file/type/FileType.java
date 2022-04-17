package app.isparks.core.file.type;

/**
 * 支持上传的文件类型
 *
 * @author chenghd
 * @date 2020/9/28
 */
public enum FileType {

    JPG("jpg",MediaType.IMAGE),
    JPEG("jpeg",MediaType.IMAGE),
    PNG("png",MediaType.IMAGE),
    WEBP("webp",MediaType.IMAGE),
    GIF("gif",MediaType.IMAGE),
    PSD("psd",MediaType.UNKNOWN),
    ZIP("zip",MediaType.PACK),
    HTML("html",MediaType.TEXT),
    TEXT("txt",MediaType.TEXT),
    PDF("pdf",MediaType.TEXT),
    UNKNOWN("unknown",MediaType.UNKNOWN),
    MP3("mp3",MediaType.AUDIO);

    FileType(String suffix,MediaType type){
        this.suffix = suffix;this.mediaType = type;
    }

    private String suffix;

    private MediaType mediaType;

    public String getSuffix() {
        return suffix;
    }

    public MediaType getMediaType(){return mediaType;}

}
