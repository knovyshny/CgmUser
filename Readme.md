# de.cgm.api.user

## Installation / Preconditions

## MongoDb

to work with application you'll need installed version
of MongoDb. Easiest way to do it, is to add MongoDb to
your local Docker Desktop

```cs
docker run -p 27017:27017 -d --name mongodb mongo --noauth
docker start mongodb
```

now MongoDb is running in your docker desktop, but 
before you start application, please install
[MongoDb Compass][1]

[1]: https://www.mongodb.com/try/download/compass "MongoDb Compass"

connect to running install of MongoDb

```cs
mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false
```

and create a database with name "cgm_user" and collection "user" there

in the case, that you would like to use your own MongoDb instance or 
you have defined credentials to access MongoDb, please change
spring.data.mongodb.connectionString in your application.properties

```cs
spring.data.mongodb.connectionString=mongodb://0.0.0.0:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false
```

## Maven

All required for "de.cgm.api.user" dependencies are handled by maven.
Just use IDE of your choice to build / start the application

## Java Version

Application was written and tested with Java 18 ( OpenJDK )
But because I used only some of new Java features
like 

```cs
List.of(...) and
stream()...toList()
```

it can easy downgraded to previous Java versions, if required

## REST API

Because requirement describe exact endpoints for REST API on one side

```cs
    saveUser
    removeUser(per ID)  
    getAllUsers  
    getUser(per ID)  
    authenticateUser
```
but from other side ask for RESTFull Service, I used REST conform approach
with

```cs
    GET /users
    GET /users/{Id}
    PUT /users/{Id}
    POST /users
    DELETE /users/{Id} 
    POST /users/authenticateUser
```
instead. I hope, that it is ok in this case.

HATEOAS is not supported, because of lack of requirements and because
in many cases it is just too heavy.

## Swagger UI

App "de.cgm.api.user" support swagger and swagger ui, therefore you
even don't need a Postman.

Just run the application and go to [Swagger UI][2] in your favorite browser

[2]: http://localhost:8080/swagger-ui/index.html "Swagger UI"

now enjoy working with REST API of cgm user.
Sure you can also use Postman or curl to make REST calls.

User schema a validated by REST API
There are validation rules ( copy from Swagger UI )
```cs
UserIncomingDto{
	id	string
		maxLength: 128
		minLength: 1
	login*	string
		maxLength: 50
		minLength: 10
	password*	string
		maxLength: 50
		minLength: 10
		pattern: ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$
	name	string
		maxLength: 100
		minLength: 0
	surname	string
		maxLength: 100
		minLength: 0
}
```

password should contain at least one Capital char, at least one lower case char, at least one digit and
has min length 10

You don't see intentionally the password of the user by calling

```cs
  curl -X 'GET' \
  'http://localhost:8080/users' \
  -H 'accept: application/json'
}
```

or by Id

```cs
  curl -X 'GET' \
  'http://localhost:8080/users/98980123-b5c5-4c50-95da-0b7712652188' \
  -H 'accept: application/json'
}
```

IMHO password should not be transfered in clear text at all.

## POSTMAN

For usage in POSTMAN there are a prepared collection
"User API.postman_collection.json" in Zip- File. 
There are some examples, how to use API available.

## Passwords

User passwords are saved as MD5 hash, so that also for DB Admins
it is not possible to find out, which password the user use

## Seeding

After start, application seed one User, just to have a chance from beginning
make GET calls /users and /users/{Id}

## Technical information

After trying to use springfox, that I used for working with spring boot some years
ago, I gave up, because it looks like, that latest springfox version 3.0.0 seems
don't work smoothly with spring boot 2.7. Therefore I decided not to lose a time and 
switched to [Springdoc][3], that works fine with current version of spring boot (2.7.0)

[3]: https://springdoc.org/ "Springdoc"

## Information about technical stack selection and how the app was written.

Well because a task should be written in minimal amount of time,
I just used sprint boot, because I already have an experience with
it and MongoDb, because it is a Database, that I currently use in other projects.
First I thought to peak in memory db like H2, but then decided against
it, because corresponding to requirement it should be store somewhere.
And IMHO for such REST API applications MongoDb is just a good choice.

Because I like generally to use swagger ui, I add a support for it.
If I have had some more time, I'll use [WebFlux][4] instead of normal
SpringMvc. But I read about WebFlux, I believe, that I understood, 
how it works, but until now I never wrote any code for it.

[4]: https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html "WebFlux"

With next steps, I created kind of Base layer for 
service that support CRUD operations for almost any
MongoDb Collections:

```cs
de.cgm.test.base.service.BaseCRUDService
```

and

```cs
de.cgm.test.base.controller.BaseCRUDController
```

so that to write specific code to handle Rest Calls for Users
after it was quick and easy.

## Gson

Gson is used to convert from IncomingDto to Entity
and from Entity to OutcomingDto

Gson was chosen, because of great experience with this lib
in some previous projects

But in the case of complex objects, specially
with Maps and Lists, it can happens, that you'll need to write
own serializer / deserializer for Gson

# Spring Actuator

Is used to observe different application's metrics
Call actuator in your browser after starting of application
and observe metrics and other statistics

http://localhost:8080/actuator

# TODOs

There are some TODOs, that are intentionally not finished, because
time limitation

1) Source Code coverage. Much more Unit Tests must be additionally written to meet quality gate
2) Error Messages for REST API. Because I used bean validation for POST & PUT
it is necessary additionally to Http Status Code provide validation messages too
   ( except POST /users/authenticateUser ).
Currently in the case of not valid Rest Call, you just get BAD_REQUEST ( HttpStatus 400 ) back.
3) REST API should be additionally protected with Rate Limiting
4) In some case I used deprected Method of Spring
5) Business logic should be extended to avoid saving of "duplicated" users with same login
6) Better Swagger / Swagger UI support with proper default / example values
for POST / PUT
7) As I used generics there some hints from local Sonar Lint like
   "Rename this generic name to match the regular expression '^[A-Z][0-9]?$'."
   I intentionally don't fix it, because IMHO in this case better to use clear / proper
   name like TIncomingDto instead T, in the case of multiple usage it easier to understand
   what is going on. I like Sonar Lint, but in this case am I disagree ...
   Sure it can be fixed / changed corresponding if Sonar rules be CGM.
8) I definitely forgot something ...