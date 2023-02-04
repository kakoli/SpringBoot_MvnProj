# Spring security
* /login - Generate JSON Web Token. Allow the request without any authN.
Set up **custom user service** to check user-pwd entered against in-memory map.

* Other requests like /hello - Validate and use JWT generated in previous step for Authorization. 
Set up **custom filter** to add this step to authN filter chain.


