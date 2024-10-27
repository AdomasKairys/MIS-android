package com.example.misandroid.database
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class RemoteDatabase(private val userDao: UserDao,
                     private  val measurementDao: MeasurementDao,
                     private val strengthDao: StrengthDao,
                     private  val userSignalDao: UserSignalDao) {
    private lateinit var connection: Connection
    init{
        val jdbcUrl = "jdbc:mariadb://seklys.ila.lt/LDB"

        connection = DriverManager.getConnection(jdbcUrl, "stud", "vLXCDmSG6EpEnhXX")

        try {
            loadUsers()
            loadMeasurements()
            loadStrengths()
        }
        catch (e: SQLException) {
            //handle exception here
        }
        finally {
            try {
                connection.close()
            } catch (e: SQLException) { /* Ignored */ }
        }

    }
    private fun loadUsers(){
        val query = connection.prepareStatement("""
            SELECT `vart`.mac, GROUP_CONCAT(`vart`.`stiprumas`) AS `stiprumai`, GROUP_CONCAT(`vart`.`sensorius`) AS `sensoriai` 
            FROM (SELECT * FROM `vartotojai` ORDER BY `vartotojai`.`sensorius`ASC) AS `vart` GROUP BY `vart`.mac""".trimIndent())
        val result = query.executeQuery()
        while(result.next()) {
            val user = UserEntity(
                result.getString("mac"),
            )
            userDao.insertUser(user);
            val userSignal = UserSignalEntity(
                0,
                result.getString("mac"),
                result.getString("stiprumai"),
                result.getString("sensoriai")
            )
            userSignalDao.insertUserSignal(userSignal);
        }
    }
    private fun loadMeasurements(){
        val query = connection.prepareStatement("SELECT * FROM `matavimai`")
        val result = query.executeQuery()
        while(result.next()) {
            val measurement = MeasurementEntity(
                result.getInt("matavimas"),
                result.getInt("x"),
                result.getInt("y"),
                result.getDouble("atstumas")
            )
            measurementDao.insertMeasurement(measurement);
        }
    }
    private fun loadStrengths(){
        val query = connection.prepareStatement("SELECT * FROM `stiprumai`")
        val result = query.executeQuery()
        while(result.next()) {
            val strength = StrengthEntity(
                result.getInt("id"),
                result.getInt("matavimas"),
                result.getString("sensorius"),
                result.getInt("stiprumas")
            )
            strengthDao.insertStrength(strength);
        }

    }
}