name := """highrung-model"""

version := "0.9.0"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.5" % "test"
)

// POM settings for Sonatype
homepage := Some(url("https://github.com/forwardloop/highrung-model"))
scmInfo := Some(ScmInfo(url("https://github.com/forwardloop/highrung-model"),
                            "git@github.com:forwardloop/highrung-model.git"))
developers := List(Developer("forwardloop",
                             "Kris",
                             "support@squashpoints.com",
                             url("https://github.com/forwardloop")))
licenses += ("GPLv3", url("https://www.gnu.org/licenses/gpl-3.0.en.html"))
publishMavenStyle := true
sonatypeProfileName := "com.github.forwardloop"
organization := "com.github.forwardloop"

// Sonatype repo settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)
