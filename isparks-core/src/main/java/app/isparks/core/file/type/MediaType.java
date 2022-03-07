package app.isparks.core.file.type;

public enum MediaType {

    IMAGE("image"),         // 图片，如：jpg、png、gif等。
    TEXT("text"),           // 文本，如：txt、html、js、css等只包含文字的文件。
    RICH_TEXT("rich_text"),// 富文本，如：word、pdf等包含文字和图片等。
    VIDE("video"),          // 视频，如：mp4、mkv、rmvb等。
    AUDIO("audio"),         // 音频，如：mp4、flac等。
    PACK("pack"),           // 打包文件，如：zip、7z等。
    UNKNOWN("unknown");

    MediaType(String type){
        this.type = type;
    }

    private String type;

    public String getType(){
        return type;
    }
}
