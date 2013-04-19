import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "lemonade2"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "commons-lang" %  "commons-lang" %  "2.5",
    "com.feth" %% "play-easymail" % "0.2-SNAPSHOT",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)     
  )

}
