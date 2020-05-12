package BTCTwilioSMS.Twilio;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import BTCTwilioSMS.Twilio.TwilioConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeTwilio {

    private final TwilioConfiguration twilioconfiguration;


    @Autowired
    public InitializeTwilio(TwilioConfiguration twilioconfiguration) {
        this.twilioconfiguration = twilioconfiguration;
        Twilio.init(twilioconfiguration.getAccountSid(),
                    twilioconfiguration.getAuthToken());
    }



}
