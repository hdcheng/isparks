package app.isparks.web.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 异常控制器
 *
 * @author： chenghd
 * @date： 2021/2/25
 */
@Controller
@RequestMapping("error")
public class ExceptionController {


    @GetMapping("404")
    public String error404(@RequestParam("reason") String reason){
        System.out.println(reason);
        return "404";
    }


}
