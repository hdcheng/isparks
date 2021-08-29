package app.isparks.web.controller.page;

import app.isparks.core.service.IPostService;
import app.isparks.service.impl.PostServiceImpl;
import app.isparks.web.controller.Router;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author： chenghd
 * @date： 2021/1/7
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    /**
     * Index redirect uri.
     */
    private final static String INDEX_REDIRECT_URI = "index.html";

    /**
     * Install redirect uri.
     */
    private final static String INSTALL_REDIRECT_URI = "install.html";

    private Router router;

    private IPostService postService;

    public AdminController(Router router, PostServiceImpl postService){
        this.postService = postService;
        this.router = router;
    }

    @GetMapping({"","/index","/"})
    public String index(Model model) {
        router.setPageModel(model,"dash");
        return "index";
    }

    @GetMapping("page/post/review/{id}")
    public String postReview(@PathVariable("id") String id,Model model){
        postService.getById(id).ifPresent((post) -> {
            model.addAttribute("title",post.getTitle());
            Map<String,Object> prop = post.getProperties();
            model.addAttribute("tags",prop.get("tags"));
            model.addAttribute("category",prop.get("category"));
            model.addAttribute("content",post.getOriginContent());
        });
        return "partials/sections/post/review";
    }

    @GetMapping("/**")
    public String pages(HttpServletRequest request, Model model){
        String url = request.getRequestURI();
        String pagePath = url.substring(url.indexOf("admin/")+6);

        router.setPageModel(model,pagePath);

        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        return "login";
    }

    @GetMapping("install")
    public String guider(Model model) {
        return "install";
    }


}
