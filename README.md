# Spring Boot & React URL Shortener

## Backend
The backend consists of a Spring Boot program and a Redis data store running on a Docker instance. 

The Spring Boot program receives URLs from the body of POST requests made to a servlet. After receiving them, it creates a hashing of the URL using the 32-bit murmur3 algorithm, and stores the key-value pair of the hash and the URL in Redis, and returns the hashed ID in the HTTP response. 

The Spring Boot controller receives GET requests containing the shortened URL that the user previously received and, more specifically, the ID of the original URL that was hashed. It takes this ID and retrieves the original URL from Redis, and sends back the original URL in the response body. 

## Front-end
The front-end is just a create-react-app web page that handles user input. 

The landing page asks users to enter in the URL that they want shortened. 

![landing page](https://github.com/rblonski18/url-shortener/blob/master/images/simple_home_page.PNG?raw=true)

If they enter in a valid URL, a fetch request is made to the backend to store it in Redis. The response contains the ID of the stored URL, and this ID is appended onto the web page path (for now, http://localhost:3000/url/{urlID}). 

![shortened url](https://github.com/rblonski18/url-shortener/blob/master/images/shortened-url.PNG?raw=true)

If the user enters in an invalid input, they're told, "Please enter in a valid input." Inputs are validated on the back-end using the Apache commons UrlValidator class. 

The user can take the shortened URL and paste it into their browser to be redirected to the long URL. The front-end goes to a 'Redirect' React component, which just retrieves the original URL from the back-end in a UseEffect hook. 

## Deployment
Deployment involves a handful of extra steps. Since we're using Redis in Docker, we can use AWS ElasticCache to launch a Redis cluster, and then link it to an AWS EC2 instance. 

For instructions, refer to:
Creating ElasticCache Cluster: https://us-east-2.console.aws.amazon.com/elasticache/home?ad=c&cp=bn&p=etc&region=us-east-2#
Creating EC2 Instance: https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html
Connecting to EC2 Instance: https://docs.aws.amazon.com/AmazonElastiCache/latest/red-ug/GettingStarted.ConnectToCacheNode.html

After getting Redis working, make according adjustments to the application.properties file so that the backend can reach the Redis endpoints.

The backend can then be packaged as a JAR file and temporarily deployed - for now we just need the backend API endpoints. In the AWS Management Console, we'll be able to find the URL path of the backend, and we'll need to go into the front-end and change fetch requests to go to this URL path instead of localhost. After completing this, we can deploy the front-end to AWS Amplify, change the CrossOrigin headers on the backend URLShortenerController file to allow Cross-Origin requests from the front-end Amplify URL, and then re-deploy the backend. 

For instructions, refer to:
Serving backend on Elastic Beanstalk: https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/create_deploy_Java.html
Serving front-end on Amplify: https://aws.amazon.com/amplify/resources/

## Further changes
If I wasn't in finals week I would have more time to make a few changes. 

1. Security is non-existent, if usernames or passwords were appended to the URL field, they would be exposed in the HTTP request and response body throughout the app, as well as being stored in Redis as just a string with no encryption. 
2. There are no preventative measures taken to discourage using the service for criminal activity, such as malware distribution.
3. Error checking is minimal



