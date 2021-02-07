**WebCrawler service hardcoded to wiprodigital.com domain.**

A spring boot app with a command line runner executes a Crawl service for the domain and crawls through all pages using anchor hyperlinks.

Service is build with following tools/libraries  
Spring boot  
OkHttp  
Nekohtml  
Lombok  
Logback

To run the application use the following command,  
./gradlew bootRun  

this will run the application and generates the links crawled. Output is displayed on the console and is written to a file "crawlResult.txt"  

**Certificates for the required domains where generated using the following commands.**

echo -n | openssl s_client -connect wiprodigital.com:443 -servername wiprodigital.com -showcerts | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > wipro.cert  

echo -n | openssl s_client -connect digistories.wiprodigital.com:443 -servername digistories.wiprodigital.com -showcerts | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' >> wipro.cert  

echo -n | openssl s_client -connect ehealth.wiprodigital.com:443 -servername ehealth.wiprodigital.com -showcerts | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' >> wipro.cert  

<!--keytool -import -v -file wipro.cert -keystore wipro.jks -storepass buildit -->