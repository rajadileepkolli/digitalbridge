# digitalbridge

###Exception Code

1101 - MessagingException while sending email

### Notes 
> you can reach applications after starting docker at http://localhost:9200/. Kibana should be running at http://localhost:5601 now.

> Manually creating user 
	```
	
use admin
db.createUser(
  {
    user: "root_username",
    pwd: passwordPrompt(), // or cleartext password
    roles: [ { role: "userAdminAnyDatabase", db: "admin" }, "readWriteAnyDatabase" ]
  }
)

	
rs.initiate( {
   _id : "digitalBridgeReplica",
   members: [
      { _id: 0, host: "mongo1:27017" },
      { _id: 1, host: "mongo2:27018" },
      { _id: 2, host: "mongo3:27019" }
   ]
})

```