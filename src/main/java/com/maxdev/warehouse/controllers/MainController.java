package com.maxdev.warehouse.controllers;


import com.maxdev.warehouse.repo.CredentialsRepository;
import com.maxdev.warehouse.repo.GoodsRepository;
import com.maxdev.warehouse.repo.UsercardsRepository;
import com.maxdev.warehouse.security.AuthManager;
import com.maxdev.warehouse.security.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Controller
public class MainController {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private UsercardsRepository usercardsRepository;
    @Autowired
    private AuthManager authManager;


    @EventListener(ApplicationReadyEvent.class)
    public void onAwake(){
        System.out.println("Main controller init");
        authManager.init();
    }



    //http://localhost:8080/auth?email=yew%40ya.ru&pwd=werwe

//    @GetMapping("/{p}")
//    public String home(Model model) {
//        model.addAttribute("title", "main page");
//        System.out.println("atHome");
//        return "home";
//    }

    @PostMapping("/auth")
    public String auth(@RequestParam(value="email") String user, @RequestParam(value="pwd") String password, @RequestParam(value="m") String mode,
                       Model model, HttpServletRequest request){
//        Authentication
        //System.out.println(goodsRepository.findById(1).get().getName());
        System.out.println(user + " : " + password + " : " + mode+ " : "+request.getRemoteAddr());
//        Authentication auth;
//        AuthenticationManager
        Authentication authentication = new Authentication(user,password,request.getRemoteAddr());
        try {
            if(mode.equals("sign up")){
                authentication = authManager.signup(authentication);
            }else{
                authentication = authManager.login(authentication);
            }
        }catch (AuthenticationException e){
            System.err.println(">> "+e.getMessage());
            model.addAttribute("error",e.getMessage());
            if(mode.equals("sign up"))
                return signup(model);
            else
                return login(model);
        }
        model.addAttribute("link","http://localhost:8080/app");

        return "redirect";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Log In");
        model.addAttribute("text", "log in");
        model.addAttribute("link", "/signup");
        model.addAttribute("ltext", "sign up");
        if(!model.containsAttribute("error"))
            model.addAttribute("error","");
        System.out.println("atLogin");
        return "auth";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Sign Up");
        model.addAttribute("text", "sign up");
        model.addAttribute("link", "/login");
        model.addAttribute("ltext", "log in");
        model.addAttribute("error","");
        System.out.println("atSignup");
        return "auth";
    }
    @GetMapping("/app")
    public String app(Model model){
        System.out.println("starting app");
        //String filePath = "src/main/warehouse-app/build/index.html";
        return "index.html";

        //return "classpath:/../../warehouse-app/build/index";
    }
    @Bean
    public ClassLoaderTemplateResolver indexTemplateResolver(){
        ClassLoaderTemplateResolver indexTemplateResolver =
                new ClassLoaderTemplateResolver();
        indexTemplateResolver.setPrefix("");
        indexTemplateResolver.setSuffix("");
        indexTemplateResolver.setCharacterEncoding("UTF-8");
        indexTemplateResolver.setOrder(1);
        indexTemplateResolver.setCheckExistence(true);

        return indexTemplateResolver;

    }



//    @GetMapping("/signin/")
//    public String signin(Model model) {
//        model.addAttribute("title", "main page");
//        return "signin";
//    }
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/images/**")
//                .addResourceLocations("classpath:/static/images/");
//    }


}
