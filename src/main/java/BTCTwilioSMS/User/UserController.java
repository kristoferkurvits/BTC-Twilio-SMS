package BTCTwilioSMS.User;

import BTCTwilioSMS.Twilio.SmsSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final SmsSender smsSender;

    public UserController(UserRepository userRepository, SmsSender smsSender) {
        this.userRepository = userRepository;
        this.smsSender = smsSender;
    }

    @GetMapping("/users")
    List<User> all(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    User user(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        smsSender.validPhoneNumber(newUser.getPhoneNumber());
        return userRepository.save(newUser);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setLowerBound(newUser.getLowerBound());
                    user.setUpperBound(newUser.getUpperBound());
                    return userRepository.save(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

}
