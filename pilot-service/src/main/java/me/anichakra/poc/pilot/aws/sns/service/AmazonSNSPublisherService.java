package me.anichakra.poc.pilot.aws.sns.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmazonSNSPublisherService implements PublisherService {

    private AmazonSNS amazonSNS;
    
  
    private String snsTopicDemoARN;

    @Autowired
    public AmazonSNSPublisherService(BasicSessionCredentials sessionCredentials, String snsTopicDemoARN) {

        this.amazonSNS = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(new AWSStaticCredentialsProvider(sessionCredentials)).build();
        this.snsTopicDemoARN = snsTopicDemoARN;


    }

    @Override
    public void publish(String subject, String body) throws Exception {
        PublishRequest publishRequest = new PublishRequest(this.snsTopicDemoARN, body, subject);
        PublishResult publishResult = this.amazonSNS.publish(publishRequest);

        System.out.println("MessageId - " + publishResult.getMessageId());

    }


}
