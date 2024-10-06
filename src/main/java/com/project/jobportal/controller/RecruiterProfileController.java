package com.project.jobportal.controller;

import com.project.jobportal.entity.RectuiterProfile;
import com.project.jobportal.entity.Users;
import com.project.jobportal.repository.IUsersRepo;
import com.project.jobportal.services.RecruiterProfileService;
import com.project.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;
import java.util.Objects;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final IUsersRepo usersRepo;

    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(IUsersRepo usersRepo, RecruiterProfileService recruiterProfileService) {
        this.usersRepo = usersRepo;
        this.recruiterProfileService = recruiterProfileService;
    }


    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
          String currentUsername = authentication.getName();
          Users users = usersRepo.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could " + "not found user"));
          Optional <RectuiterProfile> rectuiterProfile = recruiterProfileService.getOne(users.getUserId());
          if (!rectuiterProfile.isEmpty())
              model.addAttribute("profile", rectuiterProfile.get());

        }
        return "recruiter_profile";
    }


    @PostMapping("/addNew")
    private String addNew(RectuiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepo.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could " + "not found user"));
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RectuiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String uploadDir = "photos/recruiter/"+savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
