# Spring security
* /login - Generate JSON Web Token. Allow the request without any authN.
Use the default **InMemoryUserDetailsManager** to check user-pwd entered against in-memory map.

* Other requests like /hello - Validate and use **JWT token** generated in previous step for Authorization. 
Set up **custom filter** to add this step to authN filter chain.


