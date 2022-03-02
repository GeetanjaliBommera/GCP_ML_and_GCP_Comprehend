import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.File;

public class CreateBucket {

    public static void main(String[] args) {
        String access_key_id = "ASIAUVORRQZ55CBMV3HJ";
        String secret_key_id =  "rQrJP0wZzteEjDBN2e+ma8sAKN454T894tGYOpSX";
        String session_token =  "FwoGZXIvYXdzENf//////////wEaDNQ+2tMUJe3qlwCosSK/AeplZqC6ovl85EMfgEGwuHHiaCGo9zLESV69Fj4t3oRd4Nu5HBmJRabqJo5q6vLCl2AEp1D1HDb9X1xGE0Qfe1zXpaxc5OPV0rZ7fAK6/+wBq8zj6VkvlRqQ2vtHhEwM23oWpAkGxQFFYgam6GfjqQBNNoF7zkqAYg1C//c6SCcaGG1IAAiqDePmP8uzFTBaNevnI/dnGblHNqePd+pJXPkiiXEGbhZLMny4avPF2EsZUoB/TL/tYrfj+PF5WvfuKKihy4wGMi0eJP1iEsxLY1/MdDzPwwHZvju7YT5aHohMl2ULiZlYYFpsKZoez4DY1X2Q+9U=";
        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                access_key_id, secret_key_id,session_token);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                .build();
        takeCredentials(s3);
        uploadFile(s3);
    }

    private static void uploadFile(AmazonS3 s3) {
        try {
            String bucketname = "twitterdatab00881511";
            File dir = new File("C:\\Users\\AVuser\\OneDrive\\Documents\\Serverless\\ServerlessAssignment4\\PartC\\src\\main\\resources\\file_mongo_tweets.txt");
                if(dir.isFile()){
                    PutObjectRequest request = new PutObjectRequest(bucketname,dir.getName(),dir);
                    s3.putObject(request);
            }
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    private static void takeCredentials(AmazonS3 s3) {
        String bucket_name = "twitterdatab00881511";
        if (s3.doesBucketExistV2(bucket_name)) {
            System.out.format("Bucket %s already exists.\n", bucket_name);
        } else {
            try {
                s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
                e.printStackTrace();
            }
        }

    }

}
