# API JAVAMAIL to OCI - 2024
## Made by Lobo Dourado

Api that uses javamail to send email  through OCI email delivery service.

See the result in the screenshot in root of project.
You can use the collection in root of project.

## Stack
* Spring boot
* javamail
* OCI - email delivery

# Requirements
* You need an OCI account and initialize the email delivery service, 
after that you can take the credentials and put in the application.properties (See reference documentation).

### Reference Documentation
* [javaMail integration with OCI](https://docs.oracle.com/pt-br/iaas/Content/Email/Reference/javamail.htm#Integrating_JavaMail_with_Email_Delivery)

## Observations
* Oracle's documentation seems to be out of date, as well as the Javamail lib, so it was necessary to adapt and look for similar resources.