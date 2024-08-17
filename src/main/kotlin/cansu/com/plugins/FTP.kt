package cansu.com.plugins

import io.ktor.server.config.*
import org.apache.ftpserver.DataConnectionConfigurationFactory
import org.apache.ftpserver.FtpServer
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.listener.ListenerFactory
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor
import org.apache.ftpserver.usermanager.impl.BaseUser
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission
import org.apache.ftpserver.usermanager.impl.TransferRatePermission
import org.apache.ftpserver.usermanager.impl.WritePermission
import java.io.File
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Path

fun getFTPServer(serverConfig: ApplicationConfig): FtpServer {
    val serverFactory = FtpServerFactory()
    val listenerFactory = ListenerFactory().apply {
        port = 2221
        isImplicitSsl = false
        dataConnectionConfiguration = DataConnectionConfigurationFactory().apply {
            isActiveEnabled = false
            passivePorts = serverConfig.property("ftp.port.passive.range").getString()
        }.createDataConnectionConfiguration()
    }
    serverFactory.addListener("default", listenerFactory.createListener())
    val userPropFile = File("user.properties")
    userPropFile.createNewFile()
    val userManagerFactory = PropertiesUserManagerFactory().apply {
        file = userPropFile
        passwordEncryptor = SaltedPasswordEncryptor()
    }
    val userManager = userManagerFactory.createUserManager()
    userManager.save(BaseUser().admin(serverConfig))
    serverFactory.userManager = userManager
    return serverFactory.createServer()
}

fun BaseUser.admin(cfg: ApplicationConfig): BaseUser {
    val dir = cfg.property("ftp.admin.homeDirectory").getString()
    try {
        Files.createDirectories(Path.of(dir))
    } catch (_: FileAlreadyExistsException) {
        // do nothing
    }
    return apply {
        authorities = listOf(
            WritePermission(),
            ConcurrentLoginPermission(1, 1),
            TransferRatePermission(Integer.MAX_VALUE, Integer.MAX_VALUE)
        )
        name = cfg.property("ftp.admin.user").getString()
        password = cfg.property("ftp.admin.password").getString()
        homeDirectory = dir
    }
}