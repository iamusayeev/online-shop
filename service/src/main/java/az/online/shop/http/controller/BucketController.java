package az.online.shop.http.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import az.online.shop.dto.BucketDTO;
import az.online.shop.service.BucketService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @GetMapping
    public String aboutBucket(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("bucket", new BucketDTO());
        } else {
            BucketDTO bucketDto = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }
        return "bucket/bucket";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!bucketService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/buckets";
    }
}