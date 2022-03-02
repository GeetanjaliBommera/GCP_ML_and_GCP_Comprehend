import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GcpUploadFiles {
    public static void main(String[] args) throws IOException {
//        method to create bucket
        createbucket();
//      Upload files in the bucket
        uploadfiles();
    }

    private static void uploadfiles() throws IOException {
//        References for upload files.
        // https://googleapis.dev/java/google-cloud-clients/latest/com/google/cloud/storage/StorageClass.html

        // http://g.co/cloud/storage/docs/bucket-locations#location-mr
        String projectId = "assignment4-332317";
        String bucketName = "sourcedatab00881511_test";
        String objectName = "train_data";
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(new
                        FileInputStream("C:\\Users\\geeta\\OneDrive\\Documents\\Serverless\\ServerlessAssignment4\\PartB\\src\\main\\java\\assignment4-332317-c348f6b12aac.json"))).build();
        Storage storage = storageOptions.getService();


        String location = "US";

        String path = "C:\\Users\\geeta\\OneDrive\\Documents\\Serverless\\ServerlessAssignment4\\PartB\\src\\main\\resources\\Dataset\\Dataset\\Test";
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            objectName = child.getName();
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(Paths.get(String.valueOf(child))));
        }
        System.out.println(
                "Files " + " uploaded to bucket " + bucketName);
    }

    private static void createbucket() throws IOException {
//        References to create bucket
        //https://cloud.google.com/appengine/docs/standard/java11/specifying-dependencies
        //https://cloud.google.com/storage/docs/creating-buckets#storage-create-bucket-code_samples
        String projectId = "assignment4-332317";
        String bucketName = "sourcedatab00881511";
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(new
                        FileInputStream("C:\\Users\\geeta\\OneDrive\\Documents\\Serverless\\ServerlessAssignment4\\PartB\\src\\main\\java\\assignment4-332317-c348f6b12aac.json"))).build();
        Storage storage = storageOptions.getService();

        // https://googleapis.dev/java/google-cloud-clients/latest/com/google/cloud/storage/StorageClass.html
        StorageClass storageClass = StorageClass.COLDLINE;

        // http://g.co/cloud/storage/docs/bucket-locations#location-mr
        String location = "US";

        Bucket bucket =
                storage.create(
                        BucketInfo.newBuilder(bucketName)
                                .setStorageClass(storageClass)
                                .build());

        System.out.println(
                "Created bucket "
                        + bucket.getName()
                        + " in "
                        + bucket.getLocation()
                        + " with storage class "
                        + bucket.getStorageClass());

    }
}

