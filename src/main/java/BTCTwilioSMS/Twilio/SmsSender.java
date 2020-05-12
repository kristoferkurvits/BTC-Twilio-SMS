package BTCTwilioSMS.Twilio;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("twilio")
public class SmsSender implements SmsSenderInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);
    private final TwilioConfiguration twilioConfiguration;

    public SmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    //Phone number validation is done in UserController
    @Override
    public void sendSMS(SMSRequest smsRequest) {

        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();
        MessageCreator creator = Message.creator(
                to,
                from,
                message
        );
        creator.create();
        LOGGER.info("SMS sent -> " + smsRequest);

    }

    //Checks if provided number is a valid Estonian phone number
    //Implemented google's libphonenumber library
    public boolean validPhoneNumber(String phoneNumber) {

        String rawNumber = phoneNumber.substring(4);

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber EEnumber = null;
        try {
            EEnumber = phoneNumberUtil.parse(rawNumber, "EE");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        if (phoneNumberUtil.isValidNumber(EEnumber)){
            return true;
        }
        throw new IllegalArgumentException("Invalid phone number " + phoneNumber);

    }
}
