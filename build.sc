import mill._
import mill.scalalib._

object Versions {
  val scala2 = "2.13.7"
  val scala3 = "3.1.0"
}

object main extends ScalaModule {
  def scalaVersion = Versions.scala2

  def scalacOptions =
    Seq(
      "-Wunused",
      "-Yrangepos", // For SemanticDB
      "-Ytasty-reader" // this is needed to call scala 3 libraries
    )

  // Add semanticDB for mill-bsp
  def scalacPluginIvyDeps = Agg(ivy"org.scalameta:::semanticdb-scalac:4.4.30")

  def moduleDeps = Seq(scalathree)


  def compileIvyDeps = T {
    Agg(
      ivy"org.scala-lang:scala3-library_3:${scalathree.scalaVersion()}"  // this is needed by TASTy to read scala 3 code
    )
  }


  object scalathree extends ScalaModule {
  def scalaVersion = Versions.scala3

    def moduleDeps = Seq(specifications)
  }
}

object specifications extends ScalaModule {
  def scalaVersion = Versions.scala2
}
