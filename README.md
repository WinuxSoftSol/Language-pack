"# Language-pack" 

[![](https://jitpack.io/v/WinuxSoftSol/Language-pack.svg)](https://jitpack.io/#WinuxSoftSol/Language-pack)


To get a Git project into your build:

Gradle :<br>
Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	
Step 2. Add the dependency

	
	dependencies {
	         implementation 'com.github.WinuxSoftSol:Language-pack:V1.0'
	}
	

Step 3. Add the below line in your launcher activity's onCreate() method

	
	LanguagePack.init()
                .setAuth(this, "Account_id", "app_id")
                .setEnvironment(LanguagePack.ENVIRONMENT.PRODUCTION)
                .setMode(LanguagePack.MODE_ONLINE)
                .setUpdate(LanguagePack.UPDATE_INTERVAL.UPDATE_INTERVAL_2_HOUR)
                .build();

Step 4. For every textview setText() from language pack.


	laguage_text.setText(LanguagePack.get().getLocaleLanguage("some text"));

if you want change locale manually

	LanguagePack.get().setCurrentLocale(alllocales.get(position));
 

To get all locale

	List<String> allLocales = LanguagePack.get().getAllLocales();
 






Maven :<br>
Step 1. Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.WinuxSoftSol</groupId>
	    <artifactId>Language-pack</artifactId>
	    <version>V1.0</version>
	</dependency>












Sbt :<br>
Step 1. Add it in your build.sbt at the end of resolvers:

 
    resolvers += "jitpack" at "https://jitpack.io"
        
    
Step 2. Add the dependency

	
	libraryDependencies += "com.github.WinuxSoftSol" % "Language-pack" % "V1.0"	
