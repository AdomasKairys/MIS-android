package com.example.misandroid.database
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import com.example.misandroid.utility.Utils.Companion.calculateDistance

class RemoteDatabase(private val userDao: UserDao,
                     private  val measurementDao: MeasurementDao,
                     private  val userSignalDao: UserSignalDao) {
    private lateinit var connection: Connection
    init{
        val jdbcUrl = "jdbc:mariadb://seklys.ila.lt/LDB"

        connection = DriverManager.getConnection(jdbcUrl, "stud", "vLXCDmSG6EpEnhXX")

        try {
            val m = loadMeasurements()
            loadUsers(m)
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
    private fun loadUsers(measurements: List<MeasurementEntity>){
        val query = connection.prepareStatement("""
            SELECT row_number() over (order by `vart`.mac) AS `id`, `vart`.mac, 
            GROUP_CONCAT(`vart`.`stiprumas` ORDER BY `vart`.`sensorius`ASC) AS `stiprumai`, 
            GROUP_CONCAT(`vart`.`sensorius` ORDER BY `vart`.`sensorius`ASC) AS `sensoriai` 
            FROM (SELECT * FROM `vartotojai`) AS `vart` GROUP BY `vart`.mac""".trimIndent())
        val result = query.executeQuery()
        while(result.next()) {
            val user = UserEntity(
                result.getString("mac"),
            )
            userDao.insertUser(user);
            val userSignal = UserSignalEntity(
                result.getInt("id"),
                result.getString("mac"),
                result.getString("stiprumai"),
                calculateDistance(result.getString("stiprumai"),measurements)
            )
            userSignalDao.insertUserSignal(userSignal);
        }
    }
    private fun loadMeasurements() : ArrayList<MeasurementEntity>{
        val query = connection.prepareStatement("""
            SELECT `stip`.`matavimas`,CONCAT(`stip`.`x`,",",`stip`.`y`) AS `koordinates`, GROUP_CONCAT(`stip`.`stiprumas` ORDER BY `stip`.`sensorius` ASC) as `stiprumai`, GROUP_CONCAT(`stip`.`sensorius` ORDER BY `stip`.`sensorius` ASC) as `sensoriai`
            FROM (
            SELECT `stiprumai`.`matavimas`,`matavimai`.`x`,`matavimai`.`y`,`stiprumai`.`stiprumas`,`stiprumai`.`sensorius`
            FROM `stiprumai`
            JOIN `matavimai` 
            ON `stiprumai`.`matavimas`=`matavimai`.`matavimas`) AS `stip`
            GROUP BY `stip`.`matavimas`
        """.trimIndent())
        val result = query.executeQuery()
        var list: ArrayList<MeasurementEntity> = ArrayList()
        while(result.next()) {
            val measurement = MeasurementEntity(
                result.getInt("matavimas"),
                result.getString("koordinates"),
                result.getString("stiprumai"),
                result.getString("sensoriai")
            )
            measurementDao.insertMeasurement(measurement);
            list.add(measurement)
        }
        return list
    }
}