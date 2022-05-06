package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Request;
import spring.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/archery/admin/requests")
public class RequestsController
{
    @Autowired
    RequestService requestService;

    @GetMapping("")
    public ResponseEntity<List<Request>> showUnviewedRequests()
    {
        return new ResponseEntity<>(requestService.findByStatus("UNVIEWED"), HttpStatus.OK);
    }

    @PostMapping("/{requestId}/view")
    public ResponseEntity<String> viewRequest(@PathVariable long requestId)
    {
        requestService.updateStatus("VIEWED", requestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{requestId}/remove")
    public ResponseEntity<String> removeRequest(@PathVariable long requestId)
    {
        requestService.removeRequest(requestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
