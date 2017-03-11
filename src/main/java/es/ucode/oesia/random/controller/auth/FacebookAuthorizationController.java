package es.ucode.oesia.random.controller.auth;

import es.ucode.oesia.random.service.FacebookServiceImpl;
import facebook4j.FacebookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.security.Principal;

@Controller
public class FacebookAuthorizationController {

    @Autowired
    private FacebookServiceImpl facebookService;

    @GetMapping("/auth/facebook")
    public ResponseEntity<?> requestAuthorization(Principal principal, HttpServletRequest request) {
        if (facebookService.isAuthorized(principal)) {
            return new ResponseEntity<>("Already authorized", HttpStatus.OK);
        } else {
            String requestURL = request.getRequestURL().toString();
            String url = facebookService.getAuthorizationURL(requestURL);
            return new ResponseEntity<>(new AuthorizationURL(url), HttpStatus.OK);
        }
    }

    @GetMapping("/auth/facebook/callback")
    public ResponseEntity<Void> doAuthorize(Principal principal,
                                            @RequestParam("code") String oAuthCode) throws FacebookException {
        facebookService.authorize(principal, oAuthCode);
        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("/"));
        return new ResponseEntity<>(h, HttpStatus.TEMPORARY_REDIRECT);
    }
}
