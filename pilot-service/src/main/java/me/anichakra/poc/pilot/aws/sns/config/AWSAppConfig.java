package me.anichakra.poc.pilot.aws.sns.config;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
public class AWSAppConfig {

    private static final Integer TEMPORARY_CREDENTIALS_DURATION_DEFAULT = 7200;

    @Value("${aws.temporary.credentials.validity.duration}")
    String credentialsValidityDuration;

    @Value("${aws.sns.topic.demo.ARN}")
    String snsTopicDemoARN;

    @Bean(name = "snsTopicDemoARN")
    public String snsTopcARN() {
        return this.snsTopicDemoARN;
    }


    @Bean(name = "sessionCredentials")
    public BasicSessionCredentials sessionCredentials() {

        AWSSecurityTokenServiceClient sts_client = (AWSSecurityTokenServiceClient) AWSSecurityTokenServiceClientBuilder.defaultClient();
        GetSessionTokenRequest session_token_request = new GetSessionTokenRequest();
        if (this.credentialsValidityDuration == null || this.credentialsValidityDuration.trim().equals("")) {
            session_token_request.setDurationSeconds(TEMPORARY_CREDENTIALS_DURATION_DEFAULT);
        } else {
            session_token_request.setDurationSeconds(Integer.parseInt(this.credentialsValidityDuration));
        }

        GetSessionTokenResult session_token_result = sts_client.getSessionToken(session_token_request);

        Credentials session_creds = session_token_result.getCredentials();

        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                session_creds.getAccessKeyId(),
                session_creds.getSecretAccessKey(),
                session_creds.getSessionToken());
        return sessionCredentials;
    }
}


