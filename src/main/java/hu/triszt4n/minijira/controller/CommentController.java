package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.entity.CommentEntity;
import hu.triszt4n.minijira.input.CreateCommentInput;
import hu.triszt4n.minijira.input.UpdateCommentInput;
import hu.triszt4n.minijira.service.CommentService;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import hu.triszt4n.minijira.util.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    private boolean preventCommentModification(Authentication authentication, CommentEntity commentEntity) {
        final var principal = (MyUserPrincipal) authentication.getPrincipal();
        final var currentUser = principal.getUserEntity();

        // Only manager or owner is allowed to modify
        return !Objects.equals(currentUser.getId(), commentEntity.getPublisher().getId()) && currentUser.getRole() != RoleEnum.MANAGER;
    }

    @PostMapping("/new/{id}")
    public String createComment(@RequestParam Map<String, String> requestParam,
                                Authentication authentication,
                                @PathVariable Long id) {
        final var currentUser = (MyUserPrincipal) authentication.getPrincipal();
        commentService.create(
                new CreateCommentInput().setBody(requestParam.get("body")),
                id,
                currentUser.getUserEntity()
        );

        return "redirect:/tasks/".concat(String.valueOf(id));
    }

    @GetMapping("/{id}/edit")
    public String editCommentPage(@PathVariable Long id,
                                  Authentication authentication,
                                  Model model) {
        final var comment = commentService.getById(id);

        if (preventCommentModification(authentication, comment)) {
            return "redirect:/tasks/".concat(String.valueOf(comment.getTask().getId()));
        }

        model.addAttribute("updateCommentInput", new UpdateCommentInput().setBody(comment.getBody()));
        return "formPages/editComment";
    }

    @PostMapping("/{id}/edit")
    public String updateTask(@Valid @ModelAttribute("updateCommentInput") UpdateCommentInput updateCommentInput,
                             BindingResult bindingResult,
                             Authentication authentication,
                             @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "formPages/editComment";
        }

        final var comment = commentService.getById(id);
        if (preventCommentModification(authentication, comment)) {
            return "redirect:/tasks/".concat(String.valueOf(comment.getTask().getId()));
        }

        commentService.update(id, updateCommentInput);
        return "redirect:/tasks/".concat(String.valueOf(comment.getTask().getId()));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id,
                              Authentication authentication) {
        final var comment = commentService.getById(id);
        if (preventCommentModification(authentication, comment)) {
            return;
        }
        commentService.delete(id);
    }
}
