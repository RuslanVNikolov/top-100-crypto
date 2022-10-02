package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.Avatar;
import io.ruslan.top100crypto.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Controller
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    @GetMapping("/avatars")
    public String avatars() {
        return "avatars";
    }

    @GetMapping("/upload")
    public String uploadAvatar() {
        return "uploadAvatar";
    }

    @PostMapping("/avatars")
    public String addAvatar(@RequestParam("title") String title,
                            @RequestParam("image") MultipartFile image, Model model) throws IOException {
        String id = avatarService.addAvatar(title, image);
        return "redirect:/avatars/" + id;
    }

    @GetMapping("/avatars/{id}")
    public String getAvatar(@PathVariable String id, Model model) {
        Avatar photo = avatarService.getAvatarById(id);
        model.addAttribute("title", photo.getTitle());
        model.addAttribute("image",
                Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return "avatars";
    }
}
