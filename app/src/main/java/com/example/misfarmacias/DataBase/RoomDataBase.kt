package com.example.misfarmacias.dataBase

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Dao
interface FarmaciaDao {
    @Query("select * from farmaciaDataTabla")
    fun getFarmacias():LiveData<List<FarmaciasDatas>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insertAll( farmacias: List<FarmaciasDatas>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert( farmacias: FarmaciasDatas)


    @Query("delete from farmaciaDataTabla")
    fun deleteAll()


    @Query("select * from farmaciaDataTabla where Nombre  like  '%'||:query||'%' ")
    fun getAllquery(query:String):LiveData<List<FarmaciasDatas>>


    @Query("select * from farmaciaDataTabla where Nombre  like  '%'||:query||'%' ")
    fun getAllqueryCursor(query:String): Cursor
}



@Database(entities = arrayOf(FarmaciasDatas::class), version =1,exportSchema = false)
abstract class FarmaciaDatabase : RoomDatabase() {

    abstract fun farmaciaDao(): FarmaciaDao

    private class FarmaciasDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
           // INSTANCE?.farmaciaDao()?.deleteAll()

            INSTANCE?.let { database ->
                scope.launch {

                    withContext(Dispatchers.IO){
                    var farmaciaDao = database.farmaciaDao()

                    // Delete all content here.
                    //farmaciaDao.insertAll()

                    // Add sample words.
                    var farmacia =FarmaciasDatas ("1","-23.144306","-64.324604","Farmacia Alvarado II","03878 42-2835","Pueyrredón 640","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("2","-23.128613","-64.316672","Farmacia San Pedro","03878 42-8524","Av. Esquiú 720","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("3","-23.142694","-64.325125","Farmacia Santa Rosa","03878 42-1838","Av. Gral. Pizarro 490","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("4","-23.134177","-64.323319","Farmácia Alvarado","03878 42-1654","Carlos Pellegrini 179","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("5","-23.131096","-64.322729","Farmacia del Huerto","03878 42-2040","Carlos Pellegrini 435","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("6","-23.128226","-64.320478","Farmacia Integral","03878 42-4054","Hipólito Yrigoyen 696","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("7","-23.133159","-64.329725","Farmacia del zenta","03878 42-2905","Rudecindo Alvarado 119","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("8","-23.127787","-64.328608","Farmacia Terminal","03878 42-8314","9 de Julio 169","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("9","-23.132718","-64.327012","IPS Oran"," ","20 de Febrero 256","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("10","-23.131099","-64.322730","Farmacia Pieve","03878 42-2040","Carlos Pellegrini 435","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("11","-23.134935","-64.325055","Farmacia Del Pueblo","03878 42-4592","López y Planes 487","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("12","-23.134860","-64.327465","Farmacia Central","03878 42-3664","Av. Vicente López y Planes 302","","")
                        farmaciaDao.insert(farmacia)


                        farmacia =FarmaciasDatas ("13","-23.133448","-64.319035","Farmacia Santa Lucía","","Calle Rivadavia 311","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("14","-23.135951","-64.325156","Farmacia Padilla","03878 42-3397","25 de Mayo 37","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("15","-23.143780","-64.325334","Farmacia El Peregrino","03878 42-1399","Av. Gral. Pizarro 576","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("16","-23.131984","-64.328645","Farmacia Botica"," ","Cnel. Egues 184","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("17","-23.143781","-64.325337","El Peregrino de Santiago","03878 42-8081","Av. Gral. Pizarro 191","","")
                        farmaciaDao.insert(farmacia)

                        farmacia =FarmaciasDatas ("18","-23.133715","-64.324847","Farmacia Del Milagro Oran","03878 42-1300","Rudecindo Alvarado 487","","")
                        farmaciaDao.insert(farmacia)













                        // farmaciaDao.insertAll(lista)
                    }

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FarmaciaDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FarmaciaDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FarmaciaDatabase::class.java,
                    "farmacia_database"
                )
                    .addCallback(FarmaciasDatabaseCallback(scope))

                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}