package app.isparks.web.controller.page;

import app.isparks.core.service.IPostService;
import app.isparks.web.controller.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Autowired
    private Router router;

    @Autowired
    private IPostService postService;

    @RequestMapping(value = {"","/index","/"}, method = {RequestMethod.GET})
    public String index(Model model) {
        router.setPageModel(model,"dash");
        return "index";
    }

    @RequestMapping(value = "page/post/review/{id}",method = {RequestMethod.GET})
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

    @RequestMapping(value = {"/**"}, method = {RequestMethod.GET})
    public String pages(HttpServletRequest request, Model model){
        String url = request.getRequestURI();
        String pagePath = url.substring(url.indexOf("admin/")+6);

        router.setPageModel(model,pagePath);

        return "index";
    }

    @RequestMapping(value = {"/test/**"},method = {RequestMethod.GET})
    public String pagess(HttpServletRequest request, Model model){
        String url = request.getRequestURI();

        return "123";
    }


    @RequestMapping(value = "/login",method = {RequestMethod.GET})
    public String login(){
        return "login";
    }

    @RequestMapping(value = "install", method = {RequestMethod.GET})
    public String guider(Model model) {
        return "install";
    }


}
