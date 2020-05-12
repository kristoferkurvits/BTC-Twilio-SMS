package BTCTwilioSMS.Twilio;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SMSController {


    private final SMSService smsService;

    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    //Allows to send regular messages
    @PostMapping(path = "/send", consumes = "application/json")
    public void sendSMS(@Valid @RequestBody SMSRequest smsRequest){
        smsService.sendSMS(smsRequest);
    }


}
