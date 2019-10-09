To get a Git project into your build:

[![](https://jitpack.io/v/fengxiaocan/BaseUtils.svg)](https://jitpack.io/#fengxiaocan/BaseUtils)

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.fengxiaocan:BaseUtils:v1.0.0'
	}
Share this release:
