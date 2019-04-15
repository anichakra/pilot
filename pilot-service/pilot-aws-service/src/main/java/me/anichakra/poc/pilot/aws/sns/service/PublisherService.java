package me.anichakra.poc.pilot.aws.sns.service;

public interface PublisherService {
    //
    // Name of the topic
    //
 //   public static final String AWS_SNS_DEMO = "YOUR_AWS_SNS_TOPIC_ARN";

    //
    // Publish Message API
    //
    void publish(String subject, String body) throws Exception;

}
