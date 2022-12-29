package org.example.app.controllers;

import java.util.Map;

import org.example.app.html.HtmlTemplate;
import org.example.app.kubernetes.K8sHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KubernetesController {
    
    final static Logger logger = LoggerFactory.getLogger(KubernetesController.class);

    
	private String podName = K8sHandler.getPodName();

	private String namespace = K8sHandler.getNamespace();

    private Map<String,String> envVars = K8sHandler.getEnvVars();

    @RequestMapping("/k8s")
	public String k8s_env(){
		return HtmlTemplate.htmlLandingPage(
                "Pod Name: " + podName,
                "Namespace: " + namespace,
				"Kubernetes Environment Variables: " + envVars.toString()
		);
	}

    @RequestMapping("/k8s-pod")
	public String podInfo(){
        podName = K8sHandler.getPodName();
        namespace = K8sHandler.getNamespace();
		return HtmlTemplate.htmlLandingPage(
				"Pod Name: " + podName,
				"Namespace: " + namespace
		);
	}

    @RequestMapping("/test")
	public String test(){
        podName = K8sHandler.getPodName();
        namespace = K8sHandler.getNamespace();
        return HtmlTemplate.htmlLandingPage(
				"Pod Name: " + podName,
				"Namespace: " + namespace
		);
	}
	// /**
	//  * 	Get all environment variables prefixed with "KUBERNETES_"
 	//  */
	// private Map<String, String> k8sEnvVars = System.getenv()
	// 		.entrySet()
	// 		.stream()
	// 		.filter(entry -> entry.getKey().startsWith("KUBERNETES_"))
	// 		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); 
    
    
    // private String populateNamespace(){

    //     String ns = "unknown";
    //     try { 
    //         // Set up the Kubernetes API client
    //         ApiClient client = Config.defaultClient();
    //         Configuration.setDefaultApiClient(client);
    //         CoreV1Api api = new CoreV1Api();

    //         // Get the namespace of the pod
    //         V1Pod pod = api.readNamespacedPod(podName,"","true");
    //         ns = pod.getMetadata().getNamespace();
    //     }
    //     catch (Exception e) {
    //         logger.warn("Failed to read pod's namespace");
    //     }
    //     return ns;
    // }
}
