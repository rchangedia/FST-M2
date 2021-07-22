package restAssuredProject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification; 
import static io.restassured.RestAssured.given;

public class GitHubSSHKey {
	
	// Declare request specification
		RequestSpecification requestSpec;
		// Declare SSH Key
		String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC3ZrLoIDUGbCsay6Nmm/7BdMpcscmvvMDUzpHjOJSFHX89M4pcCIkmcJN/lFSseVDBf6so4L2R+JQGhNMuWJP7RcL96d5q6aw3ArRVpA1yWwsTLKsEEUdkk1djHqz1fasJjKNmaAUmt534GigeufcOCxU1MXRoGGNws9F4IS/FSbYA7M09BYXYblecSBC+8OlIIG0OKTPFEcDPgyFXpC9eVzO5TfbhfDUR25YIAVpJp6d+3dIYcULd//AHJhY+O/0/DxfI8h9JHh5h5yYn+NJKVUwdaAK1F8ihlryimSuv7xaiX2febCQLYShaKg2Fsr5UCVYE/nvRLBj1nHsY37Z/IYqcGt/eGyTv2qjPGTGTGZwEMUqV8geLGHTqKk0WbUIpGKik5dWze11v0qGa8r03Eo7qHBTqTdDlzlZ8BQQN0miH8NOAOhw3AWnW0zaAyaFrkPGmESZz14ppFbucMUn9V+oqv4rMw34CP4xdV514J/PbvPAnl4j1qCz8BszZlNk= rchangedia@gmail.com";
		int sshKeyID;
		
  @BeforeClass
  public void setUp()
  {   // Create request specification
	 requestSpec = new RequestSpecBuilder()
	// Set content type
	.setContentType(ContentType.JSON)
	// Set Authorization Token
	.addHeader("Authorization", "token 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")
	// Set base URL
	.setBaseUri("https://api.github.com")
	// Build request specification
	.build();
	  
 }
  @Test(priority = 1)
	// Test case using a DataProvider
  public void postSSHKey()
  { 
	String reqBody = "{\"title\": \"TestAPIKey\",\"key\": \""+sshKey+"\"}";
	Response response = given().spec(requestSpec) // Use requestSpec
		     .body(reqBody) // Send request body
			 .when().post("/user/keys"); // Send POST request
	String resBody = response.getBody().asPrettyString();
	
	// Print response
	System.out.println(resBody);
	System.out.println(response.getStatusCode());
  
	// Extract status from response
	sshKeyID = response.then().extract().path("id");
	
	// Assertions
	response.then().statusCode(201);
	} 
  
  @Test(priority=2)
 //Test case using a DataProvider
  public void getSSHKey()
  {
	 Response response = given().spec(requestSpec) // Use requestSpec
              .when().get("/user/keys");  //Send Get request	 
	 String resBody = response.getBody().asPrettyString();
	// Print response
	 System.out.println(resBody);
	 System.out.println(response.getStatusCode());
	// Assertions
	 response.then().statusCode(200);
  }
  @Test(priority=3)
  
  public void deleteKey()
  {
	 Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", sshKeyID).when().delete("/user/keys/{keyId}"); // Add path parameter
	 String resBody = response.getBody().asPrettyString();
	 System.out.println(resBody);
	 System.out.println(response.getStatusCode());
	// Assertions
	 response.then().statusCode(204);
  }		 
}
