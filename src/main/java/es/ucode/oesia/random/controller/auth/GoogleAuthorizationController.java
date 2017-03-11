package es.ucode.oesia.random.controller.auth;

import es.ucode.oesia.random.service.GoogleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.Principal;

@RestController
public class GoogleAuthorizationController {

    @Autowired
    private GoogleServiceImpl googleService;

    @GetMapping("/auth/google")
    public ResponseEntity<?> requestAuthorization(Principal principal, HttpServletRequest request) throws GeneralSecurityException, IOException {
        if (googleService.isAuthorized(principal)) {
            return new ResponseEntity<>("Already authorized", HttpStatus.OK);
        } else {
            String requestURL = request.getRequestURL().toString();
            String url = googleService.getAuthorizationURL(principal, requestURL);
            return new ResponseEntity<>(new AuthorizationURL(url), HttpStatus.OK);
        }
    }

    @GetMapping("/auth/google/callback")
    public ResponseEntity<Void> doAuthorize(Principal principal,
                                            @RequestParam("code") String authorizationCode) throws IOException, GeneralSecurityException {
        googleService.authorize(principal, authorizationCode);

        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("/"));
        return new ResponseEntity<>(h, HttpStatus.OK);
    }
}
