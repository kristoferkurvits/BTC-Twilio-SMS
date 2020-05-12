package BTCTwilioSMS.Twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
    private final SmsSender smsSender;

    @Autowired
    public SMSService(@Qualifier("twilio") SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSMS(SMSRequest smsRequest){
        smsSender.sendSMS(smsRequest);
    }
}
