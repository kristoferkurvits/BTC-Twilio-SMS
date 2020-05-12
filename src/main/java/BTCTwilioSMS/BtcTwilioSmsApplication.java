package BTCTwilioSMS;

import BTCTwilioSMS.BTC.BitCoin;
import BTCTwilioSMS.Twilio.SMSRequest;
import BTCTwilioSMS.Twilio.SMSService;
import BTCTwilioSMS.User.User;
import BTCTwilioSMS.User.UserRepository;
import org.joda.time.LocalDateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BtcTwilioSmsApplication {

	private static SMSService smsService;
	private static UserRepository userRepository;

	private static BitCoin bitCoin;
	private static Double BTCValue;

	private static List<User> usersUpper;
	private static List<User> usersLower;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(BtcTwilioSmsApplication.class, args);
		checkPrice();
	}

	public BtcTwilioSmsApplication(UserRepository userRepository, SMSService smsService){
		this.smsService = smsService;
		this.userRepository = userRepository;
		this.usersUpper = new ArrayList<>();
		this.usersLower = new ArrayList<>();
	}

	//Fetches BTC data after every minute to avoid too many requests
	public static synchronized void checkPrice() throws InterruptedException {


		bitCoin = new BitCoin();
		Thread BTCThread = new Thread(bitCoin);
		BTCThread.start();

		//Fetches users data every 10sec
		Thread fetchUsers = new Thread(() -> {
			while (true){
				usersUpper = userRepository.findByUpperBoundLessThan(BTCValue);
				usersLower = userRepository.findByLowerBoundGreaterThan(BTCValue);
				try {
					Thread.currentThread().sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					continue;
				}
			}

		});
		fetchUsers.start();

		StringBuilder sb = new StringBuilder();
		while(true){
			BTCValue = bitCoin.getValue();
			LocalDateTime timeFetched = bitCoin.getTimeFetched();
			sb.append("\r").append("BTC price -> " + BTCValue + " EUR" + " # " + timeFetched);
			System.out.print(sb);

			//BitCoins instance variable is set to true after every BTC data fetch to avoid unnecessary iterations through userUpper and Lower arrays
			if (bitCoin.isNotify()){
				if (!usersUpper.isEmpty()){
					for(User user: usersUpper){
						SMSRequest smsRequest = new SMSRequest(user.getPhoneNumber(), "BTC price exceeded your upper bound {" + user.getUpperBound() + " EUR} /" +
								" Current BTC price is " + BTCValue + " EUR");
						smsService.sendSMS(smsRequest);
					}
				}

				if (!usersLower.isEmpty()){
					for(User user: usersLower){
						SMSRequest smsRequest = new SMSRequest(user.getPhoneNumber(), "BTC price exceeded your lower bound {" + user.getLowerBound() + " EUR} /" +
								" Current BTC price is " + BTCValue + " EUR");
						smsService.sendSMS(smsRequest);
					}
				}
				bitCoin.setNotify(false);
			}

			//Fetches BTC data every minute
			Thread.sleep(60000);
		}
	}

}
