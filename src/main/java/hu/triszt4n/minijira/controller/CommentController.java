package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.input.CreateCommentInput;
import hu.triszt4n.minijira.input.UpdateCommentInput;
import hu.triszt4n.minijira.input.UpdateProjectInput;
import hu.triszt4n.minijira.input.UpdateTaskInput;
import hu.triszt4n.minijira.service.CommentService;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/new/{id}")
    public String createComment(@Valid @ModelAttribute("createCommentInput") CreateCommentInput createCommentInput,
                                Authentication authentication,
                                @PathVariable Long id) {
        final var currentUser = (MyUserPrincipal) authentication.getPrincipal();
        commentService.create(createCommentInput, id, currentUser.getUserEntity());
        return "redirect:/tasks/".concat(String.valueOf(id));
    }

    @GetMapping("/{id}/edit")
    public String editCommentPage(@PathVariable Long id,
                                  Model model) {
        final var comment = commentService.getById(id);

        model.addAttribute("updateCommentInput", new UpdateCommentInput().setBody(comment.getBody()));
        return "formPages/editComment";
    }

    @PostMapping("/{id}/edit")
    public String updateTask(@Valid @ModelAttribute("updateCommentInput") UpdateCommentInput updateCommentInput,
                             BindingResult bindingResult,
                             @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "formPages/editComment";
        }

        final var comment = commentService.update(id, updateCommentInput);
        return "redirect:/tasks/".concat(String.valueOf(comment.getTask().getId()));
    }
}
