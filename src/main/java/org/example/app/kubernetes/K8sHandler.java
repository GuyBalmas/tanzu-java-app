package org.example.app.kubernetes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

// import io.kubernetes.client.openapi.ApiClient;
// import io.kubernetes.client.openapi.Configuration;
// import io.kubernetes.client.openapi.apis.CoreV1Api;
// import io.kubernetes.client.openapi.models.V1LabelSelector;
// import io.kubernetes.client.openapi.models.V1Pod;
// import io.kubernetes.client.openapi.models.V1PodList;
// import io.kubernetes.client.util.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;

public abstract class K8sHandler {
    
    final static Logger logger = LoggerFactory.getLogger(K8sHandler.class);

    private static String podName = null;
	
	private static String namespace = null;

    public static String getPodName(){
        if (podName == null){
            populatePodName();
            logger.info("Pod Name read: [" + podName + "]");
        }
        return podName;
    }


    public static String getNamespace(){
        if (namespace == null){
            populateNamespace();
            logger.info("Namespace read: [" + namespace + "]");
        }
        return namespace;
    }

    public static Map<String,String> getEnvVars(){
        return k8sEnvVars;
    }


	/**
	 * 	Get all environment variables prefixed with "KUBERNETES_"
 	 */
	private static Map<String, String> k8sEnvVars = System.getenv()
			.entrySet()
			.stream()
			.filter(entry -> entry.getKey().startsWith("KUBERNETES_"))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); 
    
    
    private static void populatePodName() {
        podName = System.getenv("HOSTNAME");
    }

    private static void populateNamespace() {
        Path filePath = Paths.get("/var/run/secrets/kubernetes.io/serviceaccount/namespace");
        String fileContents = "";
        try {
            fileContents = Files.readString(filePath);
            logger.info("Namespace: [" + fileContents + "]");
        } catch (IOException e) {
            logger.warn("Failed to read namespace");
        }
        namespace = fileContents;
    }

    
    
}
