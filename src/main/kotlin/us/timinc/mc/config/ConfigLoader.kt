package us.timinc.mc.config

import kotlin.reflect.KClass
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

object ConfigLoader {

    inline fun <reified T : Any> loadConfig(modId: String, configClass: KClass<T>): T {
        val gson = GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .setLenient()
            .create()

        val configDir = File("config")
        val configFile = File(configDir, "$modId.json")
        configDir.mkdirs()

        var config: T = createInstance(configClass)

        if (configFile.exists()) {
            try {
                FileReader(configFile).use { fileReader ->
                    config = gson.fromJson(fileReader, T::class.java)
                }
            } catch (e: Exception) {
                println("Error reading config file: ${e.message}")
            }
        }

        val pw = PrintWriter(configFile)
        gson.toJson(config, pw)
        pw.close()

        return config
    }

    fun <T : Any> createInstance(kClass: KClass<T>): T {
        // Handle instantiation logic here. For simplicity, we'll just call the primary constructor assuming it has no parameters.
        // This part may need to be expanded based on the constructors of the classes you plan to use.
        val noArgConstructor = kClass.constructors.find { it.parameters.isEmpty() }
            ?: throw IllegalArgumentException("Class must have a no-argument constructor")
        return noArgConstructor.call()
    }
}
