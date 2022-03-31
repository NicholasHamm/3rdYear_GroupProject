package sweproject;

import twitter4j.*;
import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Configuration {

    private static final String USER_DATA = "VaxData/vax tweets users.txt";
    private static final String TWEET_DATA = "VaxData/vax tweets.txt";
    private static final String[] HASHTAGS = {"Vaccinated", "Covid-19", "VaccineMandate", "CovidHoax",
            "FuckVaccines", "Vaxxed", "MicrochipVaccine", "GatesVaccine", "NoVaccine", "GetVaccinated",
            "Booster", "Omicron", "LongCovid", "CovidIsNotOver", "COVIDisAirborne", "WearAMask", "GetVaxed", "covidiots",
            "CovidScam", "ArrestFaucci", "VaccineSideEffects", "VaccineDeaths", "VaccineInjuries", "NoCovid",
            "GetTheDamnVaccine", "CovidIsOver", "CoronaHoax", "CoronaVirus", "OmicronBeGone", "CovidFraud",
            "SayNoToPoisonVaccines", "fuckFauci"};

    public static ConfigurationBuilder getConfigurationBuilder() {

        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("oauth.consumerKey")
                .setOAuthConsumerSecret("oauth.consumerSecret")
                .setOAuthAccessToken("oauth.accessToken")
                .setOAuthAccessTokenSecret("oauth.accessTokenSecret");

        return cb;
    }

    public static void main(String[] args) throws TwitterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        TwitterFactory tf = new TwitterFactory(cb.build());

        // This way of configuring TwitterFactory not working...
        // TwitterFactory tf = new TwitterFactory(Configuration.getConfigurationBuilder().build());
        // twitter4j.Twitter twitter = tf.getInstance();

        //Build Twitter instance
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query(Arrays.toString(HASHTAGS));
        QueryResult result = twitter.search(query);

        //Search for old tweets
        List<Status> status = result.getTweets();
        for (Status s : status) {
            sweproject.Tweet tweet = new sweproject.Tweet(s.getId(), s.getUser().getScreenName(), s.getRetweetCount());
            writer(tweet);
        }

        //Stream tweets
        sweproject.TwitterListener tl = new sweproject.TwitterListener();
        sweproject.TwitterStreamRunner tsr = new sweproject.TwitterStreamRunner(cb, tl);
        tsr.start();
    }

    public static void writer(sweproject.Tweet tweet){
        try (FileWriter fw = new FileWriter(TWEET_DATA, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(tweet.toString().replace("\n", " ").concat("\n"));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }



    // Testing config.txt file, no input detected

//    public static void main(String[] args){
//        String consumerKey="";
//        String consumerSecret="";
//        String accessToken = "";
//        String accessSecret="";
//
//        try(FileReader reader = new FileReader("config.txt")) {
//            Properties properties = new Properties();
//            properties.load(reader);
//
//            consumerKey = properties.getProperty("consumerKey");
//            consumerSecret = properties.getProperty("consumerSecret");
//            accessToken = properties.getProperty("accessToken");
//            accessSecret = properties.getProperty("accessSecret");
//
//        } catch (Exception e) {
//            //TODO: handle exception
//        }
//
//        System.out.println(consumerKey);
//        System.out.println(consumerSecret);
//        System.out.println(accessToken);
//        System.out.println(accessSecret);
//
//    }

}