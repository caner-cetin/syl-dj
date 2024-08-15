package cansu.com.plugins

import org.apache.ftpserver.FtpServer
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.listener.ListenerFactory
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory
import java.io.File

fun GetFTPServer(): FtpServer {
    val serverFactory = FtpServerFactory()
    val listenerFactory = ListenerFactory().apply {
        port = 2221
        isImplicitSsl = false
    }
    serverFactory.addListener("default", listenerFactory.createListener())
    val userManagerFactory = PropertiesUserManagerFactory().apply {
        file = File("users.properties")
    }
    serverFactory.userManager = userManagerFactory.createUserManager()
    return serverFactory.createServer()
}