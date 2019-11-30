package com.neo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.pojo.User;
import com.neo.service.UserService;
import com.neo.utils.FileUtil;
import com.neo.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static com.neo.utils.WebTTs.AUE;
import static com.neo.utils.WebTTs.WEBTTS_URL;
import static com.neo.utils.WebTTs.buildHttpHeader;

@Controller
@Slf4j
public class UserController {

    @Resource
    UserService userService;


    @RequestMapping("/")
    public String index() {
        //return "redirect:/list";
        return "redirect:/admin";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<User> users=userService.getUserList();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/poemList")
    @ResponseBody
    public JSONArray poemList(){
        List<User> users=userService.getUserList();
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }


    @RequestMapping("/select")
    public String select(@ModelAttribute("id") long id, Model model){

        User users=userService.findUserById(id);

        model.addAttribute("users", users);

        return "user/list";


    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(User user) throws Exception {
        //①
        userService.save(user);
        System.out.println("添加的user为："+user);
        //②
        user.setUrl("http://106.13.4.50/"+user.getPoemName()+".wav");  //向数据库添加设置url
        user.setDetailContent(user.toString()); //向数据库添加设置详细内容
        userService.edit(user);
        //③
        //获取诗词的内容
        String poemName=user.getPoemName();
        //获取诗词的名字
        System.out.println("下载的音频文件名为："+poemName);
        String content=user.getPoemDescription();
        // log.info("打印诗词内容：{}",content);

        Map<String, String> header = buildHttpHeader();
        Map<String, Object> resultMap = HttpUtil.doPost2(WEBTTS_URL, header, "text=" + URLEncoder.encode(content, "utf-8"));
        System.out.println("占用内存大小： "+ URLEncoder.encode(content, "utf-8").getBytes().length);
        System.out.println("文件的名称fileName:"+resultMap.get("sid")+".wav");

        if ("audio/mpeg".equals(resultMap.get("Content-Type"))) { // 合成成功
            if ("raw".equals(AUE)) {
                // FileUtil.save("resource\\", poemName + ".wav", (byte[]) resultMap.get("body"));
                   FileUtil.save("/home/ftpuser/images", poemName + ".wav", (byte[]) resultMap.get("body"));
                //  System.out.println("合成 WebAPI 调用成功，音频保存位置：/home/ftpuser/images:" + resultMap.get("sid") + ".wav");
                System.out.println("合成 WebAPI 调用成功，音频保存位置：/home/ftpuser/images:" + poemName + ".wav");
            } else {
              // FileUtil.save("resource\\", poemName + ".mp3", (byte[]) resultMap.get("body"));
               FileUtil.save("/home/ftpuser/images", poemName + ".mp3", (byte[]) resultMap.get("body"));
                System.out.println("合成 WebAPI 调用成功，音频保存位置：/home/ftpuser/images" + resultMap.get("sid") + ".mp3");
            }
        } else { // 合成失败
            System.out.println("合成 WebAPI 调用失败，错误信息：" + resultMap.get("body").toString());
        }
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        User user=userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/userEdit";
    }

    @RequestMapping("/edit")
    public String edit(User user) {
        userService.edit(user);
        return "redirect:/list";
    }


    @RequestMapping("/delete")
    public String delete(Long id) {
        userService.delete(id);
        return "redirect:/list";
    }




    @RequestMapping("/uploadMusic")
    public String uploadMusic(Long id) throws Exception {

            User user=userService.open(id);
            //获取诗词的内容
           String poemName=user.getPoemName();
        //获取诗词的名字
        System.out.println("下载的音频文件名为："+poemName);
        String content=user.getPoemDescription();
        // log.info("打印诗词内容：{}",content);

        Map<String, String> header = buildHttpHeader();
        Map<String, Object> resultMap = HttpUtil.doPost2(WEBTTS_URL, header, "text=" + URLEncoder.encode(content, "utf-8"));
        System.out.println("占用内存大小： "+ URLEncoder.encode(content, "utf-8").getBytes().length);
        System.out.println("文件的名称fileName:"+resultMap.get("sid")+".wav");

        if ("audio/mpeg".equals(resultMap.get("Content-Type"))) { // 合成成功
            if ("raw".equals(AUE)) {
                // FileUtil.save("resource\\", poemName + ".wav", (byte[]) resultMap.get("body"));
                FileUtil.save("/home/ftpuser/images", poemName + ".wav", (byte[]) resultMap.get("body"));
                //  System.out.println("合成 WebAPI 调用成功，音频保存位置：/home/ftpuser/images:" + resultMap.get("sid") + ".wav");
                System.out.println("合成 WebAPI 调用成功，音频保存位置：/home/ftpuser/images:" + poemName + ".wav");
            } else {
                // FileUtil.save("resource\\", poemName + ".mp3", (byte[]) resultMap.get("body"));
                FileUtil.save("/home/ftpuser/images", poemName + ".mp3", (byte[]) resultMap.get("body"));
                System.out.println("合成 WebAPI 调用成功，音频保存位置：/home/ftpuser/images" + resultMap.get("sid") + ".mp3");
            }
        } else { // 合成失败
            System.out.println("合成 WebAPI 调用失败，错误信息：" + resultMap.get("body").toString());
        }
        return "redirect:/list";
    }

    @RequestMapping("/downloadMusic")
   // @ResponseBody
    public String downloadMusic(Model model,Long id) throws Exception {
        User user=userService.open(id);
       String url= user.getUrl();
        //TODO 根据url下载文件  url路径为数据库存储的文件路径
        model.addAttribute("url", url);
        return "user/downLoadMusic";
    }
    @RequestMapping("/fMusic")
    // @ResponseBody
    public String fMusic(Model model,Long id) throws Exception {
        User user=userService.open(id);
        String url= user.getUrl();
        //TODO 根据url下载文件  url路径为数据库存储的文件路径
        model.addAttribute("url", url);
        return "user/fMusic";
    }

    @GetMapping("/poemDynasty")
    @ResponseBody
    public JSONArray findByDynastyLike(@RequestParam(name = "poemDynasty") String poemDynasty) {
        System.out.println(poemDynasty);
        //String poemName="唐代";
        List<User> users=userService.findAllByPoemDynastyContaining(poemDynasty);
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }
    /**
     * 根据诗作者进行模糊查询
     * */
    @GetMapping("/poemAuthor")
    @ResponseBody
    public JSONArray findByPoemAutherLike(@RequestParam(name = "poemAuthor") String poemAuthor) {
        System.out.println(poemAuthor);
        //String poemName="李白";
        List<User> users=userService.findAllByPoemAuthorContaining(poemAuthor);
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }

    /**
     * 根据诗名进行模糊查询
     * */
    @GetMapping("/poemName")
    @ResponseBody
    public JSONArray findBypoemNameLike(@RequestParam(name = "poemName") String poemName) {
        System.out.println(poemName);
        List<User> users=userService.findAllByPoemNameContaining(poemName);
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }

    @GetMapping("/poemType")
    @ResponseBody
    public JSONArray findByPoemTypeLike(@RequestParam(name = "poemType") String poemType) {
        System.out.println(poemType);
        List<User> users=userService.findAllByPoemTypeContaining(poemType);
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }
    @GetMapping("/poemDescription")
    @ResponseBody
    public JSONArray findByPoemDescriptionLike(@RequestParam(name = "poemDescription") String poemDescription) {
        System.out.println(poemDescription);
        List<User> users=userService.findAllByPoemDescriptionContaining(poemDescription);
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }

    /**
     * 全局模糊搜索
     * */
    @GetMapping("/detailContent")
    @ResponseBody
    public JSONArray findByDetailContentLike(@RequestParam(name = "detailContent") String detailContent) {
        System.out.println(detailContent);
        List<User> users=userService.findAllByDetailContentContaining(detailContent);
        JSONArray json = new JSONArray();
        for(User user: users){
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            jo.put("poemName", user.getPoemName());
            jo.put("poemAuthor", user.getPoemAuthor());
            jo.put("poemType", user.getPoemType());
            jo.put("poemDynasty", user.getPoemDynasty());
            jo.put("poemDescription", user.getPoemDescription());
            jo.put("url","http://106.13.4.50/"+ user.getPoemName()+".wav");
            json.add(jo);
        }
        System.out.println(json);
        return json;
    }


    /**
     * 全局模糊搜索2表单提交
     * */
    @RequestMapping (value = "/detailContent2",method = RequestMethod.POST )
    public String findByDetailContentLike2(String detailContent,Model model) {

        System.out.println("收到的数据为："+detailContent);
        List<User> users=userService.findAllByDetailContentContaining(detailContent);
        System.out.println("查询出的结果为："+users);
        model.addAttribute("users", users);
        return "user/list";
    }


    @RequestMapping("/admin")
    public String index2() {
        return "login";
    }

    @PostMapping(value = {"/login"})
    public String index(String username,String password){
        System.out.println("登陆用户名："+username+"--"+"密码："+password);
        if (username.equals("admin") & password.equals("123456")) {
            System.out.println("欢迎登陆！");
            return "redirect:/list";
        }else {
            System.out.println("账号密码输入有误！请重新填写信息！");
            return "login2";
        }
    }

}
