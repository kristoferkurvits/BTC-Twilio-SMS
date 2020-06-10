# BTCTwilioSMS

#### **What does it do?**
Tracks Bitcoin price and notifies user through SMS when BTC price passes user defined threshold(s).

User enters his/hers phone number, lower bound and upper bound through POST request.
(Currently only Estonian phone numbers allowed)

POST /users {"phoneNumber": "+372XXXXXXX", "upperBound": X, "lowerBound": X}

It is possible to send regular SMS through POST /send {"phoneNumber": "+372XXXXXXX", "message": "X"}

SMS functioniality is provided by Twilio.

#### **How to run it?**
Create a Twilio account and add environment variables

TWILIO_ACCOUNT_SID=

TWILIO_AUTH_TOKEN=

TWILIO_TRIAL_NUMBER=

* Clone repo
* java -jar target/BTCTwilioSMS-0.0.1-SNAPSHOT.jar
* Make a POST request
* Wait for notification


