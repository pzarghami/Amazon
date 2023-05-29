package Baloot.service;

import Baloot.Exeption.CustomException;
import Baloot.domain.CommentDomainManager;
import Baloot.model.DTO.CommentDTO;
import Baloot.model.DTO.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentService {
    @RequestMapping(value = "/comments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response postNewComment(@RequestBody CommentDTO newComment) {
        if (newComment.getCommentCommodityId() == null || newComment.getText() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            return new Response(true, "OK", CommentDomainManager.getInstance().postNewComment(newComment));
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Related Source Not Found", e);
        }
    }

    @RequestMapping(value = "/comments/{id}/vote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response voteComment(@PathVariable(value = "id") Integer commentId, @RequestBody String voteObj) {
        try {
            var voteValue = new ObjectMapper().readTree(voteObj).get("vote").asInt();
            return new Response(true, "OK", CommentDomainManager.getInstance().voteComment(commentId.toString(), voteValue));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
