package app.isparks.service.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码图片生成器
 * @author chenghd
 */
public final class CaptchaProducer {

    private final static char[] codes = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    private final static int image_width = 130;

    private final static int image_height = 50 ;

    private final static Random random = new Random();

    /**
     * 生成指定位数的随机字符
     * @param length 字符串长度
     */
    public static String randomCode(int length){
        StringBuilder code = new StringBuilder();
        int len = codes.length;
        for(int i = 0 ; i < length ; i ++){
            code.append(codes[random.nextInt(len)]);
        }
        return code.toString();
    }

    /**
     * 生成 4 位随机验证符
     */
    public static String randomCode(){
        return randomCode(4);
    }

    /**
     * 生成验证码
     */
    public static String randomCaptcha(int width , int height , OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D  graphics = (Graphics2D)image.getGraphics();
        graphics.setColor(new Color(255,255,255));
        graphics.fillRect(0,0,width,height);

        graphics.setFont(new Font("Arial", Font.PLAIN, height - 3));
        String code = randomCode();

        graphics.setColor(randomColor());
        graphics.drawString(code, 0 , height);

        for(int i = 0 ; i < 10 ;i ++){
            drawRandomLine(graphics,width,height);
        }

        graphics.dispose();
        ImageIO.write(image,"JPG",os);
        return code;
    }

    /**
     * 生成验证码图片
     */
    public static String randomCaptcha(OutputStream os) throws IOException{
        return randomCaptcha(image_width,image_height,os);
    }

    /**
     * 随机颜色
     */
    private static Color randomColor(){
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r,g,b);
    }

    /**
     * 画干扰线
     */
    private static void drawRandomLine(Graphics g , int width , int height ) {
        g.setColor(randomColor());
        int x1 = new Random().nextInt(width);
        int y1 = new Random().nextInt(height);
        int x2 = new Random().nextInt(width);
        int y2 = new Random().nextInt(height);
        g.drawRect(x1, y1, x2, y2);
    }

}
