package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.Category;
import com.qinhan.videoblog.service.BlogService;
import com.qinhan.videoblog.service.CategoryService;
import com.qinhan.videoblog.service.MediaService;
import com.qinhan.videoblog.service.VideoService;
import com.qinhan.videoblog.web.common.VideoConstants;
import com.qinhan.videoblog.web.modelvo.BlogBodyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    VideoService videoService;
    @Autowired
    MediaService mediaService;
    @Autowired
    CategoryService categoryService;

    /**
     * 跳转到图文博客的上传界面
     * @param modelMap
     * @return
     */
    @RequestMapping("/toPicBlogUp")
    public String toPicUp(ModelMap modelMap) {
        List<Category> categoryList = null;
        try {
            categoryList=categoryService.getCategories();
        } catch (RuntimeException e) {
            String msg = null;
            if (e.getMessage().equals("FIRST_CATEGORY_GET_ERROR")) {
                modelMap.addAttribute("result", "博客获取分类失败异常");
                //然后转到异常页面
                return "pass/indexresult";
            }else{
                modelMap.addAttribute("result", "系统异常");
                //然后转到异常页面
                return "pass/indexresult";
            }
        }
        modelMap.put("categories", categoryList);
        return "uploadpictureblog";
    }

    /**
     * 跳转到视频博客上传页面
     * @param modelMap
     * @return
     */
    @RequestMapping("/toVideoBlogUp")
    public String toVideoUp(ModelMap modelMap) {
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.getCategories();
        } catch (RuntimeException e) {
            String msg = null;
            if (e.getMessage().equals("FIRST_CATEGORY_GET_ERROR")) {
                modelMap.addAttribute("result", "系统获取分类失败异常");
                //然后转到异常页面
                return "pass/indexresult";
            }else{
                modelMap.addAttribute("result", "系统异常");
                //然后转到异常页面
                return "pass/indexresult";
            }
        }
        modelMap.put("categories", categoryList);
        return "uploadvideoblog";
    }

    @RequestMapping("/uploadPics")
    @ResponseBody
    public String uploadPics(){
        return null;
    }

    /**
     * 视频播客  视频部分的上传
     * @param request
     * @param session
     * @param video
     * @param modelMap
     * @return
     */
    @RequestMapping("/uploadVideo")
    public String uploadVideo(HttpServletRequest request,HttpSession session, MultipartFile video, ModelMap modelMap){
        String username=(String) session.getAttribute("username");
        if(username==null){
            modelMap.addAttribute("result","请重新登陆");
            return "pass/indexresult";
        }
        //创建一个临时的目录来存放用户上传的临时视频
       /* String path=request.getServletContext().getRealPath("/videotmp/");*/
        String path= VideoConstants.VIDEO_TEMP_PATH;

        //获取ffmpeg工具地址
        /*String ffmpegPath=request.getServletContext().getRealPath("/tool/ffmpeg.exe");*/
        String ffmpegPath=VideoConstants.FFMPEG_INSTALLATION_PATH;
        int blogId;
        try {
           blogId= videoService.uploadVideo(video,path,username,ffmpegPath);

        } catch (RuntimeException e) {
            String msg=null;
            e.printStackTrace();
            if(e.getMessage()==null){
                modelMap.put("result","系统异常");
                return "pass/indexresult";
            }
            if(e.getMessage().equals("FILE_TOO_BIG#600")){
                modelMap.put("videoupresult","视频超过600MB限制");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "uploadvideoblog";
            }else if(e.getMessage().equals("FILE_UPLOAD_ERROR")){
                modelMap.put("videoupresult","视频上传中失败");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "uploadvideoblog";
            }else{
                modelMap.put("videoupresult","视频上传失败");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "uploadvideoblog";
            }
        }
        if(blogId!=0){
            //跳转到下载页面
            modelMap.put("videoupresult","视频上传成功");
            List<Category>  categoryList = categoryService.getCategories();
            modelMap.put("categories", categoryList);
            /*由于不能获取到上传视频后的博客视频，所以这里我们通过视频 名来获取到 博客的id*/
            modelMap.put("blogId",blogId);
            return "uploadvideoblog";
        }
        modelMap.put("videoupresult","上传失败");
        List<Category>  categoryList = categoryService.getCategories();
        modelMap.put("categories", categoryList);
        return "uploadvideoblog";
    }

    /**
     * 视频播客网站  视频播客内容的上传  同时修改博客内容也从这里保存
     * @param request
     * @param blogbody
     * @param modelMap
     * @return
     */
    @RequestMapping("/blogbodyUpload")
    public String blogbodyUpload(HttpServletRequest request,BlogBodyForm blogbody,ModelMap modelMap){
        String username=(String) request.getSession().getAttribute("username");
        if(username==null){
            modelMap.addAttribute("result","用户未登陆，请重新登陆");
            return "pass/indexresult";
        }
        blogbody.setUsername(username);

        try {
            blogService.uploadBlog(blogbody);
        } catch (RuntimeException e) {
            if(e.getMessage().equals("USER_NOT_FOUND")&&blogbody.getType()==0){
                modelMap.put("blogbodyresult","图文博客内容提交失败");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "uploadpictureblog";
            }else if(e.getMessage().equals("USER_NOT_FOUND")&&blogbody.getType()==1){
                modelMap.put("blogbodyresult","视频播客内容提交失败");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "uploadvideoblog";
            }else{
                modelMap.put("result","系统异常请联系管理员");
                return "pass/indexresult";
            }
        }


        if(blogbody.getType()==0){
            modelMap.put("blogbodyresult","图文博客内容上传成功");
            List<Category>  categoryList = categoryService.getCategories();
            modelMap.put("categories", categoryList);
            return "uploadpictureblog";
        }else if(blogbody.getType()==1){
            modelMap.put("blogbodyresult","视频播客内容上传成功");
            List<Category>  categoryList = categoryService.getCategories();
            modelMap.put("categories", categoryList);
            return "uploadvideoblog";
        }
        modelMap.put("blogbodyresult","博客上传失败");
        return "pass/indexresult";
    }

     @RequestMapping("/editBlog")
    public String editBlog(@NumberFormat Integer videoId, ModelMap modelMap){
         // TODO: 2019/5/14 这里先借用上传的代码，有时间改一下
            //获取到博客信息，渲染到修改博客页面 根据视频播客的id
        Blog blog=blogService.getVideoInfo(videoId);
         modelMap.put("blogInfo",blog);
         return "editVideoBlog";
     }

    @RequestMapping("/blogmodify")
    public String modifyBlog(HttpServletRequest request,BlogBodyForm blogbody,ModelMap modelMap){
        String username=(String) request.getSession().getAttribute("username");
        if(username==null){
            modelMap.addAttribute("result","用户未登陆，请重新登陆");
            return "pass/indexresult";
        }
        blogbody.setUsername(username);

        try {
            blogService.uploadBlog(blogbody);
        } catch (RuntimeException e) {
            if(e.getMessage().equals("USER_NOT_FOUND")&&blogbody.getType()==0){
                modelMap.put("blogbodyresult","图文博客内容提交失败");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "editVideoBlog";
            }else if(e.getMessage().equals("USER_NOT_FOUND")&&blogbody.getType()==1){
                modelMap.put("blogbodyresult","视频播客内容提交失败");
                List<Category>  categoryList = categoryService.getCategories();
                modelMap.put("categories", categoryList);
                return "editVideoBlog";
            }else{
                modelMap.put("result","系统异常请联系管理员");
                return "pass/indexresult";
            }
        }


        if(blogbody.getType()==0){
            modelMap.put("blogbodyresult","图文博客内容上传成功");
            List<Category>  categoryList = categoryService.getCategories();
            modelMap.put("categories", categoryList);
            return "uploadpictureblog";
        }else if(blogbody.getType()==1){
            modelMap.put("blogbodyresult","视频播客内容修改成功");
            List<Category>  categoryList = categoryService.getCategories();
            modelMap.put("categories", categoryList);
            Blog blog=blogService.getVideoInfo(blogbody.getBlogId());
            modelMap.put("blogInfo",blog);
            return "editVideoBlog";
        }
        modelMap.put("blogbodyresult","博客上传失败");
        return "pass/indexresult";
    }

     @RequestMapping("/deleteBlog")
    public String deleteBlog(@NumberFormat Integer videoId,ModelMap modelMap){
            //删除博客信息
         blogService.deleteBlogById(videoId);
            //请求重新渲染博客页面

         return "forward:toMyPersonblogs";
     }



}
