package com.hammak.dbproject

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hammak.dbproject.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var scrollView: ScrollView

    lateinit var db: AppDatabase
    lateinit var shipDao: ShipDao
    lateinit var classDao: ClassDao
    lateinit var hullDao: HullDao
    lateinit var cargoDao: CargoDao
    lateinit var hullCargoSupportDao: HullCargoSupportDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        scrollView = findViewById(R.id.scrollView)

        db = AppDatabase.getDatabase(applicationContext)

        shipDao = db.shipDao()
        classDao = db.classDao()
        hullDao = db.hullDao()
        cargoDao = db.cargoDao()
        hullCargoSupportDao = db.hullCargoSupportDao()


        lifecycleScope.launch(Dispatchers.IO) {
            shipDao.deleteAll()
            classDao.deleteAll()
            hullDao.deleteAll()
            cargoDao.deleteAll()
            hullCargoSupportDao.deleteAll()

            val class_ids =
                classDao.insertClasses(
                    Class(0, "Worker", 400, 150),
                    Class(0, "Hornet", 900, 30),
                    Class(0, "SpeedStar", 1500, 10)
                )
            val hull_ids = hullDao.insertHulls(
                Hull(0, "Qube"),
                Hull(0, "LightMoon"),
                Hull(0, "Dart"),
                Hull(0, "Mountain")
            )
            val cargo_ids = cargoDao.insertCargo(
                Cargo(0, "Food"),
                Cargo(0, "Fuel"),
                Cargo(0, "Steel")
            )
            hullCargoSupportDao.insertHullCargoSupport(
                HullCargoSupport(
                    hull_ids[0].toInt(),
                    cargo_ids[0].toInt()
                ),
                HullCargoSupport(
                    hull_ids[0].toInt(),
                    cargo_ids[1].toInt()
                ),
                HullCargoSupport(
                    hull_ids[0].toInt(),
                    cargo_ids[2].toInt()
                ),
                HullCargoSupport(
                    hull_ids[1].toInt(),
                    cargo_ids[0].toInt()
                ),
                HullCargoSupport(
                    hull_ids[1].toInt(),
                    cargo_ids[1].toInt()
                ),
                HullCargoSupport(
                    hull_ids[2].toInt(),
                    cargo_ids[1].toInt()
                ),
                HullCargoSupport(
                    hull_ids[3].toInt(),
                    cargo_ids[0].toInt()
                ),
                HullCargoSupport(
                    hull_ids[3].toInt(),
                    cargo_ids[1].toInt()
                ),
                HullCargoSupport(
                    hull_ids[3].toInt(),
                    cargo_ids[2].toInt()
                ),
            )

            val ships = ArrayList<Ship>()

            val ran = Random(300)

            for (i in hull_ids.indices) {
                for (j in class_ids.indices) {
                    ships.add(
                        Ship(
                            0,
                            "URITE " + hullDao.getHull(hull_ids[i].toInt()).name +
                                    " " + classDao.getClass(class_ids[j].toInt()).name,
                            ran.nextInt(500, 1500),
                            class_ids[j].toInt(),
                            hull_ids[i].toInt()
                        )
                    )
                }
            }

            shipDao.insertShipsList(ships)

            printClassTable()
            printHullTable()
            printCargoTable()
            printHullCargoSupportTable()
            printShipTable()
            printShipFromPriceRange(0, 1000)
        }
    }

    fun printClassTable() {
        val classes = classDao.getAllClasses()

        var string = "-------------[Class Table]-------------\n\n"
        if (classes.lastIndex == -1) {
            updateText("No values\n\n")
        } else {

            classes.forEach {
                string += "[Id]:" + it.id + "\n[Name]:" + it.name + "\n[Speed]:" + it.speed + "\n[Volume]:" + it.volume + "\n\n"
            }
            updateText(string)
        }
    }

    fun printHullTable() {
        val hulls = hullDao.getAllHulls()

        var string = "-------------[Hull Table]-------------\n\n"
        if (hulls.lastIndex == -1) {
            updateText("No values\n\n")
        } else {

            hulls.forEach {
                string += "[Id]:" + it.id + "\n[Name]:" + it.name + "\n\n"
            }
            updateText(string)
        }
    }

    fun printCargoTable() {
        val cargo = cargoDao.getAllCargo()

        var string = "-------------[Cargo Table]-------------\n\n"
        if (cargo.lastIndex == -1) {
            updateText("No values\n\n")
        } else {

            cargo.forEach {
                string += "[Id]:" + it.id + "\n[Name]:" + it.name + "\n\n"
            }
            updateText(string)
        }
    }

    fun printHullCargoSupportTable() {
        val hullCargoSupport = hullCargoSupportDao.getAllHullCargoSupport()

        var string = "-------------[Hull-Cargo Support Table]-------------\n\n"
        if (hullCargoSupport.lastIndex == -1) {
            updateText("No values\n\n")
        } else {

            hullCargoSupport.forEach {
                string += "[Hull id]:" + it.hullId + "\n[Cargo id]:" + it.cargoId + "\n\n"
            }
            updateText(string)
        }
    }

    fun printShipTable() {
        val ships = shipDao.getAllShips()

        var string = "-------------[Ship Table]-------------\n\n"
        if (ships.lastIndex == -1) {
            updateText("No values\n\n")
        } else {

            ships.forEach {
                string += "[Id]:" + it.id + "\n[Name]:" + it.name + "\n[Price]:" + it.price + "\n[Speed]:" + classDao.getClass(
                    it.classId
                ).speed + "\n[Volume]:" + classDao.getClass(it.classId).volume + "\n[Supported cargo types]:"

                val supportedCargo = hullCargoSupportDao.getCargoForHull(it.hullId)
                supportedCargo.forEach {
                    string += it.name + " "
                }

                string += "\n\n"
            }
            updateText(string)
        }
    }

    fun printShipFromPriceRange(prices: Int, pricee: Int) {
        val ships = shipDao.getShipsInPriceRange(prices, pricee)

        var string =
            "-------------[Ships with price in range of " + prices.toString() + " to " + pricee.toString() + "]-------------\n\n"
        if (ships.lastIndex == -1) {
            updateText("No values\n\n")
        } else {

            ships.forEach {
                string += "[Id]:" + it.id + "\n[Name]:" + it.name + "\n[Price]:" + it.price + "\n[Speed]:" + classDao.getClass(
                    it.classId
                ).speed + "\n[Volume]:" + classDao.getClass(it.classId).volume + "\n[Supported cargo types]:"

                val supportedCargo = hullCargoSupportDao.getCargoForHull(it.hullId)
                supportedCargo.forEach {
                    string += it.name + " "
                }

                string += "\n\n"
            }
            updateText(string)
        }
    }

    fun updateText(string: String) {
        runOnUiThread {
            var temp = textView.text.toString()
            temp += string
            textView.text = temp
            scrollView.scrollToBottom()
        }
    }

    fun ScrollView.scrollToBottom() {
        val lastChild = getChildAt(childCount - 1)
        val bottom = lastChild.bottom + paddingBottom
        val delta = bottom - (scrollY + height)
        smoothScrollBy(0, delta)
    }
}